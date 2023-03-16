package org.me._410.gptsearch;

import org.me._410.gptsearch.components.QueryRecordsPane;

import java.util.ArrayList;

public class Search {

    private String queryInstructionStatement = "Consider the following question, and extract about 3 search queries for Google: \"<QUERY>\" Wrap each keywords by <SEARCH> and </SEARCH>. (Example: <SEARCH>keyword1</SEARCH>)";
    private String shrinkInstructionStatement = "<CONTEXT>\n\nConsider the given article above. Summarize the given articles to the most important parts according to this question: \"<QUERY>\" Then, wrap them by <SHRINK> and </SHRINK>. (Example: <SHRINK>shrunken article</SHRINK>) If content is not related or incomprehensible, just write: <SHRINK></SHRINK>";
    private String answerDeriveStatement = "<CONTEXT> \n Now, derive an answer to the question using the given merged articles provided.\nQuestion: \"<QUERY>\"";

    private String queryStatements = "";
    private String question = "";
    private ArrayList<String> searchKeywords = new ArrayList<>();
    private ArrayList<String> searchUrls = new ArrayList<>();
    private ArrayList<String> crawlResults = new ArrayList<>();
    private String parsedData;
    private String answer;

    public Search() {}

    public void setQueryStatements(String queryStatements) {
        this.queryStatements = queryInstructionStatement.replace("<QUERY>", queryStatements);
        this.question = queryStatements;
    }

    public void addSearchKeyword(String keyword) {
        searchKeywords.add(keyword);
    }

    public void addSearchUrls(String keyword) {
        searchUrls.add(keyword);
    }

    public void addCrawlResult(String crawlResult) {
        crawlResults.add(crawlResult);
    }

    public void extractKeywords(QueryRecordsPane log, String token, String model) {
        System.out.println("Extracting keywords...");
        String gpt3Response = RunRequest.gpt3(token, model, queryStatements);
        ArrayList<String> keywords = new ArrayList<>();
        String temp = "";

        while (gpt3Response.contains("<SEARCH>")) {
            temp = gpt3Response.substring(gpt3Response.indexOf("<SEARCH>") + "<SEARCH>".length());
            temp = temp.substring(0, temp.indexOf("</SEARCH>"));
            keywords.add(temp);
            gpt3Response = gpt3Response.substring(gpt3Response.indexOf("</SEARCH>") + "</SEARCH>".length());
        }

        for (String keyword : keywords) {
            keyword = Unescaper.unescape(keyword);
            addSearchKeyword(keyword);
            System.out.println("Query: " + keyword);
            log.appendText("Query: " + keyword);
        }
    }

    public void searchGoogle(QueryRecordsPane log, String apikey, String cx) {
        System.out.println("Obtaining related search queried URLs from Google...");
        for(String keyword : searchKeywords) {
            String urlList = RunRequest.getUrlsFromGoogle(apikey, cx, keyword);
            String[] list = urlList.split(",");
            for (String s : list) {
                System.out.println("URL: " + s.trim());
                addSearchUrls(s.trim());
            }

            // Remove redundant URLs
            for (int i = 0; i < searchUrls.size(); i++) {
                for (int j = i + 1; j < searchUrls.size(); j++) {
                    if (searchUrls.get(i).equals(searchUrls.get(j))) {
                        System.out.println("Removing redundant URL: " + searchUrls.get(j));
                        searchUrls.remove(j);
                        j--;
                    }
                }
            }


        }

        // TODO Remove this droppings
        for (int i = 0; i < searchUrls.size(); i++) {
//            if (i > 4) {
//                System.out.println("TEST STG: Removing URL: " + searchUrls.get(i));
//                searchUrls.remove(i);
//                i--;
//            }
            if (searchUrls.get(i).contains("https://www.youtube.com")) {
                System.out.println("Removing YouTube URL: " + searchUrls.get(i));
                searchUrls.remove(i);
                i--;
            }
        }
    }

    public boolean crawl() {
        String crawlResult = RunRequest.crawl(searchUrls);
        if (crawlResult.contains("<<<<END_OF_DATA>>>>")) {
            System.out.println("Crawling successful!");
        } else {
            System.out.println("Crawling failed!");
            return false;
        }
        String[] list = crawlResult.split("<<<<END_OF_DATA>>>>");
        System.out.println("# of Crawled data: " + list.length);
        for (String s : list) {
            addCrawlResult(s.trim());
        }
        return true;
    }

    public void parse(int length) {
        for (int i = 0; i < crawlResults.size(); i++) {
//            crawlResults.set(i, Jsoup.parse(crawlResults.get(i)).text());
            crawlResults.set(i, Unescaper.unescape(crawlResults.get(i)));
            System.out.println("Unescaped article " + i);
//            if (crawlResults.get(i).length() > length) {
//                System.out.println("Trimming article " + i + " to " + length + " characters...");
//                crawlResults.set(i, crawlResults.get(i).substring(0, length));
//            }
        }
        System.out.println("# of Parsed data: " + crawlResults.size());
    }

    public void shrink(String token, String model) {
        parsedData = "";
        StringBuilder sb = new StringBuilder();
        int i = 0;
        for (String crawlResult : crawlResults) {
            i++;
            System.out.println("Shrinking article " + i + "...");
            String shrunken = RunRequest.gpt3(token, model, shrinkInstructionStatement.replace("<CONTEXT>", crawlResult).replace("<QUERY>", question));
            if (!shrunken.contains("<SHRINK>") || !shrunken.contains("</SHRINK>")) continue;
            sb.append(shrunken.split("<SHRINK>")[1].split("</SHRINK>")[0]);
//            sb.append(crawlResult);
        }
        parsedData = sb.toString();
    }

    public void summarize(String token, String model) {
        System.out.println("Answering question...");
        answer = RunRequest.gpt3(token, model, answerDeriveStatement.replace("<CONTEXT>", parsedData).replace("<QUERY>", question));
//        answer = "This is a test answer.";
    }

    public String getAnswer() {
        return answer;
    }

    public void clear() {
        queryStatements = "";
        question = "";
        searchKeywords.clear();
        searchUrls.clear();
        crawlResults.clear();
        parsedData = "";
        answer = "";
    }
}

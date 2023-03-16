package org.me._410.gptsearch.components;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.me._410.gptsearch.RunRequest;
import org.me._410.gptsearch.Search;

import javax.swing.*;

public class SendQueryButton extends JButton {
    public SendQueryButton() {
        super("Send Query");
    }

    public SendQueryButton(String text) {
        super(text);
    }

    public void onClick(GoogleSearchAPIKeyTextField gsapi, GoogleSearchCXTextField gscx, ModelNameTextField model, TokenTextField tokenField, ContextSizeField contextSizeField, QueryRecordsPane queryRecordsPane, QueryTextArea queryArea) {
        System.out.println("!!!!!!!!!!SEARCH START!!!!!!!!");
        System.out.println("OpenAI Token: " + tokenField.getText());
        System.out.println("OpenAI Model: " + model.getText());
        System.out.println("Google Search API Key: " + gsapi.getText());
        System.out.println("Google Search CX: " + gscx.getText());
        System.out.println("Context Size: " + contextSizeField.getText());
        System.out.println("Query: " + queryArea.getText());
        System.out.println("===============================");

        int contextSize = Integer.parseInt(contextSizeField.getText());
//        if (contextSize > 3500) {
//            System.out.println("Context size is too large. Please enter a number less than 3500.");
//            return;
//        }else
        if (contextSize < 0) {
            System.out.println("Context size is too small. Please enter a number greater than 0.");
            queryRecordsPane.appendText("Context size is too small. Please enter a number greater than 0.");
            return;
        }

        queryRecordsPane.appendText("Question: " + queryArea.getText());

        Search search = new Search();
        search.setQueryStatements(queryArea.getText());
        search.extractKeywords(queryRecordsPane, tokenField.getText(), model.getText());
        search.searchGoogle(queryRecordsPane, gsapi.getText(), gscx.getText());
        if (!search.crawl()) {
            System.out.println("Crawling failed. Please try again.");
            queryRecordsPane.appendText("Crawling failed. Please try again.");
            return;
        }
        search.parse(contextSize);
        search.shrink(tokenField.getText(), model.getText());
        search.summarize(tokenField.getText(), model.getText());

        System.out.println("Done");
        System.out.println("Total GPT-3 Tokens used: " + RunRequest.totalCharactersRequested);

        JsonObject response = JsonParser.parseString(search.getAnswer()).getAsJsonObject();
        System.out.println("Answer: " + response.get("message").getAsString());

        queryRecordsPane.appendText("===========================");
        queryRecordsPane.appendText("Answer: " + response.get("message").getAsString());
        queryRecordsPane.appendText("===========================");
        System.out.println("!!!!!!!!!!SEARCH END!!!!!!!!");
    }
}

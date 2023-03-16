package org.me._410.gptsearch;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;

public class RunRequest {

    public static long totalCharactersRequested = 0;

    private static String loadStringFromFS(String UUID) {
        String line = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader("IO_" + UUID + ".txt"));
            line = reader.readLine();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return line;
    }


    public static String gpt3(String token, String model, String queryStatement) {
        String uuid = java.util.UUID.randomUUID().toString();
        String[] cmd = {"python3", "gpt3.py", token, queryStatement, model, uuid};
        totalCharactersRequested += queryStatement.length();
        String out = executeScript(cmd);
        System.out.println("GPT3: " + out);
        return out;
    }

    public static String getUrlsFromGoogle(String apiKey, String cx, String query) {
        String uuid = java.util.UUID.randomUUID().toString();
        String[] cmd = {"python3", "google.py", apiKey, cx, query, uuid};
        return executeScript(cmd);
    }

    public static String crawl(ArrayList<String> url) {
        String uuid = java.util.UUID.randomUUID().toString();
        System.out.println("Crawling: " + url);
        ArrayList<String> cmd = new ArrayList<>();
        cmd.add("python3");
        cmd.add("crawl.py");
        cmd.add("./chromedriver");
        for (String u : url) {
            cmd.add(u);
        }
        String[] cmdArray = new String[cmd.size()];
        cmdArray = cmd.toArray(cmdArray);
        return executeScript(cmdArray);
    }

    private static String executeScript(String[] cmd) {
        StringBuilder result = new StringBuilder();
        try {
            if (cmd[0].equals("python3")) {
                if (!new File(cmd[1]).isFile()) {
                    System.out.println("Script " + cmd[1] + " does not exist. Exiting.");
                    System.exit(1);
                }
            }

            Process process = Runtime.getRuntime().exec(cmd);
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return Unescaper.unescape(result.toString());
    }
}

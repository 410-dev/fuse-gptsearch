package org.me._410.gptsearch;

import java.io.*;

public class Main {
    public static void main(String[] args) {

        String configurations = "";
        String[] config = new String[10];
        
        String conffile = "gptsc.conf";
        try {
            System.out.println("Reading " + conffile);
            if (!new File(conffile).isFile()) {
                System.out.println(conffile + " does not exist. Creating it now.");

                BufferedWriter writer = new BufferedWriter(new FileWriter(conffile));
                writer.write("## DO NOT CHANGE ORDER OF CONFIGURATIONS! ##\nopenai-token: Enter value\nmodel: Enter value\ngoogle-search-apikey: Enter value\ngoogle-search-cx: Enter value\ntotaltokens: 0\n");
                writer.close();

                if (new File(conffile).isFile()) {
                    System.out.println(conffile + " created successfully.");
                } else {
                    System.out.println(conffile + " could not be created.");
                    System.exit(1);
                }
            }else{
                System.out.println(conffile + " exists. Skipping creation.");
            }
            BufferedReader reader = new BufferedReader(new FileReader(conffile));
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                builder.append("\n");
            }
            configurations = builder.toString();
            reader.close();

            String[] parsable = configurations.split("\n");
            for (int i = 1; i < parsable.length; i++) {
                if (parsable[i].startsWith("#")) continue;
                if (parsable[i].trim().equals("")) continue;
                System.out.println(parsable[i]);
                config[i-1] = parsable[i].split(": ")[1];
            }

        }catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        Window window = new Window(config);
        window.setVisible(true);
    }
}

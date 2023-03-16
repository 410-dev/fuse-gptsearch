package org.me._410.gptsearch;

import org.me._410.gptsearch.components.DefaultSearchPanel;

import javax.swing.*;
import java.util.ArrayList;

public class Window extends JFrame {

    private String windowID;
    private DefaultSearchPanel searchPanel;

    private static ArrayList<Window> windows = new ArrayList<>();

    public Window(String[] configData) {
        super("GPT-3 Search");
        this.windowID = "default";

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(null);

        DefaultSearchPanel searchPanel = new DefaultSearchPanel(windowID, 800, 600, configData);
        add(searchPanel);

        Window.windows.add(this);
    }

    public static Window getWindow(String windowID) {
        for (Window w : Window.windows) {
            if (w.windowID.equals(windowID)) {
                return w;
            }
        }
        return null;
    }

    public String getWindowID() {
        return windowID;
    }


}

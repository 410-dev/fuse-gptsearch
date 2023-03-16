package org.me._410.gptsearch.components;

import org.me._410.gptsearch.Window;

import javax.swing.*;

public class UIUpdateButton extends JButton {

    public UIUpdateButton() {
        super("Update");
    }

    public void onClick(DefaultSearchPanel searchPanel) {
        System.out.println("Updating: " + searchPanel.getID() + " in " + searchPanel.getWindowID());
        Window w = Window.getWindow(searchPanel.getWindowID());
        if (w == null) {
            System.out.println("Window not found");
            return;
        }else{
            System.out.println("Window found: " + w.getWindowID());
        }

        DefaultSearchPanel dsp = new DefaultSearchPanel(searchPanel.getWindowID(), searchPanel.getID(), w.getWidth(), w.getHeight(), searchPanel.getConfigData());
        System.out.println("New panel: " + dsp.getID() + " in " + dsp.getWindowID());

        w.remove(searchPanel);
        w.add(dsp);
        w.revalidate();
        w.repaint();

        System.out.println("Updated: " + searchPanel.getID() + " in " + searchPanel.getWindowID());
    }
}

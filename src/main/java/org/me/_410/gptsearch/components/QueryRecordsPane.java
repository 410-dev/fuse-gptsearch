package org.me._410.gptsearch.components;

import javax.swing.*;

public class QueryRecordsPane extends JTextArea {

    public QueryRecordsPane() {
        super();
        this.setEditable(false);
        this.setWrapStyleWord(true);
        this.setLineWrap(true);
    }

    public void appendText(String text) {
        this.setEditable(true);
        this.setText(this.getText() + text + "\n");
        this.setEditable(false);
    }

}

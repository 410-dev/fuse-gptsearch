package org.me._410.gptsearch.components;

import javax.swing.*;

public class TokenLockButton extends JButton {

    public TokenLockButton() {
        super("Lock");
    }

    public void onClick(JTextField tokenField) {
        if (tokenField.isEditable()) {
            tokenField.setEditable(false);
            this.setText("Unlock");
        } else {
            tokenField.setEditable(true);
            this.setText("Lock");
        }
    }
}

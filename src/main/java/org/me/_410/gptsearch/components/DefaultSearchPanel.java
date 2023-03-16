package org.me._410.gptsearch.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.UUID;

public class DefaultSearchPanel extends JPanel {

    private JLabel tokenLabel = new JLabel("OpenAI Token:");
    private TokenTextField tokenField;
    private TokenLockButton tokenLockButton;
    private JLabel googleSearchAPIKeyLabel = new JLabel("Google Search API Key:");
    private GoogleSearchAPIKeyTextField googleSearchAPIKeyField;
    private TokenLockButton googleSearchAPIKeyLockButton;
    private JLabel googleSearchCXLabel = new JLabel("Google Search CX:");
    private GoogleSearchCXTextField googleSearchCXField;
    private TokenLockButton googleSearchCXLockButton;
    private JLabel modelNameLabel = new JLabel("OpenAI Model Name:");
    private ModelNameTextField modelNameField;
    private TokenLockButton modelNameLockButton;
    private JLabel contextSizeLabel = new JLabel("Context length (<3500):");
    private ContextSizeField contextSizeField;
    private TokenLockButton contextSizeLockButton;
    private QueryRecordsPane queryRecordsPane;
    private JScrollPane queryRecordsScrollPane;
    private JLabel queryLabel = new JLabel("Query: Enter a query to search for. The maximum characters are 3800 including the context length.");
    private QueryTextArea queryArea;
    private JScrollPane queryScrollPane;
    private SendQueryButton sendQueryButton;

    private UIUpdateButton updateButton;

    private String ID;
    private String windowID;
    private String[] configData;

    private void buildBase(String windowID, String ID, int width, int height, String[] configData) {
        this.windowID = windowID;
        this.ID = UUID.randomUUID().toString();
        this.configData = configData;
        setBounds(0, 0, width, height);
        tokenField = new TokenTextField();
        googleSearchAPIKeyField = new GoogleSearchAPIKeyTextField();
        googleSearchCXField = new GoogleSearchCXTextField();
        modelNameField = new ModelNameTextField();
        contextSizeField = new ContextSizeField();
        queryRecordsPane = new QueryRecordsPane();
        queryRecordsScrollPane = new JScrollPane(queryRecordsPane);
        queryArea = new QueryTextArea();
        queryScrollPane = new JScrollPane(queryArea);
        sendQueryButton = new SendQueryButton();
        tokenLockButton = new TokenLockButton();
        googleSearchAPIKeyLockButton = new TokenLockButton();
        googleSearchCXLockButton = new TokenLockButton();
        modelNameLockButton = new TokenLockButton();
        contextSizeLockButton = new TokenLockButton();
        updateButton = new UIUpdateButton();

        sendQueryButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                sendQueryButton.onClick(googleSearchAPIKeyField, googleSearchCXField, modelNameField, tokenField, contextSizeField, queryRecordsPane, queryArea);
            }
        });
        tokenLockButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                tokenLockButton.onClick(tokenField);
            }
        });
        googleSearchAPIKeyLockButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                googleSearchAPIKeyLockButton.onClick(googleSearchAPIKeyField);
            }
        });
        googleSearchCXLockButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                googleSearchCXLockButton.onClick(googleSearchCXField);
            }
        });
        modelNameLockButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                modelNameLockButton.onClick(modelNameField);
            }
        });
        contextSizeLockButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                contextSizeLockButton.onClick(contextSizeField);
            }
        });
        updateButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                updateButton.onClick(DefaultSearchPanel.this);
            }
        });

        contextSizeField.setText("3000");

        this.setLayout(null);

        tokenLabel.setSize(width, (int) (height * 0.05));
        tokenLabel.setLocation((int) (width * 0.05), (int) (height * 0.05));

        tokenField.setSize((int) (width * 0.6), (int) (height * 0.05));
        tokenField.setLocation((int) (width * 0.25), (int) (height * 0.05));

        tokenLockButton.setSize((int) (width * 0.1), (int) (height * 0.05));
        tokenLockButton.setLocation((int) (width * 0.85), (int) (height * 0.05));

        googleSearchAPIKeyLabel.setSize(width, (int) (height * 0.05));
        googleSearchAPIKeyLabel.setLocation((int) (width * 0.05), (int) (height * 0.1));

        googleSearchAPIKeyField.setSize((int) (width * 0.6), (int) (height * 0.05));
        googleSearchAPIKeyField.setLocation((int) (width * 0.25), (int) (height * 0.1));

        googleSearchAPIKeyLockButton.setSize((int) (width * 0.1), (int) (height * 0.05));
        googleSearchAPIKeyLockButton.setLocation((int) (width * 0.85), (int) (height * 0.1));

        googleSearchCXLabel.setSize(width, (int) (height * 0.05));
        googleSearchCXLabel.setLocation((int) (width * 0.05), (int) (height * 0.15));

        googleSearchCXField.setSize((int) (width * 0.6), (int) (height * 0.05));
        googleSearchCXField.setLocation((int) (width * 0.25), (int) (height * 0.15));

        googleSearchCXLockButton.setSize((int) (width * 0.1), (int) (height * 0.05));
        googleSearchCXLockButton.setLocation((int) (width * 0.85), (int) (height * 0.15));

        modelNameLabel.setSize(width, (int) (height * 0.05));
        modelNameLabel.setLocation((int) (width * 0.05), (int) (height * 0.2));

        modelNameField.setSize((int) (width * 0.6), (int) (height * 0.05));
        modelNameField.setLocation((int) (width * 0.25), (int) (height * 0.2));

        modelNameLockButton.setSize((int) (width * 0.1), (int) (height * 0.05));
        modelNameLockButton.setLocation((int) (width * 0.85), (int) (height * 0.2));

        contextSizeLabel.setSize(width, (int) (height * 0.05));
        contextSizeLabel.setLocation((int) (width * 0.05), (int) (height * 0.25));

        contextSizeField.setSize((int) (width * 0.6), (int) (height * 0.05));
        contextSizeField.setLocation((int) (width * 0.25), (int) (height * 0.25));

        contextSizeLockButton.setSize((int) (width * 0.1), (int) (height * 0.05));
        contextSizeLockButton.setLocation((int) (width * 0.85), (int) (height * 0.25));

        queryRecordsScrollPane.setSize((int) (width * 0.9), (int) (height * 0.45));
        queryRecordsScrollPane.setPreferredSize(new Dimension((int) (width * 0.9), (int) (height * 0.45)));
        queryRecordsScrollPane.setLocation((int) (width * 0.05), (int) (height * 0.3));

        queryLabel.setSize(width, (int) (height * 0.05));
        queryLabel.setLocation((int) (width * 0.05), (int) (height * 0.75));

        queryScrollPane.setSize((int) (width * 0.9), (int) (height * 0.05));
        queryScrollPane.setPreferredSize(new Dimension((int) (width * 0.9), (int) (height * 0.05)));
        queryScrollPane.setLocation((int) (width * 0.05), (int) (height * 0.8));

        sendQueryButton.setSize((int) (width * 0.2), (int) (height * 0.05));
        sendQueryButton.setLocation((int) (width * 0.85), (int) (height * 0.8));

        updateButton.setSize((int) (width * 0.2), (int) (height * 0.05));
        updateButton.setLocation((int) (width * 0.85), (int) (height * 0.85));

        tokenField.setText(configData[0]);
        modelNameField.setText(configData[1]);
        googleSearchAPIKeyField.setText(configData[2]);
        googleSearchCXField.setText(configData[3]);

        queryRecordsPane.appendText("Codename Fuse pre-alpha 1: Mar2023-A Build.");
        queryRecordsPane.appendText("-------------------------------------------");
        queryRecordsPane.appendText("Hello!");

        this.add(tokenLabel);
        this.add(tokenField);
        this.add(googleSearchAPIKeyLabel);
        this.add(googleSearchAPIKeyField);
        this.add(googleSearchAPIKeyLockButton);
        this.add(googleSearchCXLabel);
        this.add(googleSearchCXField);
        this.add(googleSearchCXLockButton);
        this.add(modelNameLabel);
        this.add(modelNameField);
        this.add(modelNameLockButton);
        this.add(contextSizeLabel);
        this.add(contextSizeField);
        this.add(contextSizeLockButton);
        this.add(tokenLockButton);
        this.add(queryRecordsScrollPane);
        this.add(queryLabel);
        this.add(queryScrollPane);
        this.add(sendQueryButton);
        this.add(updateButton);
    }

    public DefaultSearchPanel(String windowID, String ID, int width, int height, String[] config) {
        super();
        buildBase(windowID, ID, width, height, config);
        // TODO Load from cache

    }

    public DefaultSearchPanel(String windowID, int width, int height, String[] config) {
        super();
        buildBase(windowID, UUID.randomUUID().toString(), width, height, config);
    }

    public String getID() {
        return ID;
    }

    public String getWindowID() {
        return windowID;
    }

    public String[] getConfigData() {
        return this.configData;
    }
}

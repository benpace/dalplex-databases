package com.dalplex.gui.views;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * @author Ben Pace
 */
public abstract class QuickView extends JPanel{
    JPanel content, titlePanel;
    private JScrollPane scrollPane;
    private String[] headerText;
    private JLabel headers[], titleText;

    private Connection conn;

    public QuickView(int width, int height, Connection conn, String title, int columns,
                        String[] headerText){
        this.conn = conn;
        this.headerText = headerText;

        setSize(width, height);
        setLayout(new BorderLayout(0,10));

        titlePanel = new JPanel();
        titleText = new JLabel(title);
        titlePanel.add(titleText);

        content = new JPanel(new GridLayout(0,columns,10,5));


        headers = new JLabel[columns];
        for(int i = 0;i < columns;i++){
            headers[i] = new JLabel(headerText[i]);
            headers[i].setSize(getWidth() / 5, headers[i].getHeight());
            headers[i].setHorizontalAlignment(SwingConstants.CENTER);
            headers[i].setBorder(BorderFactory.createMatteBorder(0,0,1,0, Color.BLACK));
            content.add(headers[i]);
        }

        scrollPane = new JScrollPane(content);

        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

    }

    public Connection getConnection(){return conn;}

}

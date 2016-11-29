package com.dalplex.gui.views;

import com.dalplex.gui.*;
import com.dalplex.gui.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.ArrayList;

/**
 * @author Ben Pace
 */
public abstract class QuickView extends JPanel implements ActionListener{
    JPanel content, titlePanel;
    private JScrollPane scrollPane;
    private String[] headerText;
    private JLabel headers[], titleText;
    private com.dalplex.gui.Window window;
    private Connection conn;
    private JButton newObject;

    //private int width, height;

    public QuickView(int width, int height, Connection conn, Window window, String title, int columns,
                     String[] headerText){
        this.conn = conn;
        this.window = window;
        this.headerText = headerText;
        //this.width = width;
        //this.height = height;

        setSize(width, height);
        setLayout(new BorderLayout(0,10));

        titlePanel = new JPanel(new GridLayout(1,3));
        titleText = new JLabel(title);
        newObject = new JButton("New");
        newObject.addActionListener(this);
        titlePanel.add(new JSeparator());
        titlePanel.add(titleText);
        titlePanel.add(newObject);

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

    public Window getWindow(){return window;}
    public JButton getNewObjectButton(){return newObject;}

    public Connection getConnection(){return conn;}

}

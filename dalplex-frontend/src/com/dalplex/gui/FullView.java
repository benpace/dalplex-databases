package com.dalplex.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Ben Pace
 */
public class FullView extends JPanel{
    private JPanel fields;
    private JPanel header;
    private JScrollPane scrollPane;
    private String title;

    public FullView(int width, int height, String title){
        setSize(width, height);
        setLayout(new BorderLayout(0,10));

        header = new JPanel(new GridLayout(1,2));
        JLabel headerText = new JLabel(title);
        headerText.setHorizontalAlignment(SwingConstants.CENTER);
        header.add(headerText);
        //submit = new JButton("Submit");
        //header.add(submit);

        fields = new JPanel(new GridLayout(0,2));
        fields.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        scrollPane = new JScrollPane(fields);

        add(header, BorderLayout.NORTH);
        add(scrollPane,BorderLayout.CENTER);
    }

    public JPanel getFields(){return fields;}
    public JPanel getHeader(){return header;}

    public static void main(String[] args){
        JFrame w = new JFrame();
        w.setSize(500,500);
        w.add(new FullView(500,500, "Edit"));

        w.setVisible(true);
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

}

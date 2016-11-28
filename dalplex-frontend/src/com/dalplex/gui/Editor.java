package com.dalplex.gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Ben Pace
 */
public class Editor extends JPanel implements ActionListener {
    private JPanel fields, header;
    private JScrollPane scollPane;
    private JButton submit;
    private String title;

    public Editor(int width, int height, String title){
        setSize(width, height);
        setLayout(new BorderLayout(0,10));

        header = new JPanel(new GridLayout(1,2));
        JLabel headerText = new JLabel(title);
        headerText.setHorizontalAlignment(SwingConstants.CENTER);
        header.add(headerText);
        submit = new JButton("Submit");
        header.add(submit);

        fields = new JPanel(new GridLayout(5,2));
        fields.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        add(header, BorderLayout.NORTH);
        add(fields,BorderLayout.CENTER);
    }


    public static void main(String[] args){
        JFrame w = new JFrame();
        w.setSize(500,500);
        w.add(new Editor(500,500, "Edit"));

        w.setVisible(true);
        w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}

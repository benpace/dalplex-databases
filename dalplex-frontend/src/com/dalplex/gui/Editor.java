package com.dalplex.gui;

import com.dalplex.gui.etc.DataEntryField;

import javax.swing.*;
import java.awt.*;


/**
 * @author Ben
 */
public class Editor extends JPanel {
    private JScrollPane scrollPane;
    DataEntryField fields[];
    public Editor(int width, int height){
        setSize(width, height);

        scrollPane = new JScrollPane();

        fields = new DataEntryField[20];
        for(int i = 0;i < 20;i++){
            fields[i] = new DataEntryField(i + ":");
            scrollPane.add(fields[i]);
        }
        add(scrollPane);



    }

    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setSize(500,200);
        frame.add(new Editor(500,200));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

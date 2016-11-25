package com.dalplex.gui;

import javax.swing.*;
import java.awt.*;

/**
 * @author Ben
 */
public class MenuBar extends JPanel{
    private int width, height;
    private JButton back;
    private JButton[] buttons;

    public MenuBar(int width, int height){
        this.width = width;
        this.height = height;

        setSize(width, height);


        setLayout(new GridLayout(8,1,5,0));

        back = new JButton("Back");


        add(back);
        add (new JSeparator());

        String[] buttonLabels = {
                "Members", "Events", "Schedule", "Parking",
                "Staff", "Payroll"
        };

        buttons = new JButton[6];
        for(int i = 0;i < buttons.length;i++){
            buttons[i] = new JButton(buttonLabels[i]);
            add(buttons[i]);
        }

        setBackground(Color.RED);


    }
}

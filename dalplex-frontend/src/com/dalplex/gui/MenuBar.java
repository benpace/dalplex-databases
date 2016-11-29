package com.dalplex.gui;

import com.dalplex.gui.views.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Ben
 */
public class MenuBar extends JPanel implements ActionListener{
    private int width, height;
    private JButton back, home;
    private JButton[] buttons;
    private Window window;

    public MenuBar(int width, int height, Window window){
        this.width = width;
        this.height = height;
        this.window = window;

        setSize(width, height);


        setLayout(new GridLayout(8,1,5,0));

        back = new JButton("Back");
        back.addActionListener(this);
        add(back);

        home = new JButton("Home");
        home.addActionListener(this);
        add(home);

        String[] buttonLabels = {
                "Members", "Events", "Payroll", "Employees", "Rooms", "Equipment"
        };

        buttons = new JButton[6];
        for(int i = 0;i < buttons.length;i++){
            buttons[i] = new JButton(buttonLabels[i]);
            buttons[i].addActionListener(this);
            add(buttons[i]);
        }

        setBackground(Color.RED);


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == buttons[0]){
            window.setActivePanel(new MemberQuickView(window.WINDOW_WIDTH * (2/3), window.WINDOW_HEIGHT, window.getConnection(), window), true);

        }
        else if(e.getSource() == buttons[1]){
            System.out.println("Events");
            window.setActivePanel(new EventQuickView(window.WINDOW_WIDTH * (2/3), window.WINDOW_HEIGHT, window.getConnection(), window), true);
        }
        else if(e.getSource() == buttons[2]){
            System.out.println("Payroll");
            window.setActivePanel(new PayrollQuickView(window.WINDOW_WIDTH * (2/3), window.WINDOW_HEIGHT, window.getConnection(), window), true);
        }
        else if(e.getSource() == buttons[3]){
            window.setActivePanel(new EmployeeQuickView(window.WINDOW_WIDTH * (2/3), window.WINDOW_HEIGHT, window.getConnection(), window), true);
            System.out.println("'Employee' pressed");
        }
        else if(e.getSource() == buttons[4]){
            window.setActivePanel(new RoomQuickView(window.WINDOW_WIDTH * (2/3), window.WINDOW_HEIGHT, window.getConnection(), window), true);
            System.out.println("Rooms");
        }
        else if(e.getSource() == buttons[5]){
            window.setActivePanel(new EquipmentQuickView(window.WINDOW_WIDTH * (2/3), window.WINDOW_HEIGHT, window.getConnection(), window), true);
            System.out.println("Equipment");
        }
        else if(e.getSource() == back){
            JPanel previous = PreviousPanelHandler.getLast();
            if(previous != null)
                window.setActivePanel(previous, false);
            System.out.println("'Back' pressed");
        }
        else if(e.getSource() == home){
            window.setActivePanel(new Home(window.WINDOW_WIDTH * (2/3), window.WINDOW_HEIGHT), true);
        }
    }
}

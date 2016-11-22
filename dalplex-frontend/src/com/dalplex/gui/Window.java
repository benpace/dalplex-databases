package com.dalplex.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.sql.Connection;

/**
 * @author Ben
 */
public class Window extends JFrame{
    private int WINDOW_WIDTH = 1000, WINDOW_HEIGHT = 600;
    private Connection conn;
    private JPanel panel;


    public Window(Connection conn){
        this.conn = conn;

        setTitle("Dalplex - Dalhousie Fitness Centre");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setLayout(new FlowLayout());


        setLocationRelativeTo(null);

        panel = new JPanel(new BorderLayout());

        MenuBar menu = new MenuBar(WINDOW_WIDTH / 3, WINDOW_HEIGHT);
        panel.add(menu, BorderLayout.WEST);

        Editor editor = new Editor((int)((double)WINDOW_WIDTH * (2.0/3.0)), WINDOW_HEIGHT);

        panel.add(editor);

        add(panel);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /*
     * Simple test method
     */
    public static void main(String args[]){
        Window w = new Window(null);
    }

}

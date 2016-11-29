package com.dalplex.gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;

/**
 * @author Ben
 */
public class Window extends JFrame{
    public final int WINDOW_WIDTH = 1000, WINDOW_HEIGHT = 600;
    private Connection conn;
    private JPanel panel, active, home;
    private MenuBar menu;

    public Window(Connection conn){
        this.conn = conn;

        setTitle("Dalplex - Dalhousie Fitness Centre");
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        panel = new JPanel(new BorderLayout());
        panel.setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        menu = new MenuBar(WINDOW_WIDTH / 3, WINDOW_HEIGHT, this);
        panel.add(menu, BorderLayout.WEST);

        home = new Home(WINDOW_WIDTH * (2/3), WINDOW_HEIGHT);
        active = home;

        panel.add(active, BorderLayout.CENTER);

        add(panel);
        setVisible(true);


        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    conn.close();
                    System.out.println("Connection closed");
                    //dispose();
                    System.exit(0);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);



    }


    public Connection getConnection(){return conn;}

    public void setActivePanel(JPanel active, boolean storePrevious){
        if(storePrevious)   PreviousPanelHandler.addPanel(this.active);

        panel.remove(this.active);
        this.active = active;
        panel.add(active, BorderLayout.CENTER);

        active.updateUI();
    }

}

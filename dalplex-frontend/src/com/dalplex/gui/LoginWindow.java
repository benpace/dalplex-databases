package com.dalplex.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.dalplex.main.*;

public class LoginWindow extends JFrame implements ActionListener{
    private final int WINDOW_WIDTH = 300, WINDOW_HEIGHT = 125;

    private JPanel main, labels, buttons, input;
    private JLabel userPrompt, passPrompt;
    private JTextField username;
    private JPasswordField passwordField;
    private JButton submit;

    private Login login;

    public LoginWindow(){

        setTitle("Log Into Database");

        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        setLocationRelativeTo(null);

        main = new JPanel(new FlowLayout());

        userPrompt = new JLabel("Username:");
        username = new JTextField();
        username.setColumns(16);
        passPrompt = new JLabel("Password:");
        passwordField = new JPasswordField();
        passwordField.setColumns(16);
        passwordField.addActionListener(this);

        submit = new JButton("Log In");
        submit.addActionListener(this);

        main.add(userPrompt);
        main.add(username);
        main.add(passPrompt);
        main.add(passwordField);
        main.add(submit);

        add(main);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

    public Login getLogin(){
        setVisible(true);
        synchronized (this){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return login;
    }

    public void actionPerformed(ActionEvent e) {
        login = new Login(username.getText(), passwordField.getPassword());
        synchronized (this) {
            notify();
        }

    }
}

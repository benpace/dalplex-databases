package com.dalplex.gui;

import javax.swing.*;
import java.awt.*;

/**
 * @author Ben Pace
 */
public class Home extends JPanel {
    private JLabel title;
    private JTextArea body;
    public Home(int width, int height){
        setSize(width, height);
        setLayout(new GridLayout(2,1,0,10));
        title = new JLabel("Dalplex Database Management Software");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font(title.getFont().getName(), Font.PLAIN, 35));
        body = new JTextArea("Welcome to the database management software for Dalhousie's Dalplex fitness centre. " +
                "From here you may access many Dalplex systems, including membership and payroll. Start by selecting" +
                " an option from the left! \nChanges made to the Database may require reloading a page");
        body.setLineWrap(true);
        body.setEditable(false);
        body.setMargin(new Insets(30, 30, 30, 30));


        add(title);
        add(body);

    }
}

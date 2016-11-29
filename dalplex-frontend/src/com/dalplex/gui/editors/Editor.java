package com.dalplex.gui.editors;

import com.dalplex.gui.FullView;

import javax.swing.*;
import java.awt.event.ActionListener;

/**
 * @author Ben Pace
 */
public abstract class Editor extends FullView implements ActionListener{
    private JButton submit;
    public Editor(int width, int height, String title) {
        super(width, height, title);
        submit = new JButton("Submit");
        submit.addActionListener(this);
        getHeader().add(submit);
    }

    public JButton getSubmit(){return submit;}
}

package com.dalplex.gui.EventHandlers;


import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

/**
 * @author Ben
 */
public class PreviousFrameHandler implements ActionListener {

    Stack<JPanel> history;

    public PreviousFrameHandler(){
        history = new Stack<JPanel>();
    }

    public void addPanel(JPanel panel){ history.push(panel);}

    public JPanel getLast(){    return history.pop();}

    public void actionPerformed(ActionEvent e){
        System.out.println("Back");
    }
}

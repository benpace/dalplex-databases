package com.dalplex.gui;


import javax.swing.JPanel;
import java.util.Stack;

/**
 * Class to store and access history of JPanel objects,
 * primarily for use by the back button in the GUI
 * @author Ben
 */
public final class PreviousPanelHandler {

    private static Stack<JPanel> history = new Stack<JPanel>();
    private static Window window;

    private PreviousPanelHandler(){}

    public static void addPanel(JPanel panel){ history.push(panel);}

    public static JPanel getLast(){
        if(!history.isEmpty())
            return history.pop();
        return null;

    }

    public static void setWindow(Window w){window = w;}

    public static void goBack(){
        window.setActivePanel(getLast(),false);
    }


}

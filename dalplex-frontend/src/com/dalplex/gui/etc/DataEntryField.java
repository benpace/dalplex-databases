package com.dalplex.gui.etc;

import javax.swing.*;
import java.awt.*;

/**
 * @author Ben
 */
public class DataEntryField extends JPanel{
    private JLabel label;
    private JTextField field;

    public DataEntryField(String labelText){
        setLayout(new FlowLayout());

        label = new JLabel(labelText);
        field = new JTextField(32);

        add(label);
        add(field);

    }

    public JLabel getLabel(){return label;}
    public JTextField getField(){return field;}
}

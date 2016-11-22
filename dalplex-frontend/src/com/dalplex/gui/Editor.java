package com.dalplex.gui;

import com.dalplex.gui.etc.DataEntryField;

import javax.swing.*;


/**
 * @author Ben
 */
public class Editor extends JPanel {

    public Editor(int width, int height){
        setSize(width, height);

        DataEntryField field1 = new DataEntryField("Field1");
        add(field1);

    }


}

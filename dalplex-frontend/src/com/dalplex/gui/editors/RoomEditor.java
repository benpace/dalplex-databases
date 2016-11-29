package com.dalplex.gui.editors;

import com.dalplex.data.Room;
import com.dalplex.data.Membership;
import com.dalplex.gui.PreviousPanelHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Ben Pace
 */
public class RoomEditor extends Editor implements ActionListener{
    private Room room;
    private final String[] fieldNames = {
            "Description"
    };
    private JLabel[] fieldLabels;
    private JComponent[] data;


    public RoomEditor(int width, int height, Room e) {
        super(width, height, "Room");
        this.room = e;

        fieldLabels = new JLabel[fieldNames.length];
        data = new JComponent[fieldNames.length];

        //data = new JLabel[fieldNames.length];
        data[0] = new JTextField(room.getDescription());


        for (int i = 0; i < fieldNames.length; i++) {
            fieldLabels[i] = new JLabel(fieldNames[i]);
            fieldLabels[i].setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.BLACK));
            fieldLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
            data[i].setAlignmentX(SwingConstants.CENTER);
            getFields().add(fieldLabels[i]);
            getFields().add(data[i]);
        }

        for(int i =0;i < 40;i++)
            getFields().add(new JSeparator());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == getSubmit()){


            room.setDescription(((JTextField)data[0]).getText());

            room.publishToDB();
            PreviousPanelHandler.goBack();



        }
    }
}

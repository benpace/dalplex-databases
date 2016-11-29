package com.dalplex.gui.editors;

import com.dalplex.data.Equipment;
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
public class EquipmentEditor extends Editor implements ActionListener{
    private Equipment equipment;
    private final String[] fieldNames = {
            "Description", "QOH"
    };
    private JLabel[] fieldLabels;
    private JComponent[] data;

    public EquipmentEditor(int width, int height, Equipment e) {
        super(width, height, "Equipment");
        this.equipment = e;

        fieldLabels = new JLabel[fieldNames.length];
        data = new JComponent[fieldNames.length];

        //data = new JLabel[fieldNames.length];
        data[0] = new JTextField(equipment.getDescription());
        data[1] = new JTextField(""+equipment.getQoh());



        //Select from membership types

        for (int i = 0; i < fieldNames.length; i++) {
            fieldLabels[i] = new JLabel(fieldNames[i]);
            fieldLabels[i].setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.BLACK));
            fieldLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
            data[i].setAlignmentX(SwingConstants.CENTER);
            getFields().add(fieldLabels[i]);
            getFields().add(data[i]);
        }
        for(int i =0;i < 36;i++)
            getFields().add(new JSeparator());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == getSubmit()){
            //Check for valid info
            try{
                Integer.parseInt(((JTextField)data[1]).getText());
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(null, "QOH is of incorrect format");
                return;
            }

            equipment.setDescription(((JTextField)data[0]).getText());
            equipment.setQoh(Integer.parseInt(((JTextField)data[1]).getText()));

            equipment.publishToDB();
            PreviousPanelHandler.goBack();

        }
    }
}

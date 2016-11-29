package com.dalplex.gui.fullviews;

import com.dalplex.data.Equipment;
import com.dalplex.data.Room;
import com.dalplex.gui.FullView;

import javax.swing.*;
import java.awt.*;

/**
 * @author Ben Pace
 */
public class RoomView extends FullView{
    private Room room;
    private final String[] fieldNames = {
            "Description"
    };
    private JLabel[] fieldLabels, data;
    public RoomView(int width, int height, Room e) {
        super(width, height, "Room");
        this.room = e;

        fieldLabels = new JLabel[fieldNames.length];
        data = new JLabel[fieldNames.length];

        //data = new JLabel[fieldNames.length];
        data[0] = new JLabel(room.getDescription());



        for (int i = 0; i < fieldNames.length; i++) {
            fieldLabels[i] = new JLabel(fieldNames[i]);
            fieldLabels[i].setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.BLACK));
            fieldLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
            data[i].setHorizontalAlignment(SwingConstants.CENTER);
            getFields().add(fieldLabels[i]);
            getFields().add(data[i]);
        }
        JLabel equipTitle = new JLabel("Equipment");
        equipTitle.setFont(new Font(equipTitle.getFont().getName(), Font.BOLD, equipTitle.getFont().getSize()));
        getFields().add(equipTitle);
        getFields().add(new JSeparator());

        for(Equipment equipment: room.getEquipment()){
            getFields().add(new JLabel(equipment.getDescription()));
            getFields().add(new JLabel(""+equipment.getQoh()));
        }
    }
}

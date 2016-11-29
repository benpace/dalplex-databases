package com.dalplex.gui.views;

import com.dalplex.data.Equipment;
import com.dalplex.gui.Window;
import com.dalplex.gui.editors.EquipmentEditor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class EquipmentQuickView extends QuickView implements ActionListener{
    private static final String[] headerText = {"Description", "QOH", "Edit"};
    ArrayList<Equipment> equipment;
    private JButton[] editButtons;
    public EquipmentQuickView(int width, int height, Connection conn, Window w){
        super(width, height, conn, w, "Equipment", headerText.length, headerText);
        equipment = new ArrayList<>();
        getElements();
        editButtons = new JButton[equipment.size()];
        for(int i = 0;i < equipment.size(); i++){
            Equipment m = equipment.get(i);
            content.add(new JLabel(m.getDescription()));
            content.add(new JLabel("" + m.getQoh()));

            editButtons[i] = new JButton("X");
            editButtons[i].setHorizontalTextPosition(SwingConstants.CENTER);
            editButtons[i].addActionListener(this);

            content.add(editButtons[i]);
        }
        for(int i = 0;i < ((headerText.length * 21) - (headerText.length * equipment.size()));i++)  content.add(new JSeparator());
    }


    public void getElements() {
        try {
            Statement stmnt = getConnection().createStatement();
            String sql = "SELECT equip_id FROM " + Equipment.TABLE;
            stmnt.execute(sql);
            ResultSet rs = stmnt.getResultSet();

            while(rs.next()){
                equipment.add(new Equipment(rs.getInt("equip_id"), getConnection()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i = 0;i < editButtons.length;i++) {
            if (e.getSource() == editButtons[i]) {
                getWindow().setActivePanel(new EquipmentEditor(500, 500, equipment.get(i)), true);
            }
        }
        if(e.getSource()==getNewObjectButton()){
            getWindow().setActivePanel(new EquipmentEditor(500, 500, new Equipment(getConnection())), true);
        }
    }
}

package com.dalplex.gui.views;

import com.dalplex.data.Equipment;
import com.dalplex.data.Member;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Ben Pace
 */
public class EquipmentQuickView extends QuickView {
    private static final String[] headerText = {"Description", "QOH", "Edit", "Delete"};
    ArrayList<Equipment> equipment;
    public EquipmentQuickView(int width, int height, Connection conn){
        super(width, height, conn, "Equipment", 4, headerText);
        equipment = new ArrayList<>();
        getElements();
        for(Equipment m: equipment){
            content.add(new JLabel(m.getDescription()));
            content.add(new JLabel("" + m.getQoh()));
            content.add(new JLabel("X"));
            content.add(new JLabel("X"));
        }
        for(int i = 0;i < ((4 * 21) - (4 * equipment.size()));i++)  content.add(new JSeparator());
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
}

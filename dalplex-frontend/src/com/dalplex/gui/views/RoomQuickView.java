package com.dalplex.gui.views;

import com.dalplex.data.Member;
import com.dalplex.data.Program;
import com.dalplex.data.Room;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Ben Pace
 */
public class RoomQuickView extends QuickView {
    private static final String[] headerText = {"Description", "Edit", "Delete"};
    private ArrayList<Room> rooms;
    public RoomQuickView(int width, int height, Connection conn){
        super(width, height, conn, "Rooms", 3, headerText);
        rooms = new ArrayList<>();
        getElements();
        for(Room m: rooms){
            content.add(new JLabel(m.getDescription()));
            content.add(new JLabel("X"));
            content.add(new JLabel("X"));
        }
        for(int i = 0;i < ((3 * 21) - (3 * rooms.size()));i++)  content.add(new JSeparator());
    }


    public void getElements() {
        try {
            Statement stmnt = getConnection().createStatement();
            String sql = "SELECT room_id FROM " + Room.TABLE;
            stmnt.execute(sql);
            ResultSet rs = stmnt.getResultSet();

            while(rs.next()){
                rooms.add(new Room(rs.getInt("room_id"), getConnection()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

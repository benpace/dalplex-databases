package com.dalplex.gui.views;

import com.dalplex.data.Employee;
import com.dalplex.data.Program;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Ben Pace
 */
public class EventQuickView extends QuickView {
    private static final String[] headerText = {"Description", "Date", "Room", "Members", "Edit", "Delete"};
    private ArrayList<Program> events;
    public EventQuickView(int width, int height, Connection conn){
        super(width, height, conn, "Events", 6, headerText);
        events = new ArrayList<>();
        getElements();
        for(Program m: events){
            content.add(new JLabel(m.getDescription()));
            content.add(new JLabel("" + m.getDate()));
            content.add(new JLabel(""+m.getRoom().getDescription()));
            content.add(new JLabel(""+m.getMembers().size()));
            content.add(new JLabel("X"));
            content.add(new JLabel("X"));
        }
        for(int i = 0;i < ((6 * 21) - (6 * events.size()));i++)  content.add(new JSeparator());
    }


    public void getElements(){
        try {
            Statement stmnt = getConnection().createStatement();
            String sql = "SELECT event_id FROM " + Program.TABLE;
            stmnt.execute(sql);
            ResultSet rs = stmnt.getResultSet();

            while(rs.next()){
                events.add(new Program(rs.getInt("event_id"), getConnection()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

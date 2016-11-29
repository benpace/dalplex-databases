package com.dalplex.gui.views;

import com.dalplex.data.Employee;
import com.dalplex.data.Program;
import com.dalplex.gui.Window;
import com.dalplex.gui.editors.EventEditor;
import com.dalplex.gui.fullviews.EventView;
import com.dalplex.gui.fullviews.RoomView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Ben Pace
 */
public class EventQuickView extends QuickView implements ActionListener{
    private static final String[] headerText = {"Description", "Date", "Room", "Members", "Edit", "Delete"};
    private ArrayList<Program> events;
    private JButton[] editButtons, viewButtons;
    public EventQuickView(int width, int height, Connection conn, Window w){
        super(width, height, conn, w,"Events", 6, headerText);
        events = new ArrayList<>();
        getElements();
        editButtons = new JButton[events.size()];
        viewButtons = new JButton[events.size()];
        for(int i = 0;i < events.size();i++){
            Program m = events.get(i);
            content.add(new JLabel(m.getDescription()));
            content.add(new JLabel("" + m.getDate()));
            content.add(new JLabel(""+m.getRoom().getDescription()));
            content.add(new JLabel(""+m.getMembers().size()));

            editButtons[i] = new JButton("X");
            editButtons[i].setHorizontalTextPosition(SwingConstants.CENTER);
            editButtons[i].addActionListener(this);

            viewButtons[i] = new JButton("X");
            viewButtons[i].setHorizontalTextPosition(SwingConstants.CENTER);
            viewButtons[i].addActionListener(this);

            content.add(editButtons[i]);
            content.add(viewButtons[i]);
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

    public void actionPerformed(ActionEvent e) {
        //int index;
        for(int i = 0;i < viewButtons.length;i++) {
            if (e.getSource() == viewButtons[i]) {
                getWindow().setActivePanel(new EventView(500, 500, events.get(i)), true);
            }
            if (e.getSource() == editButtons[i]) {
                getWindow().setActivePanel(new EventEditor(500, 500, events.get(i)), true);
            }
        }
        if(e.getSource()==getNewObjectButton()){
            getWindow().setActivePanel(new EventEditor(500, 500, new Program(getConnection())), true);
        }
    }
}

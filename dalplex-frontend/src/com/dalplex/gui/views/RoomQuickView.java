package com.dalplex.gui.views;

import com.dalplex.data.Room;
import com.dalplex.gui.Window;
import com.dalplex.gui.editors.RoomEditor;
import com.dalplex.gui.fullviews.RoomView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;

public class RoomQuickView extends QuickView implements ActionListener{
    private static final String[] headerText = {"Description", "Edit", "View"};
    private ArrayList<Room> rooms;
    private JButton[] editButtons, viewButtons;
    public RoomQuickView(int width, int height, Connection conn, Window w){
        super(width, height, conn,w, "Rooms", 3, headerText);
        rooms = new ArrayList<>();
        getElements();
        editButtons = new JButton[rooms.size()];
        viewButtons = new JButton[rooms.size()];
        for(int i = 0;i < rooms.size();i++){
            Room m = rooms.get(i);
            content.add(new JLabel(m.getDescription()));

            editButtons[i] = new JButton("X");
            editButtons[i].setHorizontalTextPosition(SwingConstants.CENTER);
            editButtons[i].addActionListener(this);

            viewButtons[i] = new JButton("X");
            viewButtons[i].setHorizontalTextPosition(SwingConstants.CENTER);
            viewButtons[i].addActionListener(this);

            content.add(editButtons[i]);
            content.add(viewButtons[i]);
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
    public void actionPerformed(ActionEvent e) {
        //int index;
        for(int i = 0;i < viewButtons.length;i++){
            if(e.getSource() == viewButtons[i]){
                getWindow().setActivePanel(new RoomView(500, 500, rooms.get(i)), true);
            }
            if (e.getSource() == editButtons[i]) {
                getWindow().setActivePanel(new RoomEditor(500, 500, rooms.get(i)), true);
            }
        }
        if(e.getSource()==getNewObjectButton()){
            getWindow().setActivePanel(new RoomEditor(500, 500, new Room(getConnection())), true);
        }
    }
}

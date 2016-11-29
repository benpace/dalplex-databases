package com.dalplex.gui.editors;

import com.dalplex.data.Program;
import com.dalplex.data.Room;
import com.dalplex.gui.PreviousPanelHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EventEditor extends Editor implements ActionListener{
    private Program event;
    private final String[] fieldNames = {
            "Description", "Date", "Room"
    };
    private JLabel[] fieldLabels;
    private JComponent[] data;
    private ArrayList<Room> rooms;

    public EventEditor(int width, int height, Program e) {
        super(width, height, "Event");
        this.event = e;

        fieldLabels = new JLabel[fieldNames.length];
        data = new JComponent[fieldNames.length];

        //data = new JLabel[fieldNames.length];
        data[0] = new JTextField(event.getDescription());
        data[1] = new JTextField(""+event.getDate());


        //Select from room types
        rooms = new ArrayList<>();
        ArrayList<String> roomNames = new ArrayList<>();
        try{
            Statement stmt = e.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM room");
            while(rs.next()){
                roomNames.add(rs.getString("room_desc"));
                rooms.add(new Room(rs.getInt("room_id"), e.getConnection()));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        data[2] = new JComboBox(roomNames.toArray());
        int currentIndex = rooms.indexOf(e.getRoom());
        ((JComboBox)data[2]).setSelectedIndex(currentIndex);

        for (int i = 0; i < fieldNames.length; i++) {
            fieldLabels[i] = new JLabel(fieldNames[i]);
            fieldLabels[i].setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.BLACK));
            fieldLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
            data[i].setAlignmentX(SwingConstants.CENTER);
            getFields().add(fieldLabels[i]);
            getFields().add(data[i]);
        }
        for(int i =0;i < 34;i++)
            getFields().add(new JSeparator());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == getSubmit()){
            //Check for valid info
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = null;
            try{
                newDate = formatter.parse(((JTextField)data[1]).getText());
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Birthday is of incorrect format");
                return;
            }


            event.setDescription(((JTextField)data[0]).getText());
            event.setDate(newDate);


            int selectedIndex = ((JComboBox)data[2]).getSelectedIndex();
            Room room = rooms.get(selectedIndex);
            event.setRoom(room);


            event.publishToDB();
            PreviousPanelHandler.goBack();



        }
    }
}

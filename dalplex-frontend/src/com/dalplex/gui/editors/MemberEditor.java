package com.dalplex.gui.editors;

import com.dalplex.data.Member;
import com.dalplex.data.Membership;
import com.dalplex.gui.PreviousPanelHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MemberEditor extends Editor implements ActionListener{
    private Member member;
    private final String[] fieldNames = {
            "First Name", "Last Name", "Phone Number",
            "Birthday", "Address", "City", "Postcode", "Membership Type"
    };
    private JLabel[] fieldLabels;
    private JComponent[] data;
    private ArrayList<Membership> memTypes;

    public MemberEditor(int width, int height, Member e) {
        super(width, height, "Member: " + e.getFname());
        this.member = e;

        fieldLabels = new JLabel[fieldNames.length];
        data = new JComponent[fieldNames.length];

        //data = new JLabel[fieldNames.length];
        data[0] = new JTextField(member.getFname());
        data[1] = new JTextField(member.getLname());
        data[2] = new JTextField(member.getPhone());
        data[3] = new JTextField("" + member.getBirthday());
        data[4] = new JTextField(member.getAddress());
        data[5] = new JTextField(member.getCity());
        data[6] = new JTextField(member.getPostcode());


        //Select from membership types
        memTypes = new ArrayList<>();
        ArrayList<String> memNames = new ArrayList<>();
        try{
            Statement stmt = e.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM membership");
            while(rs.next()){
                memNames.add(rs.getString("mem_desc"));
                memTypes.add(new Membership(rs.getInt("id"), e.getConnection()));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        data[7] = new JComboBox(memNames.toArray());
        int currentIndex = memTypes.indexOf(e.getMemType());
        ((JComboBox)data[7]).setSelectedIndex(currentIndex);

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
            if(!((JTextField)data[2]).getText().matches("\\b\\d{3}[-.]?\\d{3}[-.]?\\d{4}\\b")){
                JOptionPane.showMessageDialog(null, "Phone is of incorrect format");
                return;
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = null;
            try{
                newDate = formatter.parse(((JTextField)data[3]).getText());
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Birthday is of incorrect format");
                return;
            }
            if(!((JTextField)data[6]).getText().matches("[a-zA-Z][0-9][a-zA-Z][0-9][a-zA-Z][0-9]")){
                JOptionPane.showMessageDialog(null, "Postcode is of incorrect format");
                return;
            }

            member.setFname(((JTextField)data[0]).getText());
            member.setLname(((JTextField)data[1]).getText());
            member.setPhone(((JTextField)data[2]).getText());
            member.setDate(newDate);
            member.setAddress(((JTextField)data[4]).getText());
            member.setCity(((JTextField)data[5]).getText());
            member.setPostcode(((JTextField)data[6]).getText());

            int selectedIndex = ((JComboBox)data[7]).getSelectedIndex();
            Membership membership = memTypes.get(selectedIndex);
            member.setMemType(membership);


            member.publishToDB();
            PreviousPanelHandler.goBack();



        }
    }
}

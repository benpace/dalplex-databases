package com.dalplex.gui.views;

import com.dalplex.data.Member;
import com.dalplex.gui.Window;
import com.dalplex.gui.editors.MemberEditor;
import com.dalplex.gui.fullviews.MemberView;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;


public class MemberQuickView extends QuickView implements ActionListener{
    private static final String[] headerText = {"Name", "Postcode", "Membership", "Edit", "View"};
    private ArrayList<Member> members;
    private JButton[] editButtons, viewButtons;
    public MemberQuickView(int width, int height, Connection conn, Window w){
        super(width, height, conn, w,"Members", 5, headerText);
        members = new ArrayList<>();
        getElements();
        editButtons = new JButton[members.size()];
        viewButtons = new JButton[members.size()];
        for(int i = 0;i < members.size();i++){
            Member m = members.get(i);
            content.add(new JLabel(m.getFname() + " " + m.getLname()));
            content.add(new JLabel(m.getPostcode()));
            content.add(new JLabel(m.getMemType().getDescription()));

            editButtons[i] = new JButton("X");
            editButtons[i].setHorizontalTextPosition(SwingConstants.CENTER);
            editButtons[i].addActionListener(this);

            viewButtons[i] = new JButton("X");
            viewButtons[i].setHorizontalTextPosition(SwingConstants.CENTER);
            viewButtons[i].addActionListener(this);

            content.add(editButtons[i]);
            content.add(viewButtons[i]);
        }
        for(int i = 0;i < ((5 * 21) - (5 * members.size()));i++)  content.add(new JSeparator());
    }


    public void getElements() {
        try {
            Statement stmnt = getConnection().createStatement();
            String sql = "SELECT id FROM " + Member.TABLE;
            stmnt.execute(sql);
            ResultSet rs = stmnt.getResultSet();

            while(rs.next()){
                members.add(new Member(rs.getInt("id"), getConnection()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        //int index;
        for(int i = 0;i < viewButtons.length;i++) {
            if (e.getSource() == viewButtons[i]) {
                getWindow().setActivePanel(new MemberView(500, 500, members.get(i)), true);
            }
            if (e.getSource() == editButtons[i]) {
                getWindow().setActivePanel(new MemberEditor(500, 500, members.get(i)), true);
            }
        }
        if(e.getSource()==getNewObjectButton()){
            getWindow().setActivePanel(new MemberEditor(500, 500, new Member(getConnection())), true);
        }

    }
}

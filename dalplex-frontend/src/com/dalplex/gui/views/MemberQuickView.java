package com.dalplex.gui.views;

import com.dalplex.data.Member;
import com.dalplex.gui.views.QuickView;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Ben Pace
 */

public class MemberQuickView extends QuickView {
    private static final String[] headerText = {"Name", "Postcode", "Membership", "Edit", "Delete"};
    private ArrayList<Member> members;
    public MemberQuickView(int width, int height, Connection conn){
        super(width, height, conn, "Members", 5, headerText);
        members = new ArrayList<>();
        getElements();
        for(Member m: members){
            content.add(new JLabel(m.getFname() + " " + m.getLname()));
            content.add(new JLabel(m.getPostcode()));
            content.add(new JLabel(m.getMemType().getDescription()));
            content.add(new JLabel("X"));
            content.add(new JLabel("X"));
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
}

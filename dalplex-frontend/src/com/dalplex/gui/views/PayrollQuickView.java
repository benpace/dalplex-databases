package com.dalplex.gui.views;

import com.dalplex.data.Member;
import com.dalplex.data.Payroll;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Ben Pace
 */
public class PayrollQuickView extends QuickView {
    private static final String[] headerText = {"Employee", "Last Worked", "Earned", "Total Earned", "Edit", "Delete"};
    private ArrayList<Payroll> payroll;
    public PayrollQuickView(int width, int height, Connection conn){
        super(width, height, conn, "Payroll", 6, headerText);
        payroll = new ArrayList<>();
        getElements();
        for(Payroll m: payroll){
            content.add(new JLabel(m.getEmployee().getFname() + " " + m.getEmployee().getLname()));
            content.add(new JLabel("" + m.getLastWorked()));
            content.add(new JLabel("" + m.getEarned()));
            content.add(new JLabel("" + m.getTotEarned()));
            content.add(new JLabel("X"));
            content.add(new JLabel("X"));
        }
        for(int i = 0;i < ((6 * 21) - (6 * payroll.size()));i++)  content.add(new JSeparator());
    }


    public void getElements() {
        try {
            Statement stmnt = getConnection().createStatement();
            String sql = "SELECT payroll_id FROM " + Payroll.TABLE;
            stmnt.execute(sql);
            ResultSet rs = stmnt.getResultSet();

            while(rs.next()){
                payroll.add(new Payroll(rs.getInt("payroll_id"), getConnection()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

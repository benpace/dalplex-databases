package com.dalplex.gui.views;

import com.dalplex.data.Member;
import com.dalplex.data.Payroll;
import com.dalplex.gui.Window;
import com.dalplex.gui.editors.PayrollEditor;

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
public class PayrollQuickView extends QuickView implements ActionListener{
    private static final String[] headerText = {"Employee", "Last Worked", "Earned", "Total Earned", "Edit"};
    private ArrayList<Payroll> payroll;
    private JButton[] editButtons;
    public PayrollQuickView(int width, int height, Connection conn, Window w){
        super(width, height, conn, w,"Payroll", 5, headerText);
        payroll = new ArrayList<>();
        getElements();
        editButtons = new JButton[payroll.size()];
        for(int i = 0;i < payroll.size();i++){
            Payroll m = payroll.get(i);
            content.add(new JLabel(m.getEmployee().getFname() + " " + m.getEmployee().getLname()));
            content.add(new JLabel("" + m.getLastWorked()));
            content.add(new JLabel("" + m.getEarned()));
            content.add(new JLabel("" + m.getTotEarned()));

            editButtons[i] = new JButton("X");
            editButtons[i].setHorizontalTextPosition(SwingConstants.CENTER);
            editButtons[i].addActionListener(this);

            content.add(editButtons[i]);

        }
        for(int i = 0;i < ((5 * 21) - (5 * payroll.size()));i++)  content.add(new JSeparator());
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

    @Override
    public void actionPerformed(ActionEvent e) {
        for(int i = 0;i < editButtons.length;i++) {
            if (e.getSource() == editButtons[i]) {
                getWindow().setActivePanel(new PayrollEditor(500, 500, payroll.get(i)), true);
            }
        }
        if(e.getSource()==getNewObjectButton()){
            getWindow().setActivePanel(new PayrollEditor(500, 500, new Payroll(getConnection())), true);
        }
    }
}

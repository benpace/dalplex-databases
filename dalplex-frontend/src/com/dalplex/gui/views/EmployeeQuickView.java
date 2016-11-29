package com.dalplex.gui.views;

import com.dalplex.data.Employee;
import com.dalplex.data.Member;
import com.dalplex.gui.Window;
import com.dalplex.gui.editors.EmployeeEditor;
import com.dalplex.gui.fullviews.EmployeeView;

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
public class EmployeeQuickView extends QuickView implements ActionListener{
    private static final String[] headerText = {"Name", "Title", "Salary", "Edit", "View"};
    private ArrayList<Employee> employees;
    private JButton[] editButtons, viewButtons;
    public EmployeeQuickView(int width, int height, Connection conn, Window w){
        super(width, height, conn, w, "Employees", 5, headerText);
        employees = new ArrayList<>();
        getElements();
        editButtons = new JButton[employees.size()];
        viewButtons = new JButton[employees.size()];
        for(int i = 0; i< employees.size();i++){
            Employee m = employees.get(i);
            content.add(new JLabel(m.getFname() + " " + m.getLname()));
            content.add(new JLabel(m.getTitle()));
            content.add(new JLabel(""+m.getSalary()));

            editButtons[i] = new JButton("X");
            editButtons[i].setHorizontalTextPosition(SwingConstants.CENTER);
            editButtons[i].addActionListener(this);

            viewButtons[i] = new JButton("X");
            viewButtons[i].setHorizontalTextPosition(SwingConstants.CENTER);
            viewButtons[i].addActionListener(this);

            content.add(editButtons[i]);
            content.add(viewButtons[i]);
        }
        for(int i = 0;i < ((5 * 21) - (5 * employees.size()));i++)  content.add(new JSeparator());   //Fill out remainder of page, keeps element size consistent
    }


    public void getElements(){
        try {
            Statement stmnt = getConnection().createStatement();
            String sql = "SELECT id FROM " + Employee.TABLE;
            stmnt.execute(sql);
            ResultSet rs = stmnt.getResultSet();

            while(rs.next()){
                employees.add(new Employee(rs.getInt("id"), getConnection()));
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
                getWindow().setActivePanel(new EmployeeView(500, 500, employees.get(i)), true);
            }
            if (e.getSource() == editButtons[i]) {
                getWindow().setActivePanel(new EmployeeEditor(500, 500, employees.get(i)), true);
            }
        }
        if(e.getSource()==getNewObjectButton()){
            getWindow().setActivePanel(new EmployeeEditor(500, 500, new Employee(getConnection())), true);
        }
    }
}

package com.dalplex.gui.views;

import com.dalplex.data.Employee;
import com.dalplex.data.Member;

import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Ben Pace
 */
public class EmployeeQuickView extends QuickView{
    private static final String[] headerText = {"Name", "Title", "Salary", "Edit", "Delete"};
    private ArrayList<Employee> employees;
    public EmployeeQuickView(int width, int height, Connection conn){
        super(width, height, conn, "Employees", 5, headerText);
        employees = new ArrayList<>();
        getElements();
        for(Employee m: employees){
            content.add(new JLabel(m.getFname() + " " + m.getLname()));
            content.add(new JLabel(m.getTitle()));
            content.add(new JLabel(""+m.getSalary()));
            content.add(new JLabel("X"));
            content.add(new JLabel("X"));
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
}

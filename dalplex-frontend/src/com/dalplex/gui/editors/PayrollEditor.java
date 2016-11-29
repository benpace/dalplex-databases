package com.dalplex.gui.editors;

import com.dalplex.data.Payroll;
import com.dalplex.data.Employee;
import com.dalplex.gui.PreviousPanelHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Ben Pace
 */
public class PayrollEditor extends Editor implements ActionListener{
    private Payroll payroll;
    private final String[] fieldNames = {
            "Employee", "Last Worked", "Earned",
            "Total Earned"
    };
    private JLabel[] fieldLabels;
    private JComponent[] data;
    private ArrayList<Employee> employees;

    public PayrollEditor(int width, int height, Payroll e) {
        super(width, height, "Payroll");
        this.payroll = e;

        fieldLabels = new JLabel[fieldNames.length];
        data = new JComponent[fieldNames.length];


        //Select from employee types
        employees = new ArrayList<>();
        ArrayList<String> employeeNames = new ArrayList<>();
        try{
            Statement stmt = e.getConnection().createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM employee");
            while(rs.next()){
                employeeNames.add(rs.getString("fname") + (rs.getString("lname")));
                employees.add(new Employee(rs.getInt("id"), e.getConnection()));
            }
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        data[0] = new JComboBox(employeeNames.toArray());
        int currentIndex = employees.indexOf(e.getEmployee());
        ((JComboBox)data[0]).setSelectedIndex(currentIndex);

        data[1] = new JTextField(""+payroll.getLastWorked());
        data[2] = new JTextField(""+payroll.getEarned());
        data[3] = new JTextField("" + payroll.getTotEarned());


        for (int i = 0; i < fieldNames.length; i++) {
            fieldLabels[i] = new JLabel(fieldNames[i]);
            fieldLabels[i].setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.BLACK));
            fieldLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
            data[i].setAlignmentX(SwingConstants.CENTER);
            getFields().add(fieldLabels[i]);
            getFields().add(data[i]);
        }

        for(int i =0;i < 38;i++)
            getFields().add(new JSeparator());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == getSubmit()){
            //Check for valid info
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = null;
            try{
                newDate = formatter.parse(((JTextField)data[3]).getText());
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Last Worked is of incorrect format");
                return;
            }
            try{
                Integer.parseInt(((JTextField)data[2]).getText());
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Earned is of incorrect format");
                return;
            }
            try{
                Integer.parseInt(((JTextField)data[3]).getText());
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Total Earned is of incorrect format");
                return;
            }


            payroll.setLastWorked(newDate);
            payroll.setEarned(Integer.parseInt(((JTextField)data[2]).getText()));
            payroll.setTotEarned(Integer.parseInt(((JTextField)data[3]).getText()));


            int selectedIndex = ((JComboBox)data[0]).getSelectedIndex();
            Employee employee = employees.get(selectedIndex);
            payroll.setEmployee(employee);


            payroll.publishToDB();
            PreviousPanelHandler.goBack();



        }
    }
}

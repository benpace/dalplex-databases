package com.dalplex.gui.editors;

import com.dalplex.data.Employee;
import com.dalplex.gui.PreviousPanelHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmployeeEditor extends Editor implements ActionListener{
    private Employee employee;
    private final String[] fieldNames = {
            "First Name", "Last Name", "Title", "Phone Number",
            "Birthday", "Address", "City", "Postcode", "Salary"
    };
    private JLabel[] fieldLabels;
    private JComponent[] data;

    public EmployeeEditor(int width, int height, Employee e) {
        super(width, height, "Employee: " + e.getFname());
        this.employee = e;

        fieldLabels = new JLabel[fieldNames.length];
        data = new JComponent[fieldNames.length];


        data[0] = new JTextField(employee.getFname());
        data[1] = new JTextField(employee.getLname());
        data[2] = new JTextField(employee.getTitle());
        data[3] = new JTextField(employee.getPhone());
        data[4] = new JTextField("" + employee.getBirthday());
        data[5] =  new JTextField(employee.getAddress());
        data[6] = new JTextField(employee.getCity());
        data[7] = new JTextField(employee.getPostcode());
        data[8] = new JTextField(""+employee.getSalary());


        for (int i = 0; i < fieldNames.length; i++) {
            fieldLabels[i] = new JLabel(fieldNames[i]);
            fieldLabels[i].setBorder(BorderFactory.createMatteBorder(0, 0, 0, 2, Color.BLACK));
            fieldLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
            data[i].setAlignmentX(SwingConstants.CENTER);
            getFields().add(fieldLabels[i]);
            getFields().add(data[i]);
        }
        for(int i =0;i < 22;i++)
            getFields().add(new JSeparator());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == getSubmit()){
            //Check for valid info
            if(!((JTextField)data[3]).getText().matches("\\b\\d{3}[-.]?\\d{3}[-.]?\\d{4}\\b")){
                JOptionPane.showMessageDialog(null, "Phone is of incorrect format");
                return;
            }
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date newDate = null;
            try{
                newDate = formatter.parse(((JTextField)data[4]).getText());
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Birthday is of incorrect format");
                return;
            }
            if(!((JTextField)data[7]).getText().matches("[a-zA-Z][0-9][a-zA-Z][0-9][a-zA-Z][0-9]")){
                JOptionPane.showMessageDialog(null, "Postcode is of incorrect format");
                return;
            }
            try{
                Double.parseDouble(((JTextField)data[8]).getText());
            }
            catch(Exception ex){
                JOptionPane.showMessageDialog(null, "Salary is of incorrect format");
                return;
            }


            employee.setFname(((JTextField)data[0]).getText());
            employee.setLname(((JTextField)data[1]).getText());
            employee.setTitle(((JTextField)data[2]).getText());
            employee.setPhone(((JTextField)data[3]).getText());
            employee.setDate(newDate);
            employee.setAddress(((JTextField)data[5]).getText());
            employee.setCity(((JTextField)data[6]).getText());
            employee.setPostcode(((JTextField)data[7]).getText());
            employee.setSalary(Double.parseDouble(((JTextField)data[8]).getText()));


            employee.publishToDB();
            PreviousPanelHandler.goBack();



        }
    }
}

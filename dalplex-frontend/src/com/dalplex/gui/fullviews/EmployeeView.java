package com.dalplex.gui.fullviews;

import com.dalplex.data.Employee;
import com.dalplex.gui.FullView;

import javax.swing.*;
import java.awt.*;


public class EmployeeView extends FullView {
    private Employee employee;
    private final String[] fieldNames = {
            "First Name", "Last Name", "Title", "Phone Number",
            "Birthday", "Address", "City", "Postcode", "Salary"
    };
    private JLabel[] fieldLabels, data;
    public EmployeeView(int width, int height, Employee e) {
        super(width, height, "Employee: " + e.getFname());
        this.employee = e;

        fieldLabels = new JLabel[fieldNames.length];
        data = new JLabel[fieldNames.length];

        //data = new JLabel[fieldNames.length];
        data[0] = new JLabel(employee.getFname());
        data[1] = new JLabel(employee.getLname());
        data[2] = new JLabel(employee.getTitle());
        data[3] = new JLabel(employee.getPhone());
        data[4] = new JLabel(""+employee.getBirthday());
        data[5] = new JLabel(employee.getAddress());
        data[6] = new JLabel(employee.getCity());
        data[7] = new JLabel(employee.getPostcode());
        data[8] = new JLabel(employee.getSalary()+"");


        for(int i =0;i < fieldNames.length;i++){
            fieldLabels[i] = new JLabel(fieldNames[i]);
            fieldLabels[i].setBorder(BorderFactory.createMatteBorder(0,0,0,2, Color.BLACK));
            fieldLabels[i].setHorizontalAlignment(SwingConstants.CENTER);
            data[i].setHorizontalAlignment(SwingConstants.CENTER);
            getFields().add(fieldLabels[i]);
            getFields().add(data[i]);
        }
    }
}

package com.dalplex.data;

import java.sql.Connection;

/**
 * @author Ben Pace
 */
public class Employee extends Person {
    private final String TABLE = "employee";
    private String title;
    private double salary;


    public Employee(Connection conn) {
        super(conn);
    }

    public Employee(int ID, Connection conn) {
        super(ID, conn);
    }

    public void setTitle(String title){this.title = title;  setConcurrent(false);}
    public void setSalary(double salary){this.salary = salary;  setConcurrent(false);}
    public String getTitle(){return title;}
    public double getSalary(){return salary;}


    @Override
    public void retrieveFields() {
        //TODO: Implement retreiveFields
    }

    /**
     * Attempts to update or insert object's data to the DB
     * If ID is -1, it should insert a new row and get the ID
     * Otherwise make the changes present
     */
    @Override
    public void publishToDB() {
        //TODO: Implement publishToDB
    }
}

package com.dalplex.data;

import java.sql.Connection;
import java.util.Date;

/**
 * @author Ben Pace
 */
public class Payroll extends DBItem {
    private final String TABLE = "payroll";
    private Employee employee;
    private Date lastWorked;
    private int earned, totEarned;
    /**
     * Defines a new object, not yet present on the database
     *
     * @param conn Database connection
     */
    public Payroll(Connection conn) {
        super(conn);
    }

    /**
     * Defines a new object, already present on the database
     * Uses {@link #retrieveFields()} to find remaining fields
     *
     * @param ID   unique id within table
     * @param conn DB connection
     */
    public Payroll(int ID, Connection conn) {
        super(ID, conn);
    }

    public void setEmployee(Employee employee){this.employee = employee;  setConcurrent(false);}
    public void setLastWorked(Date lastWorked){this.lastWorked = lastWorked;  setConcurrent(false);}
    public void setEarned(int earned){this.earned = earned;  setConcurrent(false);}
    public void setTotEarned(int totEarned){this.totEarned = totEarned;  setConcurrent(false);}

    public Employee getEmployee(){return employee;}
    public Date getLastWorked(){return lastWorked;}
    public int getEarned(){return earned;}
    public int getTotEarned(){return totEarned;}

    @Override
    public void retrieveFields() {
        //TODO: Implement rf
    }

    /**
     * Attempts to update or insert object's data to the DB
     * If ID is -1, it should insert a new row and get the ID
     * Otherwise make the changes present
     */
    @Override
    public void publishToDB() {
        //TODO: Implement p2db
    }
}

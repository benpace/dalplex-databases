package com.dalplex.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 * @author Ben Pace
 */
public class Payroll extends DBItem {
    public static final String TABLE = "payroll";
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
        try {
            Statement stmnt = getConnection().createStatement();
            String sql = "SELECT * FROM " + TABLE + " WHERE payroll_id=" + getID();
            stmnt.execute(sql);
            ResultSet rs = stmnt.getResultSet();
            rs.first();
            setEmployee(new Employee(rs.getInt("employee_id"), getConnection()));
            setLastWorked(rs.getDate("last_worked"));
            setEarned(rs.getInt("earned"));
            setTotEarned(rs.getInt("total_earned"));
            setConcurrent(true);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    /**
     * Attempts to update or insert object's data to the DB
     * If ID is -1, it should insert a new row and get the ID
     * Otherwise make the changes present
     */
    @Override
    public void publishToDB() {
        boolean newEntry;
        if(getID() < 0) newEntry = true;
        else            newEntry = false;

        //New Entry
        try {
            Statement stmnt = getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            String sql = "SELECT * FROM " + TABLE;
            if(!newEntry)
                sql += " WHERE payroll_id=" + getID();
            ResultSet rs = stmnt.executeQuery(sql);

            //find id value for new entry
            int maxID = 0;
            if(newEntry) {
                Statement sizeStmnt = getConnection().createStatement();
                ResultSet sizeRS = sizeStmnt.executeQuery("SELECT MAX(payroll_id) FROM " + TABLE);
                sizeRS.first();
                maxID = sizeRS.getInt("MAX(payroll_id)");
                sizeRS.close();
            }

            if(newEntry) {
                rs.moveToInsertRow();
                rs.updateInt("payroll_id", ++maxID);
                setID(maxID);
            }
            else
                rs.first();

            rs.updateInt("employee_id", employee.getID());
            rs.updateDate("last_worked", new java.sql.Date(getLastWorked().getTime()));
            rs.updateInt("earned", earned);
            rs.updateInt("total_earned", totEarned);


            if(newEntry)    rs.insertRow();
            else            rs.updateRow();

            rs.beforeFirst();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

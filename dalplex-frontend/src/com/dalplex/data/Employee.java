package com.dalplex.data;

import java.sql.*;

public class Employee extends Person {
    public static final String TABLE = "employee";
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
        try {
            Statement stmnt = getConnection().createStatement();
            String sql = "SELECT * FROM " + TABLE + " WHERE id=" + getID();
            stmnt.execute(sql);
            ResultSet rs = stmnt.getResultSet();
            rs.first();
            setFname(rs.getString("fname"));
            setLname(rs.getString("lname"));
            setPhone(rs.getString("phone_num"));
            setDate(rs.getDate("birth_day"));
            setAddress(rs.getString("address"));
            setCity(rs.getString("city"));
            setPostcode(rs.getString("post_code"));
            setTitle(rs.getString("title"));
            setSalary(rs.getDouble("salary"));
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
                sql += " WHERE id=" + getID();
            ResultSet rs = stmnt.executeQuery(sql);

            //find id value for new entry
            int maxID = 0;
            if(newEntry) {
                Statement sizeStmnt = getConnection().createStatement();
                ResultSet sizeRS = sizeStmnt.executeQuery("SELECT MAX(id) FROM " + TABLE);
                sizeRS.first();
                maxID = sizeRS.getInt("MAX(id)");
                sizeRS.close();
            }

            if(newEntry) {
                rs.moveToInsertRow();
                rs.updateInt("id", ++maxID);
                setID(maxID);
            }
            else
                rs.first();

            rs.updateString("fname", getFname());
            rs.updateString("lname", getLname());
            rs.updateString("phone_num", getPhone());
            rs.updateDate("birth_day", new Date(getBirthday().getTime()));
            rs.updateString("address", getAddress());
            rs.updateString("city", getCity());
            rs.updateString("post_code", getPostcode());
            rs.updateString("title", title);
            rs.updateDouble("salary", salary);

            if(newEntry)    rs.insertRow();
            else            rs.updateRow();

            rs.beforeFirst();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}

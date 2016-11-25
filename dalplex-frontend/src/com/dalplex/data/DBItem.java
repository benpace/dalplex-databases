package com.dalplex.data;

import java.sql.Connection;

/**
 * Defines an abstract class for all objects created by the database
 * Given the unique ID and table it is from, find the remaining fields and populate
 * can be concurrent w/ DB or not, if being edited.
 * @author Ben Pace
 */
public abstract class DBItem {
    private int ID;
    private Connection conn;
    private boolean concurrent;

    /**
     * Defines a new object, not yet present on the database
     * @param conn Database connection
     */
    public DBItem(Connection conn){
        this.conn = conn;
        this.ID = -1;
        this.concurrent = false;
    }


    /**
     * Defines a new object, already present on the database
     * Uses {@link #retrieveFields()} to find remaining fields
     * @param ID unique id within table
     * @param conn DB connection
     */
    public DBItem(int ID, Connection conn){
        this.ID = ID;
        this.conn = conn;
        this.concurrent = true;
        retrieveFields();
    }

    public boolean getConcurrent(){return concurrent;}
    public int getID(){return ID;}
    public Connection getConnection(){return conn;}

    public void setConcurrent(boolean b){this.concurrent = b;}
    public void setID(int ID){this.ID = ID; setConcurrent(false);}
    public void setConnection(Connection conn){this.conn = conn;    setConcurrent(false);}

    public abstract void retrieveFields();

    /**
     * Attempts to update or insert object's data to the DB
     * If ID is -1, it should insert a new row and get the ID
     * Otherwise make the changes present
     */
    public abstract void publishToDB();

    public boolean equals(Object o){
        /*if(o instanceof DBItem){
            return ((DBItem) o).getID() == this.getID();
        }
        else
            return false;*/

        if(o.getClass().isInstance(this)){
            return this.getClass().cast(o).getID() == this.getID();
        }
        else
            return false;
    }
}

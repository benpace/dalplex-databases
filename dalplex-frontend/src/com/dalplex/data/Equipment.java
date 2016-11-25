package com.dalplex.data;

import java.sql.Connection;

/**
 * @author Ben Pace
 */
public class Equipment extends DBItem {
    private final String TABLE = "equipment";
    private String description;
    private int qoh;

    /**
     * Defines a new object, not yet present on the database
     *
     * @param conn Database connection
     */
    public Equipment(Connection conn) {
        super(conn);
    }

    /**
     * Defines a new object, already present on the database
     * Uses {@link #retrieveFields()} to find remaining fields
     *
     * @param ID   unique id within table
     * @param conn DB connection
     */
    public Equipment(int ID, Connection conn) {
        super(ID, conn);
    }

    public void setDescription(String description){this.description = description;  setConcurrent(false);}
    public void setQoh(int qoh){this.qoh = qoh;  setConcurrent(false);}

    public String getDescription(){return description;}
    public int getQoh(){return qoh;}

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

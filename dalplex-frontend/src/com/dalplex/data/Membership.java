package com.dalplex.data;

import java.sql.Connection;

/**
 * @author Ben Pace
 */
public class Membership extends DBItem {
    private final String TABLE = "membership";
    private String description;

    /**
     * Defines a new object, not yet present on the database
     *
     * @param conn Database connection
     */
    public Membership(Connection conn) {
        super(conn);
    }

    /**
     * Defines a new object, already present on the database
     * Uses {@link #retrieveFields()} to find remaining fields
     *
     * @param ID   unique id within table
     * @param conn DB connection
     */
    public Membership(int ID, Connection conn) {
        super(ID, conn);
    }

    public void setDescription(String description){this.description = description;  setConcurrent(false);}
    public String getDescription(){return description;}


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
        //TODO: Implement publishTODB
    }
}

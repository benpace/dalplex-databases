package com.dalplex.data;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Ben Pace
 */
public class Program extends DBItem {
    private String description;
    private Date date;
    private Room room;
    private ArrayList<Member> members;

    /**
     * Defines a new object, not yet present on the database
     *
     * @param conn Database connection
     */
    public Program(Connection conn) {
        super(conn);
    }

    /**
     * Defines a new object, already present on the database
     * Uses {@link #retrieveFields()} to find remaining fields
     *
     * @param ID   unique id within table
     * @param conn DB connection
     */
    public Program(int ID, Connection conn) {
        super(ID, conn);
    }

    public void setDescription(String description){this.description = description;  setConcurrent(false);}
    public void setDate(Date date){this.date = date;  setConcurrent(false);}
    public void setRoom(Room room){this.room = room;  setConcurrent(false);}

    public String getDescription(){return description;}
    public Date getDate(){return date;}
    public Room getRoom(){return room;}
    /**
     * Gets a copy of the member list, to preserve data consistency
     * @return A copy of the member list
     */
    public ArrayList<Member> getMembers(){return new ArrayList<Member>(members);}

    public void addMember(Member m){
        members.add(m);
        setConcurrent(false);
    }
    public boolean removeMember(Member m){
        if(members.remove(m)){
            setConcurrent(false);
            return true;
        }
        else
            return false;
    }

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

package com.dalplex.data;

import java.sql.Connection;
import java.util.ArrayList;

/**
 * @author Ben Pace
 */
public class Room extends DBItem{
    private final String TABLE = "room";
    private String description;
    private ArrayList<Equipment> equipment;

    /**
     * Defines a new object, not yet present on the database
     *
     * @param conn Database connection
     */
    public Room(Connection conn) {
        super(conn);
    }

    /**
     * Defines a new object, already present on the database
     * Uses {@link #retrieveFields()} to find remaining fields
     *
     * @param ID   unique id within table
     * @param conn DB connection
     */
    public Room(int ID, Connection conn) {
        super(ID, conn);
    }

    public void setDescription(String description){this.description = description;  setConcurrent(false);}

    public String getDescription(){return description;}
    /**
     * Gets a copy of the equipment, to preserve data consistency
     * @return A copy of the equipment list
     */
    public ArrayList<Equipment> getEquipment(){return new ArrayList<Equipment>(equipment);}

    public void addEquipment(Equipment e){
        equipment.add(e);
        setConcurrent(false);
    }

    public boolean removeEquipment(Equipment e){
        if(equipment.remove(e)) {
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

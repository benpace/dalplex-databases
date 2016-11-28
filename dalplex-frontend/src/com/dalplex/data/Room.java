package com.dalplex.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Ben Pace
 */
public class Room extends DBItem{
    public static final String TABLE = "room";
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
        equipment = new ArrayList<>();
        try {
            Statement stmnt = getConnection().createStatement();
            String sql = "SELECT * FROM " + TABLE + " WHERE room_id=" + getID();
            stmnt.execute(sql);
            ResultSet rs = stmnt.getResultSet();
            rs.first();
            setDescription(rs.getString("room_desc"));

            //Get equipment
            stmnt = getConnection().createStatement();
            sql = "SELECT * FROM room_equip WHERE room_id=" + getID();
            stmnt.execute(sql);
            rs = stmnt.getResultSet();
            while(rs.next()){
                equipment.add(new Equipment(rs.getInt("equip_id"), getConnection()));
            }

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
                sql += " WHERE room_id=" + getID();
            ResultSet rs = stmnt.executeQuery(sql);

            //find id value for new entry
            int maxID = 0;
            if(newEntry) {
                Statement sizeStmnt = getConnection().createStatement();
                ResultSet sizeRS = sizeStmnt.executeQuery("SELECT MAX(room_id) FROM " + TABLE);
                sizeRS.first();
                maxID = sizeRS.getInt("MAX(room_id)");
                sizeRS.close();
            }

            if(newEntry) {
                rs.moveToInsertRow();
                rs.updateInt("room_id", ++maxID);
                setID(maxID);
            }
            else
                rs.first();

            rs.updateString("room_desc", getDescription());


            if(newEntry)    rs.insertRow();
            else            rs.updateRow();

            rs.beforeFirst();
            rs.close();


            stmnt = getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmnt.executeQuery("SELECT * FROM room_equip WHERE room_id=" + getID());

            ArrayList<Equipment> onDB = new ArrayList<>(), notOnDB = new ArrayList<>(), notinFrontEnd = new ArrayList<>();
            while(rs.next()){
                onDB.add(new Equipment(rs.getInt("equip_id"), getConnection()));
            }
            stmnt.close();
            //Members to delete
            for(int i = 0;i < onDB.size();i++){
                if(!equipment.contains(onDB.get(i)))
                    notinFrontEnd.add(onDB.get(i));
            }
            for(Equipment m: notinFrontEnd){
                stmnt = getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                rs = stmnt.executeQuery("SELECT * FROM room_equip WHERE " +
                        "room_id=" + getID() + " AND equip_id=" + m.getID());
                rs.first();
                rs.deleteRow();
                stmnt.close();
            }

            for(int i = 0;i < equipment.size();i++){
                if(!onDB.contains(equipment.get(i)))
                    notOnDB.add(equipment.get(i));
            }
            for(Equipment m: notOnDB){
                stmnt = getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                rs = stmnt.executeQuery("SELECT * FROM room_equip");
                rs.moveToInsertRow();
                rs.updateInt("room_id", getID());
                rs.updateInt("equip_id", m.getID());
                rs.insertRow();

                stmnt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

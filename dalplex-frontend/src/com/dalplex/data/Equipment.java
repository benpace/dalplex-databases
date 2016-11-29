package com.dalplex.data;

import java.sql.*;

public class Equipment extends DBItem {
    public static final String TABLE = "equipment";
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
        try {
            Statement stmnt = getConnection().createStatement();
            String sql = "SELECT * FROM " + TABLE + " WHERE equip_id=" + getID();
            stmnt.execute(sql);
            ResultSet rs = stmnt.getResultSet();
            rs.first();
            setDescription(rs.getString("equip_desc"));
            setQoh(rs.getInt("qoh"));
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
                sql += " WHERE equip_id=" + getID();
            ResultSet rs = stmnt.executeQuery(sql);

            //find id value for new entry
            int maxID = 0;
            if(newEntry) {
                Statement sizeStmnt = getConnection().createStatement();
                ResultSet sizeRS = sizeStmnt.executeQuery("SELECT MAX(equip_id) FROM " + TABLE);
                sizeRS.first();
                maxID = sizeRS.getInt("MAX(equip_id)");
                sizeRS.close();
            }

            if(newEntry) {
                rs.moveToInsertRow();
                rs.updateInt("equip_id", ++maxID);
                setID(maxID);
            }
            else
                rs.first();

            rs.updateString("equip_desc", getDescription());
            rs.updateInt("qoh", getQoh());


            if(newEntry)    rs.insertRow();
            else            rs.updateRow();

            rs.beforeFirst();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

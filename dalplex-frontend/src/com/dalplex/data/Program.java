package com.dalplex.data;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Ben Pace
 */
public class Program extends DBItem {
    public static final String TABLE = "program";
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
        members = new ArrayList<>();
        try {
            Statement stmnt = getConnection().createStatement();
            String sql = "SELECT * FROM " + TABLE + " WHERE event_id=" + getID();
            stmnt.execute(sql);
            ResultSet rs = stmnt.getResultSet();
            rs.first();
            setDescription(rs.getString("event_desc"));
            setDate(rs.getDate("event_date"));
            setRoom(new Room(rs.getInt("event_room"), getConnection()));

            //Get members
            stmnt = getConnection().createStatement();
            sql = "SELECT * FROM event_mem WHERE event_id=" + getID();
            stmnt.execute(sql);
            rs = stmnt.getResultSet();
            while(rs.next()){
                members.add(new Member(rs.getInt("mem_num"), getConnection()));
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
                sql += " WHERE event_id=" + getID();
            ResultSet rs = stmnt.executeQuery(sql);

            //find id value for new entry
            int maxID = 0;
            if(newEntry) {
                Statement sizeStmnt = getConnection().createStatement();
                ResultSet sizeRS = sizeStmnt.executeQuery("SELECT MAX(event_id) FROM " + TABLE);
                sizeRS.first();
                maxID = sizeRS.getInt("MAX(event_id)");
                sizeRS.close();
            }

            if(newEntry) {
                rs.moveToInsertRow();
                rs.updateInt("event_id", ++maxID);
                setID(maxID);
            }
            else
                rs.first();

            rs.updateString("event_desc", getDescription());
            rs.updateDate("event_date", new java.sql.Date(getDate().getTime()));
            rs.updateInt("event_room", room.getID());



            if(newEntry)    rs.insertRow();
            else            rs.updateRow();

            rs.beforeFirst();
            rs.close();


            //update list of members
            stmnt = getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
            rs = stmnt.executeQuery("SELECT * FROM event_mem WHERE event_id=" + getID());

            ArrayList<Member> onDB = new ArrayList<>(), notOnDB = new ArrayList<>(), notinFrontEnd = new ArrayList<>();
            while(rs.next()){
                onDB.add(new Member(rs.getInt("mem_num"), getConnection()));
            }
            stmnt.close();
            //Members to delete
            for(int i = 0;i < onDB.size();i++){
                if(!members.contains(onDB.get(i)))
                    notinFrontEnd.add(onDB.get(i));
            }
            for(Member m: notinFrontEnd){
                stmnt = getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                rs = stmnt.executeQuery("SELECT * FROM event_mem WHERE " +
                        "event_id=" + getID() + " AND mem_num=" + m.getID());
                rs.first();
                rs.deleteRow();
                stmnt.close();
            }

            for(int i = 0;i < members.size();i++){
                if(!onDB.contains(members.get(i)))
                    notOnDB.add(members.get(i));
            }
            for(Member m: notOnDB){
                stmnt = getConnection().createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
                rs = stmnt.executeQuery("SELECT * FROM event_mem");
                rs.moveToInsertRow();
                rs.updateInt("event_id", getID());
                rs.updateInt("mem_num", m.getID());
                rs.insertRow();

                stmnt.close();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

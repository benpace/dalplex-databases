package com.dalplex.data;

import java.sql.*;

/**
 * @author Ben Pace
 */
public class Member extends Person{
    public static final String TABLE = "reg_users";

    private Membership memType;

    public Member(Connection conn){
        super(conn);
    }
    public Member(int ID, Connection conn){
        super(ID, conn);
    }

    public void setMemType(Membership memType){this.memType = memType;  setConcurrent(false);}
    public Membership getMemType(){return memType;}

    @Override
    public void retrieveFields(){
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
            setMemType(new Membership(rs.getInt("mem_type"), getConnection()));
            setConcurrent(true);
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

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
            rs.updateInt("mem_type", getMemType().getID());

            if(newEntry)    rs.insertRow();
            else            rs.updateRow();

            rs.beforeFirst();
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

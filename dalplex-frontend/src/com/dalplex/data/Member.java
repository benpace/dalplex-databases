package com.dalplex.data;

import java.sql.Connection;

/**
 * @author Ben Pace
 */
public class Member extends Person{
    private final String TABLE = "reg_users";

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
    public void retrieveFields() {
        //TODO: Implement retrieveFields()
    }

    @Override
    public void publishToDB() {
        //TODO: Implement publishTODB()
    }

}

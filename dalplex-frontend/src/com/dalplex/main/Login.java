package com.dalplex.main;

public class Login {
    private String user;
    private char[] pass;

    public Login(String user, char[] pass){
        this.user = user;
        this.pass = pass;
    }

    public String getUser(){return user;}
    public char[] getPass(){return pass;}

    public void disposePass(){
        for(char c: pass)
            c = 0;
    }
}

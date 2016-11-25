package com.dalplex.main;
/**
 * @author Ben Pace
 */
        import javax.swing.JOptionPane;
        import java.sql.*;

        import com.dalplex.data.Person;
        import com.dalplex.gui.*;


public class Launch {
    public static void main(String[] args){
        final String DB_URL = "jdbc:mysql://localhost/";
        final String DB_NAME = "javabase";

        //Load Driver
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            System.out.println("Driver loaded");
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }

        /*
         * Get login info then attempt to connect, keep attempting until connection is made
         */
        Connection conn = null;
        boolean validLogin = false;
        while(!validLogin) {
            //Get login info
            LoginWindow loginPrompt = new LoginWindow();
            Login login = loginPrompt.getLogin();

            //Attempt connection
            try {
                conn = DriverManager.getConnection(DB_URL + DB_NAME,
                        login.getUser(), String.copyValueOf(login.getPass()));

                //Dispose of password
                login.disposePass();
                loginPrompt.dispose();
                validLogin = true;
                System.out.println("Connection successful");

            } catch (SQLException e) {
                login.disposePass();
                login = null;
                loginPrompt.dispose();
                if(e.getCause() instanceof java.net.ConnectException){
                    JOptionPane.showMessageDialog(null, "Cannot connect to DB at " +
                            DB_URL + DB_NAME);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Cannot login with given info");
                }
            }
        }

        Window window = new Window(conn);
        try {
            conn.close();
            System.out.println("Connection closed");
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}

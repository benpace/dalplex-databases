/**
 * @author Ben Pace
 */
import javax.swing.*;
import java.sql.*;

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

        //Get LogIn Info
        Connection conn = null;
        boolean validLogin = false;
        while(!validLogin) {
            LoginWindow loginPrompt = new LoginWindow();
            Login login = loginPrompt.getLogin();
            System.out.println(login.getUser());


            try {
                conn = DriverManager.getConnection(DB_URL + DB_NAME,
                        login.getUser(), String.copyValueOf(login.getPass()));

                //Dispose of password
                login.disposePass();
                loginPrompt.dispose();
                validLogin = true;

            } catch (SQLException e) {
                login.disposePass();
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


    }

}

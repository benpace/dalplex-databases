package com.dalplex.main;
/**
 * @author Ben Pace
 */
        import javax.swing.JOptionPane;
        import java.sql.*;

        import com.dalplex.data.Employee;
        import com.dalplex.data.Member;
        import com.dalplex.gui.*;


public class Launch {
    public static void main(String[] args){
        final String DB_URL = "jdbc:mysql://localhost/";
        final String DB_NAME = "dalplex";

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




        //TODO: Remove this
        //Member m = new Member(1, conn);
        Employee e = new Employee(conn);
        e.setFname("Ben");
        e.setLname("Pace");
        e.setPhone("902-740-4512");
        e.setDate(new Date(0));
        e.setAddress("Somewhere st.");
        e.setCity("CityVille");
        e.setPostcode("b5a2n8");
        e.setTitle("Chief");
        e.setSalary(5);
        e.publishToDB();

        /*try {
            Statement s = conn.createStatement();
            ResultSet rs = s.executeQuery("SELECT id FROM employees WHERE")
        } catch (SQLException e1) {
            e1.printStackTrace();
        }*/
        Employee e2 = new Employee(2, conn);
        e2.setTitle("Cool dude");
        e2.publishToDB();
        //System.out.println(m);
        Window window = new Window(conn);



    }


}

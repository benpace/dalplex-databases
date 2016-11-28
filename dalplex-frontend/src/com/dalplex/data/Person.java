package com.dalplex.data;

import java.sql.Connection;
import java.util.Date;


/**
 * @author Ben Pace
 */
public abstract class Person extends DBItem {
    private String fname, lname, address, city, postcode, phone;
    private Date birthday;


    public Person(Connection conn){
        super(conn);
    }
    public Person(int ID, Connection conn){
        super(ID, conn);
    }

    public String getFname(){return fname;}
    public String getLname(){return lname;}
    public String getPhone(){return phone;}
    public Date getBirthday(){return birthday;}
    public String getAddress(){return address;}
    public String getCity(){return city;}
    public String getPostcode(){return postcode;}

    public void setFname(String fname){this.fname = fname;  setConcurrent(false);}
    public void setLname(String lname){this.lname = lname;  setConcurrent(false);}
    public void setPhone(String phone){this.phone = phone;     setConcurrent(false);}
    public void setDate(Date date){this.birthday = date;    setConcurrent(false);}
    public void setAddress(String address){this.address = address;  setConcurrent(false);}
    public void setCity(String city){this.city = city;      setConcurrent(false);}
    public void setPostcode(String postcode){this.postcode = postcode;  setConcurrent(false);}
}

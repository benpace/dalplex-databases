# Dalplex Database
### A Database project for CSCI2132 at Dalhousie University

Built Using:
------------
* Java Development Kit 1.8.0_76
* IntelliJ IDEA (Community Edition) 2016.3 EAP
* MySQL Connector/J 5.1

How to Run/Build
----------------
Once the database is setup with the SQL commands submitted you may run the frontend application

You should be able to run either standalone with 
1. dalplex-frontend.jar
or by compiling from source
2. com.dalplex

If compiling by source you will need
* jdk 1.8.0
* MySQL Connector/J 5.1 JDBC driver library in your classpath

Your main class will be com.dalplex.main.Launch
If for whatever reason you need to change the database location from localhost or the database name they are final variables in that class

Log in with a user from your databse with sufficient permissions to read/write data




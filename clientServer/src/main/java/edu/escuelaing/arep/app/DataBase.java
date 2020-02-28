/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.escuelaing.arep.app;

/**
 *
 * @author david
 */
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
	
	static String url= "jdbc:postgres://nqdlfhjgjgbkyy:da2ac47670cce6923e0c3cd676eba357abb7e7dc8b65730d970228a9dfdd027a@ec2-184-72-236-57.compute-1.amazonaws.com:5432/decgh7ret7fkgk";
	static String user= "nqdlfhjgjgbkyy";
	static String password="da2ac47670cce6923e0c3cd676eba357abb7e7dc8b65730d970228a9dfdd027a";
	static Connection conect=null;
	Statement state=null;
	DatabaseMetaData dbm= null;
	
	
	
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		try{
			Class.forName("org.postgresql.Driver");
			conect=DriverManager.getConnection(url,user,password);
		} catch (ClassNotFoundException e) {}
                
	}
	
        
	
}
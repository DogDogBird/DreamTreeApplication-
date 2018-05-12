package com.example.user.dreamtreeapp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBC extends Thread
{
	 Connection conn = null;
	 Statement stmt = null;
	 ResultSet rs = null;

	 String DB_DRV = "com.tmax.tibero.jdbc.TbDriver";
	 String DB_IP = "localhost";
	 String DB_PORT = "8629";
	 String DB_SID = "tibero";
	 String DB_ID = "HR";
	 String DB_PWD = "tibero";
	 String DB_URL = "jdbc:tibero:thin:@" + DB_IP + ":" + DB_PORT + ":" + DB_SID;
	 
	 String strSQL;

	public JDBC() {
		
		// TODO Auto-generated constructor stub
	}
	public void connect()
	{
		try
		{
			System.out.println("=====================");
			System.out.println("DB_DRV : " + DB_DRV);
			System.out.println("DB_URL : " + DB_URL);
			System.out.println("DB_ID : " + DB_ID);
			System.out.println("DB_PWD : " + DB_PWD);
			Class.forName(DB_DRV);
			conn = DriverManager.getConnection(DB_URL, DB_ID, DB_PWD);
			System.out.println(conn);
			System.out.println("Tibero Connect Success");
			System.out.println("=====================");
			System.out.println("");
		}
		
		catch(Exception xt)
		{
			xt.printStackTrace();
		}
	}
	
	
	public User CheckUserInfo(String id, String pw)
	{
		try
		{
			User loginedUser = new User();
			System.out.println(conn);
			strSQL = "select * from DB_USERS WHERE USER_ID = " +id + "AND USER_PW = " + pw;
			stmt = conn.createStatement();
			rs = stmt.executeQuery(strSQL);
			System.out.println("STMT: " + stmt);
			System.out.println("====================");
			System.out.println("SQL : " + strSQL);
			System.out.println("--------------------");			
			
			while(rs.next())
			{
				System.out.println("USER Name : " + rs.getString(1));
				System.out.println("USER BirthDay : " + rs.getString(2));
				System.out.println("USER ID : " + rs.getString(3));
				System.out.println("USER PW : " + rs.getString(4));
				System.out.println("USER PhoneNumber : " + rs.getString(5));
				System.out.println("USER Email : " + rs.getString(6));
				System.out.println("======================");
				loginedUser.set_Name(rs.getString(1));
				loginedUser.set_Birthday(rs.getString(2));
				loginedUser.set_ID(rs.getString(3));
				loginedUser.set_PW(rs.getString(4));
				loginedUser.set_PhoneNumber(rs.getString(5));
				loginedUser.set_Email(rs.getString(6));
				
				System.out.println("로그인 성공");
				return loginedUser;
				
			}
			System.out.println("--------------------");
			return null;
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
			return null;
		}
	}
	public void UserDataWrite(String id, String password, String name)
	{
		try
		{
			strSQL = "Update";//회원가입
			stmt = conn.createStatement();
			rs = stmt.executeQuery(strSQL);
			System.out.println("====================");
			System.out.println("SQL : " + strSQL);
			System.out.println("--------------------");			
		}
		catch(Exception ex) 
		{
			ex.printStackTrace();
		}
	}
	
}

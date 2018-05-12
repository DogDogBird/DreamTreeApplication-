package com.example.user.dreamtreeapp;

enum STATUS
{
    OFFLINE, ONLINE, BUSY
}

public class User {
	
	User()
	{
		ID ="";
		PW = "";
		Name = "";
	}
	
	private String ID;
	private String PW;
	private String Name;
	private String Birthday;
	private String PhoneNumber;
	private String Email;
	
	private STATUS status;
	
	public String get_Birthday()
	{
		return Birthday;
	}
	public String get_Email()
	{
		return Email;
	}
	public String get_PhoneNumber()
	{
		return PhoneNumber;
	}
	public String get_ID()
	{
		return ID;
	}
	public String get_PW()
	{
		return PW;
	}
	public String get_Name()
	{
		return Name;
	}
	
	public STATUS get_status()
	{
		return status;
	}
	
	public void set_ID(String _ID)
	{
		ID = _ID;
	}
	public void set_PW(String _PW)
	{
		PW = _PW;
	}
	public void set_Name(String _Name)
	{
		Name = _Name;
	}
	public void set_PhoneNumber(String _PhoneNumber)
	{
		PhoneNumber = _PhoneNumber;
	}
	public void set_Birthday(String _Birthday)
	{
		Birthday = _Birthday;
	}
	public void set_Email(String _Email)
	{
		Email = _Email;
	}
	
	public void set_Status(STATUS stat)
	{
		status = stat;
	}
	
	public void displayUserInfo()
	{
	}
}

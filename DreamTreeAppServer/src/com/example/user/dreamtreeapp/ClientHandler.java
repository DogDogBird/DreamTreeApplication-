package com.example.user.dreamtreeapp;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ClientHandler extends Thread
{
	DataInputStream dis;
	DataOutputStream dos;
	ObjectOutputStream oos;
	Socket s;
	
	Boolean isLogin = false;
	String CheckLogin = "";
	
	static List<User> list;
	static User checked_user;
	static HashMap<String, String> userStatusList;
	
	static String data;
	static String[] statedata;
	
    static JDBC myDB = new JDBC();
    
	public ClientHandler(Socket s, DataInputStream dis, DataOutputStream dos)
	{    	
    	System.out.println("ClientHandler created");
		this.s = s;
		this.dis = dis;
		this.dos = dos;
		
		ServerReceiver sr = new ServerReceiver(s);
		sr.start();
	}
	
	public ClientHandler()
	{
		System.out.println("new List created");
		checked_user = new User();
	}
	
	 class ServerReceiver extends Thread 
	 {	 
		 Socket socket;
	        public ServerReceiver(Socket socket) {
	        	System.out.println("new ServerReceived created");
	            this.socket = socket;
	            try 
	            {          	
	            	myDB.connect();
	            	//FileIO fileThread = new FileIO();
	            	//fileThread.ThreadStart();
	                dis = new DataInputStream(socket.getInputStream());
	                dos = new DataOutputStream(socket.getOutputStream());              
	            } 
	            catch (IOException ie) 
	            {            	
	            }
	        }
	 
	        public void run()
	        {
	            String name = "";
	            
	            try 
	            {
	            	data = dis.readUTF();
	            	System.out.println("data   " + data);
	            	if(data.contains("LoginID_"))
	            	{
	            		getUserInfoFromClient();
	            	}
	            	else if(data.contains("SignUpD_"))
	            	{
	            		System.out.println("get Sign up Data");
	            		SignUp();
	            	}
	            	else if(data.contains("Received well"))
	            	{
	            		System.out.println("Received well from client");
	            	}
	            	else if(data.contains("StatusIs_"))
	            	{
	            		statedata = data.split(":::");
	            		System.out.println("SetUserStatus");
	            		//setUserState();
	            		//sendUserState();
	            	}
	            }
	            catch (IOException ie) 
	            {
	            	
	            } 
	            finally {
	            }
	        }
	        public void getUserInfoFromClient() throws IOException
	        {
	        	System.out.println("getUserInfoFromClient Start from handler");
	        	//System.out.println("Current User: " + checked_user.get_Status());	        
	        	
	        	String ID = "";
	        	String PW = "";
				System.out.println("data: " + data);
	        	if(data.contains("LoginID_"))
				{
					String[] splited = data.split(":");
					ID = splited[0].replace("LoginID_", "");
					System.out.println("ID: " + ID);
					PW = splited[1].replace("LoginPW_", "");
					System.out.println("PW: " + PW);	
					
					checked_user = myDB.CheckUserInfo(ID, PW);
					if(checked_user != null)
					{
						System.out.println("Current User ID: " + checked_user.get_ID());
						System.out.println("Current User PW: " + checked_user.get_PW());
						dos.writeUTF("LoginSuccessFull@!@!" + ":" + "LoginedUserID_" + checked_user.get_ID() + ":" + "LoginedUserPW_" + checked_user.get_PW() + ":" + "LoginedUserName_" + checked_user.get_Name() +  ":" + "LoginedUserBirthday_" + checked_user.get_Birthday());
						System.out.println("logined");
						dos.flush();
					}
					else if(checked_user == null)
					{
						socket.close();
						return;
					}
				}			 			
	        }
	        
	        	             
	        public void sendLoginedUserInfoToClient() throws IOException
	        {
	        	
	        	if(checked_user.get_status() == (STATUS.ONLINE))
				{
					dos.writeUTF("LoginSuccessFull@!@!" + ":" + "LoginedUserID_" + checked_user.get_ID() + ":" + "LoginedUserPW_" + checked_user.get_PW() + ":" + "LoginedUserName_" + checked_user.get_Name() +  ":" + "LoginedUserBirthday_" + checked_user.get_Birthday());
					System.out.println("logined");
					dos.flush();
				}
				else
				{
					dos.writeUTF("@#Check the ID or Password Please@#");
		        	dos.flush();
				}	
	        }
	        
	       
	        public void SignUp() throws IOException
	        {
	        	String SignUpID = "";
	        	String SignUpPW = "";
	        	String SignUpName = "";
	        	String PartnerID = "";
	        	JDBC myDB = new JDBC();
	        	if(data.contains("SignUpD_"))
				{
	        		String[] splited = data.split(":");
	        		SignUpID = splited[0].replace("SignUpD_", "");
	        		System.out.println("SignUpID: " + SignUpID);
	        		SignUpPW = splited[1].replace("SignUpPW_", "");
					System.out.println("SignUpPW: " + SignUpPW);
					SignUpName = splited[2].replace("SignUpName_", "");
					System.out.println("SignUpName: " + SignUpName);
					PartnerID = splited[3].replace("PartnerID_", "");
					System.out.println("PartnerID: " + PartnerID);
					
					for(int i=0;i<list.size();i++)
					{
						if(list.get(i).get_ID().equals(SignUpID))
						{
							System.out.println("ID Already exists");
							dos.writeUTF("ID Already Exists!@#!@#");
							dos.flush();
							return;
						}
					}
					dos.writeUTF("SignUpSuccessfull@!#!@#");
					dos.flush();
					myDB.UserDataWrite(SignUpID, SignUpPW, SignUpName);
				}
	        }
	        
	        /*
	        public void setUserState() throws IOException
	        {
	        	System.out.println("Writing User State in Server");
	        	JDBC myDB = new JDBC();
	        
	        	String ID = "";
	        	String[] splited = statedata[0].split(":");
	        	if(data.contains("StatusIs_OFFLINE"))
            	{
	        		System.out.println("data contains Status os offline");
	        		myDB.UserStateFileWrite(splited[0], "OFFLINE");
	        		myDB.updateUserStateFile(splited[0],"OFFLINE");
            	}
	        	else if(data.contains("StatusIs_BUSY"))
            	{
	        		System.out.println("data contains Status os busy");
	        		myDB.UserStateFileWrite(splited[0], "BUSY");
	        		myDB.updateUserStateFile(splited[0],"BUSY");
            	}
	        	else if(data.contains("StatusIs_ONLINE"))
            	{
	        		System.out.println("data contains Status os online");
	        		myDB.UserStateFileWrite(splited[0], "ONLINE");
	        		myDB.updateUserStateFile(splited[0],"ONLINE");
            	}
	        }
	        public void sendUserState() throws IOException
	        {	        	
	        	System.out.println("Sending User State Date to Client");
	        	String PartnerID = statedata[1].replace("CheckUsersState_:", "");
	                	        	
	        	JDBC myDB = new JDBC();
	        	myDB.ReadUserState();
	        	
	        	Set set = userStatusList.entrySet();
				Iterator iterator = set.iterator();
				while(iterator.hasNext())
				{
					Map.Entry mentry = (Map.Entry)iterator.next();
					if(mentry.getKey().equals(PartnerID))
					{
						dos.writeUTF("State_" + mentry.getValue().toString());
						System.out.println("State_" + mentry.getValue().toString());
						dos.flush();
						return;
					}
				}
				dos.writeUTF("State_OFFLINE");
				System.out.println("State_OFFLINE");
				dos.flush();
				
	        }
	        */
	    }
	    
	    public void setUserList(List<User> userList)
		{
			list = userList;
		}	 
	    public void setUserStateList(HashMap<String, String> userState)
		{
	    	userStatusList = userState;
		}	 
}

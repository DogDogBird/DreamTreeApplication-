package com.example.user.dreamtreeapp;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;


public class SocketServer 
{
  
    final static int SERVER_PORT = 7777;
 
      // ������ ������ Ŭ���̾�Ʈ�� HashMap�� �����Ͽ� �����Ѵ�.
    HashMap clients;
	DataInputStream in;
    DataOutputStream out;
 
    public SocketServer() {
        clients = new HashMap();
        Collections.synchronizedMap(clients);    // ����ȭ ó��
    }
 
    public void start() 
    {
        ServerSocket serverSocket = null;
        Socket socket = null;
 
        try 
        {
        	
            serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server Started");
 
            while (true) {
                socket = serverSocket.accept();
                System.out.println("Login try from [" + socket.getInetAddress() + ":" + socket.getPort() + "]");
 
                ClientHandler thread = new ClientHandler(socket,in,out);
                thread.start();
            }
        } 
        catch (Exception e)
        {
        	try 
        	{
				serverSocket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            e.printStackTrace();
        }
    }
 

    void sendToAll(String msg) 
    {
    	Iterator it = clients.keySet().iterator();
    	 
    	while (it.hasNext()) 
    	{
    		try 
    	    {
    			DataOutputStream out = (DataOutputStream) clients.get(it.next());
    	        out.writeUTF(msg);
    	    } 
    	        catch (Exception e) 
    	        {
    	        }
    	}
    }
    public static void main(String[] args) 
    {
    	new SocketServer().start();
    }

    
  // ��Ƽä�ü����� ServerReceiver������� Ŭ���̾�Ʈ�� �߰��� ������ �����Ǹ�
    // Ŭ���̾�Ʈ�� �Է��� ������ ���ӵ� ��� Ŭ���̾�Ʈ���� �����ϴ� ���� �Ѵ�.
   
}

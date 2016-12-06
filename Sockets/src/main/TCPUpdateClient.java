package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class TCPUpdateClient extends Thread {
	
	Socket socket = null;
	
	public TCPUpdateClient(Socket socket){
		this.socket = socket;
	}
	
	
	public void run() {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String fromServer;
			do{
			fromServer = in.readLine();
			TCPClientChat.updateClient(fromServer);
			}while(fromServer != null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}

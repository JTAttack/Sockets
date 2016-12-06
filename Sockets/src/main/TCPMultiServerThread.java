package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class TCPMultiServerThread extends Thread {

	private Socket socket = null;
	BufferedReader inFromClient;
	String recevied;
	static ArrayList<PrintWriter> clientList = new ArrayList<PrintWriter>();

	public TCPMultiServerThread(Socket socket) throws IOException {
		super("TCPMultiServerThread");
		this.socket = socket;
		inFromClient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}

	public void run() {
		try (PrintWriter outToCLient = new PrintWriter(socket.getOutputStream(), true);) {
			clientList.add(outToCLient);
			do{
			recevied = inFromClient.readLine();
			System.out.println(recevied + "GOT");
			for(int i = 0; i < clientList.size(); i++){
				clientList.get(i).println(recevied);
			}
			if(recevied.equals("SHUTDOWN")) break;
			} while(!recevied.equals("SHUTDOWN"));
			socket.close();
			clientList.remove(outToCLient);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}

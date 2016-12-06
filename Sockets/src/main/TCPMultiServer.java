package main;

import java.io.IOException;
import java.net.ServerSocket;

public class TCPMultiServer {
	
	public static void main(String[]args){
		String ip = "192.168.1.9";
		int port = 5252;
		Boolean listening = true;
		
		try (ServerSocket serverSocket = new ServerSocket(port)) { 
            while (listening) {
                new TCPMultiServerThread(serverSocket.accept()).start();
            }
        } catch (IOException e) {
            System.err.println("Could not listen on port " + port);
            System.exit(-1);
        }
	}

}

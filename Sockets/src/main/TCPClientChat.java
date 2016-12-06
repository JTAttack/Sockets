package main;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TCPClientChat {

	public static JFrame frame = new JFrame("Chat");
	public static JTextField input = new JTextField(20);
	public static JTextArea chatArea = new JTextArea(5, 20);
	public static GridLayout lay = new GridLayout(0, 1);
	public static JScrollPane scrollPane = new JScrollPane(chatArea);
	public static String sendToServer;
	public static Socket clientSocket;
	public static PrintWriter outToServer;

	public static void main(String[] args) throws IOException {
		clientSocket = new Socket("192.168.1.9", 5252);
		outToServer = new PrintWriter(clientSocket.getOutputStream(), true);
		new TCPUpdateClient(clientSocket).start();
		createWindow();
	}

	public static void updateClient(String s) {
		chatArea.append(s + "\n");
	}

	public static void createWindow() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(lay);
		input.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				sendToServer = input.getText();
				BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
				outToServer.println(sendToServer );
				input.setText("");

			}
		});
		frame.add(input);
		frame.add(chatArea);
		frame.pack();
		frame.setVisible(true);

	}

}

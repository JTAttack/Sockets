package test;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.SecondaryLoop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import javax.security.auth.callback.TextInputCallback;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Tic_Tac_Toe_GUI implements ActionListener {
	private JLabel label = new JLabel("Number of clicks:  0     ");
	private JFrame frame = new JFrame();
	public static JButton TL = new JButton("");
	public static JButton TM = new JButton("");
	public static JButton TR = new JButton("");
	public static JButton ML = new JButton("");
	public static JButton MM = new JButton("");
	public static JButton MR = new JButton("");
	public static JButton BL = new JButton("");
	public static JButton BM = new JButton("");
	public static JButton BR = new JButton("");
	public static JButton PlayAgain = new JButton("Play Again?");
	public static Random rand = new Random();
	public static int Player1;
	public static int Player2;
	public static int Player1Score = 0;
	public static int Player2Score = 0;
	private static JLabel firstPlayer = new JLabel("");
	public static int turn = 0;
	public static String who;
	public static String spotChosen;
	private static JLabel PickFreeSpot = new JLabel("");
	public static JPanel panel = new JPanel();
	private static JLabel Winner = new JLabel("");
	private static JLabel P1Score = new JLabel("Player's 1 Score is: 0");
	private static JLabel P2Score = new JLabel("Player's 2 Score is: 0");
	public static Boolean FirstStart = Boolean.valueOf(true);
	static Tic_Tac_Toe_GUI main;
	BufferedReader inFromUser;
	Socket clientSocket;
	DataOutputStream outToServer;
	BufferedReader inFromServer;
	ServerSocket welcomeSocket;
	Socket connectionSocket;
	BufferedReader inFromClient;
	DataOutputStream outToClient;

	public Tic_Tac_Toe_GUI() throws IOException {
		TL.addActionListener(this);
		TM.addActionListener(this);
		TR.addActionListener(this);
		ML.addActionListener(this);
		MM.addActionListener(this);
		MR.addActionListener(this);
		BL.addActionListener(this);
		BM.addActionListener(this);
		BR.addActionListener(this);
		PlayAgain.addActionListener(this);

		TL.setName("FREE");
		TM.setName("FREE");
		TR.setName("FREE");
		ML.setName("FREE");
		MM.setName("FREE");
		MR.setName("FREE");
		BL.setName("FREE");
		BM.setName("FREE");
		BR.setName("FREE");

		TL.setOpaque(true);
		TM.setOpaque(true);
		TR.setOpaque(true);
		ML.setOpaque(true);
		MM.setOpaque(true);
		MR.setOpaque(true);
		BL.setOpaque(true);
		BM.setOpaque(true);
		BR.setOpaque(true);

		TL.setBackground(Color.ORANGE);
		TM.setBackground(Color.ORANGE);
		TR.setBackground(Color.ORANGE);
		ML.setBackground(Color.ORANGE);
		MM.setBackground(Color.ORANGE);
		MR.setBackground(Color.ORANGE);
		BL.setBackground(Color.ORANGE);
		BM.setBackground(Color.ORANGE);
		BR.setBackground(Color.ORANGE);

		TL.setActionCommand("TL");
		TM.setActionCommand("TM");
		TR.setActionCommand("TR");
		ML.setActionCommand("ML");
		MM.setActionCommand("MM");
		MR.setActionCommand("MR");
		BL.setActionCommand("BL");
		BM.setActionCommand("BM");
		BR.setActionCommand("BR");
		PlayAgain.setActionCommand("Again");

		panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 10, 30));
		panel.setLayout(new GridLayout(0, 3));
		panel.add(TL);
		panel.add(TM);
		panel.add(TR);
		panel.add(ML);
		panel.add(MM);
		panel.add(MR);
		panel.add(BL);
		panel.add(BM);
		panel.add(BR);
		panel.add(firstPlayer);
		panel.add(PickFreeSpot);
		panel.add(Winner);
		panel.add(P1Score);
		panel.add(P2Score);
		panel.add(PlayAgain);
		panel.setBackground(Color.DARK_GRAY);
		PlayAgain.setVisible(false);

		firstPlayer.setForeground(Color.WHITE);
		PickFreeSpot.setForeground(Color.WHITE);
		Winner.setForeground(Color.WHITE);
		P1Score.setForeground(Color.WHITE);
		P2Score.setForeground(Color.WHITE);

		this.frame.add(panel, "Center");
		this.frame.setDefaultCloseOperation(3);
		this.frame.setTitle("Tic-Tac-Toe");
		this.frame.pack();
		this.frame.setVisible(true);
		this.frame.setSize(500, 500);
		createClient();

	}

	public void createClient() throws IOException {
		if (hostAvailabilityCheck() == false) {
			createServer();
		}
		inFromUser = new BufferedReader(new InputStreamReader(System.in));
		try {
			clientSocket = new Socket("192.168.1.9", 4444);
			System.out.println("CLient Created at " + clientSocket.getInetAddress());
			outToServer = new DataOutputStream(clientSocket.getOutputStream());
			inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
		} catch (IOException e) {
		}
		connectServerNClient();
	}

	public void createServer() throws IOException {
		welcomeSocket = new ServerSocket(4444);
		System.out.println("Server Created at " + InetAddress.getLocalHost().toString());
	}
	public void connectServerNClient() throws IOException{
		connectionSocket = welcomeSocket.accept();
		inFromClient = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()));
		outToClient = new DataOutputStream(connectionSocket.getOutputStream());
	}
	public void sendDataToServer(String spot, String type) throws IOException{
		outToServer.writeBytes(spot + " " + type);
	}

	public static boolean hostAvailabilityCheck() {
		try (Socket s = new Socket("192.168.1.9", 4444)) {
			s.close();
			return true;
		} catch (IOException ex) {
			System.out.println("SERVER FOUND");
		}
		return false;
	}

	public static void randomStart() {
		Player1 = rand.nextInt(2);
		if (Player1 == 0) {
			firstPlayer.setText("Player 1 is up first!");
		} else {
			Player2 = 1;
			firstPlayer.setText("Player 2 is up first!");
		}
	}

	public static void turn() {
		if ((turn == 0) || (turn == 2) || (turn == 4) || (turn == 6) || (turn == 8)) {
			who = "X";
		}
		if ((turn == 1) || (turn == 3) || (turn == 5) || (turn == 7)) {
			who = "O";
		}
		if (Player1 == 0) {
			if ((turn == 2) || (turn == 4) || (turn == 6) || (turn == 8)) {
				firstPlayer.setText("Player 1 Is Up");
			} else if ((turn == 1) || (turn == 3) || (turn == 5) || (turn == 7)) {
				firstPlayer.setText("Player 2 Is Up");
			}
		} else if (Player1 == 1) {
			if ((turn == 1) || (turn == 3) || (turn == 5) || (turn == 7)) {
				firstPlayer.setText("Player 1 Is Up");
			} else if ((turn == 2) || (turn == 4) || (turn == 6) || (turn == 8)) {
				firstPlayer.setText("Player 2 Is Up");
			}
		}
	}

	public static void setspotX() throws IOException {
		if ((spotChosen.equals("TL")) && (!TL.getName().equals("TAKEN"))) {
			TL.setText("X");
			TL.setName("TAKEN");
			main.sendDataToServer("TL", "X");
			turn += 1;
			turn();
			PickFreeSpot.setText("");
		} else if ((spotChosen.equals("TM")) && (!TM.getName().equals("TAKEN"))) {
			TM.setText("X");
			TM.setName("TAKEN");
			turn += 1;
			turn();
			PickFreeSpot.setText("");
		} else if ((spotChosen.equals("TR")) && (!TR.getName().equals("TAKEN"))) {
			TR.setText("X");
			TR.setName("TAKEN");
			turn += 1;
			turn();
			PickFreeSpot.setText("");
		} else if ((spotChosen.equals("ML")) && (!ML.getName().equals("TAKEN"))) {
			ML.setText("X");
			ML.setName("TAKEN");
			turn += 1;
			turn();
			PickFreeSpot.setText("");
		} else if ((spotChosen.equals("MM")) && (!MM.getName().equals("TAKEN"))) {
			MM.setText("X");
			MM.setName("TAKEN");
			turn += 1;
			turn();
			PickFreeSpot.setText("");
		} else if ((spotChosen.equals("MR")) && (!MR.getName().equals("TAKEN"))) {
			MR.setText("X");
			MR.setName("TAKEN");
			turn += 1;
			turn();
			PickFreeSpot.setText("");
		} else if ((spotChosen.equals("BL")) && (!BL.getName().equals("TAKEN"))) {
			BL.setText("X");
			BL.setName("TAKEN");
			turn += 1;
			turn();
			PickFreeSpot.setText("");
		} else if ((spotChosen.equals("BM")) && (!BM.getName().equals("TAKEN"))) {
			BM.setText("X");
			BM.setName("TAKEN");
			turn += 1;
			turn();
			PickFreeSpot.setText("");
		} else if ((spotChosen.equals("BR")) && (!BR.getName().equals("TAKEN"))) {
			BR.setText("X");
			BR.setName("TAKEN");
			turn += 1;
			turn();
			PickFreeSpot.setText("");
		} else {
			PickFreeSpot.setText("Pick A Free Spot!");
			PickFreeSpot.setForeground(Color.RED);
		}
		CheckForWinner();
	}

	public static void setspotO() {
		if ((spotChosen.equals("TL")) && (!TL.getName().equals("TAKEN"))) {
			TL.setText("O");
			TL.setName("TAKEN");
			turn += 1;
			turn();
			PickFreeSpot.setText("");
		} else if ((spotChosen.equals("TM")) && (!TM.getName().equals("TAKEN"))) {
			TM.setText("O");
			TM.setName("TAKEN");
			turn += 1;
			turn();
			PickFreeSpot.setText("");
		} else if ((spotChosen.equals("TR")) && (!TR.getName().equals("TAKEN"))) {
			TR.setText("O");
			TR.setName("TAKEN");
			turn += 1;
			turn();
			PickFreeSpot.setText("");
		} else if ((spotChosen.equals("ML")) && (!ML.getName().equals("TAKEN"))) {
			ML.setText("O");
			ML.setName("TAKEN");
			turn += 1;
			turn();
			PickFreeSpot.setText("");
		} else if ((spotChosen.equals("MM")) && (!MM.getName().equals("TAKEN"))) {
			MM.setText("O");
			MM.setName("TAKEN");
			turn += 1;
			turn();
			PickFreeSpot.setText("");
		} else if ((spotChosen.equals("MR")) && (!MR.getName().equals("TAKEN"))) {
			MR.setText("O");
			MR.setName("TAKEN");
			turn += 1;
			turn();
			PickFreeSpot.setText("");
		} else if ((spotChosen.equals("BL")) && (!BL.getName().equals("TAKEN"))) {
			BL.setText("O");
			BL.setName("TAKEN");
			turn += 1;
			turn();
			PickFreeSpot.setText("");
		} else if ((spotChosen.equals("BM")) && (!BM.getName().equals("TAKEN"))) {
			BM.setText("O");
			BM.setName("TAKEN");
			turn += 1;
			turn();
			PickFreeSpot.setText("");
		} else if ((spotChosen.equals("BR")) && (!BR.getName().equals("TAKEN"))) {
			BR.setText("O");
			BR.setName("TAKEN");
			turn += 1;
			turn();
			PickFreeSpot.setText("");
		} else {
			PickFreeSpot.setText("Pick A Free Spot!");
			PickFreeSpot.setForeground(Color.RED);
		}
		CheckForWinner();
	}

	public static void CheckForWinner() {
		if (((TL.getText().equals("X")) && (TM.getText().equals("X")) && (TR.getText().equals("X")))
				|| ((ML.getText().equals("X")) && (MM.getText().equals("X")) && (MR.getText().equals("X")))
				|| ((BL.getText().equals("X")) && (BM.getText().equals("X")) && (BR.getText().equals("X")))
				|| ((TL.getText().equals("X")) && (ML.getText().equals("X")) && (BL.getText().equals("X")))
				|| ((TM.getText().equals("X")) && (MM.getText().equals("X")) && (BM.getText().equals("X")))
				|| ((TR.getText().equals("X")) && (MR.getText().equals("X")) && (BR.getText().equals("X")))
				|| ((TL.getText().equals("X")) && (MM.getText().equals("X")) && (BR.getText().equals("X")))
				|| ((BL.getText().equals("X")) && (MM.getText().equals("X")) && (TR.getText().equals("X")))) {
			if (Player1 == 0) {
				Winner.setText("Player 1 Has Won!");
				Player1Score += 1;
				System.out.println("YO");
				P1Score.setText("Player's 1 Score is: " + Player1Score);
				DisableButtons();
				PlayAgain();
			} else if (Player1 == 1) {
				Winner.setText("Player 2 Has Won!");
				Player2Score += 1;
				P2Score.setText("Player's 2 Score is: " + Player2Score);
				DisableButtons();
				PlayAgain();
			}
		}
		if (((TL.getText().equals("O")) && (TM.getText().equals("O")) && (TR.getText().equals("O")))
				|| ((ML.getText().equals("O")) && (MM.getText().equals("O")) && (MR.getText().equals("O")))
				|| ((BL.getText().equals("O")) && (BM.getText().equals("O")) && (BR.getText().equals("O")))
				|| ((TL.getText().equals("O")) && (ML.getText().equals("O")) && (BL.getText().equals("O")))
				|| ((TM.getText().equals("O")) && (MM.getText().equals("O")) && (BM.getText().equals("O")))
				|| ((TR.getText().equals("O")) && (MR.getText().equals("O")) && (BR.getText().equals("O")))
				|| ((TL.getText().equals("O")) && (MM.getText().equals("O")) && (BR.getText().equals("O")))
				|| ((BL.getText().equals("O")) && (MM.getText().equals("O")) && (TR.getText().equals("O")))) {
			if (Player1 == 0) {
				Winner.setText("Player 2 Has Won!");
				Player2Score += 1;
				P2Score.setText("Player's 2 Score is: " + Player2Score);
				DisableButtons();
				PlayAgain();
			} else if (Player1 == 1) {
				Winner.setText("Player 1 Has Won!");
				Player1Score += 1;
				P1Score.setText("Player's 1 Score is: " + Player1Score);
				DisableButtons();
				PlayAgain();
			}
		}
		if (turn == 9) {
			Winner.setText("Its A Tie!");
			DisableButtons();
			PlayAgain();
		}
	}

	public static void DisableButtons() {
		TL.setEnabled(false);
		TM.setEnabled(false);
		TR.setEnabled(false);
		ML.setEnabled(false);
		MM.setEnabled(false);
		MR.setEnabled(false);
		BL.setEnabled(false);
		BM.setEnabled(false);
		BR.setEnabled(false);
	}

	public static void PlayAgain() {
		PlayAgain.setVisible(true);
	}

	public static void reset() {
		TL.setText("");
		TM.setText("");
		TR.setText("");
		ML.setText("");
		MM.setText("");
		MR.setText("");
		BL.setText("");
		BM.setText("");
		BR.setText("");
		Winner.setText("");

		turn = 0;

		PlayAgain.setVisible(false);

		TL.setName("FREE");
		TM.setName("FREE");
		TR.setName("FREE");
		ML.setName("FREE");
		MM.setName("FREE");
		MR.setName("FREE");
		BL.setName("FREE");
		BM.setName("FREE");
		BR.setName("FREE");

		TL.setEnabled(true);
		TM.setEnabled(true);
		TR.setEnabled(true);
		ML.setEnabled(true);
		MM.setEnabled(true);
		MR.setEnabled(true);
		BL.setEnabled(true);
		BM.setEnabled(true);
		BR.setEnabled(true);
	}

	public void actionPerformed(ActionEvent e) {
		spotChosen = e.getActionCommand();
		System.out.println(spotChosen);
		if ((who.equals("X")) && (!spotChosen.equals("Again"))) {

		} else if ((who.equals("O")) && (!spotChosen.equals("Again"))) {
			setspotO();
		}
		if (spotChosen.equals("Again")) {
			reset();
			randomStart();
			turn();
		}
	}

	public static void main(String[] args) {
		try {
		main = new Tic_Tac_Toe_GUI();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}

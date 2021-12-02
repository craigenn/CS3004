package fins;

import java.io.*;
import java.net.*;

public class Client2 {
	public static void main(String[] args) throws IOException{
		// Set up the socket, in and out variables

		Socket bankClientSocket = null;
		PrintWriter out = null;
		BufferedReader in = null;
		int bankSocketNumber = 4545;
		String bankServerName = "localhost";
		String ClientID = "Client2";

		try {
			bankClientSocket = new Socket(bankServerName, bankSocketNumber);
			out = new PrintWriter(bankClientSocket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(bankClientSocket.getInputStream()));
		} catch (UnknownHostException e) {
			System.err.println("Don't know about host: localhost ");
			System.exit(1);
		} catch (IOException e) {
			System.err.println("Couldn't get I/O for the connection to: "+ bankSocketNumber);
			System.exit(1);
		}

		BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
		String fromServer;
		String fromUser;

		System.out.println("Initialised " + ClientID + " client and IO connections");
		System.out.println("Main Menu:");
		System.out.println("Press 0 to add");
		System.out.println("Press 1 to subtract");
		System.out.println("Press 2 to transfer");
		System.out.println("Press 3 to check balance");
		// This is modified as it's the client that speaks first

		while (true) {

			fromUser = stdIn.readLine();
			if (fromUser != null) {
				System.out.println(ClientID + " sending " + fromUser + " to ActionServer");
				out.println(fromUser);
			}
			fromServer = in.readLine();
			System.out.println(ClientID + " received " + fromServer + " from ActionServer");
		}



	}
}



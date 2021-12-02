package fins;
import java.net.*;
import java.io.*;

public class bankServer {
	public static void main(String[] args) throws IOException {
		ServerSocket bankServerSocket = null;
		boolean listening = true;
		String bankServerName = "ActionServer";
		int bankServerNumber = 4545;
		double sharedvar=1000.00;

		Actionstate bankActionstate1 = new Actionstate(sharedvar);


		try {
			bankServerSocket = new ServerSocket(bankServerNumber);
		} catch (IOException e) {
			System.err.println("Could not start " + bankServerName + " specified port.");
			System.exit(-1);
		}
		System.out.println(bankServerName + " started");

		while(listening) {
			new bankServerThread(bankServerSocket.accept(), "Client1", bankActionstate1).start();
			new bankServerThread(bankServerSocket.accept(), "Client2", bankActionstate1).start();
			new bankServerThread(bankServerSocket.accept(), "Client3", bankActionstate1).start();
			System.out.println("New " + bankServerName + " thread started.");
		}
		bankServerSocket.close();

	}
}

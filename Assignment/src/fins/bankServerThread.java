package fins;

import java.io.*;
import java.net.*;

public class bankServerThread extends Thread{
	private Socket bankSocket = null;
	private Actionstate mySharedActionStateObject;
	private String ServerThreadname;
	private double mySharedVariable;

	//Setup the thread
	public bankServerThread(Socket bankSocket, String ServerThreadName, Actionstate SharedObject) {

		this.bankSocket = bankSocket;
		mySharedActionStateObject = SharedObject;
		ServerThreadname = ServerThreadName;
	}

	public void run()
	{
		try {
			System.out.println(ServerThreadname + "initialising.");
			PrintWriter out = new PrintWriter(bankSocket.getOutputStream(), true);
			BufferedReader in = new BufferedReader(new InputStreamReader(bankSocket.getInputStream()));
			String inputLine, outputLine;

			while ((inputLine = in.readLine()) != null) {
				// Get a lock first
				try { 
					mySharedActionStateObject.acquireLock();  
					outputLine = mySharedActionStateObject.processInput(ServerThreadname, inputLine);
					out.println(outputLine);
					mySharedActionStateObject.releaseLock();  
				} 
				catch(InterruptedException e) {
					System.err.println("Failed to get lock when reading:"+e);
				}
			}
			out.close();
			in.close();
			bankSocket.close();
		}catch(IOException e) {
			e.printStackTrace();
		}

	}

}

package fins;

public class Actionstate {
	private Actionstate mySharedObj;
	private String threadName;
	private double mySharedVariable, Client1balance, Client2balance, Client3balance;
	private boolean accessing=false; // true a thread has a lock, false otherwise
	private int threadsWaiting=0; // number of waiting writers


	Actionstate(double shared){

		Client1balance = shared;
		Client2balance = shared;
		Client3balance = shared;

	}


	//Attempt to aquire a lock

	public synchronized void acquireLock() throws InterruptedException{
		Thread me = Thread.currentThread(); // get a ref to the current thread
		System.out.println(me.getName()+" is attempting to acquire a lock!");	
		++threadsWaiting;
		while (accessing) {  // while someone else is accessing or threadsWaiting > 0
			System.out.println(me.getName()+" waiting to get a lock as someone else is accessing...");
			//wait for the lock to be released - see releaseLock() below
			wait();
		}
		// nobody has got a lock so get one
		--threadsWaiting;
		accessing = true;
		System.out.println(me.getName()+" got a lock!"); 
	}

	// Releases a lock to when a thread is finished

	public synchronized void releaseLock() {
		//release the lock and tell everyone
		accessing = false;
		notifyAll();
		Thread me = Thread.currentThread(); // get a ref to the current thread
		System.out.println(me.getName()+" released a lock!");
	}


	/* The processInput method */
	public synchronized String processInput(String myThreadName, String theInput) {

		System.out.println(myThreadName + " received "+ theInput);
		String theOutput = null;
		//Check what the client said
		switch(theInput) {
		case "0":
			add_money(myThreadName, 100.00);
			//theOutput = reply(myThreadName);
			System.out.println(myThreadName + " current balance is: ");
			theOutput = reply(myThreadName);

			break;
		case "1":
			subtract_money(myThreadName, 100.00);
			System.out.println(myThreadName + " Account updated");
			theOutput = reply(myThreadName);
			break;
		case "2":
			String Client3 = "Client3";
			transfer(myThreadName, Client3, 200.00);
			theOutput = myThreadName + " has sent Client3 : 200";
			break;
		case "3":
			System.out.println(myThreadName + " current balance is: ");
			switch(myThreadName) {
			case "Client1":
				System.out.println(Client1balance);
				theOutput = "current balance: " + String.valueOf(Client1balance);
				break;
			case "Client2":
				System.out.println(Client2balance);
				theOutput = String.valueOf(Client2balance);
				break;
			case "Client3":
				System.out.println(Client3balance);
				theOutput = String.valueOf(Client3balance);
				break;
			}
		}









		return theOutput;

	}

	private String reply(String myThreadName) {
		String theOutput = "";
		switch(myThreadName) {
		case "Client1":
			System.out.println(Client1balance);
			theOutput = "New balance is : " + String.valueOf(Client1balance);
			break;
		case "Client2":
			System.out.println(Client2balance);
			theOutput = "New balance is : " + String.valueOf(Client2balance);
			break;
		case "Client3":
			System.out.println(Client3balance);
			theOutput = "New balance is : " + String.valueOf(Client3balance);
			break;
		}
		return theOutput;
	}

	private void add_money(String account, double value) {
		switch(account) {
		case "Client1":
			Client1balance = Client1balance + value;
			System.out.println(account + " Account updated");
			break;
		case "Client2":
			Client2balance = Client2balance + value;
			System.out.println(account + " Account updated");
			break;
		case "Client3":
			Client3balance = Client3balance + value;
			System.out.println(account + " Account updated");
			break;
		}
	}

	private void subtract_money(String account, double value) {
		switch(account) {
		case "Client1":
			Client1balance = Client1balance - value;
			System.out.println(account + " Account updated");
			break;
		case "Client2":
			Client2balance = Client2balance - value;
			System.out.println(account + " Account updated");
			break;
		case "Client3":
			Client3balance = Client3balance - value;
			System.out.println(account + " Account updated");
			break;
		}
	}

	private void transfer(String account1, String account2, double value){
		switch(account1) {
		case "Client1":
			Client1balance  -= value;
			Client3balance  += value;
			break;
		case "Client2":
			Client2balance  -= value;
			Client3balance  += value;
			break;
		}


	}



}

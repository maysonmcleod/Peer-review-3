
//-----------------------------------------
// NAME		: Mayson McLeod
// STUDENT NUMBER	: 7970371
// COURSE		: COMP 2150
// INSTRUCTOR	: John Anderson
// ASSIGNMENT	: assignment #5
// QUESTION	: question #1   
// 
// REMARKS: Creat a window to manipulate a bank server
//
//
//-----------------------------------------
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
//**************************************************************
// CLASS: ClientWindow
//
// REMARKS:
// This class defines a tabbed window which will present a user
// interface for communicating with the BankServer class
// (it actually holds the server as well, since this isn't a true
// distributed client/server).
// The major portions of the interface are divided into
// two tabs on a tabbed panel - a CustomerInterface, which deals with
// changes to or display of customer information, and an
// AccountInterface which displays/allows changes to information about
// a particular account. A quit button and two text
// fields which show the communication between the
// BankClient and the BankServer are also provided at the
// bottom of the window.
// ***************************************************************

public class ClientWindow extends JFrame implements WindowListener, ActionListener {

	// These variables contain the quit button and
	// the informational text fields that are placed
	// at the bottom of the window.
	final private int WIDTH = 400;
	final private int HEIGHT = 300;
	private JPanel quitPanel, bottomPanel;
	private LabelledField commandPanel, responsePanel;
	private JButton quitButton;

	private int currCustomerID;
	private CustomerPanel customerPanel;
	private TransactionPanel transactionPanel;

	private JTabbedPane tabs;
	// INSERT MORE HERE --> the end result you see in the running
	// code is organized as a pair of panels placed on Tabs
	// that handle the customer interface and account interface.
	// since there are a few places where these are overlap, each
	// gets a pointer to the parent as an argument to its constructor
	// so that it can send a message to the parent to invoke a routine
	// there, and allow the panels to affect things outside themselves.

	// The bank server to which commands will be sent,
	// and from which responses will come.
	private BankServer myServer;

	// the ClientWindow constructor
	public ClientWindow() {
		currCustomerID = -1;
		myServer = new BankServer();
		this.setTitle("Bank of John: How can you help us today?");
		this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

		getContentPane().setLayout(new BorderLayout(0, 10));
		// bind the window listener!
		addWindowListener(this);

		// Make a panel for the quit button alone, so that it
		// will remain small and centered in the window.
		quitPanel = new JPanel();
		quitPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
		quitButton = new JButton("Quit");
		quitButton.addActionListener(this);
		quitPanel.add(quitButton);

		// the panel for the tabs
		tabs = new JTabbedPane();

		customerPanel = new CustomerPanel(myServer);
		transactionPanel = new TransactionPanel(myServer);

		tabs.addTab("customer info", customerPanel);
		tabs.addTab("Transactions", transactionPanel);

		// Create a panel containing two LabelledFields which will
		// show the commands and responses being sent to and from
		// the server, as well as the quit button.
		bottomPanel = new JPanel();
		bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));
		commandPanel = new LabelledField("Request:", 20, false);
		responsePanel = new LabelledField("Response:", 20, false);

		bottomPanel.add(commandPanel);
		bottomPanel.add(responsePanel);
		bottomPanel.add(quitPanel);
		add(bottomPanel, BorderLayout.SOUTH);
		add(tabs, BorderLayout.NORTH);

		// lay the contents out
		pack();

		// Center the window in the context of the screen size (how cool is that?)
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Dimension frameSize = this.getSize();
		if (frameSize.height > screenSize.height)
			frameSize.height = screenSize.height;
		if (frameSize.width > screenSize.width)
			frameSize.width = screenSize.width;
		this.setLocation((screenSize.width - frameSize.width) / 2, (screenSize.height - frameSize.height - 25) / 2);
	} // end ClientWindow() constructor

	public int getCurrCustomerID() {
		return currCustomerID;
	}

	// The doCustomerCommand method will accept a string and send it to
	// the server (after echoing it to the commandPanel). The response
	// from the server will handled by the processCustString method.
	// void doCustomerCommand(String s) {
	// String reply;
	// commandPanel.setText(s);
	// responsePanel.setText(reply = myServer.processLine(s));
	// if ((reply.compareTo("Not Found") != 0)
	// && ((reply.substring(0,5)).compareTo("Error") != 0))
	// processCustString(reply);
	// } //end doCustomerCommand(String)

	public void displayAccountInfo(int accountNum) {

	}

	public void clearCurrCustomer() {
		currCustomerID = -1;
		commandPanel.setText("Clear current customer");
		responsePanel.setText("Cleared");
		transactionPanel.clear();
	}

	// The processCustString method will accept a string of information
	// about a customer (which came as a response from the server) in the
	// format:
	// IDNum /Name/Address/ NumAccts AcctID1 AcctID2 ...
	// It will parse this string, and send the appropriate messages to
	// cause this information to be displayed.
	public void processCustString(String custData) {
		custData = custData.trim(); // discard excess blanks

		if (checkServerResponse(custData)) {

			String[] custInfoSplit = custData.split("/");

			String id = custInfoSplit[0].strip();
			currCustomerID = Integer.parseInt(id);
			String name = custInfoSplit[1];

			String accountInfo[] = custInfoSplit[3].strip().split(" ");
			int numAccounts = Integer.parseInt(accountInfo[0]);
			String[] accounts = new String[numAccounts];
			for (int i = 0; i < numAccounts; i++) {
				accounts[i] = accountInfo[i + 1];
			}
			transactionPanel.setAcountList(accounts);
		}
		// use whatever parsing you want to to split the data up - split(), a tokenizer,
		// your own code, anything else you need. Read the documentation as to how the
		// elements of a string are separated and what the server expects.

		// you need to manage the list of accounts for a customer too
		// This is where the JList comes in.

	} // end method processCustString(String)

	private boolean checkServerResponse(String serverResponse) {
		boolean valid = true;

		String[] splitResponse = serverResponse.split("/");
		if (splitResponse.length != 4) {
			valid = false;
		}

		return valid;
	}

	// The doAccountCommand method will accept a string and send it to
	// the server (after echoing it to the commandPanel). The response
	// from the server will handled by the processAcctString method. It
	// will return a boolean value indicating whether or not a valid
	// response was received. ("Not Found" is the invalid response.)
	// boolean doAccountCommand(String command) {
	// String reply;
	// commandPanel.setText(command);
	// responsePanel.setText(reply = myServer.processLine(command));
	// if ((reply.compareTo("Not Found") != 0) &&
	// ((reply.substring(0,5)).compareTo("Error") != 0)) {
	// processAcctString(reply);
	// return true;
	// }
	// else return false;
	// } //end method doAccountCommand

	// The processAcctString method will accept a string of information
	// about an account (which came as a response from the server) in the
	// format:
	// TypeCode AcctID Balance Value OtherInfo
	// It will parse this string, and send the appropriate messages to
	// cause this information to be displayed. The interpretation of
	// the last few fields will depend on the TypeCode.
	// Note that I used an old tokenizer, you can re-do this with a string.split if
	// you want to...
	void processAcctString(String acctData) {
		String ignored; // A place to put strings that are not needed
		acctData = acctData.trim(); // remove excess blanks

		// I had a panel of account components and methods to set the
		// various items in it

	} // end method processAcctString

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		System.out.println("open window");
	}

	@Override
	public void windowClosing(WindowEvent e) {
		// TODO Auto-generated method stub
		System.out.println("window closing");
	}

	@Override
	public void windowClosed(WindowEvent e) {
		// TODO Auto-generated method stub
		System.out.println("close window");
	}

	@Override
	public void windowIconified(WindowEvent e) {
		// TODO Auto-generated method stub
		System.out.println("add window icon");
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO Auto-generated method stub
		System.out.println("remove window icon");
	}

	@Override
	public void windowActivated(WindowEvent e) {
		// TODO Auto-generated method stub

		System.out.println("activate window");
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO Auto-generated method stub
		System.out.println("deactivate window");
	}

	public void setRequestResponse(String request, String response) {
		commandPanel.setText(request);
		responsePanel.setText(response);

	}

	public void setCustomer(int ID) {
		currCustomerID = ID;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("Quit")) {
			this.dispose();
			System.out.println("***End of processing***");
		}
	}

	// The two tabs will need methods here to be able to make changes to
	// one another and get information from one another, since they are
	// unrelated other than this shared container.

}

// ------------end class BankClient--------------------

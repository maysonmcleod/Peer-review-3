import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;  //you shouldn't need any more than this!

//There are segments of a whole bunch of classes here, just put
//into a single file for convenience. If you are using this as a basic
//design, make sure you separate these into multiple files!

/************class BankClientSwing***********************/

//This class contains the main() method, which simply
//creates and opens a ClientWindow.

public class BankClientSwing {
	public static void main(String[] args) {
		ClientWindow testWindow = new ClientWindow();
		testWindow.setVisible(true);
	} //end main()
}
//------------end class BankClient--------------------







//there's lots of stuf for you to add yet!  Both tabbed panels have yet to be defined.
//Remember to separate the above into different classes and document the code properly!


//Some additional hints:
//to use the Jlist, you want it to be scrollable, so it needs to
//be embedded in a JScrollPane.  This is similar to what we saw in
//the examples in class.  The JScrollPane will automatically slap
//up a scrollbar when necessary.  See the documentation on JLists -
//they operate on a JListModel - basically what you add and remove
//as data to this model shows up (or not) on the list GUI component.
//for example, listItems.clear() [if listItems is declared as below]
//would wipe out what's in the actual JList because it clears the data
//out of ListModel.

//  The Jlist needs a ListSelectionListener (you need javax.event.*
//  for this because it's not an event from the basic AWT).
//	to implement ListSelectionListener you need only implement

//  public void valueChanged(ListSelectionEvent e) {

//	remember this is invoked whenever a list item is changed, so
//  make sure it doesn't do anything when there is no value
//  selected, or it will cause things to happen whenever the
//  list is altered (e.g. when displaying new customer data!)

//	Note that when doing searches the server expects something in the
//  cust id field; I inserted "0" behind the scenes to pass to the
//  server so that it gets a valid value that it knows isn't something
//  it should look for - remember that if you do
//  specify a 0 for the id as well as nothing else to
//  search for it returns an error.

//  again you don't HAVE to follow the general outline of my code
//  to the letter, but if it is a help here is how I structured things:


//class CustomerInterface extends JPanel implements ActionListener {

	//some instance variables

	//constructor
	//public CustomerInterface(ClientWindow parent) {

	//	...
	//} //end constructor

	//Clear() method - blank out fields.  This will need to clear
	//stuff in the other pane too

	//public void Clear() { ...
	//	}

	//more stuff here...

	//The following methods are used by the main ClientWindow
	//object to display data that arrives as a response from
	//the server.
	//public void setName(String s) { nameField.setText(s);}
	//public void setAddr(String s) { addrField.setText(s);}
	//public void setID(String s) { idField.setText(s);}

    //} //end class CustomerInterface



/***************class AccountInterface******************/

/* This class implements the middle panel of the
   Bank Client, containing all of the necessary components for working
   with accounts. */

//class AccountInterface extends JPanel implements ActionListener, ListSelectionListener  {

	//instance variables

	//some sub-panels

	//some components, including a JComboBox and a JList...

	//constructor
	///public AccountInterface(ClientWindow newParent) {

	//see the documentation on JComboBox for how it is used, as well as the JList stuff mentioned above.

	//.....

	//} //end constructor

	//private char getNewAcctType(int choice) {
		//translate the account type choices (0=sav, 1=chq, etc) to
		//appropriate letters to place on the command line to send the
		//server (used in constructor, above)
		//...more code here...
	//}

    //blank out the account info fields so that old account info is
    //not left over when we change customers!
   // public void clearAcctInfo() {
		// ...more code here...

	//}

	//more code here...

	//CentAmount converts the string typed by the user into the amount field
	//into a string giving the amount in cents. For example, "100" and "100.00"
	//both mean $100.00 and change into "10000" (cents). If any error occurs,
	//"0" is returned.
	//private String CentAmount() {
	//	String s;
		//	try { s = Long.toString(Math.round(100.0*(Double.valueOf(amount.getText()).doubleValue())));}
		//catch(RuntimeException e) {s = "0";}
	//	return s;
		//}


			//public void clearAccts() {//listItems = a List Model (see JList Docs!)
			//    listItems.clear(); }
			//public void addAcct(String s)
			//    {add the elements to the list model...
			     //make sure this new one is the currently selected value
         ///...more code here...

//} //end class AccountInterface
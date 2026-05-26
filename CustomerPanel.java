//-----------------------------------------
// NAME		: Mayson McLeod
// STUDENT NUMBER	: 7970371
// COURSE		: COMP 2150
// INSTRUCTOR	: John Anderson
// ASSIGNMENT	: assignment #5
// QUESTION	: question #1   
// 
// REMARKS: A JPanel that contains the buttons and text for the customer tab
//
//
//-----------------------------------------
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*; 

public class CustomerPanel extends JPanel implements ActionListener{

    private JButton clearButton,findButton,newButton;
	private JPanel buttonPannel;
	private JPanel fieldPannel;
	private LabelledField IDField,nameField,adressField;
	private final int LABEL_WIDTH = 750;
	private final Dimension dimensions = new Dimension(LABEL_WIDTH,LABEL_WIDTH/3);
	private BankServer server;

	/*
        CustomerPanel

        PURPOSE: construct the JPanel for the customer tab
        PARAMETERS: BankServer server: the server being manipulated
    */
    CustomerPanel(BankServer server){
		this.server = server;
		
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setPreferredSize(dimensions);
        Dimension buttonDimensions = new Dimension(LABEL_WIDTH/10,LABEL_WIDTH/10);
		clearButton = new JButton("Clear");
		clearButton.setPreferredSize(buttonDimensions);
		clearButton.addActionListener(this);
		findButton = new JButton("Find");
		findButton.addActionListener(this);
		findButton.setPreferredSize(buttonDimensions);
		newButton = new JButton("New");

		newButton.setPreferredSize(buttonDimensions);
		newButton.addActionListener(this);
		buttonPannel = new JPanel();
		buttonPannel.setPreferredSize(new Dimension(LABEL_WIDTH/9,LABEL_WIDTH/3));
		buttonPannel.add(clearButton);
		buttonPannel.add(findButton);
		buttonPannel.add(newButton);

		
		IDField = new LabelledField("Customer ID Number:", 20);
		
		nameField = new LabelledField("Name:", 20);
		adressField = new LabelledField("Adress:", 20);
		add(buttonPannel);

		fieldPannel = new JPanel();
		fieldPannel.setLayout(new BoxLayout(fieldPannel, BoxLayout.Y_AXIS));
		fieldPannel.add(IDField);
		fieldPannel.add(adressField);
		fieldPannel.add(nameField);
		add(fieldPannel);
    }

	/*
        actionPerformed

        PURPOSE: perform the correct action when an action listener is alerted
        PARAMETERS: ActionEvent e
        RETURNS: void method
    */
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.out.println(e.getActionCommand());
		String command = e.getActionCommand();
		if (command.equals("New")) {
			newCustomer();
		}else if(command.equals("Find")){
			findCustomer();
		}else if(command.equals("Clear")){
			clear();
		}
	}

	private void findCustomer(){
		String commandToServer = "";
		if (IDField.getText().equals("")) {
			commandToServer = "S /" + "0" + "/" + nameField.getText()+"/";
		}else{

			commandToServer = "S /" + IDField.getText() + "/" + nameField.getText()+"/";
		}
		String serverResponse = server.processLine(commandToServer);
		Container window = getWindow();
		((ClientWindow)window).setRequestResponse(commandToServer,serverResponse);
		if (!serverResponse.equals("")) {
			
		}
		((ClientWindow)window).processCustString(serverResponse);
	}

	private void clear(){
		((ClientWindow)getWindow()).clearCurrCustomer();
		IDField.setText("");
		nameField.setText("");
		adressField.setText("");
	}

	private void newCustomer(){
		String serverCommand = "C /";
		Container window = getWindow();
		String serverResponse = "";
		String name = nameField.getText();
		String adress = adressField.getText();
		serverCommand +=name + "/" + adress + "/";
		
			serverResponse = server.processLine(serverCommand);
			((ClientWindow)window).processCustString(serverResponse);
		
		
		
		
		
		((ClientWindow)window).setRequestResponse(serverCommand,serverResponse);
		
		
	}

	private Container getWindow(){
		Container parent = getParent();

		while (!(parent instanceof ClientWindow)) {
			parent = parent.getParent();
		}
		return parent;
	}
}

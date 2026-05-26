//-----------------------------------------
// NAME		: Mayson McLeod
// STUDENT NUMBER	: 7970371
// COURSE		: COMP 2150
// INSTRUCTOR	: John Anderson
// ASSIGNMENT	: assignment #5
// QUESTION	: question #1   
// 
// REMARKS: A JPanel that contains the buttons and text for the transaction tab
//
//
//-----------------------------------------
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.*;
import java.util.concurrent.Flow; 

public class TransactionPanel extends JPanel implements ActionListener, ListSelectionListener {
    private BankServer server;//the server being manipulated
    
    //the three sections of this tab
    private JPanel accountsPanel;
    private JPanel transactionPanel;
    private JPanel accountInfoPanel;


    private LabelledField accountType,accountNumber,balance,shares,sharePrice,serviceCharge;//the labels for account info
    private final int PANEL_WIDTH = 250;
    private Dimension mainPanelsSize = new Dimension(PANEL_WIDTH,PANEL_WIDTH);
    private JButton withdraw,deposit;

    private JList accountList;
    private JComboBox<String> accountTypes;
    private final String[] ACCOUNT_TYPES = {"Savings","Chequing","Locked in","Mutual Fund"};//the possible diffrent account types
    private LabelledField transactionAmount; 
    private JLabel accountsLabel;
    private JLabel newAccountLabel;
    private JPanel newAccountPanel;

    /*
        TransactionPanel

        PURPOSE: construct the transaction tab by initializing the components, adding the neccesary listeners and adding to the correct panels
        PARAMETERS: 
            BankServer server: the serve this panel is connected to
    */
    TransactionPanel(BankServer server){
        this.server = server;
        accountsPanel = new JPanel();
        accountsPanel.setPreferredSize(mainPanelsSize);

        newAccountLabel = new JLabel("Create new account:");
        accountTypes = new JComboBox<>(ACCOUNT_TYPES);
        accountTypes.addActionListener(this);
        newAccountPanel = new JPanel();
        newAccountPanel.add(newAccountLabel);
        newAccountPanel.add(accountTypes);


        accountList = new JList();
        accountList.addListSelectionListener(this);
        JScrollPane scrollPane = new JScrollPane(accountList);
        accountList.setPreferredSize(new Dimension(PANEL_WIDTH,(PANEL_WIDTH/5)*4));
        accountsPanel.add(newAccountPanel);
        accountsLabel = new JLabel("Accounts");
        accountsPanel.add(accountsLabel);
        accountsPanel.add(scrollPane);
        

        transactionPanel = new JPanel();
        transactionPanel.setPreferredSize(mainPanelsSize);
        transactionPanel.setBorder(BorderFactory.createTitledBorder("Transaction"));
        transactionAmount = new LabelledField("Amount:", 15);
        transactionPanel.add(transactionAmount);
        withdraw = new JButton("Withdrawal");
        withdraw.addActionListener(this);
        Dimension wdButtonSize = new Dimension((int)(PANEL_WIDTH/2.5),PANEL_WIDTH/10);
        withdraw.setPreferredSize(wdButtonSize);
        transactionPanel.add(withdraw);
        deposit = new JButton("Deposit");
        deposit.addActionListener(this);
        deposit.setPreferredSize(wdButtonSize);
        transactionPanel.add(deposit);



        accountInfoPanel = new JPanel();
        accountInfoPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        accountInfoPanel.setPreferredSize(mainPanelsSize);
        accountInfoPanel.setBorder(BorderFactory.createTitledBorder("Account Info:"));
        accountType = new LabelledField("Account Type:", 15,false);
        accountNumber = new LabelledField("Account Number:", 15,false);
        balance = new LabelledField("Balance:", 15,false);
        shares = new LabelledField("Shares:", 15,false);
        sharePrice = new LabelledField("Share Price:", 15,false);
        serviceCharge = new LabelledField("Service Charge:", 15,false);

        accountInfoPanel.add(accountType);
        accountInfoPanel.add(accountNumber);
        accountInfoPanel.add(balance);
        accountInfoPanel.add(shares);
        accountInfoPanel.add(sharePrice);
        accountInfoPanel.add(serviceCharge);

        add(accountsPanel);
        add(transactionPanel);
        add(accountInfoPanel);
    }

    /*
        clear

        PURPOSE: clear all of the infromation on the panel
        PARAMETERS: no parameters
        RETURNS: void method
    */
    public void clear(){
        String[] blankArray = {""};
        accountList.setListData(blankArray);
        transactionAmount.setText("");
        //accountType,accountNumber,balance,shares,sharePrice,serviceCharge
        accountType.setText("");
        accountNumber.setText("");
        balance.setText("");
        shares.setText("");
        sharePrice.setText("");
        serviceCharge.setText("");
    }

    /*
        setAcountList

        PURPOSE: set the list of accounts given an array of strings
        PARAMETERS: 
            String[] accounts: the strings to add to the list
        RETURNS: void method
    */
    public void setAcountList(String[] accounts){
       accountList.setListData(accounts);;

    }

    /*
        deposit

        PURPOSE: deposit the amount currently in the transaction box
        PARAMETERS: no parameters
        RETURNS: void method
    */
    private void deposit(){
        ClientWindow client = getWindow();
        String ids = client.getCurrCustomerID() + " " + accountList.getSelectedValue();
        String command = "D " + ids + " " + dollarsToCents(transactionAmount.getText());
        
        setAccountInfo(server.processLine(command));
        
    }

    /*
        withdraw

        PURPOSE: withdraw the amount currently in the transaction box
        PARAMETERS: no parameters
        RETURNS: void method
    */
    private void withdraw(){
        ClientWindow client = getWindow();
        String ids = client.getCurrCustomerID() + " " + accountList.getSelectedValue();

        String command = "W " + ids + " " + dollarsToCents(transactionAmount.getText());
        
        setAccountInfo(server.processLine(command));
    }

    /*
        dollarsToCents

        PURPOSE: given a string of numbers change it into a cent amount
        PARAMETERS: the string being changed into cents
        RETURNS: the dollars in cents
    */
    private String dollarsToCents(String dollars){
        double centsDBL = Double.parseDouble(dollars) *100;
        String cents = String.valueOf(centsDBL);
        return cents.substring(0, cents.indexOf("."));
    }

    /*
        actionPerformed

        PURPOSE: perform an action event
        PARAMETERS: 
            ActionEvent e: the event that happened
        RETURNS: void method
    */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == accountTypes) {
            makeNewAccount();
        }else if(e.getSource() == withdraw){
            withdraw();
        }else if(e.getSource() == deposit){
            deposit();
        }
        
        System.out.println(e.getActionCommand());
    }

    /*
        makeNewAccount

        PURPOSE: make a new account that is the type currently selected in the JComboBox
        PARAMETERS: no parameters
        RETURNS: void method
    */
    private void makeNewAccount(){
        ClientWindow client = getWindow();
        String serverCommand = "N "+((String)accountTypes.getSelectedItem()).charAt(0) + " "+ client.getCurrCustomerID();
        server.processLine(serverCommand);
        updateAccountList();
        
    }

    /*
        updateAccountList

        PURPOSE: display the accounts of the current customer
        PARAMETERS: no parameters
        RETURNS: void method
    */
    private void updateAccountList(){
        ClientWindow client = getWindow();
        int id = client.getCurrCustomerID();
        String custInfo = server.processLine("S /" + id + "//");
        
        custInfo = custInfo.trim(); //discard excess blanks

		String[] custInfoSplit = custInfo.split("/");

		String accountInfo[] = custInfoSplit[3].strip().split(" ");
		int numAccounts = Integer.parseInt(accountInfo[0]);
		String[] accounts = new String[numAccounts];
		for (int i = 0; i < numAccounts; i++) {
			accounts[i] = accountInfo[i+1];
		}
		setAcountList(accounts);
    }

    /*
        getWindow

        PURPOSE: find and return the parent window of this JPanel
        PARAMETERS: no parameters
        RETURNS: return the CLientWindow that is the parent
    */
    private ClientWindow getWindow(){
		Container parent = getParent();

		while (!(parent instanceof ClientWindow)) {
			parent = parent.getParent();
		}
		return (ClientWindow)parent;
	}

    /*
        valueChanged

        PURPOSE: run an action when the JList is changed
        PARAMETERS: no parameters
        RETURNS: void method
    */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (e.getSource() == accountList && accountList.getSelectedValue() != null) {
            ClientWindow window = getWindow();
           String accountNumber = (String)accountList.getSelectedValue();
           String response = server.processLine("F " +window.getCurrCustomerID() + " " + accountNumber);
           System.out.println(response);
           setAccountInfo(response);
        }
    }

    /*
        setAccountInfo

        PURPOSE: set the infromation for an account given a response from the server
        PARAMETERS: 
            String serverResponse: the string from the server containg the infromation from the account 
        RETURNS: void method
    */
    private void setAccountInfo(String serverResponse){
        String[] accountInfoSplit = serverResponse.split(" ");
        
        if (accountInfoSplit[0].equals("C")) {
            accountType.setText("Chequing");
            
            serviceCharge.setText(centsToDollars(accountInfoSplit[4]));
        }else if(accountInfoSplit[0].equals("S")){
            accountType.setText("Savings");
            serviceCharge.setText("N/A");
        }else if(accountInfoSplit[0].equals("L")){
            accountType.setText("Locked in");
        }else if(accountInfoSplit[0].equals("M")){
            String numShares = getShares(accountInfoSplit[2]);
            String priceOfShares = centsToDollars(accountInfoSplit[4]);
            String mfbalanceCents = String.valueOf(Double.parseDouble(priceOfShares) *Double.parseDouble(numShares)*100);

            accountType.setText("Mutual Fund");
            serviceCharge.setText("N/A");
            sharePrice.setText(priceOfShares);
            shares.setText(numShares);
            balance.setText(centsToDollars(mfbalanceCents));
        }
        if (!accountInfoSplit[0].equals("M")) {//if not a mutual fund shares are NA
            sharePrice.setText("N/A");
            balance.setText(centsToDollars(accountInfoSplit[2]));
            shares.setText("N/A");
        }
        accountNumber.setText(accountInfoSplit[1]);
        
    }

    /*
        getShares

        PURPOSE: calculate the number of shares given a fraction of shares
        PARAMETERS: String cents: the fraction value of shares
        RETURNS: void method
    */
    private String getShares(String cents){
        
        String dollars = String.valueOf(Double.parseDouble(cents)*0.001);

        int decemalIndex = dollars.indexOf(".");
        if (decemalIndex != -1) {
            dollars+= "00";
            dollars = dollars.substring(0, decemalIndex+3);
        }
        return dollars;
    }

    /*
        centsToDollars

        PURPOSE: change a money value in cents into dollars
        PARAMETERS: String cents: the number of cents
        RETURNS: return a string that is the dollar value
    */
    private String centsToDollars(String cents){
        
        String dollars = String.valueOf(Double.parseDouble(cents)*0.01);

        int decemalIndex = dollars.indexOf(".");
        if (decemalIndex != -1) {
            dollars+= "00";
            dollars = dollars.substring(0, decemalIndex+3);
        }
        return dollars;
    }
}

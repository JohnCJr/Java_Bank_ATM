package application;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Transaction {
	
	private static int transactionNumber = 0;
	private float customerNumber; // user pin number
	private float accountNumber;
	private char transactionType;	// W for withdraw or D for deposit
	private Date transactionDate = null;
	private float transactionAmount;
	private String timeStamp;	// formatted account creation date using pattern as it's template
	String pattern = "MM-dd-yyyy";
	SimpleDateFormat formatter = new SimpleDateFormat(pattern);
	String timeDate = "MM-dd-yyyy hh:mm a";
	SimpleDateFormat timeFormatter = new SimpleDateFormat(timeDate);	// formatter to display date and time
	private DecimalFormat currency = new DecimalFormat("\u00a4#,##0.00");	// formatter for dollar values
	
	
	// default constructor
	Transaction() {
		transactionNumber++;
		customerNumber = 00000;
		accountNumber = 0000000;
		transactionType = 'W';
		transactionDate = new Date();
		transactionAmount = 1000;
	}
	
	// constructor with parameters
	Transaction(float customerNumber, float accountNumber, char transactionType, float transactionAmount) {
		transactionNumber++;
		this.customerNumber = customerNumber;
		this.accountNumber = accountNumber;
		this.transactionType = transactionType;
		getCurrentDate();
		this.transactionAmount = transactionAmount;
	}

	
	public static int getTransactionNumber() {
		return transactionNumber;
	}

	public float getCustomerNumber() {
		return customerNumber;
	}

	public void setCustomerNumber(float customerNumber) {
		this.customerNumber = customerNumber;
	}

	public float getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(float accountNumber) {
		this.accountNumber = accountNumber;
	}

	public char getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(char transactionType) {
		this.transactionType = transactionType;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public float getTransactionAmount() {
		return transactionAmount;
	}

	public void setTransactionAmount(float transactionAmount) {
		this.transactionAmount = transactionAmount;
	}
	
	// sets timeStamp with the current date
	public void getCurrentDate() {
		
		transactionDate = new Date();
		timeStamp = timeFormatter.format(transactionDate);
	}
	
	public void computeTransaction(float accountBalance) {
		
		if (transactionType == 'W') {
			accountBalance -= transactionAmount;
		}
		else if (transactionType == 'D') {
			accountBalance += transactionAmount;
		}

	}
	
	// WARNING: this function is meant to be used before the ArrayList is updated with the new balance,
	//          will write the wrong transaction amount if called after the new balance is set
	// used to write transaction details to a text file named after the client's name
	// the text file serves as a receipt that can be accessed after the program closes
	public void writeTransaction(ArrayList<Customer> list, int index) throws IOException {
		String name = list.get(index).getCustomerName();
		String phoneNumber = list.get(index).getCustomerPhone();
		String address = list.get(index).getCustomerAddress();
		String emailAddress = list.get(index).getCustomerEmail();
		String transaction = currency.format(transactionAmount);
		String type;
		String accountText;
		String accountNumberText = String.format("%.0f", accountNumber);
		String accountOpenDate;
		float previousBalance;
		String previousBalanceText; 
		String newBalance;
		
		FileWriter writer = new FileWriter(name + ".txt");
		BufferedWriter buffer = new BufferedWriter(writer);
		
		if (Math.round(accountNumber)%10 ==  1) {
			accountText = "Checking";
			previousBalance = list.get(index).checkingAccount.getBalance();
			accountOpenDate = formatter.format(list.get(index).checkingAccount.getOpenDate());
			previousBalanceText = currency.format(previousBalance);
			}
		else {
			accountText = "Savings";
			previousBalance = list.get(index).savingsAccount.getBalance();
			accountOpenDate = formatter.format(list.get(index).savingsAccount.getOpenDate());
			previousBalanceText = currency.format(previousBalance);
			}
		
		if (transactionType == 'W') {
			transaction = "-" + transaction;
			newBalance = currency.format(previousBalance - transactionAmount);
			type = "Withdraw";
			}
		else {
			transaction = "+" + transaction;
			newBalance = currency.format(previousBalance + transactionAmount);
			type = "Deposit";
			}
		
		buffer.write("Infinity Bank:\n225 Grand Dr.\nName: " + name + "\nAddress: " + address 
				+ "\nPhone: " + phoneNumber + "\nEmail: " + emailAddress + "\nAccount type: " 
				+ accountText + "\nAccount number: " + accountNumberText + "\nAccount opening date: "
				+ accountOpenDate + "\nType: " + type + "\nPrevious balance: " + previousBalanceText 
				+ "\nTransaction: " + transaction + "\nNew balance: " + newBalance + "\nTotal transactions: " 
				+ Transaction.transactionNumber + "\nDate: " + timeStamp);
		
		System.out.println("...File write complete");
		buffer.close();
	}
	
	// WARNING: this function is meant to be used before the ArrayList is updated with the new balances,
	//          will write the wrong balance transfer amount if called after the new balances are set
	// used to write balance transfer details to a text file named after the client's name
	// the text file serves as a receipt that can be accessed after the program closes
	public void writeTransfer(ArrayList<Customer> list, int index) throws IOException {
		
		String name = list.get(index).getCustomerName();
		String phoneNumber = list.get(index).getCustomerPhone();
		String address = list.get(index).getCustomerAddress();
		String emailAddress = list.get(index).getCustomerEmail();
		String checkingTransaction = currency.format(transactionAmount);
		String checkingAccountNumberText = list.get(index).checkingAccount.getAccountNumber().toString();
		String checkingAccountOpenDate = formatter.format(list.get(index).checkingAccount.getOpenDate());
		float checkingPreviousBalance = list.get(index).checkingAccount.getBalance();
		String checkingPreviousBalanceText = currency.format(checkingPreviousBalance);
		String newCheckingBalance;
		String savingsAccountOpenDate = formatter.format(list.get(index).savingsAccount.getOpenDate());
		float savingsPreviousBalance = list.get(index).savingsAccount.getBalance();
		String savingsPreviousBalanceText = currency.format(savingsPreviousBalance);
		String newSavingsBalance;
		String checkingType;
		String savingsTransaction = currency.format(transactionAmount);
		String savingsAccountNumberText = list.get(index).savingsAccount.getAccountNumber().toString();
		String savingsType;
		String lineBreak = "\n--------------------------------------";
	
		
		
		FileWriter writer = new FileWriter(list.get(index).getCustomerName() + ".txt");
		BufferedWriter buffer = new BufferedWriter(writer);
		
		if (list.get(index).checkingAccount.currentTransaction.getTransactionType() == 'W') {
			checkingTransaction = "-" + checkingTransaction;
			savingsTransaction = "+" + savingsTransaction;
			newCheckingBalance = currency.format(checkingPreviousBalance - transactionAmount);
			newSavingsBalance = currency.format(savingsPreviousBalance + transactionAmount);
			checkingType = "Withdraw";
			savingsType = "Deposit";
			}
		else {
			checkingTransaction = "+" + checkingTransaction;
			savingsTransaction = "-" + savingsTransaction;
			newCheckingBalance = currency.format(checkingPreviousBalance + transactionAmount);
			newSavingsBalance = currency.format(savingsPreviousBalance - transactionAmount);
			checkingType = "Deposit";
			savingsType = "Withdraw";
			}
		buffer.write("Infinity Bank:\n225 Grand Dr.\nName: " +  name + "\nAddress: " + address  
				+ "\nPhone number: " + phoneNumber + "\nEmail: " + emailAddress + lineBreak + "\nAccount type: Checking" + "\nAccount number: " 
				+ checkingAccountNumberText + "\nAccount opening date: " + checkingAccountOpenDate + "\nPrevious balance: " 
				+ checkingPreviousBalanceText + "\nTransaction type: "+ checkingType + "\nTransaction amount: " + checkingTransaction
				+ "\nNew balance: " + newCheckingBalance + lineBreak + "\nAccount type: Savings" + "\nAccount number: " 
				+ savingsAccountNumberText + "\nAccount opening date: " + savingsAccountOpenDate + "\nPrevious balance: "
				+ savingsPreviousBalanceText + "\nTransaction type: " + savingsType + "\nTransaction amount: " + savingsTransaction 
				+ "\nNew balance: " + newSavingsBalance + lineBreak + "\nTotal transactions: " + Transaction.transactionNumber 
				+ "\nDate: " + timeStamp); 
		
		System.out.println("...File write complete");
		buffer.close();
	}
}

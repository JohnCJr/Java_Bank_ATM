package application;

import java.util.Date;

public class Account {
	
	private Number accountNumber; // 7-digits, must end with 0 and either 1(checking) or 2(savings)
	private Number accountType;	// 1 - checking, 2 - savings
	private Date openDate;	// date that the account was opened
	private float balance;	// current balance in the account
	private Number customerID;	//5-digit ID
	Transaction currentTransaction = null;	
	
	Account(){
		accountNumber = 0000000;
		accountType = 1;
		openDate = new Date();
		balance = 12000;
		customerID = 00000;	
	}
	
	// constructor that will be called when creating the ArrayList of Customers
	Account(Number accountNumber, Number accountType, Date openDate, float balance, Number customerID) {
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.openDate = openDate;
		this.balance = balance;
		this.customerID = customerID;
	}
	
	public Number getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(Number accountNumber) {
		this.accountNumber = accountNumber;
	}
	public Number getAccountType() {
		return accountType;
	}
	public void setAccountType(Number accountType) {
		this.accountType = accountType;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	public float getBalance() {
		return balance;
	}
	public void setBalance(float balance) {
		this.balance = balance;
	}
	public Number getCustomerID() {
		return customerID;
	}
	public void setCustomerID(Number customerID) {
		this.customerID = customerID;
	}
	
	// function that creates a transaction inside the Account object that it is being called with at that moment 
	// for example if this is called with a checking account Account then this transaction will be tied to that
    public void createTransaction(char transactionType, float transactionAmount) {
    	 currentTransaction = new Transaction(customerID.floatValue(), accountNumber.floatValue(), transactionType, transactionAmount);
		
	}
}

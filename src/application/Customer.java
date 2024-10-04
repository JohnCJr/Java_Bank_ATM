package application;

import java.util.Date;

public class Customer {
	
	private int customerID;	// needs to be a 5-digit ID
	private int customerPIN;	// needs to be a 4-digit PIN
	private String customerName;
	private String customerEmail;
	private String customerPhone;
	private String customerAddress;
	
	Account checkingAccount = null;
	Account savingsAccount = null;
	
	// default constructor
	Customer(){
		customerID = 00000;
		customerPIN = 0000;
		customerName = "None";
		customerEmail = "None";
		customerPhone = "None";
		customerAddress = "None";
	}
	
	// constructor with parameter that will be used when filling the ArrayList of Customers
	Customer(int customerID, int customerPIN, String customerName, String customerEmail, String customerPhone, String customerAddress,
			Number checkingAccountNumber, Number checkingAccountType, Date checkingOpenDate, float checkingBalance, Number savingAccountNumber, 
			Number savingAccountType, Date savingOpenDate, float savingBalance){
		this.customerID = customerID;
		this.customerPIN = customerPIN;
		this.customerName = customerName;
		this.customerEmail = customerEmail;
		this.customerPhone = customerPhone;
		this.customerAddress = customerAddress;
		
		// Objects of type Account are created using their parameter constructors
		checkingAccount = new Account(checkingAccountNumber, checkingAccountType, checkingOpenDate, checkingBalance, customerID);
		savingsAccount = new Account(savingAccountNumber, savingAccountType, savingOpenDate, savingBalance, customerID);
	}
	
	public int getCustomerID() {
		return customerID;
	}
	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}
	public int getCustomerPIN() {
		return customerPIN;
	}
	public void setCustomerPIN(int customerPIN) {
		this.customerPIN = customerPIN;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerEmail() {
		return customerEmail;
	}
	public void setCustomerEmail(String customerEmail) {
		this.customerEmail = customerEmail;
	}
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public String getCustomerAddress() {
		return customerAddress;
	}
	public void setCustomerAddress(String customerAddress) {
		this.customerAddress = customerAddress;
	}
	
	/*
	public void setAccount(Number accountNumber, Number accountType, Date openDate, float balance, Number customerID) {
		checkingAccount = new Account(accountNumber, accountType, openDate, balance, customerID);
	
	}
*/
}

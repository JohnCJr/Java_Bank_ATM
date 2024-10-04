package application;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ReceiptController {
	
	@FXML
	private Label bankAddressLabel;
	@FXML
	private Label userNameLabel;
	@FXML
	private Label customerAddressLabel;
	@FXML
	private Label custumerPhoneLabel;
	@FXML
	private Label customerEmailLabel;
	@FXML
	private Label checkingOpenDateLabel;
	@FXML
	private Label checkingAccountNumberLabel;
	@FXML
	private Label checkingStartingBalanceLabel;
	@FXML
	private Label checkingWithdrawLabel;	// will be empty label if no withdraw was made on checking account
	@FXML
	private Label checkingDepositLabel;	// will be empty label if no deposit was made on checking account
	@FXML
	private Label checkingGrandTotalLabel;	// balance after transaction/transfer is made
	@FXML
	private Label savingsOpenDateLabel;
	@FXML
	private Label savingsAccountNumberLabel;
	@FXML
	private Label savingsStartingBalanceLabel;
	@FXML
	private Label savingsWithdrawLabel;	// will be empty label if no withdraw was made on savings account
	@FXML
	private Label savingsDepositLabel;	// will be empty label if no deposit was made on savings account
	@FXML
	private Label savingsGrandTotalLabel;	// balance after transaction/transfer is made
	@FXML
	private Label dateLabel;	// label for date that transaction was made
	@FXML
	private Label totalTransactionCountLabel;
	@FXML
	ImageView imageFrame;
	@FXML
	ImageView imageFrame2;
	
	
	private String pattern = "MM-dd-yyyy";	// pattern used for account open date
	private String timeDay = "MM-dd-yyyy hh:mm a";	// pattern that is used for the date that the transaction was made
	private SimpleDateFormat formatter = new SimpleDateFormat(pattern);	// formats date
	private SimpleDateFormat timeFormatter = new SimpleDateFormat(timeDay);	// formats date and time
	private DecimalFormat currency = new DecimalFormat("\u00a4#,##0.00");	// formats number value to U.S currency
	private float currentTransactionAmount;
	private float checkingStartingBalance;
	private float checkingEndBalance;
	private float savingsStartingBalance;
	private float savingsEndBalance;
	
	// function that fills the labels in the receipt scene if a withdraw or deposit is made
	public void fillTransactionReceipt(ArrayList<Customer>list, int index, float transactionAmount, Number accountNum, char transactionType) {
		currentTransactionAmount = transactionAmount;
		
		if (transactionType == 'W') {
			if (accountNum.intValue() ==  1) {
				dateLabel.setText(timeFormatter.format(list.get(index).checkingAccount.currentTransaction.getTransactionDate()));
				checkingWithdrawLabel.setText(currency.format(currentTransactionAmount));
				checkingStartingBalance = (list.get(index).checkingAccount.getBalance()) + currentTransactionAmount;
				checkingEndBalance = list.get(index).checkingAccount.getBalance();
				savingsStartingBalance = list.get(index).savingsAccount.getBalance();
				savingsEndBalance = list.get(index).savingsAccount.getBalance();
			}
			else {
				dateLabel.setText(timeFormatter.format(list.get(index).savingsAccount.currentTransaction.getTransactionDate()));
				savingsWithdrawLabel.setText(currency.format(currentTransactionAmount));
				checkingStartingBalance = (list.get(index).checkingAccount.getBalance());
				checkingEndBalance = list.get(index).checkingAccount.getBalance();
				savingsStartingBalance = list.get(index).savingsAccount.getBalance() + currentTransactionAmount;
				savingsEndBalance = list.get(index).savingsAccount.getBalance();
			}	
		}
		else {
			if (accountNum.intValue() ==  1) {
				dateLabel.setText(timeFormatter.format(list.get(index).checkingAccount.currentTransaction.getTransactionDate()));
				checkingDepositLabel.setText(currency.format(currentTransactionAmount));
				checkingStartingBalance = (list.get(index).checkingAccount.getBalance()) - currentTransactionAmount;
				checkingEndBalance = list.get(index).checkingAccount.getBalance();
				savingsStartingBalance = list.get(index).savingsAccount.getBalance();
				savingsEndBalance = list.get(index).savingsAccount.getBalance();
			}
			else {
				dateLabel.setText(timeFormatter.format(list.get(index).savingsAccount.currentTransaction.getTransactionDate()));
				savingsDepositLabel.setText(currency.format(currentTransactionAmount));
				checkingStartingBalance = (list.get(index).checkingAccount.getBalance());
				checkingEndBalance = list.get(index).checkingAccount.getBalance();
				savingsStartingBalance = list.get(index).savingsAccount.getBalance() - currentTransactionAmount;
				savingsEndBalance = list.get(index).savingsAccount.getBalance();
				}
		}
		bankAddressLabel.setText("225 Grand Dr.");
		customerAddressLabel.setText(list.get(index).getCustomerAddress());
		custumerPhoneLabel.setText(list.get(index).getCustomerPhone());
		customerEmailLabel.setText(list.get(index).getCustomerEmail());
		userNameLabel.setText(list.get(index).getCustomerName());
		checkingOpenDateLabel.setText(formatter.format(list.get(index).checkingAccount.getOpenDate()));
		checkingAccountNumberLabel.setText(list.get(index).checkingAccount.getAccountNumber().toString());
		checkingStartingBalanceLabel.setText(currency.format(checkingStartingBalance));
		checkingGrandTotalLabel.setText(currency.format(checkingEndBalance));
		
		savingsOpenDateLabel.setText(formatter.format(list.get(index).savingsAccount.getOpenDate()));
		savingsAccountNumberLabel.setText(list.get(index).savingsAccount.getAccountNumber().toString());
		savingsStartingBalanceLabel.setText(currency.format(savingsStartingBalance));
		savingsGrandTotalLabel.setText(currency.format(savingsEndBalance));
		totalTransactionCountLabel.setText(Integer.toString(Transaction.getTransactionNumber()));
	}
	
	// function that fills the labels in the receipt scene if a balance transfer is made
	public void fillTransferReceipt(ArrayList<Customer>list, int index) {
		currentTransactionAmount = list.get(index).checkingAccount.currentTransaction.getTransactionAmount();
		char checkingTransactionType = list.get(index).checkingAccount.currentTransaction.getTransactionType();
		
		if (checkingTransactionType == 'W') {
				checkingWithdrawLabel.setText(currency.format(currentTransactionAmount));
				checkingStartingBalance = (list.get(index).checkingAccount.getBalance()) + currentTransactionAmount;
				checkingEndBalance = list.get(index).checkingAccount.getBalance();
				savingsDepositLabel.setText(currency.format(currentTransactionAmount));
				savingsStartingBalance = list.get(index).savingsAccount.getBalance() - currentTransactionAmount;
				savingsEndBalance = list.get(index).savingsAccount.getBalance();
				}
		else {
			checkingDepositLabel.setText(currency.format(currentTransactionAmount));
			checkingStartingBalance = (list.get(index).checkingAccount.getBalance()) - currentTransactionAmount;
			checkingEndBalance = list.get(index).checkingAccount.getBalance();
			savingsWithdrawLabel.setText(currency.format(currentTransactionAmount));
			savingsStartingBalance = list.get(index).savingsAccount.getBalance() + currentTransactionAmount;
			savingsEndBalance = list.get(index).savingsAccount.getBalance();
		}
		
		bankAddressLabel.setText("225 Grand Dr.");
		customerAddressLabel.setText(list.get(index).getCustomerAddress());
		custumerPhoneLabel.setText(list.get(index).getCustomerPhone());
		customerEmailLabel.setText(list.get(index).getCustomerEmail());
		dateLabel.setText(timeFormatter.format(list.get(index).checkingAccount.currentTransaction.getTransactionDate()));
		userNameLabel.setText(list.get(index).getCustomerName());
		checkingOpenDateLabel.setText(formatter.format(list.get(index).checkingAccount.getOpenDate()));
		checkingAccountNumberLabel.setText(list.get(index).checkingAccount.getAccountNumber().toString());
		checkingStartingBalanceLabel.setText(currency.format(checkingStartingBalance));
		checkingGrandTotalLabel.setText(currency.format(checkingEndBalance));
		
		savingsOpenDateLabel.setText(formatter.format(list.get(index).savingsAccount.getOpenDate()));
		savingsAccountNumberLabel.setText(list.get(index).savingsAccount.getAccountNumber().toString());
		savingsStartingBalanceLabel.setText(currency.format(savingsStartingBalance));
		savingsGrandTotalLabel.setText(currency.format(savingsEndBalance));
		totalTransactionCountLabel.setText(Integer.toString(Transaction.getTransactionNumber()));
	}
}

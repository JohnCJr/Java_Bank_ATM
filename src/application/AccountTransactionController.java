package application;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class AccountTransactionController implements Initializable{

	@FXML
	private Label transactionLabel;
	@FXML
	private Label transactionAmountLabel;
	@FXML
	private Label transactionWarningLabel;
	@FXML
	private TextField amountTextField;
	
	private DecimalFormat currency = new DecimalFormat("\u00a4#,##0.00");	// U.S currency formatter
	private float input;	// user input for transaction amount
	private Number accountType;
	private float currentBalance;
	private char transactionType;
	private ArrayList<Customer> clients = new ArrayList<Customer>();
	private ClientList list = new ClientList();
	private int index;

	
	// initializes the clients ArrayList 
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		index = list.getIndex();
		clients = list.getClientList();
	}
	
	// updates the balance and stores it into the array, updating the text file containing the client information
	// also sets up the receipt scene by passing on information about the transaction
	public void updateBalanceAmount (ActionEvent e) throws IOException {
		
		if (accountType.intValue() == 1) {
			currentBalance = clients.get(index).checkingAccount.getBalance();
		}
		else {
			currentBalance = clients.get(index).savingsAccount.getBalance();
		}
		
		try {
			input = Float.parseFloat(amountTextField.getText());
			if (transactionType == 'W' && input > currentBalance) {
				amountTextField.clear();
				setTransactionWarningLabel("Cannot withdraw more than your current balance!");
			}
			else if (input <= 0) {
				amountTextField.clear();
				setTransactionWarningLabel("Must enter a number greater than 0!");
			}
			else {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation");
				alert.setHeaderText("You are about to process a transaction.");
				alert.setContentText("Please click ok to confirm.");
				if(alert.showAndWait().get() == ButtonType.OK) {
					
					currentBalance = processTransaction(currentBalance, input, transactionType);
					if (accountType.intValue() == 1) {
						clients.get(index).checkingAccount.createTransaction(transactionType, input);	// creates checking account transaction object
						clients.get(index).checkingAccount.currentTransaction.writeTransaction(clients, index);	// writes transaction to text file
						clients.get(index).checkingAccount.setBalance(currentBalance);	// sets new balance after transaction for checking account
					}
					else {
						clients.get(index).savingsAccount.createTransaction(transactionType, input);	// creates savings account transaction object
						clients.get(index).savingsAccount.currentTransaction.writeTransaction(clients, index);	// writes transaction to text file
						clients.get(index).savingsAccount.setBalance(currentBalance);	// sets new balance after transaction for savings account
					}
					list.updateClientList(clients);
					
					Alert completion = new Alert(AlertType.INFORMATION); // new after that informs user that the program will "close" and set the receipt scene
					completion.setTitle("Transaction Complete.");
					completion.setHeaderText("Thank you for banking with us!");
					completion.setContentText("The program will now close and a receipt of your transaction will be displayed.");
					completion.showAndWait();
					
					FXMLLoader loader = new FXMLLoader (getClass().getResource("Receipt.fxml"));
					Parent root = loader.load();
					Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
					ReceiptController receipt = loader.getController();
					receipt.fillTransactionReceipt(clients, index, input, accountType, transactionType);	// passes information onto the receipt scene
					Scene scene = new Scene(root);
					stage.setMinHeight(660);
					stage.setMinWidth(500);
					stage.setMaxHeight(660);
					stage.setMaxWidth(500);
					stage.setTitle("Receipt");
					stage.setScene(scene);
					stage.show();
				}
				else {amountTextField.clear();}
			}		
		}catch(NumberFormatException excpt) {
			transactionWarningLabel.setText("Must insert a numerical value");
		}catch (Exception InputMismatch) {
			Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			stage.close();
			System.out.println("Something went wrong. Please try again.");
		}
	}
	
	// confirms if the user wants to cancel their transaction and return to scene2
	public void cancelConfirmation(ActionEvent e) {
		try {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Cancel");
			alert.setHeaderText("You are about to cancel your transaction.");
			alert.setContentText("Are you sure you want to cancel? You will be returned to your account summary.");
			if(alert.showAndWait().get() == ButtonType.OK) {
				FXMLLoader loader = new FXMLLoader (getClass().getResource("Scene2.fxml"));
				Parent root = loader.load();
				Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
				Scene scene = new Scene(root);
				stage.setHeight(600);
				stage.setWidth(800);
				stage.setMaxHeight(600);
				stage.setMaxWidth(800);
				stage.setTitle("Account Summary");
				stage.setScene(scene);
				stage.show();	
				}
		}catch (Exception excpt) {
			System.out.println("Something went wrong!");
		}
	}
	
	public void setTransactionLabel(String type, String account) {
		transactionLabel.setText("How much would you like to " + type +" to your "
				+ account + " account?");
	}
	
	public void setTransactionAmountLabel(float balance) {
		transactionAmountLabel.setText(currency.format(balance));
	}
	
	public void setTransactionWarningLabel(String warning) {
		transactionWarningLabel.setText(warning);
	}
	
	public void setAccountType(Number accountType) {
		this.accountType = accountType;
	}
	
	public void setTransactionType(char c) {
		transactionType = c;
	}
	
	// returns the balance of the account based on the type of transaction the user makes
	public float processTransaction(float current, float userInput, char type) {
		float value;
		if (type == 'W') {value = current - userInput;}
		else {value = current + userInput;}
		return value;
	}
	
	
  
}

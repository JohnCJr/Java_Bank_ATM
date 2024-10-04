package application;

import java.net.URL;
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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class AccountSelectionController implements Initializable{
	
	@FXML
	private Button checkingButton;
	@FXML
	private Button savingsButton;
	
	private ArrayList<Customer> clients = new ArrayList<Customer>();
	ClientList list = new ClientList();
	private int index;
	private float checkingBalance;
	private float savingsBalance;
	private String accountText;	// stores the account type as a string
	private Number accountType;	// 1- checking 2- savings
	private char transactionType; // 'W' - withdraw 'D' - deposit
	private String transaction;	// stores string value of the type of transaction being made
	private float balance;	//	will store balance of account in which a transaction is being made
	
	// initializes the scene with the index and list of clients along with the balance of both the checking and savings accounts 
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		index = list.getIndex();
		clients = list.getClientList();
		checkingBalance = clients.get(index).checkingAccount.getBalance();
		savingsBalance = clients.get(index).savingsAccount.getBalance();
	}

	// stores the value of the balance based on what the user pressed, checking or savings, 
	// and passes that information to the next scene 
	public void getButton(ActionEvent e){
		if (transactionType == 'W') {transaction = "withdraw";}
		else {transaction = "deposit";}
		
		if (e.getSource().equals(checkingButton))
		{
			accountText = "checking";
			accountType = 1;
			balance = checkingBalance;
		}
		else if (e.getSource().equals(savingsButton)){
			accountText = "savings";
			accountType = 2;
			balance = savingsBalance;
		}
		
		try {
			FXMLLoader loader = new FXMLLoader (getClass().getResource("TransactionScene.fxml"));
			Parent root = loader.load();
			Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			Scene scene = new Scene(root);
			AccountTransactionController accountTransactionController = loader.getController();
			accountTransactionController.setAccountType(accountType);
			accountTransactionController.setTransactionLabel(transaction,accountText);
			accountTransactionController.setTransactionAmountLabel(balance);
			accountTransactionController.setTransactionType(transactionType);
			stage.setTitle("Process Transaction");
			stage.setMinHeight(500);
			stage.setMinWidth(700);
			stage.setMaxHeight(600);
			stage.setMaxWidth(700);
			stage.setScene(scene);
			stage.show();
		}catch(Exception excpt){
			Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			stage.close();
			System.out.println("Something went wrong!");
		}
	}
	
	// will confirm the cancellation of the transaction and return the user to the account summary scene (Scene2)
	public void cancelConfirmation(ActionEvent e) {
		
		try {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Cancel");
			alert.setHeaderText("You are about to cancel your transaction.");
			// this is the body of the alert
			alert.setContentText("Are you sure you want to cancel?");
			if(alert.showAndWait().get() == ButtonType.OK) {
				FXMLLoader loader = new FXMLLoader (getClass().getResource("Scene2.fxml"));
				Parent root = loader.load();
				Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
				Scene scene = new Scene(root);
				stage.setTitle("Account Summary");
				stage.setHeight(600);
				stage.setWidth(800);
				stage.setMaxHeight(600);
				stage.setMaxWidth(800);
				stage.setScene(scene);
				stage.show();	
				}
		}catch (Exception excpt) {
			Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			stage.close();
			System.out.println("Something went wrong!");
		}
		
	}
	
	public void setTransactionType(char c) {
		transactionType = c;
	}

	
	
}

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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class BalanceTransferController implements Initializable{

	@FXML
	private ChoiceBox<String> toChoiceBox;
	@FXML
	private ChoiceBox<String> fromChoiceBox;
	@FXML
	private TextField transferInput;
	@FXML
	private Label checkingBalanceLabel;
	@FXML
	private Label savingsBalanceLabel;
	
	private String[] accounts = {"checking", "savings"};	// array used to the options of the ChoiceBoxes
	private DecimalFormat currency = new DecimalFormat("\u00a4#,##0.00");
	private float savingsBalance;
	private float checkingBalance;
	private float inputValue;
	private float limit;
	private char checkingTransactionType;
	private char savingsTransactionType;
	private ArrayList<Customer> clients = new ArrayList<Customer>();
	private ClientList list = new ClientList();
	private int index;

	// initializes the labels for the savings and checking balance, the ChoiceBox options, 
	// and the limit for the transfer
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		index = list.getIndex();
		clients = list.getClientList();
		savingsBalance = clients.get(index).savingsAccount.getBalance();
		checkingBalance = clients.get(index).checkingAccount.getBalance();
		checkingBalanceLabel.setText(currency.format(checkingBalance));
		savingsBalanceLabel.setText(currency.format(savingsBalance));
		toChoiceBox.getItems().addAll(accounts);
		fromChoiceBox.getItems().addAll(accounts);
		toChoiceBox.setValue(accounts[0]);
		fromChoiceBox.setValue(accounts[1]);
		limit = savingsBalance;
	}

	// gets selection based off what was selected from the toChoiceBox
	// and assigns proper values based off of choice
	public void getToSelection(ActionEvent e) {
		if (toChoiceBox.getValue() ==  accounts[0]){
			fromChoiceBox.setValue(accounts[1]);
			limit = savingsBalance;
			checkingTransactionType = 'D';
			savingsTransactionType = 'W';
		}
		else {
			fromChoiceBox.setValue(accounts[0]);
			limit = checkingBalance;
			checkingTransactionType = 'W';
			savingsTransactionType = 'D';
		}
	}
	
	/// gets selection based off what was selected from the fromChoiceBox
	// and assigns proper values based off of choice
	public void getFromSelection(ActionEvent e) {
		if (fromChoiceBox.getValue() ==  accounts[0]){
			toChoiceBox.setValue(accounts[1]);
			limit = checkingBalance;
			checkingTransactionType = 'W';
			savingsTransactionType = 'D';
		}
		else {
			toChoiceBox.setValue(accounts[0]);
			limit = savingsBalance;
			checkingTransactionType = 'D';
			savingsTransactionType = 'W';
		}
	}
	
	// submits transfer amount, updates ArrayList to update client text file, 
	// writes transaction to text file named after the user, "closes" the program and display receipt scene
	public void submitTransfer(ActionEvent e) {
		
		try {
			inputValue = Float.parseFloat(transferInput.getText());

			if (inputValue > limit) {
				Alert warning = new Alert(AlertType.WARNING);
				warning.setTitle("Invalid Entry");
				warning.setHeaderText("You cannot tranfer more than what is in your account!");
				warning.setContentText("Please enter a number within your balance range.");
				warning.showAndWait();
				transferInput.clear();
			}
			else if (inputValue <= 0) {
				Alert warning = new Alert(AlertType.WARNING);
				warning.setTitle("Invalid Entry");
				warning.setHeaderText("You cannot enter an amount less than or equal to 0!");
				warning.setContentText("Please enter a number greater than 0.");
				warning.showAndWait();
				transferInput.clear();
			}
			else {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Confirmation");
				alert.setHeaderText("You are about to process a transfer.");
				alert.setContentText("Please click ok to confirm.");
				if(alert.showAndWait().get() == ButtonType.OK) {
					if (toChoiceBox.getValue() ==  accounts[0]){
						checkingBalance += inputValue;
						savingsBalance -= inputValue;
					}
					else {
						checkingBalance -= inputValue;
						savingsBalance += inputValue;
					}
					clients.get(index).checkingAccount.createTransaction(checkingTransactionType, inputValue);
					clients.get(index).savingsAccount.createTransaction(savingsTransactionType, inputValue);
					clients.get(index).checkingAccount.currentTransaction.writeTransfer(clients, index);
					clients.get(index).checkingAccount.setBalance(checkingBalance);
					clients.get(index).savingsAccount.setBalance(savingsBalance);
					list.updateClientList(clients);
					
					Alert completion = new Alert(AlertType.INFORMATION);
					completion.setTitle("Balance transfer complete.");
					completion.setHeaderText("Thank you for banking with us!");
					completion.setContentText("The program will now close and a receipt of your transaction will be displayed.");
					completion.showAndWait();
					
					FXMLLoader loader = new FXMLLoader (getClass().getResource("Receipt.fxml"));
					Parent root = loader.load();
					Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
					ReceiptController receipt = loader.getController();
					receipt.fillTransferReceipt(clients, index);
					Scene scene = new Scene(root);
					stage.setMinHeight(660);
					stage.setMinWidth(500);
					stage.setMaxHeight(660);
					stage.setMaxWidth(500);
					stage.setTitle("Receipt");
					stage.setScene(scene);
					stage.show();
				}
				
			}		
		}catch(NumberFormatException | IOException excpt) {
			transferInput.clear();
			Alert warning = new Alert(AlertType.WARNING);
			warning.setTitle("Invalid Entry");
			warning.setHeaderText("You have entered an invalid value.");
			warning.setContentText("Please enter a proper value");
			warning.showAndWait();
		}
	}
	
	// cancels transaction and returns the user to Scene2
	public void cancelTransfer(ActionEvent e) {
		
		try {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Cancel");
			alert.setHeaderText("You are about to cancel your transfer.");
			// this is the body of the alert
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
			Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			stage.close();
			System.out.println("Something went wrong!");
		}
		
	}
}

package application;

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
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class Scene2Controller implements Initializable{

	private Stage stage;
	private Scene scene;
	private Parent root;
	private int index;
	private char transactionType;	// stores transaction type 'D' - deposit  'W' - withdraw
	private DecimalFormat currency = new DecimalFormat("\u00a4#,##0.00");	// used to format numerical values into U.S currency
	private ArrayList<Customer> clients = new ArrayList<Customer>();
	ClientList list = new ClientList();
	
	@FXML
	private Label welcomeLabel;
	@FXML
	private Label checkingBalanceLabel;
	@FXML
	private Label savingsBalanceLabel;
	@FXML
	private Button checkingButton;
	
	
	// initializes the clients ArrayList along with the labels present in the scene attached to this controller
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		index = list.getIndex();
		clients = list.getClientList();
		String name = clients.get(index).getCustomerName();
		welcomeLabel.setText("Welcome back " + name);
		setCheckingBalanceLabel(clients.get(index).checkingAccount.getBalance());
		setSavingsBalanceLabel(clients.get(index).savingsAccount.getBalance());
	}

	public void setCheckingBalanceLabel(float text)
	{
		checkingBalanceLabel.setText(currency.format(text));
	}
	public void setSavingsBalanceLabel(float text)
	{
		savingsBalanceLabel.setText(currency.format(text));
	}
	
	// confirms if the user wants to log out, will be returned to scene1 (the sign in screen)
	public void logoutConfirmation(ActionEvent e) {
		
		try {
			FXMLLoader loader = new FXMLLoader (getClass().getResource("Scene1.fxml"));
			root = loader.load();
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Log out");
			alert.setHeaderText("You are about to log out.");
			alert.setContentText("Are you sure you want to log out?");
			if(alert.showAndWait().get() == ButtonType.OK) {
				scene = new Scene(root);
				stage.setTitle("User Sign-in");
				stage.setScene(scene);
				stage.setHeight(420);
				stage.setWidth(600);
				stage.show();
				}
		}catch (Exception excpt) {
			Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			stage.close();
			System.out.println("Something went wrong!");
		}
		
	}
	
	// sets the transaction type as a withdraw and passes that information onto the next scene
	public void withdrawConfirmation(ActionEvent e) {
		try {
			transactionType = 'W';
			FXMLLoader loader = new FXMLLoader (getClass().getResource("AccountSelectionScene.fxml"));
			root = loader.load();
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			AccountSelectionController accountSelectionController = loader.getController();
			accountSelectionController.setTransactionType(transactionType);
			stage.setTitle("Account Selection");
			stage.setScene(scene);
			stage.setMaxHeight(400);
			stage.setMaxWidth(600);
			stage.show();
		}catch (Exception excpt) {
			Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			stage.close();
			System.out.println("Something went wrong!");
		}
		
	}
	
	// sets the transaction type as a deposit and passes that information onto the next scene
	public void depositConfirmation(ActionEvent e) {
		try {
			transactionType = 'D';
			FXMLLoader loader = new FXMLLoader (getClass().getResource("AccountSelectionScene.fxml"));
			root = loader.load();
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			AccountSelectionController accountSelectionController = loader.getController();
			accountSelectionController.setTransactionType(transactionType);
			stage.setTitle("Account Selection");
			stage.setScene(scene);
			stage.setMaxHeight(400);
			stage.setMaxWidth(600);
			stage.show();	
		}catch (Exception excpt) {
			Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			stage.close();
			System.out.println("Something went wrong!");
		}
		
	}
	
	// will send the user to the balance transfer scene
	public void transferConfirmation(ActionEvent e) {
		try {
			FXMLLoader loader = new FXMLLoader (getClass().getResource("BalanceTransferScene.fxml"));
			root = loader.load();
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setTitle("Balance Transfer");
			stage.setScene(scene);
			stage.setHeight(380);
			stage.setWidth(600);
			stage.setMaxHeight(380);
			stage.setMaxWidth(600);
			stage.show();	
		}catch (Exception excpt) {
			Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			stage.close();
			System.out.println("Something went wrong!");
		}
	}
	
	public void setClients(ArrayList<Customer> clients)
	{
		this.clients = clients;
	}
	
}

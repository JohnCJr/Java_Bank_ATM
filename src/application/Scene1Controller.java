package application;

import java.io.IOException;
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
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Scene1Controller implements Initializable{
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	private ArrayList<Customer> clients = new ArrayList<Customer>();	// 
	private ClientList list = new ClientList();	// contains the ArrayList that will be returned to the clients ArrayList
	private int index;
	private int correctID;
	private int correctPin;
	private int failedAttempts = 0;	// the program will close after 3 failed attempts, this variable will track the number of failed attempts
	
	@FXML
	private TextField idInput = new TextField();
	@FXML
	private TextField pinInput = new TextField();
	@FXML
	private Button sumbit;
	@FXML
	private Label attemptsLabel;
	@FXML
	private Button exitButton;
	@FXML
	ImageView imageFrame;
	@FXML
	ImageView imageFrame2;
	
	// initialize the scene when called
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		clients = list.getClientList();	// assigns clients with the ArrayList found in the ClientList class
		Image image = new Image(getClass().getResourceAsStream("globe.png"));
		imageFrame.setImage(image);
		Image image2 = new Image(getClass().getResourceAsStream("greek_infinity.png"));
		imageFrame2.setImage(image2);
	}

	// method that finds the index that matches the ID input made by the user, returns 0 as an index otherwise
	public int findCustomerIndex(ArrayList<Customer> list, int idGuess) {
		for (int i = 0; i < list.size(); i++)
		{
			if (idGuess == list.get(i).getCustomerID()){return i;}
		}
		return 0;
	}
	
	// serves as an alternative to exiting the program
	public void exitConfirmation(ActionEvent e) {
		stage = (Stage)((Node)e.getSource()).getScene().getWindow();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Exit");
		alert.setHeaderText("You are about to exit.");
		alert.setContentText("Are you sure you want to exit?");
		
		if(alert.showAndWait().get() == ButtonType.OK) {stage.close();}
		
	}
	
	// will change scenes if correct id and pin combination entered
	// will also notify user of failed attempts and remaining attempts
	public void submitCredentials(ActionEvent e) throws IOException {
		try {
			int idGuess = Integer.parseInt(idInput.getText());
			int pinGuess = Integer.parseInt(pinInput.getText());
			int idLength = String.valueOf(idGuess).length();
			int pinLength = String.valueOf(pinGuess).length();
			
			index = findCustomerIndex(clients, idGuess);
			correctID = clients.get(index).getCustomerID();
			correctPin = clients.get(index).getCustomerPIN();
		
		if (idGuess == correctID && pinGuess == correctPin)
		{
			list.setIndex(index);	// sets the static index variable, granting all controllers access to the same index 
			FXMLLoader loader = new FXMLLoader (getClass().getResource("Scene2.fxml"));
			root = loader.load();
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setHeight(600);
			stage.setWidth(800);
			stage.setTitle("Account Summary");
			stage.setScene(scene);
			stage.show();	
		}
		else
		{
			if (idGuess == correctID && pinGuess != correctPin)
			{
				failedAttempts++;
				attemptsLabel.setText("PIN doesn't match user name: " + (3 - failedAttempts) + " attempt(s) left.");
				pinInput.clear();
			}
			else if (idLength > 5 || pinLength > 4)
			{
				failedAttempts++;
				attemptsLabel.setText("Must be 5-digit ID and 4-digit PIN! " + (3 - failedAttempts) + " attempt(s) left.");
				idInput.clear();
				pinInput.clear();
			}
			else {
				failedAttempts++;
				attemptsLabel.setText("Incorrect: " + (3 - failedAttempts) + " attempt(s) left.");
				idInput.clear();
				pinInput.clear();
			}
		}
		}
		catch(NumberFormatException excpt){	
			failedAttempts++;
			attemptsLabel.setText("A number must be entered on both fields. " + (3 - failedAttempts) + " attempt(s) left.");	// will display when a non-number input is entered
			idInput.clear();
			pinInput.clear();
		}
		catch(Exception excpt){
			attemptsLabel.setText("SOMETHING WENT WRONG");
		}
		
		if (failedAttempts == 3)
		{
			stage = (Stage)((Node)e.getSource()).getScene().getWindow();	// gets the current stage
			Alert failAlert = new Alert(AlertType.WARNING);
			failAlert.setTitle("Shutting down.");
			failAlert.setHeaderText("Too many failed attempts!");
			failAlert.setContentText("Program will now close.");
			failAlert.showAndWait();
			stage.close();	// will close the whole program since 3 failed attempts were made
			
		}
	}
	
	
}

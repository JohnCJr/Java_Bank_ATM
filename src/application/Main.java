package application;

/* Created by John Cabanas
 * BCS 345 Final project
 * Due date: 06/30/22
 * Description: This project is meant to resemble a banking system where customers could enter their pin number and password, 
 * which would grant them access to their account summary. Moreover, the user would be allowed to make transactions such as 
 * withdraw, deposits, and balance transfers while also updating the text file used to obtain the user's information.
 * The user will only be given 3 attempts to gain access to their account. In this case, this program will consider anything 
 * other than the correct combination a failed attempt. This application will close upon completing a transaction of any sort, update the list of clients,
 * display a receipt for the user to see, and store the most recent transaction in a text file named after said user. Prior to executing a transaction, 
 * the user will be allowed to backtrack, even being able to sign out and enter a different account if they input the correct pin and password combination.  
 */


import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Main extends Application {

	
	@Override
	public void start(Stage primaryStage) throws IOException {
		
		try {
			FXMLLoader loader = new FXMLLoader (getClass().getResource("Scene1.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			primaryStage.setScene(scene);
			primaryStage.setMaxHeight(600);	// sets the maximum height of the stage
			primaryStage.setMaxWidth(800);	// sets the maximum width of the stage
			primaryStage.setTitle("User Sign-in");
			primaryStage.show();
			
			// confirmation alert will appear to confirm exit request
			primaryStage.setOnCloseRequest(e -> {
				e.consume();
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Exit");
				alert.setHeaderText("You are about to exit.");
				alert.setContentText("Are you sure you want to exit?");
				
				if(alert.showAndWait().get() == ButtonType.OK) {primaryStage.close();}
				});
		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args){
		launch(args);
	}
}

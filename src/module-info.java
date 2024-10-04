module Final_Project_Bank_System {
	requires javafx.controls;
	requires javafx.fxml;
	requires javafx.graphics;
	requires javafx.base;
	requires java.mail;
	requires activation;
	
	opens application to javafx.graphics, javafx.fxml;
}

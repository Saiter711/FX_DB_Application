package application;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

public class ChangePasswordController {

	
	 	@FXML
	    private PasswordField passwordHaslo = new PasswordField();

	    @FXML
	    private PasswordField passwordPotwierdz = new PasswordField();

	    @FXML
	    private Button buttonZatwierdz;

	    @FXML
	    private Button buttonAnuluj;
	    
	    public void buttonConfirmOnAction(ActionEvent action) {
	    	String haslo, potwierdz;
	    	haslo = passwordHaslo.getText();
			potwierdz = passwordPotwierdz.getText();
			BufferedReader reader;
			try {
				if(haslo.equals(potwierdz) && !haslo.trim().isEmpty()) {
					
					try(FileWriter fw = new FileWriter("configtxt\\hasla.txt", true);
						    BufferedWriter bw = new BufferedWriter(fw);
						    PrintWriter out = new PrintWriter(bw)) {
							out.println(LoginPanelController.username + " " + haslo + " " + LoginPanelController.id);
							Alert alert = new Alert(Alert.AlertType.INFORMATION);
							alert.setContentText("Hasło zostało zmienione!");
							alert.showAndWait();
							moveTo(action, "UserView.fxml");
							}
					catch (IOException ioe) {
						System.out.println("Nie znaleziono pliku");
					}
				}
				else if(haslo.trim().isEmpty()) {
					Alert alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText("Hasło nie może być znakami białymi!");
					alert.show();
				}
				else { 
					throw new NumberFormatException();
				}
			}
			catch (Exception exc) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Hasło lub nazwa użytkownika nieprawidłowe. Spróbuj jeszcze raz.");
				alert.show();
			}
		}
	    
	    public void buttonAnulujOnAction(ActionEvent action) {
	    	moveTo(action, "UserView.fxml");
	    }
	    
	    private void moveTo(ActionEvent event, String document) {
	    	try {
				Parent root = FXMLLoader.load(getClass().getResource(document));
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
				stage.setTitle("WBD - Project");
				stage.setScene(scene);
				stage.show();
			}
			catch(Exception e) {
				System.out.print("Błąd");
			}
	    }  
}

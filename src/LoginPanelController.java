package application;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import oracle.security.crypto.cert.ValidationException;
import javafx.scene.Node;

public class LoginPanelController implements Initializable {

	    @FXML
	    private Label labelLogin;

	    @FXML
	    private TextField textPassword = new TextField();

	    @FXML
	    private Label labelPassword;

	    @FXML
	    private TextField textLogin = new TextField();
	    
	    public static String login, password, username;
	    public static Integer id = -1;
		private Connection conn;
	
	    public void buttonSignInOnAction(ActionEvent action) {
	    	login = textLogin.getText().trim();
			password = textPassword.getText().trim();
			
			BufferedReader reader;
			try {
				if(login.equals("###") && password.equals("###")) {
					conn = DBConnection.getConnection(login, password);
					changeScreen(action, "PracownicyView.fxml");
				}
				else {
					reader = new BufferedReader(new FileReader(
							"configtxt\\hasla.txt"));
					String line = reader.readLine();
					String[] data;
					while (line != null) {
						data = line.split(" ");
						if(login.equals(data[0]) && password.equals(data[1])) {
							username = data[0];
							login = "####";
							password = "####";
							id = Integer.parseInt((data[2]));
							System.out.print(id);
							conn = DBConnection.getConnection(login, password);
							changeScreen(action, "UserView.fxml");
							break;
						}
						line = reader.readLine();
					}
					reader.close();
					if(!login.equals("####")) {
						throw new NumberFormatException();
					}
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			catch (Exception exc) {
				Alert alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Hasło lub nazwa użytkownika nieprawidłowe. Spróbuj jeszcze raz.");
				alert.show();
			}
		}
	    
	    public void changeScreen(ActionEvent action, String document) {
	    	try {
		    	Parent root = FXMLLoader.load(getClass().getResource(document));
		    	System.out.println("KURWA");
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				Stage stage = (Stage)((Node)action.getSource()).getScene().getWindow();
				stage.setTitle("WBD - Project");
				stage.setScene(scene);
				stage.show();
			}
			catch(Exception e) {
				System.out.print("Błąd");
			}
	    }
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		
	}
	
	
}


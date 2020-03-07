package application;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddNewPracownikController implements Initializable{


    @FXML
    private TextField textImie = new TextField();

    @FXML
    private TextField textNazwisko = new TextField();

    @FXML
    private TextField textDataUro = new TextField();

    @FXML
    private TextField textMiasto = new TextField();

    @FXML
    private TextField textUlica = new TextField();

    @FXML
    private TextField textNrBudynku = new TextField();

    @FXML
    private TextField textKod = new TextField();

    @FXML
    private TextField textNrTele = new TextField();

    @FXML
    private TextField textEmail = new TextField();

    @FXML
    private TextField textNrKonta = new TextField();

    @FXML
    private TextField textDataZat = new TextField();

    @FXML
    private TextField textNarodowosc = new TextField();
    
    @FXML
    private TextField textHaslo = new TextField();

    @FXML
    private TextField textPowtorz = new TextField();
    
    @FXML
    private DatePicker UroPicker = new DatePicker();
    
    @FXML
    private DatePicker ZatrPicker = new DatePicker();
    
    private Connection conn;
    
    public void buttonDodajOnAction(ActionEvent event) {
    	String imie, nazwisko, miasto, ulica, nrbudynku, kod, nrtele, email, nrkonta, narodowosc, haslo, powtorz;
    	Integer result;
    	Date datauro, datazat;
    	try {
    		imie = textImie.getText().trim();
    		imie = imie.substring(0, 1).toUpperCase() + imie.substring(1);
    		nazwisko = textNazwisko.getText().trim();
    		nazwisko = nazwisko.substring(0, 1).toUpperCase() + nazwisko.substring(1);
    		datauro = Date.valueOf(UroPicker.getValue());
    		miasto = textMiasto.getText().trim();
    		ulica = textUlica.getText().trim();
    		nrbudynku = textNrBudynku.getText().trim();
    		kod = textKod.getText().trim();
    		nrtele = textNrTele.getText().trim();
    		email = textEmail.getText().trim();
    		nrkonta = textNrKonta.getText().trim();
    		datazat = Date.valueOf(ZatrPicker.getValue());
    		narodowosc = textNarodowosc.getText().trim();
    		haslo = textHaslo.getText().trim();
    		powtorz = textPowtorz.getText().trim();
    		
    		if(haslo.equals(powtorz)) {
	    		conn = DBConnection.getConnection(LoginPanelController.login, LoginPanelController.password);
	    		Pracownik pracownik = new Pracownik();
	    		result = pracownik.insertPracownik(conn, imie, nazwisko, datauro, miasto, ulica,
	    				nrbudynku, kod, nrtele, email, nrkonta, datazat, narodowosc);
	    		pracownik.savePracownik(conn, imie, nazwisko, nrtele, haslo);
	    		Alert alert = new Alert(Alert.AlertType.INFORMATION);
	    		alert.setContentText("Rekord został dodany!");
	    		alert.showAndWait();
	    		moveTo(event);
    		}
    		else {
    			Alert alert = new Alert(Alert.AlertType.ERROR);
        		alert.setContentText("Hasła nie są jednakowe");
        		alert.showAndWait();
    		}
    	
    	}
    	catch(NumberFormatException exc) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.setContentText("Zły format numeru budynku, oczekuję liczby");
    		alert.showAndWait();
    	}
    	catch(Exception exc) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Errors with data access");
			alert.setContentText("Details: " + exc.getMessage());
			alert.showAndWait();
    	}
    	
    }
    
    public void buttonAnulujOnAction(ActionEvent event) {
    	moveTo(event);
    }
    
    private void moveTo(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("PracownicyView.fxml"));
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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    	
    }
}

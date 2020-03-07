package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddNewObjectController implements Initializable{

	@FXML
    private Label Nazwa;

    @FXML
    private Label Typ;

    @FXML
    private Label DataZal;

    @FXML
    private Label Miasto;

    @FXML
    private Label Ulica;

    @FXML
    private Label NrBudynku;

    @FXML
    private TextField textNrBudynku = new TextField();

    @FXML
    private TextField textMiasto = new TextField();

    @FXML
    private DatePicker dataZalPicker = new DatePicker();

    @FXML
    private TextField textUlica = new TextField();

    @FXML
    private TextField textNazwa = new TextField();

    @FXML
    private ComboBox<String> comboTyp = new ComboBox<String>();

    @FXML
    private Button buttonDodaj;

    @FXML
    private Button buttonAnuluj;
    
    private ObservableList<String> TypArray = FXCollections.observableArrayList("Silownia", "Basen", "Hala", "Male boisko", "Inne");
    private Connection conn;
    
    public void buttonDodajOnAction(ActionEvent event) {
    	String nazwa, typ, miasto, ulica;
    	Integer nr, result;
    	Date data;
    	try {
    		nazwa = textNazwa.getText().trim();
    		typ = comboTyp.getValue().trim().toUpperCase();
    		miasto = textMiasto.getText().trim();
    		ulica = textUlica.getText().trim();
    		nr = Integer.parseInt(textNrBudynku.getText().trim());
    		data = Date.valueOf(dataZalPicker.getValue());
    		conn = DBConnection.getConnection(LoginPanelController.login, LoginPanelController.password);
    		result = new Obiekt().insertObiekt(conn, nazwa, typ, data, miasto, ulica, nr);
    		Alert alert = new Alert(Alert.AlertType.INFORMATION);
    		alert.setContentText("Rekord został dodany!");
    		alert.showAndWait();
    		moveTo(event);
    	}
    	catch(NumberFormatException exc) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.setContentText("Zły format danych, upewnij się, że dane zostały wprowadzone prawidłowo.");
    		alert.showAndWait();
    	}
    }
    
    public void buttonAnulujOnAction(ActionEvent event) {
    	moveTo(event);
    }
    
    private void moveTo(ActionEvent event) {
    	try {
			Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
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
    	comboTyp.setItems(TypArray);
    }
}

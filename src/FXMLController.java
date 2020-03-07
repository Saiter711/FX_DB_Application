package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class FXMLController implements Initializable {
	
	@FXML
    private TableView<Obiekt> TableObiekt = new TableView<Obiekt>();

    @FXML
    private TableColumn<Obiekt, Integer> IdObiektu = new TableColumn<Obiekt, Integer>("IdObiektu");

    @FXML
    private TableColumn<Obiekt, String> NazwaObiektu = new TableColumn<Obiekt, String>("NazwaObiektu");

    @FXML
    private TableColumn<Obiekt, String> TypObiektu = new TableColumn<Obiekt, String>("TypObiektu");

    @FXML
    private TableColumn<Obiekt, Date> DataZalObiektu = new TableColumn<Obiekt, Date>("DataZalObiektu");

    @FXML
    private TableColumn<Obiekt, String> Miasto = new TableColumn<Obiekt, String>("Miasto");;

    @FXML
    private TableColumn<Obiekt, String> Ulica = new TableColumn<Obiekt, String>("Ulica");;

    @FXML
    private TableColumn<Obiekt, Integer> NrBudynku = new TableColumn<Obiekt, Integer>("NrBudynku");;

    @FXML
    private TextField textTypObiektu = new TextField();

    @FXML
    private Label labelFiltry;

    @FXML
    private Label labelTypObiektu;

    @FXML
    private Button buttonSearch;
    
    @FXML
    private Button buttonDodaj;

    @FXML
    private Button buttonUsun;
    
    @FXML
    private Button buttonTablePracownicy;

	
	private Connection conn;
	private ObservableList<Obiekt> listaObiektow = FXCollections.observableArrayList();
	private Obiekt obiekt;
	
	static {
	    try {
	        Class.forName ("oracle.jdbc.OracleDriver");
	    } 
	    catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	}
	
	public void buttonSearchOnAction(ActionEvent action) {
		conn = DBConnection.getConnection(LoginPanelController.login, LoginPanelController.password);
		listaObiektow = new Obiekt().getRestrictedList(conn, textTypObiektu.getText().trim());
		System.out.println("BBB: "+TypObiektu.getText().trim());
		setTableViewObiekt(listaObiektow);
	}
	
	public void buttonUsunOnAction(ActionEvent action) {
		Integer rowIndex = TableObiekt.getSelectionModel().getSelectedIndex();
		Alert alert;
		if(rowIndex<1) {
			alert = new Alert(Alert.AlertType.ERROR);
			alert.setContentText("Nie został zaznaczony żaden wiersz!");
			alert.showAndWait();
		}
		Integer obiektid = TableObiekt.getSelectionModel().getSelectedItem().getObiektId();
		conn = DBConnection.getConnection(LoginPanelController.login, LoginPanelController.password);
		
		alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Potwierdzenie");
		alert.setContentText("Czy na pewno chcesz usunąć ten rekord?");
		Optional<ButtonType> res = alert.showAndWait();
		if (res.get() == ButtonType.OK) {
			Integer result = new Obiekt().removeObiekt(conn, obiektid);
			if(result > 0) {
				alert = new Alert(Alert.AlertType.INFORMATION);
				alert.setContentText("Rekord został usunięty!");
				alert.showAndWait();
			}
		}
		listaObiektow = obiekt.getAll(conn);
		setTableViewObiekt(listaObiektow);
	}
	
	public void buttonDodajOnAction(ActionEvent action) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("AddNewObject.fxml"));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = (Stage)((Node)action.getSource()).getScene().getWindow();
			stage.setTitle("WBD - Project");
			stage.setScene(scene);
			stage.show();
		}
		catch(Exception e) {
			System.out.print("Błąd!");
		}
	}
	
	public void buttonChangeOnAction(ActionEvent action) {
		moveTo(action, "PracownicyView.fxml");
	}
	
	public void buttonWylogujOnAction(ActionEvent action) {
		moveTo(action, "LoginPanel.fxml");
	}
	
	public void moveTo(ActionEvent action, String document) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource(document));
			Scene scene = new Scene(root);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			Stage stage = (Stage)((Node)action.getSource()).getScene().getWindow();
			stage.setTitle("WBD - Project");
			stage.setScene(scene);
			stage.show();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		conn = DBConnection.getConnection(LoginPanelController.login, LoginPanelController.password);
		obiekt = new Obiekt();
		listaObiektow = obiekt.getAll(conn);
		
		setTableViewObiekt(listaObiektow);
	}
	
	private void setTableViewObiekt(ObservableList<Obiekt> listaobiektow) {
		IdObiektu.setCellValueFactory(new PropertyValueFactory<>("obiektId"));
		NazwaObiektu.setCellValueFactory(new PropertyValueFactory<>("nazwaObiektu"));
		TypObiektu.setCellValueFactory(new PropertyValueFactory<>("typObiektu"));
		DataZalObiektu.setCellValueFactory(new PropertyValueFactory<>("dataZalozenia"));
		Miasto.setCellValueFactory(new PropertyValueFactory<>("miasto"));
		Ulica.setCellValueFactory(new PropertyValueFactory<>("ulica"));
		NrBudynku.setCellValueFactory(new PropertyValueFactory<>("nrBudynku"));
		
		TableObiekt.setItems(listaobiektow);
	}
}

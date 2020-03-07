package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
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

public class PracownicyViewController implements Initializable {
	
		@FXML
		private TableView<Pracownik> TablePracownik = new TableView<Pracownik>();

	    @FXML
	    private TableColumn<Pracownik, String> Imie = new TableColumn<Pracownik,String>();

	    @FXML
	    private TableColumn<Pracownik, String> Nazwisko= new TableColumn<Pracownik,String>();

	    @FXML
	    private TableColumn<Pracownik, Date> DataUro= new TableColumn<Pracownik,Date>();

	    @FXML
	    private TableColumn<Pracownik, String> Miasto= new TableColumn<Pracownik,String>();

	    @FXML
	    private TableColumn<Pracownik, String> NrTele= new TableColumn<Pracownik,String>();

	    @FXML
	    private TableColumn<Pracownik, String> Email= new TableColumn<Pracownik,String>();

	    @FXML
	    private TableColumn<Pracownik, String> NrKonta= new TableColumn<Pracownik,String>();

	    @FXML
	    private TextField textNazwisko = new TextField();

	    @FXML
	    private TextField textRokZatr = new TextField();
	    
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
	    private Button buttonTabelaObiekty;
	    
	    private Connection conn;
		private ObservableList<Pracownik> listaPracownikow = FXCollections.observableArrayList();
		private Pracownik pracownik;
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
			String datauro = textRokZatr.getText().trim();
			if(datauro.isBlank() || datauro.isEmpty() || Integer.parseInt(datauro) <0) {
				datauro = "1800";
			}
			listaPracownikow = new Pracownik().getRestrictedList(conn, textNazwisko.getText().trim(), Integer.parseInt(datauro));
			System.out.println("BBB: "+Nazwisko.getText().trim());
			setTableViewPracownik(listaPracownikow);
		}
		
		public void buttonWylogujOnAction(ActionEvent action) {
			moveTo(action, "LoginPanel.fxml");
		}
		
		public void moveTo(ActionEvent action, String document) {
			try {
				Parent root = FXMLLoader.load(getClass().getResource(document));
				System.out.println("1");
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				System.out.println("2");
				Stage stage = (Stage)((Node)action.getSource()).getScene().getWindow();
				System.out.println("3");
				stage.setTitle("WBD - Project");
				stage.setScene(scene);
				System.out.println("4");
				stage.show();
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		public void buttonChangeOnAction(ActionEvent action) {
			moveTo(action, "FXMLDocument.fxml");
		}
		
		public void buttonUsunOnAction(ActionEvent action) {
			Integer rowIndex = TablePracownik.getSelectionModel().getSelectedIndex();
			Alert alert;
			if(rowIndex<1) {
				alert = new Alert(Alert.AlertType.ERROR);
				alert.setContentText("Nie został zaznaczony żaden wiersz!");
				alert.showAndWait();
			}
			
			String imie = TablePracownik.getSelectionModel().getSelectedItem().getImie();
			String nazwisko = TablePracownik.getSelectionModel().getSelectedItem().getNazwisko();
			String nrtele = TablePracownik.getSelectionModel().getSelectedItem().getNumerTele();
			conn = DBConnection.getConnection(LoginPanelController.login, LoginPanelController.password);
			
			alert = new Alert(Alert.AlertType.CONFIRMATION);
			alert.setTitle("Potwierdzenie");
			alert.setContentText("Czy na pewno chcesz usunąć ten rekord?");
			Optional<ButtonType> res = alert.showAndWait();
			if (res.get() == ButtonType.OK) {
				Integer result = new Pracownik().removePracownik(conn, imie, nazwisko, nrtele);
				if(result > 0) {
					alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setContentText("Rekord został usunięty!");
					alert.showAndWait();
				}
				else {
					alert = new Alert(Alert.AlertType.ERROR);
					alert.setContentText("Nie udało się usunąc rekordu.");
					alert.showAndWait();
				}
			}
			listaPracownikow = pracownik.getAll(conn);
			setTableViewPracownik(listaPracownikow);
		}
		
		public void buttonDodajOnAction(ActionEvent action) {
			try {
				Parent root = FXMLLoader.load(getClass().getResource("AddNewPraownik.fxml"));
				Scene scene = new Scene(root);
				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
				Stage stage = (Stage)((Node)action.getSource()).getScene().getWindow();
				stage.setTitle("WBD - Project");
				stage.setScene(scene);
				stage.show();
			}
			catch(Exception e) {
				System.out.print("Błąd1");
			}
		}
		
		@Override
		public void initialize(URL url, ResourceBundle rb) {
			conn = DBConnection.getConnection(LoginPanelController.login, LoginPanelController.password);
			pracownik = new Pracownik();
			listaPracownikow = pracownik.getAll(conn);
			setTableViewPracownik(listaPracownikow);
		}
		
		private void setTableViewPracownik(ObservableList<Pracownik> listaPracownikow) {
			Imie.setCellValueFactory(new PropertyValueFactory<>("imie"));
			Nazwisko.setCellValueFactory(new PropertyValueFactory<>("nazwisko"));
			DataUro.setCellValueFactory(new PropertyValueFactory<>("dataUro"));
			Miasto.setCellValueFactory(new PropertyValueFactory<>("miasto"));
			NrTele.setCellValueFactory(new PropertyValueFactory<>("numerTele"));
			Email.setCellValueFactory(new PropertyValueFactory<>("email"));
			NrKonta.setCellValueFactory(new PropertyValueFactory<>("numerKonta"));
			
			TablePracownik.setItems(listaPracownikow);
		}
}

package application;
import java.math.BigDecimal;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class SalaryViewController implements Initializable {

	    @FXML
	    private TableView<Wynagrodzenie> tableWynagrodzenie = new TableView();

	    @FXML
	    private TableColumn<Wynagrodzenie, Date> columnDate = new TableColumn<Wynagrodzenie, Date>();

	    @FXML
	    private TableColumn<Wynagrodzenie, BigDecimal> columnPodstawowa = new TableColumn<Wynagrodzenie, BigDecimal>();

	    @FXML
	    private TableColumn<Wynagrodzenie, BigDecimal> columnDodatkowa = new TableColumn<Wynagrodzenie, BigDecimal>();

	    @FXML
	    private Label labelOsoba = new Label();
	    
	    private Connection conn;
	    private ObservableList<Wynagrodzenie> listaWynagrodzen = FXCollections.observableArrayList();
	    
	    public void buttonGoBackOnAction(ActionEvent action) {
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

	    @Override
		public void initialize(URL url, ResourceBundle rb) {
			conn = DBConnection.getConnection(LoginPanelController.login, LoginPanelController.password);
			Wynagrodzenie wynagrodzenie = new Wynagrodzenie();
			listaWynagrodzen = wynagrodzenie.getAll(conn, LoginPanelController.id);
			labelOsoba.setText(UserViewController.imie + " " + UserViewController.nazwisko);
			setTableViewObiekt(listaWynagrodzen);
		}
	    
	    private void setTableViewObiekt(ObservableList<Wynagrodzenie> listaWynagrodzen) {
			columnDate.setCellValueFactory(new PropertyValueFactory<>("data"));
			columnPodstawowa.setCellValueFactory(new PropertyValueFactory<>("podstawowa"));
			columnDodatkowa.setCellValueFactory(new PropertyValueFactory<>("dodatkowa"));
			tableWynagrodzenie.setItems(listaWynagrodzen);
		}   
}

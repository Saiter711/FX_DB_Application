package application;
import java.net.URL;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
public class UserViewController implements Initializable{

	@FXML
    private Label labelImie = new Label();

    @FXML
    private Label labelNazwisko = new Label();

    @FXML
    private Label labelDataUro = new Label();

    @FXML
    private Label labelMiasto = new Label();

    @FXML
    private Label labelUlica = new Label();

    @FXML
    private Label labelNrBud = new Label();

    @FXML
    private Label labelKod = new Label();

    @FXML
    private Label labelNrTele = new Label();

    @FXML
    private Label labelEmail = new Label();

    @FXML
    private Label labelNrKonta = new Label();

    @FXML
    private Label labelDataZatr = new Label();

    @FXML
    private Button buttonHistoria;

    @FXML
    private Button buttonHaslo;

    @FXML
    private Button buttonWyloguj;
	
    private Connection conn;
	private Pracownik infopracownik = new Pracownik();
	public static String imie, nazwisko;
	
	public void buttonHistoriaOnAction(ActionEvent action) {
		moveTo(action, "SalaryView.fxml");
	}
	
	public void buttonWylogujOnAction(ActionEvent action) {
		moveTo(action, "LoginPanel.fxml");
	}
	
	public void buttonHasloOnAction(ActionEvent action) {
		moveTo(action, "ChangePassword.fxml");
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
		Pracownik pracownik = new Pracownik();
		infopracownik = pracownik.getOne(conn, LoginPanelController.id);
		
		setInfoPracownik(infopracownik);
	}
    
    public void setInfoPracownik(Pracownik pracownik) {
    	
    	Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
            	DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            	String date;
            	imie = pracownik.getImie();
            	labelImie.setText(pracownik.getImie());
            	nazwisko = pracownik.getNazwisko();
            	labelNazwisko.setText(pracownik.getNazwisko());
            	date = df.format(pracownik.getDataUro());
            	labelDataUro.setText(date);
            	labelMiasto.setText(pracownik.getMiasto());
            	System.out.println(pracownik.getMiasto());
            	labelUlica.setText(pracownik.getUlica());
            	labelNrBud.setText(pracownik.getNrBudynku());
            	labelKod.setText(pracownik.getKod());
            	labelNrTele.setText(pracownik.getNumerTele());
            	labelEmail.setText(pracownik.getEmail());
            	labelNrKonta.setText(pracownik.getNumerKonta());
            	DateFormat dx = new SimpleDateFormat("yyyy-MM-dd");
            	date = dx.format(pracownik.getDataZatr());
            	
            	labelDataZatr.setText(date);
            }     
        });
        t.start();
    }
}

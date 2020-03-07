package application;

import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class Obiekt {
	private Integer ObiektId, NrBudynku;
	private String NazwaObiektu, TypObiektu, Miasto, Ulica;
	private Date DataZalozenia;
	
	public Integer getObiektId() {
		return ObiektId;
	}
	
	public Integer getNrBudynku() {
		return NrBudynku;
	}
	
	public void setNrBudynku(Integer nrBudynku) {
		NrBudynku = nrBudynku;
	}
	
	public String getMiasto() {
		return Miasto;
	}
	
	public void setMiasto(String miasto) {
		Miasto = miasto;
	}
	
	public String getUlica() {
		return Ulica;
	}
	
	public void setUlica(String ulica) {
		Ulica = ulica;
	}
	
	public void setObiektId(Integer obiektId) {
		ObiektId = obiektId;
	}
	
	public String getNazwaObiektu() {
		return NazwaObiektu;
	}
	
	public void setNazwaObiektu(String nazwaObiektu) {
		NazwaObiektu = nazwaObiektu;
	}
	
	public String getTypObiektu() {
		return TypObiektu;
	}
	
	public void setTypObiektu(String typObiektu) {
		TypObiektu = typObiektu;
	}
	
	public Date getDataZalozenia() {
		return DataZalozenia;
	}
	
	public void setDataZalozenia(Date dataZalozenia) {
		DataZalozenia = dataZalozenia;
	}
	
	public int insertObiekt(Connection conn, String nazwa, String typ, Date data, String miasto, String ulica, Integer nr) {
		String sql = "INSERT into Obiekt_treningowy(Nazwa, Typ, Data_zalozenia, Miasto, Ulica, Numer_budynku, Id_klubu) "
				+ "values (?,?,?,?,?,?,1)";
		PreparedStatement stmt;
		Integer res = 0;
		
		try {
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, nazwa);
			stmt.setString(2, typ);
			stmt.setDate(3, data);
			stmt.setString(4, miasto);
			stmt.setString(5, ulica);
			stmt.setInt(6, nr);
			res = stmt.executeUpdate();
		}
		catch(SQLException exc) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Errots with data access");
			alert.setContentText("Details: " + exc.getMessage());
			alert.showAndWait();
		}
		catch(Exception e) {
			System.out.print("Nie dodalem do bazy");
		}
		return res;
	}
	
	public int removeObiekt(Connection conn, Integer obiektid) {
		String sql = "DELETE from Obiekt_treningowy where Id_obiektu =?";
		PreparedStatement stmt;
		Integer res = 0;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, obiektid);
			res = stmt.executeUpdate();
		}
		catch(SQLException exc) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Errots with data access");
			alert.setContentText("Details: " + exc.getMessage());
			alert.showAndWait();
		}
		return res;
	}
	
	public ObservableList<Obiekt> getAll(Connection conn) {
		ObservableList<Obiekt> listaObiektow = FXCollections.observableArrayList();
		String sql = "SELECT Id_obiektu, Nazwa, Typ, Data_zalozenia, Miasto, Ulica, Numer_budynku "
				+ "from Obiekt_treningowy order by Id_obiektu";
		Statement stmt;
		ResultSet rs;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Obiekt obiekt = new Obiekt();
				obiekt.ObiektId = rs.getInt(1);
				obiekt.NazwaObiektu = rs.getString(2);
				obiekt.TypObiektu = rs.getString(3);
				obiekt.DataZalozenia = rs.getDate(4);
				obiekt.Miasto = rs.getString(5);
				obiekt.Ulica = rs.getString(6);
				obiekt.NrBudynku = rs.getInt(7);
				System.out.println("PublisherId: " + obiekt.ObiektId.toString() + " PublisherName: "
				+ obiekt.NazwaObiektu + "Data urodzenia: " + obiekt.DataZalozenia.toString());
				listaObiektow.add(obiekt);
			}
		} 
		catch (SQLException exc) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Errots with data access");
			alert.setContentText("Details: " + exc.getMessage());
			alert.showAndWait();
		}
		return listaObiektow;
	}
	
	public ObservableList<Obiekt> getRestrictedList(Connection conn, String typObiektu) {
		ObservableList<Obiekt> listaObiektow = FXCollections.observableArrayList();
		String sql = "SELECT Id_obiektu, Nazwa, Typ, Data_zalozenia, Miasto, Ulica, Numer_budynku "
				+ "from Obiekt_treningowy where upper(Typ) like ? order by Id_obiektu";
		PreparedStatement stmt;
		ResultSet rs;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, typObiektu.toUpperCase()+"%");
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Obiekt obiekt = new Obiekt();
				obiekt.ObiektId = rs.getInt(1);
				obiekt.NazwaObiektu = rs.getString(2);
				obiekt.TypObiektu = rs.getString(3);
				obiekt.DataZalozenia = rs.getDate(4);
				obiekt.Miasto = rs.getString(5);
				obiekt.Ulica = rs.getString(6);
				obiekt.NrBudynku = rs.getInt(7);
				System.out.println("PublisherId: " + obiekt.ObiektId.toString() + " PublisherName: "
						+ obiekt.NazwaObiektu + "Data urodzenia: " + obiekt.DataZalozenia.toString());
				listaObiektow.add(obiekt);		
			}
		}
		catch(SQLException exc) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Errots with data access");
			alert.setContentText("Details: " + exc.getMessage());
			alert.showAndWait();
		}
		return listaObiektow;
	}
}

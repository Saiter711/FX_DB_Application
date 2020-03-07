package application;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

public class Pracownik {
	private String Imie, Nazwisko, Miasto, Ulica, NrBudynku, Kod, NumerTele, Email, NumerKonta;
	private Date dataUro, dataZatr;
	
	public String getUlica() {
		return Ulica;
	}
	
	public void setUlica(String ulica) {
		Ulica = ulica;
	}
	
	public String getNrBudynku() {
		return NrBudynku;
	}
	
	public void setNrBudynku(String nrBudynku) {
		NrBudynku = nrBudynku;
	}
	
	public String getKod() {
		return Kod;
	}
	
	public void setKod(String kod) {
		Kod = kod;
	}
	
	public Date getDataZatr() {
		return dataZatr;
	}
	
	public void setDataZatr(Date dataZatr) {
		this.dataZatr = dataZatr;
	}
	
	public String getImie() {
		return Imie;
	}
	
	public void setImie(String imie) {
		Imie = imie;
	}
	
	public String getNazwisko() {
		return Nazwisko;
	}
	
	public void setNazwisko(String nazwisko) {
		Nazwisko = nazwisko;
	}
	
	public String getMiasto() {
		return Miasto;
	}
	
	public void setMiasto(String miasto) {
		Miasto = miasto;
	}
	
	public String getNumerTele() {
		return NumerTele;
	}
	
	public void setNumerTele(String numerTele) {
		NumerTele = numerTele;
	}
	
	public String getEmail() {
		return Email;
	}
	
	public void setEmail(String email) {
		Email = email;
	}
	
	public String getNumerKonta() {
		return NumerKonta;
	}
	
	public void setNumerKonta(String numerKonta) {
		NumerKonta = numerKonta;
	}
	
	public Date getDataUro() {
		return dataUro;
	}
	
	public void setDataUro(Date dataUro) {
		this.dataUro = dataUro;
	}
	
	public int insertPracownik(Connection conn, String imie, String nazwisko, Date datauro, 
			String miasto, String ulica, String nr,String kod, String nrtele, String email,
			String nrkonta, Date datazat, String narodowosc) {
		String sql = "INSERT into Pracownicy(Imie, Nazwisko, Data_urodzenia, Miasto, Ulica, Numer_budynku, "
				+ "Numer_mieszkania, Kod_pocztowy, Numer_telefonu,"
				+ "Email, Numer_konta, Data_zatrudnienia, Narodowosc, Id_klubu) "
				+ "values (?,?,?,?,?,?,NULL,?,?,?,?,?,?,1)";
		PreparedStatement stmt;
		Integer res = 0;
		
		try {
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, imie);
			stmt.setString(2, nazwisko);
			stmt.setDate(3, datauro);
			stmt.setString(4, miasto);
			stmt.setString(5, ulica);
			stmt.setString(6, nr);
			stmt.setString(7, kod);
			stmt.setString(8, nrtele);
			stmt.setString(9, email);
			stmt.setString(10, nrkonta);
			stmt.setDate(11, datazat);
			stmt.setString(12, narodowosc);
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
	
	public int removePracownik(Connection conn, String imie, String nazwisko, String nrtele) {
		String sql = "DELETE from Pracownicy where Imie =? and Nazwisko =? and Numer_telefonu=?";
		PreparedStatement stmt;
		Integer res = 0;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, imie);
			stmt.setString(2, nazwisko);
			stmt.setString(3, nrtele);
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
	
	public void savePracownik(Connection conn, String imie, String nazwisko, String nrtele, String haslo) {
		String sql = "SELECT Id_pracownika from Pracownicy WHERE Imie =? and Nazwisko =? and Numer_telefonu=?";
		PreparedStatement stmt;
		ResultSet rs;
		Integer res = 0;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, imie);
			stmt.setString(2, nazwisko);
			stmt.setString(3, nrtele);
			rs = stmt.executeQuery();
			while(rs.next()) {
				res = rs.getInt(1);
			}
		}
		catch (SQLException exc) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Errots with data access");
			alert.setContentText("Details: " + exc.getMessage());
			alert.showAndWait();
	 	}
		try(FileWriter fw = new FileWriter("configtxt\\hasla.txt", true);
			    BufferedWriter bw = new BufferedWriter(fw);
			    PrintWriter out = new PrintWriter(bw)) {
				out.println(nazwisko+ imie   + " " + haslo + " " + res);
		}
		catch (IOException ioe) {
			System.out.println("Nie znaleziono pliku");
		}
	}
	
	public ObservableList<Pracownik> getAll(Connection conn) {
		ObservableList<Pracownik> listaPracownikow = FXCollections.observableArrayList();
		String sql = "SELECT Imie, Nazwisko, Data_urodzenia, Miasto, Numer_telefonu, Email, Numer_konta from Pracownicy order by Id_pracownika";
		Statement stmt;
		ResultSet rs;
		try {
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			while (rs.next()) {
				Pracownik pracownik = new Pracownik();
				pracownik.Imie = rs.getString(1);
				pracownik.Nazwisko = rs.getString(2);
				pracownik.dataUro = rs.getDate(3);
				pracownik.Miasto = rs.getString(4);;
				pracownik.NumerTele = rs.getString(5);
				pracownik.Email = rs.getString(6);
				pracownik.NumerKonta = rs.getString(7);
				System.out.println("PublisherId: " + pracownik.Imie + " PublisherName: "
				+ pracownik.Nazwisko + "Data urodzenia: " + pracownik.dataUro.toString());
				listaPracownikow.add(pracownik);
			}
		} 
		catch (SQLException exc) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Errots with data access");
			alert.setContentText("Details: " + exc.getMessage());
			alert.showAndWait();
		}
		return listaPracownikow;
	}
	
	public Pracownik getOne(Connection conn, Integer idpracownika) {
		String sql = "SELECT Imie, Nazwisko, Data_urodzenia, Miasto, Ulica, Numer_budynku, "
				+ "Kod_pocztowy, Numer_telefonu, Email, Numer_konta, Data_zatrudnienia "
				+ "from Pracownicy WHERE Id_pracownika = ?";
		PreparedStatement prstmt;
		ResultSet rs;
		Pracownik pracownik = new Pracownik();
		try {
			prstmt = conn.prepareStatement(sql);
			prstmt.setInt(1, idpracownika);
			rs = prstmt.executeQuery();
			System.out.println(rs);
			while (rs.next()) {
				pracownik.Imie = rs.getString(1);
				pracownik.Nazwisko = rs.getString(2);
				pracownik.dataUro = rs.getDate(3);
				pracownik.Miasto = rs.getString(4);
				pracownik.Ulica = rs.getString(5);
				pracownik.NrBudynku = rs.getString(6);
				pracownik.Kod = rs.getString(7);
				pracownik.NumerTele = rs.getString(8);
				pracownik.Email = rs.getString(9);
				pracownik.NumerKonta = rs.getString(10);
				pracownik.dataZatr = rs.getDate(11);
				System.out.print("TUTAJ2");
				System.out.println("PublisherId: " + pracownik.Imie + " PublisherName: "
				+ pracownik.Nazwisko + "Data urodzenia: " + pracownik.dataUro.toString());
			}
		} 
		catch (SQLException exc) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Errots with data access");
			alert.setContentText("Details: " + exc.getMessage());
			alert.showAndWait();
		}
		return pracownik;
	} 
	
	public ObservableList<Pracownik> getRestrictedList(Connection conn, String Nazwisko1, int datauro){
		ObservableList<Pracownik> listaPracownikow = FXCollections.observableArrayList();
		String sql = "SELECT Imie, Nazwisko, Data_urodzenia, Miasto, Numer_telefonu, Email, Numer_konta "
				+ "from Pracownicy where (Nazwisko like ? AND EXTRACT(YEAR FROM data_urodzenia) >= ?) order by Id_pracownika";
		PreparedStatement stmt;
		ResultSet rs;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, Nazwisko1+"%");
			stmt.setInt(2, datauro);
			rs = stmt.executeQuery();
			
			while(rs.next()) {
				Pracownik pracownik = new Pracownik();
				pracownik.Imie = rs.getString(1);
				pracownik.Nazwisko = rs.getString(2);
				pracownik.dataUro = rs.getDate(3);
				pracownik.Miasto = rs.getString(4);;
				pracownik.NumerTele = rs.getString(5);
				pracownik.Email = rs.getString(6);
				pracownik.NumerKonta = rs.getString(7);
				System.out.println("PublisherId: " + pracownik.Imie + " PublisherName: "
				+ pracownik.Nazwisko + "Data urodzenia: " + pracownik.dataUro.toString());
				listaPracownikow.add(pracownik);	
			}
		}
		catch(SQLException exc) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Errots with data access");
			alert.setContentText("Details: " + exc.getMessage());
			alert.showAndWait();
		}
		return listaPracownikow;
	}
}

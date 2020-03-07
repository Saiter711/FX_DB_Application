package application;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import oracle.sql.NUMBER;

public class Wynagrodzenie {
	Date data;
	BigDecimal podstawowa, dodatkowa;
	
	public BigDecimal getPodstawowa() {
		return podstawowa;
	}
	
	public void setPodstawowa(BigDecimal podstawowa) {
		this.podstawowa = podstawowa;
	}
	
	public BigDecimal getDodatkowa() {
		return dodatkowa;
	}
	
	public void setDodatkowa(BigDecimal dodatkowa) {
		this.dodatkowa = dodatkowa;
	}
	
	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	public ObservableList<Wynagrodzenie> getAll(Connection conn, Integer idpracownika) {
		ObservableList<Wynagrodzenie> listaWynagrodzen = FXCollections.observableArrayList();
		String sql = "SELECT Data, Kwota_podstawowa, Kwota_dodatkowa from Wynagrodzenia WHERE Id_pracownika = ?";
		PreparedStatement stmt;
		ResultSet rs;
		try {
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, idpracownika);
			rs = stmt.executeQuery();
			while (rs.next()) {
				Wynagrodzenie wynagrodzenie = new Wynagrodzenie();
				wynagrodzenie.data = rs.getDate(1);
				wynagrodzenie.podstawowa = rs.getBigDecimal(2);
				wynagrodzenie.dodatkowa = rs.getBigDecimal(3);
				listaWynagrodzen.add(wynagrodzenie);
			}
		}
		catch (SQLException exc) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Errots with data access");
			alert.setContentText("Details: " + exc.getMessage());
			alert.showAndWait();
		}
		return listaWynagrodzen;
	}
}

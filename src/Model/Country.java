package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Main.JDBC.connection;

public class Country {

    private int countryId;
    private String country;

    public Country(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    public int getCountryId() { return countryId; }

    public void setCountryId(int countryId) { this.countryId = countryId; }

    public String getCountry() { return country; }

    public void setCountry(String country) { this.country = country; }


    public static ObservableList<Country> getCountries() {
        ObservableList<Country> countries = FXCollections.observableArrayList();
        try {
            PreparedStatement pStmt = connection.prepareStatement("SELECT * From countries");
            ResultSet rs = pStmt.executeQuery();
            while (rs.next()) {
                Country newCountry = new Country(
                        rs.getInt("Country_ID"),
                        rs.getString("Country"));
                countries.add(newCountry);
            }
            return countries;
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }
}

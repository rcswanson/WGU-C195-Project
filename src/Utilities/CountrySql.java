package Utilities;

import Model.Country;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Main.JDBC.connection;

public class CountrySql {

    // EXECUTES STATEMENT TO RETRIEVE ALL DATA FROM SCHEMA
    public static ObservableList<Country> getCountries() {
        ObservableList<Country> countries = FXCollections.observableArrayList();
        try {
            PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM countries");
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

    // EXECUTES STATEMENT TO RETRIEVE COUNTRY BY ID
    public static Country getCountryId(String country) throws SQLException {
        PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM countries WHERE Country=?");
        pStmt.setString(1, country);
        try {
            pStmt.execute();
            ResultSet rs = pStmt.getResultSet();
            while (rs.next()) {
                Country newCountry = new Country(
                        rs.getInt("Country_ID"),
                        rs.getString("Country"));
                return newCountry;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }
}
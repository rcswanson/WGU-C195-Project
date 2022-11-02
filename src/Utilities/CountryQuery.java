package Utilities;

import Model.Country;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Main.JDBC.connection;

public class CountryQuery {

    public static ObservableList<Country> getCountries() throws SQLException {
        ObservableList<Country> countries = FXCollections.observableArrayList();

        PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM countries");
        pStmt.execute();
        ResultSet rs = pStmt.getResultSet();

        while (rs.next()) {
            Country newCountry = getResultSet(rs);
            countries.add(newCountry);
        }
        return countries;
    }

    public static Country getCountryWithId(int Id) throws SQLException {

        Country country = new Country();
        String query = "SELECT * FROM countries WHERE Country_ID=?";
        PreparedStatement pStmt = connection.prepareStatement(query);

        pStmt.setInt(1, Id);
        pStmt.execute();
        ResultSet rs = pStmt.getResultSet();

        country = getResultSet(rs);

        return country;
    }

    private static Country getResultSet(ResultSet rs) throws SQLException{
        Country country = new Country();

        country.setCountryId(rs.getInt("Country_ID"));
        country.setCountry(rs.getString("Country"));

        return country;
    }
}
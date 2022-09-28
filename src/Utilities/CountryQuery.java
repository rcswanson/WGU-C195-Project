package Utilities;

import Main.JDBC;
import Model.Country;
import Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Main.JDBC.getConnection;

public class CountryQuery {

    public static ObservableList<Country> getCountries() throws SQLException {
        ObservableList<Country> countries = FXCollections.observableArrayList();

        String queryStatement = "SELECT * FROM countries";

        DBQuery.setPreparedStatement(getConnection(), queryStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {

                Country newCountry = new Country(
                        resultSet.getInt("Country_ID"),
                        resultSet.getString("Country")
                );

                countries.add(newCountry);
            }
            return countries;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public static Country getCountryId(String country) throws SQLException {

        String queryStatement = "SELECT * FROM countries WHERE Country=?";

        DBQuery.setPreparedStatement(getConnection(), queryStatement);
        PreparedStatement preparedStatement = DBQuery.getPreparedStatement();

        preparedStatement.setString(1, country);

        try {
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getResultSet();

            while (resultSet.next()) {
                Country newCountry = new Country(
                        resultSet.getInt("Country_ID"),
                        resultSet.getString("Country")
                );
                return newCountry;
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return null;
    }

}

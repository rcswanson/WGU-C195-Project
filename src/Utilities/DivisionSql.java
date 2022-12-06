package Utilities;

import Main.JDBC;
import Model.Country;
import Model.Division;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Main.JDBC.connection;


public class DivisionSql {

    // EXECUTES SQL STATEMENT TO RETRIEVE ALL DATA FROM SCHEMA
    public static ObservableList<Division> getDivisions() throws SQLException {
        ObservableList<Division> divisions = FXCollections.observableArrayList();
        try {
            PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM first_level_divisions");
            pStmt.execute();
            ResultSet rs = pStmt.getResultSet();
            while (rs.next()) {
                Division newDivision = new Division(
                        rs.getInt("Division_ID"),
                        rs.getString("Division"),
                        rs.getInt("Country_ID"));
                divisions.add(newDivision);
            }
            return divisions;
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }

    // EXECUTES SQL STATEMENT TO RETRIEVE DATA FROM SCHEMA TO MATCH ID
    public static Division getDivisionId(String division) throws SQLException {
        PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM first_level_divisions WHERE Division=?");
        pStmt.setString(1, division);
        try {
            pStmt.execute();
            ResultSet rs = pStmt.getResultSet();

            while (rs.next()) {
                Division newDivision = new Division(
                        rs.getInt("Division_ID"),
                        rs.getString("Division"),
                        rs.getInt("Country_ID"));
                return newDivision;
            }
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }


    // EXECUTES SQL STATEMENT RETRIEVING DIVISION THAT MATCHES SELECTED COUNTRY
    public static ObservableList<Division> getDivisionsByCountry(String country) throws SQLException {
        Country newCountry = CountrySql.getCountryId(country);
        ObservableList<Division> divisions = FXCollections.observableArrayList();
        PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM first_level_divisions WHERE Country_ID=?");
        pStmt.setInt(1, newCountry.getCountryId());
        try {
            pStmt.execute();
            ResultSet rs = pStmt.getResultSet();
            while (rs.next()) {
                Division newDivision = new Division(
                        rs.getInt("Division_ID"),
                        rs.getString("Division"),
                        rs.getInt("Country_ID"));
                divisions.add(newDivision);
            }
            return divisions;
        } catch (SQLException se) {
            se.printStackTrace();
        }
        return null;
    }
}


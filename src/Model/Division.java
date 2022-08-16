package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static Main.JDBC.connection;

public class Division {

    private int divisionId;
    private String division;
    private int countryId;

    public Division(int divisionId, String division, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }

    public ObservableList<Division> getDivisions() {
        ObservableList<Division> divisions = FXCollections.observableArrayList();
        try {
            PreparedStatement pStmt = connection.prepareStatement("SELECT * FROM first_level_divisions");
            ResultSet rs = pStmt.executeQuery();
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

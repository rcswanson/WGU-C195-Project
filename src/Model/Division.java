package Model;

public class Division {

    private int divisionId;
    private String division;
    private int countryId;

    // CONSTRUCTOR
    public Division(int divisionId, String division, int countryId) {
        this.divisionId = divisionId;
        this.division = division;
        this.countryId = countryId;
    }


    // GETTERS AND SETTERS
    public int getDivisionId() {
        return divisionId;
    }

    public String getDivision() {
        return division;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public void setDivision(String division) {
        this.division = division;
    }
}

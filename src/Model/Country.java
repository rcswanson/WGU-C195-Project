package Model;

public class Country {

    private int countryId;
    private String country;

    public Country() {

    }

    // CONSTRUCTOR
    public Country(int countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    //GETTERS AND SETTERS
    public int getCountryId() { return countryId; }

    public void setCountryId(int countryId) { this.countryId = countryId; }

    public String getCountry() { return country; }

    public void setCountry(String country) { this.country = country; }

}

package BankingSystem;

import javax.persistence.*;

@Embeddable
public class Location {

    private String city;
    private String state;
    private String street;
    private int pinCode;

    public Location()
    {

    }

    public Location(String city, String state, String street, int pinCode) {
        this.city = city;
        this.state = state;
        this.street = street;
        this.pinCode = pinCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getPinCode() {
        return pinCode;
    }

    public void setPinCode(int pinCode) {
        this.pinCode = pinCode;
    }
}

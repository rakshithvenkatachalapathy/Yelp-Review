package practice.data;

import com.google.gson.JsonArray;

public class BusinessDetails {

    private String businessId, name, city, state;
    private double latitude, longitude;
    private String neighborhood;
    private JsonArray neighborhoods;

    public BusinessDetails(String businessId, String name, String city, String state, double latitude, double longitude) {
        this.businessId = businessId;
        this.name = name;
        this.city = city;
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public BusinessDetails(String businessId, String name, String city, String state, double latitude, double longitude, String neighborhood) {
        this.businessId = businessId;
        this.name = name;
        this.city = city;
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
        this.neighborhood = neighborhood;
    }

    public BusinessDetails(String businessId, String name, String city, String state, double latitude, double longitude, JsonArray neighborhoods) {
        this.businessId = businessId;
        this.name = name;
        this.city = city;
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
        this.neighborhoods = neighborhoods;
    }

    public String getBusinessId() {
        return businessId;
    }

    public String getName() {
        return name;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public JsonArray getNeighborhoods() {
        return neighborhoods;
    }

}

package blackriders.dummyproject;

/**
 * Created by Sanwal Singh on 4/4/17.
 */

public class City {

    public String cityID,cityName,stateID,countryID,latitude,longitude;

    public City(String cityID, String cityName, String stateID, String countryID, String latitude, String longitude) {
        this.cityID = cityID;
        this.cityName = cityName;
        this.stateID = stateID;
        this.countryID = countryID;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public City() {
    }

    public String getCityID() {
        return cityID;
    }

    public void setCityID(String cityID) {
        this.cityID = cityID;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStateID() {
        return stateID;
    }

    public void setStateID(String stateID) {
        this.stateID = stateID;
    }

    public String getCountryID() {
        return countryID;
    }

    public void setCountryID(String countryID) {
        this.countryID = countryID;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}

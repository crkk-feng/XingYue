package com.example.xingyue.data;

public class LocationDataBean {
    private Double Latitude;
    private Double Longitude;
    private String LocationName;

    public LocationDataBean(Double latitude, Double longitude, String locationName) {
        Latitude = latitude;
        Longitude = longitude;
        LocationName = locationName;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public String getLocationName() {
        return LocationName;
    }

    public void setLocationName(String locationName) {
        LocationName = locationName;
    }
}

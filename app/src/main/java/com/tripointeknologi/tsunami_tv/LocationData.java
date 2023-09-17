package com.tripointeknologi.tsunami_tv;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class LocationData {
    private LatLng latLng;
    private String name;
    private String LocationName;
    private Date date;

    public LocationData(LatLng latLng, String name, String locationName, Date date){
        this.latLng = latLng;
        this.name = name;
        this.LocationName = locationName;
        this.date = date;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getName() {
        return name;
    }

    public Date getDate() {
        return date;
    }

    public String getLocationName() {
        return LocationName;
    }
}

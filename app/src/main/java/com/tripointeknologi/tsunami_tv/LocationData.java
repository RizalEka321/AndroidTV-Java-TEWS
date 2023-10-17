package com.tripointeknologi.tsunami_tv;

import com.google.android.gms.maps.model.LatLng;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class LocationData {
    private LatLng latLng;
    private String name;
    private String alamat;
    private Date date;

    public LocationData(LatLng latLng, String name, String alamat, Date date) {
        this.latLng = latLng;
        this.name = name;
        this.alamat = alamat;
        this.date = date;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy HH:mm:ss", new Locale("id", "ID"));
        return dateFormat.format(date);
    }

    public String getAlamat() {
        return alamat;
    }

    public String getAll() {
        return "SignalData{" +
                "latLng=" + latLng +
                ", name='" + name + '\'' +
                ", alamat=" + alamat + '\'' +
                ", date=" + date +
                '}';
    }
}

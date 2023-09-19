package com.tripointeknologi.tsunami_tv;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class SignalData {
    private LatLng latLng;
    private String name;
    private String status;
    private String alamat;
    private Date date;

    public SignalData(LatLng latLng, String name, String status, String alamat, Date date){
        this.latLng = latLng;
        this.name = name;
        this.status = status;
        this.date = date;
        this.alamat = alamat;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getName(){
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getAlamat(){ return alamat;}

    public Date getDate() {
        return date;
    }

     public String getAll(){
         return "SignalData{" +
                 "latLng=" + latLng +
                 ", name='" + name + '\'' +
                 ", status='" + status + '\'' +
                 ", alamat="+ alamat+ '\'' +
                 ", date=" + date +
                 '}';
     }
}

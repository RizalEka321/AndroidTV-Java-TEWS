package com.tripointeknologi.tsunami_tv;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class SignalData {
    private LatLng latLng;
    private String name;
    private String keterangan;
    private Date date;

    public SignalData(LatLng latLng, String name, String keterangan, Date date){
        this.latLng = latLng;
        this.name = name;
        this.keterangan = keterangan;
        this.date = date;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getNames(){
        return name;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public Date getDate() {
        return date;
    }

     public String getAll(){
         return "SignalData{" +
                 "latLng=" + latLng +
                 ", name='" + name + '\'' +
                 ", keterangan='" + keterangan + '\'' +
                 ", date=" + date +
                 '}';
     }
}

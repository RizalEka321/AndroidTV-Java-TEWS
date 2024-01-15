package com.tripointeknologi.tsunami_tv.Models;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class M_rpu {
    private String nama;
    private String alamat;
    private String latitude;
    private String longitude;
    private String rpu_id;
    private String status;
    private String tanggal;

    public M_rpu() {

    }

    public M_rpu(String rpu_id, String status, String tanggal, String latitude, String longitude, String nama, String alamat) {
        this.rpu_id = rpu_id;
        this.nama = nama;
        this.alamat = alamat;
        this.status = status;
        this.tanggal = tanggal;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getNama() {
        return nama;
    }

    public String getRpu_id() {
        return rpu_id;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getStatus() {
        return status;
    }

    public String getTanggal() {
        return tanggal;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    @Override
    public String toString() {
        return "LocationData{" +
                "rpu_id='" + rpu_id + '\'' +
                ", status='" + status + '\'' +
                ", tanggal='" + tanggal + '\'' +
                '}';
    }
}
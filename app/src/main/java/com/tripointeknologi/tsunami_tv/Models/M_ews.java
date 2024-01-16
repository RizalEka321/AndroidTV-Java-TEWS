package com.tripointeknologi.tsunami_tv.Models;

import com.google.android.gms.maps.model.LatLng;

public class M_ews {
    private String device_id;
    private String alamat;
    private String latitude;
    private String longitude;
    private String nama;
    private String status;
    private String tanggal;

    public M_ews() {

    }

    public M_ews(String device_id, String status, String tanggal, String alamat, String nama, String latitude, String longitude) {
        this.device_id = device_id;
        this.status = status;
        this.tanggal = tanggal;
        this.alamat = alamat;
        this.nama = nama;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getDevice_id() {
        return device_id;
    }

    public String getStatus() {
        return status;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getNama() {

        return nama;
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
                "device_id='" + device_id + '\'' +
                ", status='" + status + '\'' +
                ", tanggal='" + tanggal + '\'' +
                '}';
    }
}

package com.tripointeknologi.tsunami_tv;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class SignalData {
    private LatLng latLng;
    private String name;
    private String status;
    private String alamat;
    private String voltase;
    private String temperatur;
    private String tanggal_aktifasi;
    private String keterangan;
    private Date date;

    public SignalData(LatLng latLng, String name, String status, String alamat,String voltase, String temperatur, String tanggal_aktifasi, String keterangan, Date date) {
        this.latLng = latLng;
        this.name = name;
        this.status = status;
        this.date = date;
        this.alamat = alamat;
        this.voltase = voltase;
        this.temperatur = temperatur;
        this.tanggal_aktifasi = tanggal_aktifasi;
        this.keterangan = keterangan;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getAlamat() {
        return alamat;
    }

    public String getVoltase() {
        return voltase;
    }

    public String getTemperatur() {
        return temperatur;
    }

    public String getTanggal_aktifasi() {
        return tanggal_aktifasi;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public Date getDate() {
        return date;
    }

    public String getAll() {
        return "SignalData{" +
                "latLng=" + latLng +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                ", alamat=" + alamat + '\'' +
                ", date=" + date +
                '}';
    }
}
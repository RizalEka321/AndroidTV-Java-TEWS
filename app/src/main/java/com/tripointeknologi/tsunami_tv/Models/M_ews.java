package com.tripointeknologi.tsunami_tv.Models;

import com.google.android.gms.maps.model.LatLng;

public class M_ews {
    private String device_id;
    private String mode;
    private String status;
    private String tanggal;

    public M_ews() {

    }

    public M_ews(String device_id, String mode, String status, String tanggal) {
        this.device_id = device_id;
        this.mode = mode;
        this.status = status;
        this.tanggal = tanggal;
    }

    public String getDevice_id() {
        return device_id;
    }

    public String getMode() {
        return mode;
    }

    public String getStatus() {
        return status;
    }

    public String getTanggal() {
        return tanggal;
    }

    public LatLng getLatLng() {
        // Implement the logic to convert device_id or any other property to LatLng
        // For example, you may use a predefined mapping or a lookup mechanism.
        // The below example assumes a simple conversion for demonstration purposes.

        double latitude = 0.0;
        double longitude = 0.0;

        // Example mapping for demonstration purposes
        switch (device_id) {
            case "BWI001":
                latitude = -8.61306544945518;
                longitude = 114.06503372193593;
                break;
            // Add more cases as needed

            default:
                // Handle default case or throw an exception if needed
        }

        return new LatLng(latitude, longitude);
    }

    // Other methods...

    @Override
    public String toString() {
        return "LocationData{" +
                "device_id='" + device_id + '\'' +
                ", mode='" + mode + '\'' +
                ", status='" + status + '\'' +
                ", tanggal='" + tanggal + '\'' +
                '}';
    }
}

package com.emergency.model;

public class Driver {
    private String driverId;
    private String name;
    private String licenseNumber;

    public Driver(String driverId, String name, String licenseNumber) {
        this.driverId = driverId;
        this.name = name;
        this.licenseNumber = licenseNumber;
    }

    public String getDriverId() { return driverId; }
    public String getName() { return name; }
    public String getLicenseNumber() { return licenseNumber; }
}

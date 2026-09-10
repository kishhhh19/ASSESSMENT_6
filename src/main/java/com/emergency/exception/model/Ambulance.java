package com.emergency.model;

public class Ambulance {
    private String ambulanceId;
    private AmbulanceType ambulanceType;
    private AmbulanceState state;
    private Driver driver;

    public Ambulance(String ambulanceId, AmbulanceType ambulanceType, Driver driver) {
        this.ambulanceId = ambulanceId;
        this.ambulanceType = ambulanceType;
        this.state = AmbulanceState.AVAILABLE;
        this.driver = driver;
    }

    public String getAmbulanceId() { return ambulanceId; }
    public AmbulanceType getAmbulanceType() { return ambulanceType; }
    public AmbulanceState getState() { return state; }
    public void setState(AmbulanceState state) { this.state = state; }
    public Driver getDriver() { return driver; }
}

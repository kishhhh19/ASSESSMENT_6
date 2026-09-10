package com.emergency.model;

public class EmergencyRequest implements Comparable<EmergencyRequest> {
    private String patientId;
    private String emergencyType;
    private EmergencyPriority priority;
    private String pickupLocation;
    private String destinationHospital;
    private double estimatedDistance;
    private String status;

    public EmergencyRequest(String patientId, String emergencyType, EmergencyPriority priority, String pickupLocation, String destinationHospital, double estimatedDistance) {
        this.patientId = patientId;
        this.emergencyType = emergencyType;
        this.priority = priority;
        this.pickupLocation = pickupLocation;
        this.destinationHospital = destinationHospital;
        this.estimatedDistance = estimatedDistance;
        this.status = "PENDING";
    }

    @Override
    public int compareTo(EmergencyRequest other) {
        int priorityCompare = Integer.compare(this.priority.getRank(), other.priority.getRank());
        if (priorityCompare != 0) return priorityCompare;
        return Double.compare(this.estimatedDistance, other.estimatedDistance);
    }

    public String getPatientId() { return patientId; }
    public EmergencyPriority getPriority() { return priority; }
    public double getEstimatedDistance() { return estimatedDistance; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getPickupLocation() { return pickupLocation; }
}

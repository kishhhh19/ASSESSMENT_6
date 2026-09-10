package com.emergency.service;

import com.emergency.exception.InvalidEmergencyRequestException;
import com.emergency.model.*;

import java.util.*;
import java.util.concurrent.PriorityBlockingQueue;

public class DispatchService {
    private final PriorityBlockingQueue<EmergencyRequest> waitingQueue = new PriorityBlockingQueue<>();
    private final List<Ambulance> ambulances = new ArrayList<>();
    private final List<String> emergencyHistory = new ArrayList<>();

    public void registerAmbulance(Ambulance ambulance) {
        ambulances.add(ambulance);
    }

    public synchronized void processEmergency(EmergencyRequest request, AmbulanceType requiredType) {
        if (request == null || request.getPickupLocation() == null) {
            throw new InvalidEmergencyRequestException("Invalid emergency request parameters.");
        }

        Ambulance allocated = findAvailableAmbulance(requiredType);
        if (allocated != null) {
            assignAmbulance(request, allocated);
        } else {
            waitingQueue.add(request);
            request.setStatus("WAITING");
            emergencyHistory.add("Queued emergency for patient: " + request.getPatientId());
        }
    }

    private Ambulance findAvailableAmbulance(AmbulanceType type) {
        return ambulances.stream()
                .filter(a -> a.getState() == AmbulanceState.AVAILABLE && a.getAmbulanceType() == type)
                .findFirst()
                .orElse(null);
    }

    private void assignAmbulance(EmergencyRequest request, Ambulance ambulance) {
        ambulance.setState(AmbulanceState.DISPATCHED);
        request.setStatus("DISPATCHED");
        emergencyHistory.add("Assigned Ambulance " + ambulance.getAmbulanceId() + " to Patient " + request.getPatientId());
    }

    public synchronized void updateAmbulanceState(Ambulance ambulance, AmbulanceState newState) {
        ambulance.setState(newState);
        emergencyHistory.add("Ambulance " + ambulance.getAmbulanceId() + " state updated to " + newState);

        if (newState == AmbulanceState.AVAILABLE && !waitingQueue.isEmpty()) {
            EmergencyRequest nextRequest = waitingQueue.poll();
            if (nextRequest != null) {
                assignAmbulance(nextRequest, ambulance);
            }
        }
    }

    public List<String> getEmergencyHistory() { return emergencyHistory; }
    public PriorityBlockingQueue<EmergencyRequest> getWaitingQueue() { return waitingQueue; }
}

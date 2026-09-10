package com.emergency.service;

import com.emergency.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DispatchServiceTest {
    private DispatchService dispatchService;
    private Ambulance testAmbulance;

    @BeforeEach
    public void setUp() {
        dispatchService = new DispatchService();
        Driver driver = new Driver("D001", "John Doe", "LIC12345");
        testAmbulance = new Ambulance("A001", AmbulanceType.ICU, driver);
        dispatchService.registerAmbulance(testAmbulance);
    }

    @Test
    public void testImmediateDispatch() {
        EmergencyRequest request = new EmergencyRequest("P001", "Cardiac", EmergencyPriority.CRITICAL, "Zone A", "City Hospital", 3.5);
        dispatchService.processEmergency(request, AmbulanceType.ICU);

        assertEquals("DISPATCHED", request.getStatus());
        assertEquals(AmbulanceState.DISPATCHED, testAmbulance.getState());
    }

    @Test
    public void testQueueWhenAmbulanceBusy() {
        EmergencyRequest request1 = new EmergencyRequest("P001", "Cardiac", EmergencyPriority.CRITICAL, "Zone A", "City Hospital", 3.5);
        EmergencyRequest request2 = new EmergencyRequest("P002", "Trauma", EmergencyPriority.CRITICAL, "Zone B", "City Hospital", 1.0);

        dispatchService.processEmergency(request1, AmbulanceType.ICU);
        dispatchService.processEmergency(request2, AmbulanceType.ICU);

        assertEquals("DISPATCHED", request1.getStatus());
        assertEquals("WAITING", request2.getStatus());
        assertEquals(1, dispatchService.getWaitingQueue().size());
    }
}

package com.trafficmon;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EventLoggerTest {

    @Test
    public void getEventLogTest() {
        String registration = "12345";
        Vehicle testVehicle = Vehicle.withRegistration(registration);

        EventLogger logger = new EventLogger();

        logger.addEntry(testVehicle);

        assertTrue(testVehicle.equals(logger.getEventLog().get(0).getVehicle()));
    }

    @Test
    public void putsEntryCorrectly() {
        String registration = "12345";
        Vehicle testVehicle = Vehicle.withRegistration(registration);

        EventLogger logger = new EventLogger();

        logger.addEntry(testVehicle);

        assertTrue(logger.getEventLog().get(0) instanceof EntryEvent);
    }

    @Test
    public void putsExitCorrectly() {
        String registration = "12345";
        Vehicle testVehicle = Vehicle.withRegistration(registration);

        EventLogger logger = new EventLogger();

        logger.addEntry(testVehicle);
        logger.addExit(testVehicle);

        assertTrue(logger.getEventLog().get(1) instanceof ExitEvent);
    }

    @Test
    public void wontAddExitIfNotPreviouslyRegistered() {
        String registration = "12345";
        Vehicle testVehicle = Vehicle.withRegistration(registration);

        EventLogger logger = new EventLogger();

        logger.addExit(testVehicle);

        assertEquals(0, logger.getEventLog().size());
    }



}

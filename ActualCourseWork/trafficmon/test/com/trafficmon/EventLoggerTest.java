package com.trafficmon;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class EventLoggerTest {

    EventLogger logger = new EventLogger();
    Vehicle testVehicle = Vehicle.withRegistration("12345");

    @Test
    public void getEventLogTest() {
        logger.addEntry(testVehicle);

        assertTrue(testVehicle.equals(logger.getEventLog().get(0).getVehicle()));
    }

    @Test
    public void putsEntryCorrectly() {
        logger.addEntry(testVehicle);

        assertTrue(logger.getEventLog().get(0) instanceof EntryEvent);
    }

    @Test
    public void putsExitCorrectly() {
        logger.addEntry(testVehicle);
        logger.addExit(testVehicle);

        assertTrue(logger.getEventLog().get(1) instanceof ExitEvent);
    }

    @Test
    public void wontAddExitIfNotPreviouslyRegistered() {
        logger.addExit(testVehicle);

        assertEquals(0, logger.getEventLog().size());
    }



}

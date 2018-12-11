package com.trafficmon;

import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ZoneBoundaryCrossingTest {

    @Test
    public void getVehicleTest(){
        String registration = "12345";
        Vehicle testVehicle = Vehicle.withRegistration(registration);
        ZoneBoundaryCrossing testCrossing = new ZoneBoundaryCrossing(testVehicle);

        assertEquals(testCrossing.getVehicle(), testVehicle);
    }

    @Test
    public void getHourTest(){
        String registration = "12345";
        Vehicle testVehicle = Vehicle.withRegistration(registration);
        Date date = new Date(System.currentTimeMillis());
        ZoneBoundaryCrossing testCrossing = new ZoneBoundaryCrossing(testVehicle);

        SimpleDateFormat hourExtract = new SimpleDateFormat("HH");
        int hour = Integer.parseInt(hourExtract.format(date));

        assertEquals(hour, testCrossing.getHour());
    }

    @Test
    public void entryEventTest() {
        String registration = "12345";
        Vehicle testVehicle = Vehicle.withRegistration(registration);

        EntryEvent testEntry = new EntryEvent(testVehicle);

        assertTrue(testEntry instanceof ZoneBoundaryCrossing);
    }

    @Test
    public void exitEventTest() {
        String registration = "12345";
        Vehicle testVehicle = Vehicle.withRegistration(registration);

        ExitEvent testExit = new ExitEvent(testVehicle);

        assertTrue(testExit instanceof ZoneBoundaryCrossing);
    }

    @Test
    public void entryAndExitEventsNotEqual() {
        String registration = "12345";
        Vehicle testVehicle = Vehicle.withRegistration(registration);

        EntryEvent testEntry = new EntryEvent(testVehicle);
        ExitEvent testExit = new ExitEvent(testVehicle);

        assertFalse(testEntry.equals(testExit));
    }

    @Test
    public void getTimeTest(){

        String registration = "12345";
        Vehicle testVehicle = Vehicle.withRegistration(registration);
        ZoneBoundaryCrossing test = new ZoneBoundaryCrossing(testVehicle);

        long currentTime = System.currentTimeMillis();
        long crossingTime = test.getTime();

        assertEquals(currentTime, crossingTime);
    }

}

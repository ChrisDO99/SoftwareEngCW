package com.trafficmon;
import org.jmock.Expectations;
import org.junit.Test;

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Random;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CongestionChargeSystemTest {

    /*
    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    EntryEvent entryevent = context.mock(EntryEvent.class);
    CongestionChargeSystem congestionchargesystem = new CongestionChargeSystem();
    String registration = "12345";
    Vehicle testVehicle = Vehicle.withRegistration(registration);


    @Test
    public void enteringZoneRegistersEvent() {
        context.checking(new Expectations() {{
            exactly(1).of(entryevent);
        }});

        congestionchargesystem.vehicleEnteringZone(testVehicle);
    }
    */
    private static final String REGISTRATION = "123456";

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    Vehicle vehicle = context.mock(Vehicle.class);
    CongestionChargeSystem congestionChargeSystem = new CongestionChargeSystem();

    CongestionChargeSystem conmock = context.mock(CongestionChargeSystem.class);


    @Test
    public void vehicleRegisters(){
        //Tests if vehicle registration is equal to the registration it was given
        String inputRegistration = "123456";
        Vehicle testVehicle = Vehicle.withRegistration(inputRegistration);


        String testString = "Vehicle [" + inputRegistration + ']';

        String vehicleString = testVehicle.toString();


        assertThat(vehicleString, is(testString));
    }

    @Test
    public void vehicleEquals(){
        //Tests the Vehicle equals function, that the vehicle is equal to itself
        String inputRegistration = "123456";
        Vehicle testVehicle = Vehicle.withRegistration(inputRegistration);

        String inputRegistration2 = "234567";
        Vehicle testVehicle2 = Vehicle.withRegistration(inputRegistration2);

        assertTrue(testVehicle.equals(testVehicle));
        assertFalse(testVehicle.equals(testVehicle2));
    }

    //Add test for hashcode in Vehicle

    //Add test for ZoneBoundaryCrossing with CongestionChargeSystem

    @Test
    public void vehicleRegisteredInCrossing() {
        //Tests whether the vehicle in ZoneBoundaryCrossing objects is the same as they were given
        String inputRegistration = "123456";
        Vehicle testVehicle = Vehicle.withRegistration(inputRegistration);

        EntryEvent testEntry = new EntryEvent(testVehicle);
        ExitEvent testExit = new ExitEvent(testVehicle);

        assertThat(testVehicle, is(testEntry.getVehicle()));
        assertThat(testVehicle, is(testExit.getVehicle()));
    }

    @Test
    public void vehicleAddedToEventLog() {
        //Tests whether the vehicle in the eventlog is the same one as was added
        String inputRegistration = "123456";
        Vehicle testVehicle = Vehicle.withRegistration(inputRegistration);

        CongestionChargeSystem testSystem = new CongestionChargeSystem();

        testSystem.vehicleEnteringZone(testVehicle);
        testSystem.vehicleLeavingZone(testVehicle);

        List<ZoneBoundaryCrossing> eventLog = testSystem.getEventLog();

        assertThat(testVehicle, is(eventLog.get(0).getVehicle()));
        assertThat(testVehicle, is(eventLog.get(1).getVehicle()));
    }

    @Test
    public void eventLogRegistersEntryAndExit() {
        //Tests whether entry and exit events are correctly registered in eventlog
        String inputRegistration = "123456";
        Vehicle testVehicle = Vehicle.withRegistration(inputRegistration);

        CongestionChargeSystem testSystem = new CongestionChargeSystem();

        testSystem.vehicleEnteringZone(testVehicle);
        testSystem.vehicleLeavingZone(testVehicle);

        List<ZoneBoundaryCrossing> eventLog = testSystem.getEventLog();

        assertTrue(eventLog.get(0) instanceof EntryEvent);
        assertTrue(eventLog.get(1) instanceof ExitEvent);
    }

    @Test
    public void notAddedToEventLogIfExitOnly() {
        //Tests the previouslyRegistered() function in CongestionChargeSystem. A vehicle's exit isn't added if
        //it hasn't previously entered
        String inputRegistration = "123456";
        Vehicle testVehicle = Vehicle.withRegistration(inputRegistration);

        CongestionChargeSystem testSystem = new CongestionChargeSystem();

        testSystem.vehicleLeavingZone(testVehicle);

        List<ZoneBoundaryCrossing> eventLog = testSystem.getEventLog();

        assertThat(0, is(eventLog.size()));
    }

    @Test
    public void checkOrderingTest() {

        String inputRegistration = "123456";
        Vehicle testVehicle = Vehicle.withRegistration(inputRegistration);

        CongestionChargeSystem testSystem = new CongestionChargeSystem();

        testSystem.vehicleLeavingZone(testVehicle);

        List<ZoneBoundaryCrossing> eventLog = testSystem.getEventLog();

    }


    @Test
    public void sixPoundsIfBefore2PM() {
        /*
        String registration = "12345";
        Vehicle testVehicle = Vehicle.withRegistration(registration);
        EntryEvent entry = new EntryEvent(testVehicle);

        Random rand = new Random();
        int randomHour = rand.nextInt(14);

        SimpleDateFormat hourExtract = new SimpleDateFormat("HH");
        Date currentDate = new Date(entry.getTime());
        int currentHour = Integer.parseInt(hourExtract.format(currentDate));

        int timeDifference = currentHour - randomHour;
        long moddedTime = entry.getTime() - (timeDifference * 60 * 60 * 1000);
        entry.setTime(moddedTime);

        /*
        SimpleDateFormat sdformat = new SimpleDateFormat("YYYY/MM/dd:HH:mm:ss");
        Date dateTest = new Date(entry.timestamp());
        System.out.println(sdformat.format(dateTest));
        */

        //CongestionChargeSystem testSystem = new CongestionChargeSystem();
        //testSystem.eventLog.add(entry);


    }

    @Test
    public void fourPoundsIfAfter2PM() {

    }

    @Test
    public void notChargedTwice4Hours() {

    }

    @Test
    public void twelvePoundsIfLongerThan4Hours() {

    }

}
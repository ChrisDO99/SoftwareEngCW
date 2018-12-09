package com.trafficmon;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;


public class CongestionChargeSystemTest {

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();


    Crossing mockCrossing = context.mock(Crossing.class);
    VehicleInterface mockVehicle = context.mock(VehicleInterface.class);
    OperationsInterface mockOperations = context.mock(OperationsInterface.class);
    CongestionChargeSystem testSystem = new CongestionChargeSystem();

    String registration = "12345";
    Vehicle testVehicle = Vehicle.withRegistration(registration);
    EntryEvent testEntry = new EntryEvent(testVehicle);
    ExitEvent testExit = new ExitEvent(testVehicle);


    @Test
    public void jMockTest() {

        context.checking(new Expectations() {{
            exactly(1).of(mockCrossing).getVehicle();
        }});

        testSystem.vehicleEnteringZone(testVehicle);
        testSystem.vehicleLeavingZone(testVehicle);

        testSystem.testJMock(testEntry);
    }

    @Test
    public void checkOrderingOfTest() {

        testSystem.vehicleEnteringZone(testVehicle);
        testSystem.vehicleLeavingZone(testVehicle);

        context.checking(new Expectations() {{
            atLeast(1).of(mockCrossing).getVehicle();
            will(returnValue(8));
            exactly(1).of(mockCrossing).getTime();
            will(returnValue(5));
            exactly(1).of(mockOperations).triggerInvestigationInto(testVehicle);
        }});

        testSystem.calculateCharges();
    }


    @Test
    public void sixPoundsIfBefore2PM() {

        List<ZoneBoundaryCrossing> crossings = new ArrayList<ZoneBoundaryCrossing>();
        crossings.add(mockCrossing);

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


        CongestionChargeSystem testSystem = new CongestionChargeSystem();
        testSystem.eventLog.add(entry);


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
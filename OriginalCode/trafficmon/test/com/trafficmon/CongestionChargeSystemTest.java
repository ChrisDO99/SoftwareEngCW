package com.trafficmon;
import org.jmock.Expectations;
import org.junit.Test;
import java.util.Random;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CongestionChargeSystemTest {

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    EntryEvent entryevent = context.mock(EntryEvent.class);
    CongestionChargeSystem congestionchargesystem = new CongestionChargeSystem();
    String registration = "12345";
    Vehicle testVehicle = Vehicle.withRegistration(registration);

    @Test
    public void enteringZoneRegistersEvent() {


        context.checking(new Expectations() {{
            exactly(1).of(entryevent).ZoneBoundaryCrossing;
        }});

        congestionchargesystem.vehicleEnteringZone(testVehicle);
    }

    @Test
    public void sixPoundsIfBefore2PM() {

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
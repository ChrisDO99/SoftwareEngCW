package com.trafficmon;
import org.junit.Test;
import java.util.Random;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class CongestionChargeSystemTest {

    @Test
    public void sixPoundsIfBefore2PM() {

        String registration = "12345";
        Vehicle testVehicle = Vehicle.withRegistration(registration);
        EntryEvent entry = new EntryEvent(testVehicle);

        Random rand = new Random();
        int randomHour = rand.nextInt(14);

        SimpleDateFormat hourExtract = new SimpleDateFormat("HH");
        Date currentDate = new Date(entry.timestamp());
        int currentHour = Integer.parseInt(hourExtract.format(currentDate));

        int timeDifference = currentHour - randomHour;
        long moddedTime = entry.timestamp() - (timeDifference * 60 * 60 * 1000);
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
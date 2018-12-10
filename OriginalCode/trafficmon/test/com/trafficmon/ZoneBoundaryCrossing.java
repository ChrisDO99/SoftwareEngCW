package com.trafficmon;
import org.junit.Test;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.Assert.*;


public class ZoneBoundaryTest {

    @Test
    public void getVehicleTest(){

        String registration = "12345";
        Vehicle testVehicle = Vehicle.withRegistration(registration);
        ZoneBoundaryCrossing test = new ZoneBoundaryCrossing(testVehicle);

        assertEquals(test.getVehicle(), testVehicle);
    }


    @Test
    public void getHourTest(){

        long time = System.currentTimeMillis();
        SimpleDateFormat hourExtract = new SimpleDateFormat("20");
        Date date = new Date(time);
        int hour = Integer.parseInt(hourExtract.format(date));

        assertEquals(hour, 20);
    }

    @Test

    public void getTimeTest(){

        String registration = "12345";
        Vehicle testVehicle = Vehicle.withRegistration(registration);
        ZoneBoundaryCrossing test = new ZoneBoundaryCrossing(testVehicle);


        long time = System.currentTimeMillis();
        long newTime = test.getTime();

        assertEquals(time, newTime);

    }

    @Test
    public void setTime(){


        String registration = "12345";
        Vehicle testVehicle = Vehicle.withRegistration(registration);
        ZoneBoundaryCrossing test = new ZoneBoundaryCrossing(testVehicle);

        test.setTime(123458);
        long newTime = test.getTime();

        assertEquals(newTime, 123458);
    }
}

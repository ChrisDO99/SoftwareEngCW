package com.trafficmon;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.*;

public class ChargeCalculatorTest {

    @Test
    public void calculateChargeForTimeInZoneTest(){

        List<ZoneBoundaryCrossing> crossings = new ArrayList<ZoneBoundaryCrossing>();

        String registration = "12345";
        Vehicle testVehicle = Vehicle.withRegistration(registration);
        ZoneBoundaryCrossing crossing = new ZoneBoundaryCrossing(testVehicle);

        EntryEvent entry1 = new EntryEvent(testVehicle);
        entry1.setTime(1544460000000L); //16th ohour of the day

        ExitEvent exit1 = new ExitEvent(testVehicle);
        exit1.setTime(1544467200000L); //18th hour of the day, exactly 2 hours from entry1

        EntryEvent entry2 = new EntryEvent(testVehicle);
        entry2.setTime(1544467210000L); //18th hour of the day, exactly 10 seconds from exit1

        ExitEvent exit2 = new ExitEvent(testVehicle);
        exit2.setTime(1544467410000L); //18th hour of the day, exactly 410 seconds from entry2

        crossings.add(entry1);
        crossings.add(exit1);
        crossings.add(entry2);
        crossings.add(exit2);

        ChargeCalculator calculator = new ChargeCalculator(crossings);
        BigDecimal cost = calculator.calculateChargeForTimeInZone();

        assertEquals(BigDecimal.valueOf(4), cost);
    }
}

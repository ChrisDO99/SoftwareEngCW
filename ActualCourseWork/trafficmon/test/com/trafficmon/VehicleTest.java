package com.trafficmon;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class VehicleTest {

    @Test
    public void toStringTest(){

        String registration = "12345";
        Vehicle testVehicle = Vehicle.withRegistration(registration);

        String test = testVehicle.toString();

        assertEquals(test, "Vehicle [12345]");

    }

    @Test
    public void equalsTest(){

        String registration1 = "12345";
        Vehicle testVehicle1 = Vehicle.withRegistration(registration1);

        String registration2 = "123456";
        Vehicle testVehicle2 = Vehicle.withRegistration(registration2);

        assertEquals(false, testVehicle1.equals(testVehicle2));
    }

    @Test
    public void hashCodeTest(){

        String registration0 = "";
        Vehicle testVehicle0 = Vehicle.withRegistration(registration0);

        String registration1 = "12345";
        Vehicle testVehicle1 = Vehicle.withRegistration(registration1);

        String registration2 = "12345";
        Vehicle testVehicle2 = Vehicle.withRegistration(registration2);


        assertEquals(0, testVehicle0.hashCode());
        assertTrue(registration1.equals(registration2) && registration2.equals(registration1));
        assertTrue(registration1.hashCode() == registration2.hashCode());

    }

}

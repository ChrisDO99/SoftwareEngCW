package com.trafficmon;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class VehicleTest {

    String registration = "12345";
    Vehicle testVehicle = Vehicle.withRegistration(registration);

    @Test
    public void toStringTest(){

        String test = testVehicle.toString();

        assertEquals(test, "Vehicle [12345]");
    }

    @Test
    public void vehicleDoesNotEqualDifferentVehicle(){
        String registration2 = "123456";
        Vehicle testVehicle2 = Vehicle.withRegistration(registration2);

        assertEquals(false, testVehicle.equals(testVehicle2));

    }

    @Test
    public void vehicleEqualsItself(){
        assertEquals(true, testVehicle.equals(testVehicle));
    }

    @Test
    public void vehicleReturnsFalseForNull(){
        assertEquals(false, testVehicle.equals(null));
    }

    @Test
    public void hashCodeTest(){
        String registration0 = "";
        Vehicle testVehicle0 = Vehicle.withRegistration(registration0);

        assertEquals(0, testVehicle0.hashCode());
    }

}

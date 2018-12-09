package com.trafficmon;

public class EntryEvent extends ZoneBoundaryCrossing implements Crossing {
    public EntryEvent(Vehicle vehicleRegistration) {
        super(vehicleRegistration);
    }
}

package com.trafficmon;

import java.util.ArrayList;
import java.util.List;

public class EventLogger {

    private final List<Crossing> eventLog = new ArrayList<Crossing>();

    public void addEntry(Vehicle vehicle) {
        eventLog.add(new EntryEvent(vehicle));
    }

    public void addExit(Vehicle vehicle) {
        if (!previouslyRegistered(vehicle)) {
            return;
        }
        eventLog.add(new ExitEvent(vehicle));
    }

    private boolean previouslyRegistered(Vehicle vehicle) {
        for (Crossing crossing : eventLog) {
            if (crossing.getVehicle().equals(vehicle)) {
                return true;
            }
        }
        return false;
    }

    public List<Crossing> getEventLog() {
        return eventLog;
    }

}

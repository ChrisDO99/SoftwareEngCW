package com.trafficmon;

import java.util.*;

public class CongestionChargeSystem {

    private EventLogger logger = new EventLogger();

    public void vehicleEnteringZone(Vehicle vehicle) {
        logger.addEntry(vehicle);
    }

    public void vehicleLeavingZone(Vehicle vehicle) {
        logger.addExit(vehicle);
    }

    public void calculateCharges() {

        List<Crossing> eventLog = logger.getEventLog();

        Map<Vehicle, List<Crossing>> crossingsByVehicle = new HashMap<Vehicle, List<Crossing>>();

        for (Crossing crossing : eventLog) {
            if (!crossingsByVehicle.containsKey(crossing.getVehicle())) {
                crossingsByVehicle.put(crossing.getVehicle(), new ArrayList<Crossing>());
            }
            crossingsByVehicle.get(crossing.getVehicle()).add(crossing);
        }

        for (Map.Entry<Vehicle, List<Crossing>> vehicleCrossings : crossingsByVehicle.entrySet()) {
            List<Crossing> crossings = vehicleCrossings.getValue();

            ChargeCalculator calculator = new ChargeCalculator(crossings);

            calculator.calculateChargeForTimeInZone();

            calculator.deductCharges();
        }
    }
}
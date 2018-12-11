package com.trafficmon;

import java.math.BigDecimal;
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
            Vehicle vehicle = vehicleCrossings.getKey();
            List<Crossing> crossings = vehicleCrossings.getValue();

            if (!checkOrderingOf(crossings)) {
                OperationsTeam.getInstance().triggerInvestigationInto(vehicle);
            } else {

                ChargeCalculator calculator = new ChargeCalculator(crossings);

                BigDecimal charge = calculator.calculateChargeForTimeInZone();

                try {
                    //this is where charge is deducted
                    RegisteredCustomerAccountsService.getInstance().accountFor(vehicle).deduct(charge);
                } catch (InsufficientCreditException ice) {
                    OperationsTeam.getInstance().issuePenaltyNotice(vehicle, charge);
                } catch (AccountNotRegisteredException e) {
                    OperationsTeam.getInstance().issuePenaltyNotice(vehicle, charge);
                }
            }
        }
    }

    private boolean checkOrderingOf(List<Crossing> crossings) {

        Crossing lastEvent = crossings.get(0);

        for (Crossing crossing : crossings.subList(1, crossings.size())) {
            if (crossing.getTime() < lastEvent.getTime()) {
                return false;
            }
            if (crossing instanceof EntryEvent && lastEvent instanceof EntryEvent) {
                return false;
            }
            if (crossing instanceof ExitEvent && lastEvent instanceof ExitEvent) {
                return false;
            }
            lastEvent = crossing;
        }

        return true;
    }

}

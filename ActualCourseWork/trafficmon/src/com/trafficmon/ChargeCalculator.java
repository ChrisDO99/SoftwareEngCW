package com.trafficmon;

import java.math.BigDecimal;
import java.util.List;

public class ChargeCalculator {

    private List<Crossing> crossings;
    private PenaltiesService penaltyService;
    private Vehicle vehicle;
    public BigDecimal charge;

    public ChargeCalculator(List<Crossing> crossingsList) {
        this.crossings = crossingsList;
        this.penaltyService = OperationsTeam.getInstance();
        this.vehicle = crossingsList.get(0).getVehicle();
    }

    public ChargeCalculator(List<Crossing> crossingsList, PenaltiesService penaltyService) {
        this.crossings = crossingsList;
        this.penaltyService = penaltyService;
        this.vehicle = crossingsList.get(0).getVehicle();
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

    private int calculateEntryCharge(Crossing crossing) {
        int entryCost = 1234;
        if (crossing.getHour() < 14) {
            entryCost = 6;
        }
        else if (crossing.getHour() >= 14) {
            entryCost = 4;
        }
        return entryCost;
    }

    public void calculateChargeForTimeInZone() {

        if(crossings.size()%2 == 1) {
            throw new IllegalArgumentException("All vehicles must leave");
        }

        if (!checkOrderingOf(crossings)) {
            penaltyService.triggerInvestigationInto(vehicle);
            charge = new BigDecimal(0);
        }
        else {

            int totalCost = 0;
            long totalTime = 0;

            totalCost += calculateEntryCharge(crossings.get(0));

            for (int i = 0; i < crossings.size() - 1; i += 2) {
                if (i + 2 < crossings.size()) {
                    long timeDelta = crossings.get(i + 2).getTime() - crossings.get(i).getTime();
                    if (timeDelta > (4 * 60 * 60 * 1000)) {
                        totalCost += calculateEntryCharge(crossings.get(i + 2));
                    }
                }
            }

            for (int i = 0; i + 1 < crossings.size(); i += 2) {
                long pairTime = crossings.get(i + 1).getTime() - crossings.get(i).getTime();
                totalTime += pairTime;
            }

            if (totalTime > (4 * 60 * 60 * 1000)) {
                totalCost = 12;
            }

            charge = new BigDecimal(totalCost);
        }

    }

    public void deductCharges() {
        if (!charge.equals(new BigDecimal(0))) {
            try {
                RegisteredCustomerAccountsService.getInstance().accountFor(vehicle).deduct(charge);
            } catch (InsufficientCreditException ice) {
                penaltyService.issuePenaltyNotice(vehicle, charge);
            } catch (AccountNotRegisteredException e) {
                penaltyService.issuePenaltyNotice(vehicle, charge);
            }
        }
    }

}

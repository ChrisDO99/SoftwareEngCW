package com.trafficmon;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChargeCalculator {

    private List<Crossing> crossings;

    public ChargeCalculator(List<Crossing> crossingsList) {
        this.crossings = crossingsList;
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

    public BigDecimal calculateChargeForTimeInZone() {

        int totalCost = 0;
        BigDecimal charge;
        long totalTime = 0;

        totalCost += calculateEntryCharge(crossings.get(0));

        for (int i =0; i < crossings.size()-1; i+=2) {
            if (i+2 < crossings.size()) {
                long timeDelta = crossings.get(i+2).getTime() - crossings.get(i).getTime();
                if (timeDelta > (4 * 60 * 60 * 1000)) {
                    totalCost += calculateEntryCharge(crossings.get(i+2));
                }
            }
        }

        for (int i =0; i+1 < crossings.size(); i+=2) {
            long pairTime = crossings.get(i + 1).getTime() - crossings.get(i).getTime();
            totalTime += pairTime;
        }

        if (totalTime > (4 * 60 * 60 * 1000)) {
            totalCost = 12;
        }

        charge = new BigDecimal(totalCost);

        return charge;
    }


}

package com.trafficmon;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;



public class ChargeCalculatorTest {

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    long twentyThreeOEight = 1544483284396L;
    Vehicle testVehicle = Vehicle.withRegistration("12345");
    List<Crossing> crossings = new ArrayList<>();

    Crossing mockEntry = context.mock(Crossing.class, "Entry");
    Crossing mockExit = context.mock(Crossing.class, "Exit");
    Crossing mockEntry2 = context.mock(Crossing.class, "Entry2");
    Crossing mockExit2 = context.mock(Crossing.class, "Exit2");

    @Test
    public void sixPoundsIfBefore2PM() {

        long tenOEight = twentyThreeOEight - 13*60*60*1000L;

        context.checking(new Expectations() {{
            exactly(1).of(mockEntry).getVehicle();
            will(returnValue(testVehicle));
            exactly(2).of(mockEntry).getTime();
            will(returnValue(tenOEight));
            exactly(2).of(mockExit).getTime();
            will(returnValue(tenOEight + 2*60*60*1000L));
            atMost(2).of(mockEntry).getHour();
            will(returnValue(10));
        }});

        crossings.add(mockEntry);
        crossings.add(mockExit);

        ChargeCalculator testCalculator = new ChargeCalculator(crossings);

        testCalculator.calculateChargeForTimeInZone();

        assertThat(testCalculator.charge, is(new BigDecimal(6.00)));
    }

    @Test(expected = IllegalArgumentException.class)
    public void vehicleDoesntLeaveThrowsException() {
        crossings.add(new EntryEvent(testVehicle));

        ChargeCalculator testCalculator = new ChargeCalculator(crossings);

        testCalculator.calculateChargeForTimeInZone();
    }

    @Test
    public void fourPoundsIfAfter2PM() {
        long sixteenOEight = twentyThreeOEight - 7*60*60*1000L;

        context.checking(new Expectations() {{
            exactly(1).of(mockEntry).getVehicle();
            will(returnValue(testVehicle));
            exactly(2).of(mockEntry).getTime();
            will(returnValue(sixteenOEight));
            exactly(2).of(mockExit).getTime();
            will(returnValue(sixteenOEight + 2*60*60*1000L));
            atMost(2).of(mockEntry).getHour();
            will(returnValue(16));
        }});

        crossings.add(mockEntry);
        crossings.add(mockExit);

        ChargeCalculator testCalculator = new ChargeCalculator(crossings);

        testCalculator.calculateChargeForTimeInZone();

        assertThat(testCalculator.charge, is(new BigDecimal(4.00)));
    }

    @Test
    public void notChargedTwiceWithin4Hours() {
        long sixteenOEight = twentyThreeOEight - 7*60*60*1000L;

        context.checking(new Expectations() {{
            exactly(1).of(mockEntry).getVehicle();
            will(returnValue(testVehicle));
            exactly(3).of(mockEntry).getTime();
            will(returnValue(sixteenOEight));
            exactly(4).of(mockEntry2).getTime();
            will(returnValue(sixteenOEight + 2*60*60*1000L));
            exactly(3).of(mockExit).getTime();
            will(returnValue(sixteenOEight + 60*60*1000L));
            exactly(2).of(mockExit2).getTime();
            will(returnValue(sixteenOEight + 3*60*60*1000L));
            atMost(2).of(mockEntry).getHour();
            will(returnValue(16));
            atMost(2).of(mockEntry2).getHour();
            will(returnValue(18));
        }});

        crossings.add(mockEntry);
        crossings.add(mockExit);
        crossings.add(mockEntry2);
        crossings.add(mockExit2);

        ChargeCalculator testCalculator = new ChargeCalculator(crossings);

        testCalculator.calculateChargeForTimeInZone();

        assertThat(testCalculator.charge, is(new BigDecimal(4.00)));
    }

    @Test
    public void chargeTwiceAfter4Hours() {
        long sixteenOEight = twentyThreeOEight - 7*60*60*1000L;

        context.checking(new Expectations() {{
            exactly(1).of(mockEntry).getVehicle();
            will(returnValue(testVehicle));
            exactly(3).of(mockEntry).getTime();
            will(returnValue(sixteenOEight));
            exactly(4).of(mockEntry2).getTime();
            will(returnValue(sixteenOEight + 5*60*60*1000L));
            exactly(3).of(mockExit).getTime();
            will(returnValue(sixteenOEight + 60*60*1000L));
            exactly(2).of(mockExit2).getTime();
            will(returnValue(sixteenOEight + 6*60*60*1000L));
            atMost(2).of(mockEntry).getHour();
            will(returnValue(16));
            atMost(2).of(mockEntry2).getHour();
            will(returnValue(21));
        }});

        crossings.add(mockEntry);
        crossings.add(mockExit);
        crossings.add(mockEntry2);
        crossings.add(mockExit2);

        ChargeCalculator testCalculator = new ChargeCalculator(crossings);

        testCalculator.calculateChargeForTimeInZone();

        assertThat(testCalculator.charge, is(new BigDecimal(8.00)));
    }

    @Test
    public void twelvePoundsIfLongerThan4Hours() {

        long tenOEight = twentyThreeOEight - 13*60*60*1000L;

        context.checking(new Expectations() {{
            exactly(1).of(mockEntry).getVehicle();
            will(returnValue(testVehicle));
            exactly(2).of(mockEntry).getTime();
            will(returnValue(tenOEight));
            exactly(2).of(mockExit).getTime();
            will(returnValue(tenOEight + 5*60*60*1000L));
            atMost(2).of(mockEntry).getHour();
            will(returnValue(10));
        }});

        crossings.add(mockEntry);
        crossings.add(mockExit);

        ChargeCalculator testCalculator = new ChargeCalculator(crossings);

        testCalculator.calculateChargeForTimeInZone();

        assertThat(testCalculator.charge, is(new BigDecimal(12.00)));
    }

    @Test
    public void checkOrderingOfTest() {
        long tenOEight = twentyThreeOEight - 13*60*60*1000L;

        PenaltiesService mockPenaltiesService = context.mock(PenaltiesService.class);

        context.checking(new Expectations() {{
            exactly(1).of(mockEntry).getVehicle();
            will(returnValue(testVehicle));
            exactly(1).of(mockEntry).getTime();
            will(returnValue(tenOEight));
            exactly(1).of(mockExit).getTime();
            will(returnValue(tenOEight - 5*60*60*1000L));
            exactly(1).of(mockPenaltiesService).triggerInvestigationInto(testVehicle);
        }});

        crossings.add(mockEntry);
        crossings.add(mockExit);

        ChargeCalculator testCalculator = new ChargeCalculator(crossings, mockPenaltiesService);

        testCalculator.calculateChargeForTimeInZone();

        assertThat(testCalculator.charge, is(new BigDecimal(0.00)));
    }

}

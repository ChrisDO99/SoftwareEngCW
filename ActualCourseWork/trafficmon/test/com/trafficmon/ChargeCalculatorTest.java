package com.trafficmon;

import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class ChargeCalculatorTest {

    /*
    * Tests for all of the new functionality - using JMock
     */

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();

    long twentyThreeOEight = 1544483284396L;


    @Test
    public void jMockTest() {
        Crossing mockCrossing = context.mock(Crossing.class);

        context.checking(new Expectations() {{
            exactly(1).of(mockCrossing).getTime();
            will(returnValue(2L));
        }});
        CongestionChargeSystem testSystem = new CongestionChargeSystem(mockCrossing);

        long result = testSystem.testJMock(mockCrossing);

        assertThat(result, is(2L));
    }


    @Test
    public void sixPoundsIfBefore2PM() {

        long tenOEight = twentyThreeOEight - 13*60*60*1000L;

        Crossing mockEntry = context.mock(Crossing.class, "Entry");
        Crossing mockExit = context.mock(Crossing.class, "Exit");

        context.checking(new Expectations() {{
            exactly(1).of(mockEntry).getTime();
            will(returnValue(tenOEight));
            exactly(1).of(mockExit).getTime();
            will(returnValue(tenOEight + 2*60*60*1000L));
            atMost(2).of(mockEntry).getHour();
            will(returnValue(10));
        }});

        List<Crossing> crossings = new ArrayList<>();

        crossings.add(mockEntry);
        crossings.add(mockExit);

        ChargeCalculator testCalculator = new ChargeCalculator(crossings);

        BigDecimal result = testCalculator.calculateChargeForTimeInZone();

        assertThat(result, is(new BigDecimal(6.00)));
    }



    @Test
    public void fourPoundsIfAfter2PM() {

        long sixteenOEight = twentyThreeOEight - 7*60*60*1000L;

        Crossing mockEntry = context.mock(Crossing.class, "Entry");
        Crossing mockExit = context.mock(Crossing.class, "Exit");

        context.checking(new Expectations() {{
            exactly(1).of(mockEntry).getTime();
            will(returnValue(sixteenOEight));
            exactly(1).of(mockExit).getTime();
            will(returnValue(sixteenOEight + 2*60*60*1000L));
            atMost(2).of(mockEntry).getHour();
            will(returnValue(16));
        }});

        List<Crossing> crossings = new ArrayList<>();

        crossings.add(mockEntry);
        crossings.add(mockExit);

        ChargeCalculator testCalculator = new ChargeCalculator(crossings);

        BigDecimal result = testCalculator.calculateChargeForTimeInZone();

        assertThat(result, is(new BigDecimal(4.00)));
    }

    @Test
    public void notChargedTwiceWithin4Hours() {
        long sixteenOEight = twentyThreeOEight - 7*60*60*1000L;

        Crossing mockEntry1 = context.mock(Crossing.class, "Entry1");
        Crossing mockExit1 = context.mock(Crossing.class, "Exit1");
        Crossing mockEntry2 = context.mock(Crossing.class, "Entry2");
        Crossing mockExit2 = context.mock(Crossing.class, "Exit2");

        context.checking(new Expectations() {{
            atMost(2).of(mockEntry1).getHour();
            will(returnValue(16));
            exactly(2).of(mockEntry1).getTime();
            will(returnValue(sixteenOEight));
            exactly(2).of(mockEntry2).getTime();
            will(returnValue(sixteenOEight + 2*60*60*1000L));
            atMost(2).of(mockEntry2).getHour();
            will(returnValue(18));
            exactly(1).of(mockExit1).getTime();
            will(returnValue(sixteenOEight + 60*60*1000L));
            exactly(1).of(mockExit2).getTime();
            will(returnValue(sixteenOEight + 3*60*60*1000L));
        }});

        List<Crossing> crossings = new ArrayList<>();

        crossings.add(mockEntry1);
        crossings.add(mockExit1);
        crossings.add(mockEntry2);
        crossings.add(mockExit2);

        ChargeCalculator testCalculator = new ChargeCalculator(crossings);

        BigDecimal result = testCalculator.calculateChargeForTimeInZone();

        assertThat(result, is(new BigDecimal(4.00)));
    }

    @Test
    public void chargeTwiceAfter4Hours() {
        long sixteenOEight = twentyThreeOEight - 7*60*60*1000L;

        Crossing mockEntry1 = context.mock(Crossing.class, "Entry1");
        Crossing mockExit1 = context.mock(Crossing.class, "Exit1");
        Crossing mockEntry2 = context.mock(Crossing.class, "Entry2");
        Crossing mockExit2 = context.mock(Crossing.class, "Exit2");

        context.checking(new Expectations() {{
            atMost(2).of(mockEntry1).getHour();
            will(returnValue(16));
            exactly(2).of(mockEntry1).getTime();
            will(returnValue(sixteenOEight));
            exactly(2).of(mockEntry2).getTime();
            will(returnValue(sixteenOEight + 5*60*60*1000L));
            atMost(2).of(mockEntry2).getHour();
            will(returnValue(21));
            exactly(1).of(mockExit1).getTime();
            will(returnValue(sixteenOEight + 60*60*1000L));
            exactly(1).of(mockExit2).getTime();
            will(returnValue(sixteenOEight + 6*60*60*1000L));
        }});

        List<Crossing> crossings = new ArrayList<>();

        crossings.add(mockEntry1);
        crossings.add(mockExit1);
        crossings.add(mockEntry2);
        crossings.add(mockExit2);

        ChargeCalculator testCalculator = new ChargeCalculator(crossings);

        BigDecimal result = testCalculator.calculateChargeForTimeInZone();

        assertThat(result, is(new BigDecimal(8.00)));
    }

    @Test
    public void twelvePoundsIfLongerThan4Hours() {

        long tenOEight = twentyThreeOEight - 13*60*60*1000L;

        Crossing mockEntry = context.mock(Crossing.class, "Entry");
        Crossing mockExit = context.mock(Crossing.class, "Exit");

        context.checking(new Expectations() {{
            exactly(1).of(mockEntry).getTime();
            will(returnValue(tenOEight));
            exactly(1).of(mockExit).getTime();
            will(returnValue(tenOEight + 5*60*60*1000L));
            atMost(2).of(mockEntry).getHour();
            will(returnValue(10));
        }});

        List<Crossing> crossings = new ArrayList<>();

        crossings.add(mockEntry);
        crossings.add(mockExit);

        ChargeCalculator testCalculator = new ChargeCalculator(crossings);

        BigDecimal result = testCalculator.calculateChargeForTimeInZone();

        assertThat(result, is(new BigDecimal(12.00)));
    }

}

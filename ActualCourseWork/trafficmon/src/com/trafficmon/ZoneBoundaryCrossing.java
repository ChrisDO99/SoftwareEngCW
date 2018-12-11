package com.trafficmon;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ZoneBoundaryCrossing implements Crossing{

    private final Vehicle vehicle;
    private long time;

    public ZoneBoundaryCrossing(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.time = System.currentTimeMillis();
    }

    @Override
    public Vehicle getVehicle() {
        return vehicle;
    }

    @Override
    public int getHour() {
        SimpleDateFormat hourExtract = new SimpleDateFormat("HH");
        Date date = new Date(time);
        int hour = Integer.parseInt(hourExtract.format(date));
        return hour;
    }

    @Override
    public long getTime() {
        return time;
    }

    @Override
    public void setTime(long newTime) {
        time = newTime;
    }

    @Override
    public void testFunction() {}

}

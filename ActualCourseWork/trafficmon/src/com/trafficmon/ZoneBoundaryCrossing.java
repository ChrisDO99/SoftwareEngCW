package com.trafficmon;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class ZoneBoundaryCrossing {

    private final Vehicle vehicle;
    private long time;

    public ZoneBoundaryCrossing(Vehicle vehicle) {
        this.vehicle = vehicle;
        this.time = System.currentTimeMillis();
    }

    public Vehicle getVehicle() {

        return vehicle;
    }

    public int getHour() {
        SimpleDateFormat hourExtract = new SimpleDateFormat("HH");
        Date date = new Date(time);
        int hour = Integer.parseInt(hourExtract.format(date));
        return hour;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long newTime) {
        time = newTime;
    }

}

package com.trafficmon;

public interface Crossing {

    public String hello = "Hello";
    long time;

    Vehicle getVehicle();
    long getTime();
    void setTime(long c);
    int getHour();
    void testFunction();
}

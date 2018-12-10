package com.trafficmon;

public interface Crossing {

    public String hello = "Hello";
    long time = 0;

    Vehicle getVehicle();
    long getTime();
    void setTime(long c);
    int getHour();
    void testFunction();
}

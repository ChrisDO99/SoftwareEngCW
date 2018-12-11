package com.trafficmon;

public interface Crossing {

    Vehicle getVehicle();
    long getTime();
    void setTime(long c);
    int getHour();

}

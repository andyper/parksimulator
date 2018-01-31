package nl.hanze.t12.life.logic;

import java.awt.*;

public abstract class Car {

    private Location location;
    private int minutesLeft;
    private boolean isPaying;
    public boolean hasToPay;
    public boolean passSpot;
    public boolean reserveert;
    public double moetBetalen;


    /**
     * Constructor for objects of class Car
     * Joris was hier weer 
     */
    public Car() {

    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public int getMinutesLeft() {
        return minutesLeft;
    }

    public void setMinutesLeft(int minutesLeft) {
        this.minutesLeft = minutesLeft;
    }
    
    public boolean getIsPaying() {
        return isPaying;
    }
    
    

    public void setIsPaying(boolean isPaying) {
        this.isPaying = isPaying;
    }

    public boolean getHasToPay() {
        return hasToPay;
    }
    public boolean getPassSpot() {
        return passSpot;
    }
    
    public boolean getReserveert() {
        return reserveert;
    }
    
    public double getMoetBetalen() {
    	return moetBetalen;
    }

    public void setMoetBetalen(double stayMinutes) {
        this.moetBetalen = stayMinutes * 0.05;
    }
    
    public void setHasToPay(boolean hasToPay) {
        this.hasToPay = hasToPay;
    }
    
    public void setPassSpot(boolean passSpot) {
        this.passSpot = passSpot;
    }
    
    public void setReserveert(boolean reserveert) {
        this.reserveert = reserveert;
    }


    public void tick() {
        minutesLeft--;
    }
    
    public abstract Color getColor();
}
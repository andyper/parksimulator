package nl.hanze.t12.life.logic;

import java.awt.*;

public abstract class Car {

    private Location location;
    private int minutesLeft;
    private boolean isPaying;
    private boolean hasToPay;
    public boolean passSpot;
    private boolean reserved;


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
    
    public boolean getReserved() {
        return reserved;
    }

    public void setHasToPay(boolean hasToPay) {
        this.hasToPay = hasToPay;
    }
    
    public void setPassSpot(boolean passSpot) {
        this.passSpot = passSpot;
    }
    
    public void setReserved(boolean reserved) {
        this.reserved = reserved;
    }

    public void tick() {
        minutesLeft--;
    }
    
    public abstract Color getColor();
}
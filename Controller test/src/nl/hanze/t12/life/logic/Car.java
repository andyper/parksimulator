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
    
    // toegevoegd voor checks in de SimulatorModel. Kijkt of een auto moet betalen of niet.
    public boolean getHasToPay() {
        return hasToPay;
    }

    // toegevoegd voor checks in de SimulatorModel. Set of een auto moet betalen of niet.
    public void setHasToPay(boolean hasToPay) {
        this.hasToPay = hasToPay;
    }
    
    // toegevoegd voor checks in de SimulatorModel. Kijkt of een auto wel of niet op een passSpot mag.
    public boolean getPassSpot() {
        return passSpot;
    }
    
 // toegevoegd voor checks in de SimulatorModel. Set of een auto wel of niet op een passSpot mag.
    public void setPassSpot(boolean passSpot) {
        this.passSpot = passSpot;
    }
    
    // toegevoegd voor checks in de SimulatorModel. Kijkt of een auto wel of niet reserveert.
    public boolean getReserveert() {
        return reserveert;
    }
    
 // toegevoegd voor checks in de SimulatorModel. Set of een auto wel of niet reserveert.
    public void setReserveert(boolean reserveert) {
        this.reserveert = reserveert;
    }
    
    // toegevoegd voor checks in de SimulatorModel. Geeft door hoeveel de auto moet betalen.
    public double getMoetBetalen() {
    	return moetBetalen;
    }

    // toegevoegd voor checks in de SimulatorModel. Berekent hoeveel de auto moet betalen.
    public void setMoetBetalen(double stayMinutes) {
        this.moetBetalen = stayMinutes * 0.05;
    }
    
    // toegevoegd voor checks in de SimulatorModel. Berekend hoeveel de Reserved auto moet betalen.
    public void setMoetBetalenReserved(double stayMinutes) {
        this.moetBetalen = stayMinutes * 0.06;
    }

    public void tick() {
        minutesLeft--;
    }
    
    public abstract Color getColor();
}
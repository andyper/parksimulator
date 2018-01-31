package nl.hanze.t12.life.logic;

import java.util.Random;
import java.awt.*;

public class ReservedCar extends Car {
	private static final Color COLOR=Color.green;
	
    public ReservedCar() {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
        this.setPassSpot(false);
        this.setReserveert(true);
        this.setReserved(true);


    }
    public Color getColor(){
    	return COLOR;
    }
    
}
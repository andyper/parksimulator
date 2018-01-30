package nl.hanze.t12.life.logic;

import java.util.Random;
import java.awt.*;

public class SubCar extends Car {
	private static final Color COLOR=Color.green;
	
    public SubCar() {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
    }
    
    public Color getColor(){
    	return COLOR;
    }
}

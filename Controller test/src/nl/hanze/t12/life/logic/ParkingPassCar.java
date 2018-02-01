package nl.hanze.t12.life.logic;

import java.util.Random;
import java.awt.*;

public class ParkingPassCar extends Car {
	private static final Color COLOR=Color.blue;
	
    public ParkingPassCar() {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(false);
        // setPassSpot() toegevoed om te gebruiken voor checks in de SimulatorModel
        this.setPassSpot(true);

    }
    
    public Color getColor(){
    	return COLOR;
    }
}

package nl.hanze.t12.life.logic;

import java.util.Random;
import java.awt.*;

public class AdHocCar extends Car {
	// moet betalen toegevoegd om te gebruiken voor check in de SimulatorModel
	private static final Color COLOR=Color.red;
	
	
    public AdHocCar() {
    	// setMoetBetalen toegevoegd om door te geven aan SimulatorModel om de totale omzet te berekenen
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
    	this.setMoetBetalen(stayMinutes);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
        // setPassSpot() en setReserveert() toegevoed om te gebruiken voor checks in de SimulatorModel
        this.setPassSpot(false);
        this.setReserveert(false);
    }
    
    public Color getColor(){
    	return COLOR;
    }
}
   

package nl.hanze.t12.life.logic;

import java.util.Random;
import java.awt.*;

public class ReservedCar extends Car {
	private static final Color COLOR=Color.green;
	
    public ReservedCar() {
    	// setMoetBetalen toegevoegd om door te geven aan SimulatorModel om de totale omzet te berekenen
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setMoetBetalenReserved(stayMinutes);
        this.setHasToPay(true);
        // setPassSpot() en setReserveert() toegevoed om te gebruiken voor checks in de SimulatorModel
        this.setPassSpot(false);
        this.setReserveert(true);

    }
    public Color getColor(){
    	return COLOR;
    }
    
}
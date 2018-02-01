package nl.hanze.t12.life.logic;

import java.util.Random;
import java.awt.*;

public class AdHocCar extends Car {
	private static final Color COLOR=Color.red;
	public static double opbrengstPerAuto;
	public double prijsPerMinuut = 0.05;
	public double moetBetalen;
	
	
    public AdHocCar() {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
    	this.setMoetBetalen(stayMinutes);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
        this.setPassSpot(false);
        this.setReserveert(false);
    }
    
    public Color getColor(){
    	return COLOR;
    }
}
   

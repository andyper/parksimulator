package nl.hanze.t12.life.logic;

import java.util.Random;
import java.awt.*;

/**
 * Constructor for objects of class Car
 * Joris was hier ook!
 */

public class AdHocCar extends Car {
	private static final Color COLOR=Color.red;
	public static double opbrengstPerAuto;
	public double prijsPerMinuut = 0.05;
	
	
    public AdHocCar() {
    	Random random = new Random();
    	int stayMinutes = (int) (15 + random.nextFloat() * 3 * 60);
        this.setMinutesLeft(stayMinutes);
        this.setHasToPay(true);
    }
    
    public Color getColor(){
    	return COLOR;
    }
    
    public void berekenOpbrengst(int stayMinutes, double prijsPerMinuut) {
    	opbrengstPerAuto = stayMinutes * prijsPerMinuut;
    }
    public static double krijgOpbrengst() {
    	return opbrengstPerAuto;
    }
}

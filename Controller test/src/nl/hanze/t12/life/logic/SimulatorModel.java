package nl.hanze.t12.life.logic;

import java.util.Random;

import javax.swing.JTextField;

import nl.hanze.t12.life.exception.LifeException;
import nl.hanze.t12.life.view.SimulatorView;
import nl.hanze.t12.life.logic.Car;


public class SimulatorModel extends AbstractModel implements Runnable {

	private static final String AD_HOC = "1";
	private static final String PASS = "2";
	private static final String RESERVED = "3";

	private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private SimulatorView simulatorView;
    public int abonnementPlekken;
    public int totalAdHocCars;
    public int totalPassCars;
    public int totalReservedCars;
    public int totalAdHocCarsStat;
    public int totalPassCarsStat;
    public int totalReservedCarsStat;
    public int lostPassCar;
    public int lostAdHocCar;
    public int lostReservedCar;
    private double omzetCar;
    private double omzetReservedCar;
    private int timeLostCar;
    private double lostProfit;
    public int day = 0;
    public int hour = 0;
    public int minute = 0;

    private int tickPause = 100;

    public int weekDayArrivals; // average number of arriving cars per hour
    public int weekendArrivals; // average number of arriving cars per hour
    public int weekDayPassArrivals; // average number of arriving cars per hour
    public int weekendPassArrivals; // average number of arriving cars per hour
    public int weekDayReservedArrivals; // average number of arriving cars per hour
    public int weekendReservedArrivals; // average number of arriving cars per hour

    int enterSpeed = 3; // number of cars that can enter per minute
    int paymentSpeed = 7; // number of cars that can pay per minute
    int exitSpeed = 5; // number of cars that can leave per minute
    
    private int numOfTicks; //Voor uitvoeren in stappen zoals LifeLogic
    private boolean run; //Voor uitvoeren in stappen zoals LifeLogic
    
    // toegevoegd voor de betaling/reserverring
    int pay = 2;
    int payReserved = 1;
    boolean carsPaying;
    int total;
    int totalReserved=0;
    int totalAdhoc=0;
    
    boolean passSpot;
    static String reserveTime;
    static int reserveLocation;
    
    
    public SimulatorModel() {
        
		run=false;
		
    }
    
    // deze wordt uitgevoerd door de init controller
    public void randomInit() {
		entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        simulatorView = new SimulatorView(3, 6, 30);
		
	}
    
    // De onderstaande methodes zetten het aantal arrivals per auto op vershillende tijden.
    public int setWeekDayArrivals() {
    	if (day == 3 && hour >19 && hour < 22){
    		weekDayArrivals = 120;
    	}
    	else if(hour > 21 || hour < 6) {
    		weekDayArrivals = 30;
    	}
    	else {
    		weekDayArrivals = 100;		
    }
    return weekDayArrivals;

    }
    
    public int setWeekDayPassArrivals() {
    	if (day == 3 && hour >19 && hour < 22){
    		weekDayPassArrivals = 60;
    	}
    	else if(hour > 21 || hour < 6) {
    		weekDayPassArrivals = 10;
    	}
    	else {
    		weekDayPassArrivals = 50;		
    }
    return weekDayPassArrivals;

    }
    
    public int setWeekDayReservedArrivals() {
    	if (day == 3 && hour >19 && hour < 22){
    		weekDayReservedArrivals = 30;
    	
    	}
    	else if(hour > 21 || hour < 6) {
    		weekDayReservedArrivals = 4;
    	}
    	else {
    		weekDayReservedArrivals = 25;		
    }
    return weekDayReservedArrivals;

    }
    
    public int setWeekendArrivals() {
    	if(day == 5 && hour > 19 && hour < 23) {
    		weekendArrivals = 200;
    	}
    	else {
    		weekendArrivals = 150;		
    }
    return weekendArrivals;

    }
    
    public int setWeekendPassArrivals() {
    	if(day == 5 && hour > 19 && hour < 23) {
    		weekendPassArrivals = 10;
    	}
    	else {
    		weekendPassArrivals = 5;		
    }
    return weekendPassArrivals;

    }
    
    public int setWeekendReservedArrivals() {
    	if(day == 5 && hour > 19 && hour < 23) {
    		weekendReservedArrivals = 300;
    	}
    	else {
    		weekendReservedArrivals = 250;		
    }
    return weekendReservedArrivals;

    }


   

   // Voor uitvoeren in stappen zoals LifeLogic
    public void doTick() throws LifeException {
		tick();
	}
	
    // geeft het aantal stappen dat de simulatie moet uitvoeren door aan run()
	public void doTicks(int numOfTicks) throws LifeException {
		this.numOfTicks=numOfTicks;
		run=true;
		new Thread(this).start();
		
	}
	// stopt de simulatie
	public void stopTicks() {
		run=false;
	}
	
	// voert de simulatie uit in het aantaal doorgegeven stappen
	@Override
	public void run() {
		for(int i=0;i<numOfTicks && run;i++) {
			tick();
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		run=false;
	}
	
    private void tick() {
		notifyViews();
    	advanceTime();
    	handleExit();
    	updateViews();
    	// Pause.
        try {
            Thread.sleep(tickPause);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    	handleEntrance();
    }

    private void advanceTime(){
        // Advance the time by one minute.
        minute++;
        while (minute > 59) {
            minute -= 60;
            hour++;
        }
        while (hour > 23) {
            hour -= 24;
            day++;
        }
        while (day > 6) {
            day -= 7;
        }

    }

    private void handleEntrance(){
    	carsArriving();
    	carsEntering(entrancePassQueue);
    	carsEntering(entranceCarQueue);
    }
    
    private void handleExit(){
        carsReadyToLeave();
        carsPaying();
        carsLeaving();
    }
    
    private void updateViews(){
    	simulatorView.tick();
        // Update the car park view.
        simulatorView.updateView();	
    }
    
    // geupdated om ervoor te zorgen dat de RESERVED arrivals er ook in konden worden verwerkt
    private void carsArriving(){
    	int numberOfCars=getNumberOfCars(setWeekDayArrivals(), setWeekendArrivals());
        addArrivingCars(numberOfCars, AD_HOC);    	
    	numberOfCars=getNumberOfCars(setWeekDayPassArrivals(), setWeekendPassArrivals());
        addArrivingCars(numberOfCars, PASS);
        numberOfCars=getNumberOfCars(setWeekDayReservedArrivals(), setWeekendReservedArrivals());
        addArrivingCars(numberOfCars, RESERVED);  
    }

    private void carsEntering(CarQueue queue){
        int i=0;
        // Remove car from the front of the queue and assign to a parking space.
    	while (queue.carsInQueue()>0 && 
    			i<enterSpeed) {
            Car car = queue.removeCar();
            if(car.hasToPay == false) {
    			totalPassCarsStat++;
    		}
    		else if(car.reserveert == true) {
    			totalReservedCarsStat++;
    		}
    		else {
    			totalAdHocCarsStat++;
    		}
            if(queue == entranceCarQueue && simulatorView.getNumberOfOpenSpots()>0) {
            	Location freeLocation = simulatorView.getFirstFreeLocation();
            	simulatorView.setCarAt(freeLocation, car);
            	simulatorView.numberOfOpenSpots--;
            	i++;
            }
            else{ // toegevoegd om abonnement auto's naar hun eigen vrije plekken te rijden
            	if(simulatorView.getNumberOfOpenPassSpots()>0 && (car.getHasToPay() == false && car.getPassSpot() == true)) {
            		Location freeLocation = simulatorView.getFirstFreeLocationPass();
            		simulatorView.setCarAt(freeLocation, car);
            		simulatorView.numberOfOpenPassSpots--;
            		i++;
            	}
            	else {
            		// als de abonnement plekken vol zijn worden dan komen de abonnement auto's in de normale plekken te staan
            		if(simulatorView.getNumberOfOpenSpots()>0) {
            			Location freeLocation = simulatorView.getFirstFreeLocation();
            			simulatorView.setCarAt(freeLocation, car);
            			simulatorView.numberOfOpenSpots--;
            			car.passSpot = false;
            			i++;
            		}
            	}
            }
        }
    }
    
    private void carsReadyToLeave(){
        // Add leaving cars to the payment queue.
        Car car = simulatorView.getFirstLeavingCar();
        while (car!=null) {
        	if (car.getHasToPay()){
	            car.setIsPaying(true);
	            paymentCarQueue.addCar(car);
	            //onderscheid tussen Adhoc en gereserveerd
	            if (car.getReserveert()) {
	            totalReservedCars--;
	            totalReservedCarsStat--;
        		}
	            else {
	            totalAdHocCars--;
	            totalAdHocCarsStat--;
	            }

        	}
        	else {
        		carLeavesSpot(car);
        		totalPassCars--;
        		totalPassCarsStat--;
        	}
            car = simulatorView.getFirstLeavingCar();
        }
    }

    private void carsPaying(){
        // Let cars pay.
    	carsPaying = true;
    	int i=0;
    	while (paymentCarQueue.carsInQueue()>0 && i < paymentSpeed){
            Car car = paymentCarQueue.removeCar();
            if(car.getReserveert()) {
            omzetReservedCar = omzetReservedCar + car.getMoetBetalen();	
            }
            else {
            omzetCar = omzetCar + car.getMoetBetalen();
            }
            carLeavesSpot(car);
            i++;
    	}
    }
    
    private void carsLeaving(){
        // Let cars leave.
    	int i=0;
    	while (exitCarQueue.carsInQueue()>0 && i < exitSpeed){
            exitCarQueue.removeCar();
            i++;
            
    	}	
    }
    
    private int getNumberOfCars(int weekDay, int weekend){
        Random random = new Random();

        // Get the average number of cars that arrive per hour.
        int averageNumberOfCarsPerHour = day < 5
                ? weekDay
                : weekend;

        // Calculate the number of cars that arrive this minute.
        double standardDeviation = averageNumberOfCarsPerHour * 0.3;
        double numberOfCarsPerHour = averageNumberOfCarsPerHour + random.nextGaussian() * standardDeviation;
        return (int)Math.round(numberOfCarsPerHour / 60);	
    }
    
    private void addArrivingCars(int numberOfCars, String type){
        // Add the cars to the back of the queue.
    	switch(type) {
    	case AD_HOC: 
            for (int i = 0; i < numberOfCars; i++) {
            	if(entranceCarQueue.carsInQueue() > 8 ) {
            		lostAdHocCar++;
            		// toegevoegt om de gemiste omzet te berekenen
            		Random random = new Random();
            		this.timeLostCar = (int) ((15 + random.nextFloat() * 3 * 60));
            		lostProfit = lostProfit + (this.timeLostCar) * 0.05;
            	}
            	else {
            	entranceCarQueue.addCar(new AdHocCar());
            	totalAdHocCars++;
            	}
            }
            break;
    	case PASS:
            for (int i = 0; i < numberOfCars; i++) {
            	if(entrancePassQueue.carsInQueue() > 8 ) {
            		lostPassCar++;
            	}
            	else {
            	entrancePassQueue.addCar(new ParkingPassCar());
            	totalPassCars++;
            	}
            }
            break;	   
    	case RESERVED:
            for (int i = 0; i < numberOfCars; i++) {
            	if(entrancePassQueue.carsInQueue() > 8 ) {
            		lostReservedCar++;
            		// toegevoegt om de gemiste omzet te berekenen
            		Random random = new Random();
            		this.timeLostCar = (int) ((15 + random.nextFloat() * 3 * 60));
            		lostProfit = lostProfit + (this.timeLostCar) * 0.06;
            	}
            	else {
            
            	entrancePassQueue.addCar(new ReservedCar());
            	totalReservedCars++;
            	}
            }
            break;	
    	}
    }
    
    private void carLeavesSpot(Car car){
    	simulatorView.removeCarAt(car.getLocation());
        exitCarQueue.addCar(car);
    }

    // onderste drie zijn om een specefiek aantal auto's te krijgen om die te verwerken in de GUI
	public int getAantalAdHocCars() {
		return totalAdHocCarsStat;
	}
	
	public int getAantalPassCars() {
		return totalPassCarsStat;
	}
	
	public int getAantalReservedCars() {
		return totalReservedCarsStat;
	}
	
	// De volgende 18 methodes worden allemaal gebruikt om de getallen in de GUI te krijgen
    public int getNumberOfOpenSpots(){
        return simulatorView.numberOfOpenSpots;
    }

    public int getNumberOfOpenPassSpots(){
        return simulatorView.numberOfOpenPassSpots;
    }

    public int getTotalNumberOfOpenSpots(){
        return simulatorView.numberOfOpenSpots+simulatorView.numberOfOpenPassSpots;
    }

    public int getTotalNumberOfCarsQueue(){
        return simulatorView.entranceCarQueue;
    }
    
    public int getTotalNumberOfPassCarsQueue(){
        return simulatorView.entrancePassQueue;
    }
    
    public int getTotalNumberOfCarsLeft(){
        return simulatorView.lostAdHocCar;
    }
    
    public int getTotalNumberOfPassCarsLeft(){
        return simulatorView.lostPassCar;
    }

    public int getDay() {
    	return day;
    }
    
    public int getMinute() {
    	return minute;
    }
    public int getHour() {
    	return hour;
    }
    
    public double getOmzetCar() {
    	return omzetCar;
    } 
    
    public double getLostProfit() {
    	return lostProfit;
    }
    
    public double getOmzetReservedCar() {
		return omzetReservedCar;
	}
    
    public int autoDoorgeredenPass() {
    	return lostPassCar;
    }
    
    public int autoDoorgeredenReserved() {
    	return lostReservedCar;
    }
    
    public int autoDoorgereden() {
    	return lostAdHocCar;
    }
    
    public int getTotalEntranceCarQueue() {
    	return entranceCarQueue.carsInQueue();
    }
    
    public int getTotalEntranceCarQueuePass() {
    	return entrancePassQueue.carsInQueue();
    }
    
    // de onderste 2 worden gebruikt om de de dag en uur in te stellen via InitController

	public void setDay(int day2) {
		day = day2;
		
	}
	
	public void setHour(int hour2) {
		hour = hour2;
		
	}
}

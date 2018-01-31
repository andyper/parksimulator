package nl.hanze.t12.life.logic;

import java.util.Random;

import jdk.nashorn.internal.codegen.types.Type;
import nl.hanze.t12.life.exception.LifeException;
import nl.hanze.t12.life.view.SimulatorView;

public class SimulatorModel extends AbstractModel implements Runnable {

	private static final String AD_HOC = "1";
	private static final String PASS = "2";
	private static final String RESERVED = "3";

	private CarQueue entranceCarQueue;
    private CarQueue entrancePassQueue;
    private CarQueue paymentCarQueue;
    private CarQueue exitCarQueue;
    private SimulatorView simulatorView;
    public int totalAdHocCars;
    public int totalPassCars;
    public int lostPassCar;
    public int lostAdHocCar;
    public int totalReservedCars;
    
    private int day = 0;
    private int hour = 0;
    private int minute = 0;

    private int tickPause = 100;

    int weekDayArrivals= 100; // average number of arriving cars per hour
    int weekendArrivals = 200; // average number of arriving cars per hour
    int weekDayPassArrivals= 50; // average number of arriving cars per hour
    int weekendPassArrivals = 5; // average number of arriving cars per hour
    int weekDayReservedArrivals= 25; // average number of arriving cars per hour
    int weekendReservedArrivals = 300; // average number of arriving cars per hour

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

    public SimulatorModel() {
        entranceCarQueue = new CarQueue();
        entrancePassQueue = new CarQueue();
        paymentCarQueue = new CarQueue();
        exitCarQueue = new CarQueue();
        simulatorView = new SimulatorView(3, 6, 30);
        //Voor uitvoeren in stappen zoals LifeLogic
        
		run=false;
		
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

    private void handleEntrance(){// aangepast om rekening te houden voor als de queue te lang is.
    	carsArriving();
    	if(entrancePassQueue.carsInQueue() > 5) {
    		lostPassCar++;
    	}
    	else {
    	carsEntering(entrancePassQueue);
    	}
    	if(entranceCarQueue.carsInQueue() > 5) {
    		lostAdHocCar++;
    	}
    	else {
    	carsEntering(entranceCarQueue);
    	}
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
    
    private void carsArriving(){
    	int numberOfCars=getNumberOfCars(weekDayArrivals, weekendArrivals);
        addArrivingCars(numberOfCars, AD_HOC);    	
    	numberOfCars=getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
        addArrivingCars(numberOfCars, PASS);
        numberOfCars=getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
        addArrivingCars(numberOfCars, RESERVED);  
    }

    private void carsEntering(CarQueue queue){
        int i=0;
        // Remove car from the front of the queue and assign to a parking space.
    	while (queue.carsInQueue()>0 && 
    			i<enterSpeed) {
            Car car = queue.removeCar();
            if(queue == entranceCarQueue && simulatorView.getNumberOfOpenSpots()>0 ) {
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
	            if (car.equals(AD_HOC)) {
	            totalAdHocCars--;
        		}
	            else {
	            totalReservedCars--;
	            }

        	}
        	else {
        		carLeavesSpot(car);
        		totalPassCars--;
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
            // TODO Handle payment.
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
            	entranceCarQueue.addCar(new AdHocCar());
            	totalAdHocCars++;
            }
            break;
    	case PASS:
            for (int i = 0; i < numberOfCars; i++) {
            	entrancePassQueue.addCar(new ParkingPassCar());
            	totalPassCars++;
            }
            break;	   
    	case RESERVED:
            for (int i = 0; i < numberOfCars; i++) {
            	entrancePassQueue.addCar(new ReservedCar());
            	totalPassCars++;
            }
            break;	
    	}
    }
    
    private void carLeavesSpot(Car car){
    	simulatorView.removeCarAt(car.getLocation());
        exitCarQueue.addCar(car);
    }

	public int getAantalCars() {
		
		return (int) ((totalPassCars+totalAdHocCars)/1.5);
	}
	
	public int getAantalAdHocCars() {
		
		return totalAdHocCars;
	}
	
	//voor de statistieken
    public int getNumberOfOpenSpots(){
        return simulatorView.numberOfOpenSpots;
    }

    public int getNumberOfOpenPassSpots(){
        return simulatorView.numberOfOpenPassSpots;
    }

    public int getTotalNumberOfOpenSpots(){
        return simulatorView.numberOfOpenSpots+simulatorView.numberOfOpenPassSpots;
    }
    
    
    // auto's laten betalen (alleen Adhoc en gereserveerd)
    private void carsPayment(int stayMinutes, int pay, int reservedPay, String type) {
    	int total;
    	if (carsPaying == true) {
    		switch (type) {
    		case AD_HOC:
    			int totalAdhoc= stayMinutes * pay;
    			break;
    		case RESERVED:
    			int totalReserved = stayMinutes * (pay + payReserved);
    			break;
    		}
    		
    	}
    }
    
    public int omzet() {
    		return (int) (totalReserved + totalAdhoc);
    	}  
    
    /*public static String getReservedTime() {
    	String time();
    	Random random = new Random();
    	
    	int dayLow = 8;
    	int dayHigh = 1;
    	int hourLow = 0;
    	int hourHigh = 24;
    	int minuteLow = 0;
    	int minuteHigh = 60;
    	int day = random.nextInt(dayLow - dayHigh) +1;
    	int hour = random.nextInt(hourLow - hourHigh) +1;
    	int minute = random.nextInt(minuteLow - minuteHigh) +1;
    	
    	time = 
    }*/
}


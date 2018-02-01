package nl.hanze.t12.life.logic;

import java.util.Random;

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

    int weekDayArrivals= 200; // average number of arriving cars per hour
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
    static String reserveTime;
    static int reserveLocation;

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
    
    private void carsArriving(){
    	int numberOfCars=getNumberOfCars(weekDayArrivals, weekendArrivals);
        addArrivingCars(numberOfCars, AD_HOC);    	
    	numberOfCars=getNumberOfCars(weekDayPassArrivals, weekendPassArrivals);
        addArrivingCars(numberOfCars, PASS);
        numberOfCars=getNumberOfCars(weekDayReservedArrivals, weekendReservedArrivals);
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

	public int getAantalAdHocCars() {
		
		return totalAdHocCarsStat;
	}
	
	public int getAantalPassCars() {
		return totalPassCarsStat;
	}
	
	public int getAantalReservedCars() {
		return totalReservedCarsStat;
	}
	/* DIT IS NIEUW
	public int getAantalResCars() {
		return totalResCars;
	}
	*/
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
    
}

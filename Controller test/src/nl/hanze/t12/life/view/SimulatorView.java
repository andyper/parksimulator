package nl.hanze.t12.life.view;

import javax.swing.*;

import nl.hanze.t12.life.logic.*;
import java.awt.*;

public class SimulatorView extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CarParkView carParkView;
    private int numberOfFloors;
    private int numberOfRows;
    private int numberOfPlaces;
    public int numberOfOpenSpots;
    public int numberOfOpenPassSpots;
    public int entranceCarQueue;
    public int entrancePassQueue;
    public int lostAdHocCar;
    public int lostPassCar;
    private Car[][][] cars;
    public int abonnementPlekken;

    public SimulatorView(int numberOfFloors, int numberOfRows, int numberOfPlaces) {
    	abonnementPlekken = 6;//Dit getal is voor aantal abonnement plekken per rij. Je kan het dus aanpassen tussen 1 -30;
        this.numberOfFloors = numberOfFloors;
        this.numberOfRows = numberOfRows;
        this.numberOfPlaces = numberOfPlaces;
        this.numberOfOpenSpots =numberOfFloors*numberOfRows*(numberOfPlaces-abonnementPlekken);
        this.numberOfOpenPassSpots =numberOfFloors*numberOfRows*abonnementPlekken;
        cars = new Car[numberOfFloors][numberOfRows][numberOfPlaces];
        
        
        carParkView = new CarParkView();

        Container contentPane = getContentPane();
        contentPane.add(carParkView, BorderLayout.CENTER);
        pack();
        setVisible(true);
        

        updateView();
        
    }
    public void updateView() {
        carParkView.updateView();
    }
    
	public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public int getNumberOfRows() {
        return numberOfRows;
    }

    public int getNumberOfPlaces() {
        return numberOfPlaces;
    }

    // geeft door hoeveel plekken er vrij zijn in de parkeergarage.
    public int getNumberOfOpenSpots(){
    	return numberOfOpenSpots;
    }

    // geeft door hoeveel abonnement plekken er vrij zijn in de parkeergarage.
    public int getNumberOfOpenPassSpots(){
    	return numberOfOpenPassSpots;
    }
    
    // geeft de entranceCarQueue door
    public int getNumberOfEntranceCarQueue() {
    	return entranceCarQueue;
    }
    
    public Car getCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        return cars[location.getFloor()][location.getRow()][location.getPlace()];
    }

    public boolean setCarAt(Location location, Car car) {
        if (!locationIsValid(location)) {
            return false;
        }
        Car oldCar = getCarAt(location);
        if (oldCar == null) {
            cars[location.getFloor()][location.getRow()][location.getPlace()] = car;
            car.setLocation(location);
            return true;
        }
        return false;
    }

    public Car removeCarAt(Location location) {
        if (!locationIsValid(location)) {
            return null;
        }
        Car car = getCarAt(location);
        if (car == null) {
            return null;
        }
        if(car.getPassSpot() == true){
			numberOfOpenPassSpots++;
		}
		else {
			numberOfOpenSpots++;
		}
        cars[location.getFloor()][location.getRow()][location.getPlace()] = null;
        
        
        car.setLocation(null);
        return car;
    }

    // geeft de eerste vrije loctie voor de normale plekken door
    public Location getFirstFreeLocation() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = abonnementPlekken; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    if (getCarAt(location) == null) {
                        return location;
                    }
                }
            }
        }
        return null;
    }
    
    // geeft de eerste vrije loctie voor de abonnement plekken door
    public Location getFirstFreeLocationPass() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < abonnementPlekken; place++) {
                    Location location = new Location(floor, row, place);
                    if (getCarAt(location) == null) {
                        return location;
                    }
                }
            }
        }
        return null;
    }
    
    // geeft de eerste vrije loctie voor de normale plekken door voor reserveer auto's
    public Location getReservedLocation() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                	if (place != abonnementPlekken) {
                    Location location = new Location(floor, row, place);
                    if (getCarAt(location) == null) {
                    return location;
                    	}
                	}
                }
            }
        }
		return null;
    }

    public Car getFirstLeavingCar() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null && car.getMinutesLeft() <= 0 && !car.getIsPaying()) {
                        return car;
                    }
                }
            }
        }
        return null;
    }

    public void tick() {
        for (int floor = 0; floor < getNumberOfFloors(); floor++) {
            for (int row = 0; row < getNumberOfRows(); row++) {
                for (int place = 0; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    if (car != null) {
                        car.tick();
                    }
                }
            }
        }
    }

    private boolean locationIsValid(Location location) {
        int floor = location.getFloor();
        int row = location.getRow();
        int place = location.getPlace();
        if (floor < 0 || floor >= numberOfFloors || row < 0 || row > numberOfRows || place < 0 || place > numberOfPlaces) {
            return false;
        }
        return true;
    }


private class CarParkView extends JPanel {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Dimension size;
    private Image carParkImage;    

    /**
     * Constructor for objects of class CarPark
     */
    public CarParkView() {
        size = new Dimension(0, 0);
    }

    /**
     * Overridden. Tell the GUI manager how big we would like to be.
     */
    public Dimension getPreferredSize() {
        return new Dimension(800, 500);
    }

    /**
     * Overriden. The car park view component needs to be redisplayed. Copy the
     * internal image to screen.
     */
    public void paintComponent(Graphics g) {
        if (carParkImage == null) {
            return;
        }

        Dimension currentSize = getSize();
        if (size.equals(currentSize)) {
            g.drawImage(carParkImage, 0, 0, null);
        }
        else {
            // Rescale the previous image.
            g.drawImage(carParkImage, 0, 0, currentSize.width, currentSize.height, null);
        }
    }

    public void updateView() {
        // Create a new car park image if the size has changed.
        if (!size.equals(getSize())) {
            size = getSize();
            carParkImage = createImage(size.width, size.height);
        }
        Graphics graphics = carParkImage.getGraphics();
        for(int floor = 0; floor < getNumberOfFloors(); floor++) {// eerste blok zorgt voor het aanmaken van abonnementplekken
            for(int row = 0; row < getNumberOfRows(); row++) {
                for(int place = 0; place < abonnementPlekken; place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    Color color = car == null ? Color.gray : car.getColor();
                    drawPlace(graphics, location, color);
                }
            }
        }
        
        for(int floor = 0; floor < getNumberOfFloors(); floor++) {
            for(int row = 0; row < getNumberOfRows(); row++) {
                for(int place = abonnementPlekken; place < getNumberOfPlaces(); place++) {
                    Location location = new Location(floor, row, place);
                    Car car = getCarAt(location);
                    Color color = car == null ? Color.white : car.getColor();
                    drawPlace(graphics, location, color);
                }
            }
        }
        repaint();
    }

    /**
     * Paint a place on this car park view in a given color.
     */
    private void drawPlace(Graphics graphics, Location location, Color color) {
        graphics.setColor(color);
        graphics.fillRect(
                location.getFloor() * 260 + (1 + (int)Math.floor(location.getRow() * 0.5)) * 75 + (location.getRow() % 2) * 20,
                60 + location.getPlace() * 10,
                20 - 1,
                10 - 1); // TODO use dynamic size or constants
    }
  }
}

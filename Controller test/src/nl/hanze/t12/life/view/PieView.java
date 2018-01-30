package nl.hanze.t12.life.view;

import java.awt.*;
import nl.hanze.t12.life.logic.*;

public class PieView extends AbstractView {
	private static final long serialVersionUID = 5455934187803194147L;

	public PieView(SimulatorModel simulator) {
		super(simulator);
		setSize(200, 200);
	}

	public void paintComponent(Graphics g) {
		int aantalCars=getModel().getAantalCars();
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, 200, 200);
		g.setColor(Color.BLUE);
		g.fillArc(10, 10, 180, 180, 0, aantalCars);
	}	
}

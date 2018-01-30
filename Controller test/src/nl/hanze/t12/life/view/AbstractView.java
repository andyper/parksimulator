package nl.hanze.t12.life.view;

import javax.swing.*;
import nl.hanze.t12.life.logic.*;

public abstract class AbstractView extends JPanel {
	private static final long serialVersionUID = 6437976554496769048L;
	protected SimulatorModel simulator;

	public AbstractView(SimulatorModel simulator) {
		this.simulator=simulator;
		simulator.addView(this);
	}
	
	public SimulatorModel getModel() {
		return simulator;
	}
	
	public void updateView() {
		repaint();
	}
}

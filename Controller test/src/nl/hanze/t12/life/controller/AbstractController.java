package nl.hanze.t12.life.controller;

import javax.swing.*;
import nl.hanze.t12.life.logic.*;

public abstract class AbstractController extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected SimulatorModel simulator;
	 
	public AbstractController(SimulatorModel simulator) {
		this.simulator=simulator;
	}
}

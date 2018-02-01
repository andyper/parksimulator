package nl.hanze.t12.life.controller;

import java.awt.event.*;
import javax.swing.*;
import nl.hanze.t12.life.exception.*;
import nl.hanze.t12.life.logic.*;

public class ParkeerController extends AbstractController implements ActionListener {
	private static final long serialVersionUID = -8776795932665582315L;
	// maakt elementen aan voor de controller
	private JButton tickOne;
	private JTextField ticks;
	private JButton startTicks;
	private JButton stopTicks;
	
	public ParkeerController(SimulatorModel simulator) {
		super(simulator);
		setSize(450, 50);
		
		// geedt de elementen waarden
		tickOne=new JButton("One step");
		tickOne.addActionListener(this);
		ticks=new JTextField();
		startTicks=new JButton("Start");
		startTicks.addActionListener(this);
		stopTicks=new JButton("Stop");
		stopTicks.addActionListener(this);
		
		// voegt de elementen toe
		this.setLayout(null);
		add(tickOne);
		add(ticks);
		add(startTicks);
		add(stopTicks);
		tickOne.setBounds(50, 10, 70, 30);
		ticks.setBounds(140, 10, 70, 30);
		startTicks.setBounds(229, 10, 70, 30);
		stopTicks.setBounds(319, 10, 70, 30);

		setVisible(true);
	}

	@Override
	// voert actie van knop uit
	public void actionPerformed(ActionEvent e) {
		if (e.getSource()==tickOne) {
			try {
				simulator.doTick();
			} catch (LifeException ex) {
				ex.printStackTrace();
			}
			return;
		}
		
		if (e.getSource()==startTicks) {
			try {
				int steps=parseSteps();				
				if (steps<1 || steps>1000) throw new LifeException("Illegal number of steps");
				simulator.doTicks(steps);
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			return;
		}
		
		if (e.getSource()==stopTicks) {
			simulator.stopTicks();
		}
		
	}
	
	private int parseSteps() throws NumberFormatException {
		return Integer.parseInt(ticks.getText());
	}
}

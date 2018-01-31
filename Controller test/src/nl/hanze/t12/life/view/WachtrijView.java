package nl.hanze.t12.life.view;

import javax.swing.*;
import java.awt.*;
import nl.hanze.t12.life.logic.*;

public class WachtrijView extends AbstractView{
	
	private JLabel aantalAutosQueue;
	private JLabel aantalPassAutosQueue;
	private JLabel aantalAutosLeft;
	private JLabel aantalPassAutosLeft;
	
	
	public WachtrijView(SimulatorModel simulator) {
		super(simulator);
		Dimension size = new Dimension(200,200);
		
		this.aantalAutosQueue = new JLabel("Auto's in de rij: ");
		this.aantalPassAutosQueue = new JLabel("Auto's in de pass rij: ");
		this.aantalAutosLeft = new JLabel("Auto's doorgereden: ");
		this.aantalPassAutosLeft = new JLabel("Pass rij auto's doorgereden: ");
		
		aantalAutosQueue.setBounds(0, 50, 200, 20);
		aantalPassAutosQueue.setBounds(0, 50, 200, 20);
		aantalAutosLeft.setBounds(0, 50, 200, 20);
		aantalPassAutosLeft.setBounds(0, 50, 200, 20);
		
		add(aantalAutosQueue);
		add(aantalPassAutosQueue);
		add(aantalAutosLeft);
		add(aantalPassAutosLeft);
	}
	
	public void updateView() {
		SimulatorModel simulator = (SimulatorModel) super.simulator;
		
		aantalAutosQueue.setText(("Auto's in de rij: ") + simulator.getTotalNumberOfCarsQueue());
		aantalPassAutosQueue.setText(("Auto's in de pass rij: ") + simulator.getTotalNumberOfPassCarsQueue());
		aantalAutosLeft.setText(("Auto's doorgereden: ") + simulator.autoDoorgereden());
		aantalPassAutosLeft.setText(("Pass rij auto's doorgereden: ") + simulator.autoDoorgeredenPass());
	}
}
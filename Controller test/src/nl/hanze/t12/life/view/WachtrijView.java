package nl.hanze.t12.life.view;

import javax.swing.*;
import java.awt.*;
import nl.hanze.t12.life.logic.*;

public class WachtrijView extends AbstractView{
	
	// Label wordt aangemaakt
	private JLabel aantalAutosQueue;
	private JLabel aantalPassAutosQueue;
	private JLabel aantalAutosLeft;
	private JLabel aantalPassAutosLeft;
	private JLabel aantalReserveerAutosLeft;
	
	
	public WachtrijView(SimulatorModel simulator) {
		super(simulator);
		Dimension size = new Dimension(200,200);
		
		// Label krijgt waarde
		this.aantalAutosQueue = new JLabel("Auto's in de rij: ");
		this.aantalPassAutosQueue = new JLabel("Auto's in de pass rij: ");
		this.aantalAutosLeft = new JLabel("Auto's doorgereden: ");
		this.aantalPassAutosLeft = new JLabel("Pass houders doorgereden: ");
		this.aantalReserveerAutosLeft = new JLabel("Reserveerders doorgereden: ");
		
		// Label krijgt locatie
		aantalAutosQueue.setBounds(0, 25, 200, 20);
		aantalPassAutosQueue.setBounds(0, 50, 200, 20);
		aantalAutosLeft.setBounds(0, 75, 200, 20);
		aantalPassAutosLeft.setBounds(0, 100, 200, 20);
		aantalReserveerAutosLeft.setBounds(0, 125, 200, 20);
		
		// Label wordt toegevoegd
		add(aantalAutosQueue);
		add(aantalPassAutosQueue);
		add(aantalAutosLeft);
		add(aantalPassAutosLeft);
		add(aantalReserveerAutosLeft);
	}
	
	public void updateView() {
		SimulatorModel simulator = (SimulatorModel) super.simulator;
		
		// Label wordt geupdated
		aantalAutosQueue.setText(("Auto's in de rij: ") + simulator.getTotalEntranceCarQueue());
		aantalPassAutosQueue.setText(("Auto's in de pass rij: ") + simulator.getTotalEntranceCarQueuePass());
		aantalAutosLeft.setText(("Auto's doorgereden: ") + simulator.autoDoorgereden());
		aantalPassAutosLeft.setText(("Pass houders doorgereden: ") + simulator.autoDoorgeredenPass());
		aantalReserveerAutosLeft.setText(("Reserveerders doorgereden: ") + simulator.autoDoorgeredenReserved());
	}
}
package nl.hanze.t12.life.view;

import javax.swing.*;
import java.awt.*;
import nl.hanze.t12.life.logic.*;

public class StatView extends AbstractView{
	
	private JLabel aantalPlekkenVrijNormaal;
	private JLabel aantalPlekkenVrijPass;
	private JLabel aantalPlekkenVrijTotaal;	
	
	public StatView(SimulatorModel simulator) {
		super(simulator);
		Dimension size = new Dimension(200,200);
		
		this.aantalPlekkenVrijNormaal = new JLabel("plekken vrij: ");
		this.aantalPlekkenVrijPass = new JLabel("plekken vrij pashouders: ");
		this.aantalPlekkenVrijTotaal = new JLabel("plekken vrij totaal: ");
		
		aantalPlekkenVrijNormaal.setBounds(0, 0, 200, 20);
		aantalPlekkenVrijPass.setBounds(0, 25, 200, 20);
		aantalPlekkenVrijTotaal.setBounds(0, 50, 200, 20);

		
		add(aantalPlekkenVrijNormaal);
		add(aantalPlekkenVrijPass);
		add(aantalPlekkenVrijTotaal);
	}
	
	public void updateView() {
		SimulatorModel simulator = (SimulatorModel) super.simulator;
		
		aantalPlekkenVrijNormaal.setText(("Vrije plekken normaal: ") + simulator.getNumberOfOpenSpots());
		aantalPlekkenVrijPass.setText(("Vrije plekken pashouders: ") + simulator.getNumberOfOpenPassSpots());
		aantalPlekkenVrijTotaal.setText(("Totaal plekken vrij: ") + simulator.getTotalNumberOfOpenSpots());

		
		
		
	}
}

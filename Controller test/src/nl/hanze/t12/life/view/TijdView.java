package nl.hanze.t12.life.view;

import javax.swing.*;

import com.sun.org.apache.xml.internal.utils.StringVector;

import java.awt.*;
import nl.hanze.t12.life.logic.*;
public class TijdView extends AbstractView{
	
	// Label wordt aangemaakt
	private JLabel TijdDag;
	private JLabel TijdUur;
	private JLabel TijdMinuut;
	
	public TijdView(SimulatorModel simulator) {
		super(simulator);
		
		// Label krijgt waarde
		this.TijdDag = new JLabel("Maandag");
		this.TijdUur = new JLabel("");
		this.TijdMinuut = new JLabel("");
		
		// Label krijgt locatie
		TijdDag.setBounds(0, 0, 200, 30);
		TijdUur.setBounds(0, 10, 200, 30);
		TijdMinuut.setBounds(0, 20, 200, 30);
		
		// Label wordt toegevoegd
		add(TijdDag);
		add(TijdUur);
		add(TijdMinuut);
		
		
	}
	
	public void updateView() {
		SimulatorModel simulator = (SimulatorModel) super.simulator;

		// Label wordt geupdated
		switch (simulator.getDay()) {
		case 0:
			TijdDag.setText("Maandag");
			break;
		case 1:
			TijdDag.setText("Dinsdag");
			break;
		case 2:
			TijdDag.setText("Woensdag");
			break;
		case 3:
			TijdDag.setText("Donderdag");
			break;
		case 4:
			TijdDag.setText("Vrijdag");
			break;
		case 5:
			TijdDag.setText("Zaterdag");
			break;
		case 6:
			TijdDag.setText("Zondag");
			break;

		default:
			break;
		}
		
		
		//Tijd display in de vorm 00:00
		if (simulator.getHour() < 10){
			TijdUur.setText(("0")+ String.valueOf(simulator.getHour()) +  (" :"));

		}
		else {
			TijdUur.setText(String.valueOf(simulator.getHour()) +  (" :"));

		}
		
		if (simulator.getMinute() < 10) {
			TijdMinuut.setText(("0") + String.valueOf(simulator.getMinute()));

		}
		else {
			TijdMinuut.setText(String.valueOf(simulator.getMinute()));

		}
		
		
	}
}

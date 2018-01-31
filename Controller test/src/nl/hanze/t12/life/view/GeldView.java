package nl.hanze.t12.life.view;

import javax.swing.*;
import javax.swing.JComponent;
import java.applet.*;

//import com.sun.prism.paint.Color;

import java.awt.*;
import nl.hanze.t12.life.logic.*;


public class GeldView extends AbstractView{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel totaleOpbrengstCar;
	private JLabel totaleOpbrengstReservedCar;
	private JLabel totaleWinst;
	private JLabel misgelopenWinst;
	
	
	public GeldView(SimulatorModel simulator) {
		super(simulator);
		
		this.totaleOpbrengstCar = new JLabel("Opbrengst normale auto's");
		this.totaleOpbrengstReservedCar = new JLabel("Opbrengst reservaties");
		this.totaleWinst = new JLabel("Totale Winst");
		this.misgelopenWinst = new JLabel("Misgelopen geld");
		
		totaleOpbrengstCar.setBounds(0, 0, 200, 20);
		totaleOpbrengstReservedCar.setBounds(0, 25, 200, 20);
		totaleWinst.setBounds(0, 50, 200, 20);
		misgelopenWinst.setBounds(0, 75, 200, 20);
		
		add(totaleOpbrengstCar);
		add(totaleOpbrengstReservedCar);
		add(totaleWinst);
		add(misgelopenWinst);
		
	}

	
	public void updateView() {
		SimulatorModel simulator = (SimulatorModel) super.simulator;
		
		totaleOpbrengstCar.setText(("Opbrengst normale auto's: ") + (String.format("%.2f", simulator.getOmzetCar())));
		totaleOpbrengstReservedCar.setText(("Opbrengst reservaties: ") + (String.format("%.2f", simulator.getOmzetReservedCar())));
		totaleWinst.setText(("Totale winst: ") + (String.format("%.2f", simulator.getOmzetReservedCar() + simulator.getOmzetCar())));
	}
}

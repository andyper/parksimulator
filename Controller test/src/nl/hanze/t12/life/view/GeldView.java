package nl.hanze.t12.life.view;

import javax.swing.*;
import javax.swing.JComponent;
import java.applet.*;

//import com.sun.prism.paint.Color;

import java.awt.*;
import nl.hanze.t12.life.logic.*;


public class GeldView extends AbstractView{

	private JLabel totaleOpbrengst;
	private JLabel totaleWinst;
	private JLabel misgelopenWinst;
	
	
	public GeldView(SimulatorModel simulator) {
		super(simulator);
		Dimension size = new Dimension(200,200);
		
		this.totaleOpbrengst = new JLabel("Totale opbrengst");
		this.totaleWinst = new JLabel("Totale Winst");
		this.misgelopenWinst = new JLabel("Misgelopen geld");
		
		totaleOpbrengst.setBounds(0, 0, 200, 20);
		totaleWinst.setBounds(0, 25, 200, 20);
		misgelopenWinst.setBounds(0, 50, 200, 20);
		
		add(totaleOpbrengst);
		add(totaleWinst);
		add(misgelopenWinst);
		
	}

	
	public void updateView() {
		SimulatorModel simulator = (SimulatorModel) super.simulator;
		
		totaleOpbrengst.setText(("Totale opbrengst:") + (AdHocCar.krijgOpbrengst()));
	}
}

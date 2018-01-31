package nl.hanze.t12.life.view;

import javax.swing.*;
import javax.swing.JComponent;
import java.applet.*;

//import com.sun.prism.paint.Color;

import java.awt.*;
import nl.hanze.t12.life.logic.*;

public class PieStatView extends AbstractView{
	private JLabel aantalNormaleBezet;
	private JLabel aantalPassBezet;
	private JLabel aantalReservedBezet;
	
	public PieStatView(SimulatorModel simulator) {
		super(simulator);
		
		this.aantalNormaleBezet = new JLabel("0 normale plekken bezet");
		this.aantalPassBezet = new JLabel("0 pas plekken bezet");
		this.aantalReservedBezet = new JLabel("0 gereserveerde plaatsen bezet");
		//TODO aantal gereserveerde plakken. Dit moet naast  het aantal ingenomen gereserveerde plakken (aantalResBezet)
		
		aantalNormaleBezet.setBounds(0, 0, 200, 20);
		aantalPassBezet.setBounds(0,  60,  200,  20);
		aantalReservedBezet.setBounds(0, 120, 200, 20);
		
		aantalNormaleBezet.setForeground(Color.RED);
		aantalPassBezet.setForeground(Color.BLUE);
		aantalReservedBezet.setForeground(Color.GREEN);
		
		add(aantalNormaleBezet);
		add(aantalPassBezet);
		add(aantalReservedBezet);
		
		
	}
	
		public void updateView() {
			SimulatorModel simulator = (SimulatorModel) super.simulator;
			
			aantalNormaleBezet.setText(String.valueOf(simulator.getAantalAdHocCars() + (" normale auto's")));
			aantalPassBezet.setText(String.valueOf(simulator.getAantalPassCars() + (" abonnement auto's")));
			aantalReservedBezet.setText(String.valueOf(simulator.getAantalReservedCars()) + (" gereserveerde auto's"));
	
	}
}

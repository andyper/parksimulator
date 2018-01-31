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
	private JLabel aantalResBezet;
	
	public PieStatView(SimulatorModel simulator) {
		super(simulator);
		Dimension size = new Dimension(200,200);
		
		this.aantalNormaleBezet = new JLabel("0 normale plekken bezet");
		this.aantalPassBezet = new JLabel("0 pas plekken bezet");
		this.aantalResBezet = new JLabel("0 gereserveerde plaatsen bezet");
		
		
		aantalNormaleBezet.setBounds(0, 0, 200, 20);
		aantalPassBezet.setBounds(0,  60,  200,  20);
		aantalResBezet.setBounds(0, 120, 200, 20);
		
		aantalNormaleBezet.setForeground(Color.BLUE);
		aantalPassBezet.setForeground(Color.RED);
		aantalResBezet.setForeground(Color.GREEN);
		
		add(aantalNormaleBezet);
		add(aantalPassBezet);
		add(aantalResBezet);
		
		
	}
	
		public void updateView() {
			SimulatorModel simulator = (SimulatorModel) super.simulator;
			
			aantalNormaleBezet.setText(String.valueOf(simulator.getAantalCars() + (" normale plekken bezet")));
			aantalPassBezet.setText(String.valueOf(simulator.getAantalPassCars() + (" passhouder plekken bezet")));
			//aantalResBezet.setText(String.valueOf(simulator.getAantalResCars()) + (" Gereserveerde plaatsen bezet"));
	
	}
}

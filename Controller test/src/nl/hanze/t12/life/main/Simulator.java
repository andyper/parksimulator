package nl.hanze.t12.life.main;

import javax.swing.*;

import nl.hanze.t12.life.controller.*;
import nl.hanze.t12.life.logic.*;
import nl.hanze.t12.life.view.AbstractView;
import nl.hanze.t12.life.view.*;
public class Simulator extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private JFrame screen;
	private AbstractView statView;
	private AbstractView tijdView;
	private AbstractView pieStatView;
	private AbstractView geldView;
	AbstractController parkeerController;
	private AbstractView aantalAutoView;
	private SimulatorModel simulator;
	private AbstractController initController;
	private AbstractView WachtrijView;
	
	public Simulator() {
		simulator=new SimulatorModel();
		JLabel label1 = new JLabel("Aantal auto's in garage");
		JLabel aantalAdHocCars = new JLabel(String.valueOf(simulator.getAantalAdHocCars()));
		JLabel aantalPassCars = new JLabel(String.valueOf(simulator.totalPassCars));
		parkeerController=new ParkeerController(simulator);
		initController=new InitController(simulator);
		aantalAutoView=new PieView(simulator);
		statView = new StatView(simulator);
		tijdView = new TijdView(simulator);
		pieStatView = new PieStatView(simulator);
		geldView = new GeldView(simulator);
		WachtrijView = new WachtrijView(simulator);
		
		screen=new JFrame("ParkeerSimulator");
		screen.setSize(800, 550);
		screen.setResizable(false);
		screen.setLayout(null);
		screen.getContentPane().add(aantalAutoView);
		screen.getContentPane().add(label1);
		screen.getContentPane().add(aantalAdHocCars);
		screen.getContentPane().add(aantalPassCars);
		screen.getContentPane().add(statView);
		screen.getContentPane().add(tijdView);
		screen.getContentPane().add(pieStatView);
		screen.getContentPane().add(geldView);
		screen.getContentPane().add(WachtrijView);
		
		
		label1.setBounds(60, 10, 200, 20);
		screen.getContentPane().add(initController);
		screen.getContentPane().add(parkeerController);
		aantalAutoView.setBounds(30, 30, 200, 200);
		statView.setBounds(240, 30, 200, 200);
		tijdView.setBounds(400, 10, 200, 20);
		pieStatView.setBounds(30, 250, 200, 200);
		geldView.setBounds(250, 260, 200, 200);
		initController.setBounds(700, 360, 90, 150);
		WachtrijView.setBounds(550, 30, 200, 200);
		parkeerController.setBounds(175, 470, 450, 50);
		screen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		screen.setVisible(true);
	}
	
}

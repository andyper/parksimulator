package nl.hanze.t12.life.controller;

import java.awt.event.*;
import javax.swing.*;

import nl.hanze.t12.life.logic.*;

public class InitController extends AbstractController implements ActionListener {
	private static final long serialVersionUID = 8084081366423909672L;
	// maakt elementen aan voor de controller
	private JLabel dag;
	private JTextField day;
	private JLabel uur;
	private JTextField hour;
	private JButton init;
	
	
	public InitController(SimulatorModel simulator) {
		super(simulator);
		setSize(90, 150);
		
		// geedt de elementen waarden
		dag=new JLabel("Voer dag in");
		day=new JTextField();
		uur=new JLabel("Voer uur in");
		hour=new JTextField();
		init=new JButton("Init");
		init.addActionListener(this);
		
		// voegt de elementen toe
		this.setLayout(null);
		add(dag);
		add(day);
		add(uur);
		add(hour);
		add(init);
		dag.setBounds(10, 8, 70, 30);
		day.setBounds(10, 30, 70, 30);
		uur.setBounds(10, 68, 70, 30);
		hour.setBounds(10, 90, 70, 30);
		init.setBounds(10, 120, 70, 30);

		setVisible(true);
	}

	@Override
	// voert actie van knop uit
	public void actionPerformed(ActionEvent e) {
		try {
			int dayField=parseSize();
			int hourField=parseSize2();
			simulator.setDay(dayField);
			simulator.setHour(hourField);
			simulator.randomInit();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private int parseSize() throws NumberFormatException {
		return Integer.parseInt(day.getText());
	}
	private int parseSize2() throws NumberFormatException {
		return Integer.parseInt(hour.getText());
	}

}

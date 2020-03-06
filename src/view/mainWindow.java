package view;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import view.graphPanel;
import controller.controller;

public class mainWindow extends JFrame{
	private controller ctrl;
	private graphPanel graphPanel;
	private SetingsPanel controlPanel;
	
	public mainWindow(controller c)
	{
		super("Genetic algorithm");
		ctrl=c;
		initGUI();
	}
	
	private void initGUI() {
		JPanel mainPanel=new JPanel(new BorderLayout());
		setContentPane(mainPanel);
		
		graphPanel=new graphPanel(ctrl);
		this.add(graphPanel, BorderLayout.CENTER);
		controlPanel=new SetingsPanel(ctrl);
		this.add(controlPanel, BorderLayout.WEST);
		
		this.setPreferredSize(new Dimension(900,700));
		this.setMinimumSize(new Dimension(900,700));
		this.setMaximumSize(new Dimension(900,700));
		//this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);    // Al cerrarlo se termina la aplicación.
		this.setLocationRelativeTo(null);
		this.pack();
		this.setVisible(true);
	}
	
	public void exit()
	{
		int option=JOptionPane.showOptionDialog(new JFrame(), "Are you sure you want to exit?",
				"Exit", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, JOptionPane.YES_OPTION);
		if(option==0)
		{
			System.exit(0);
		}
	}
}

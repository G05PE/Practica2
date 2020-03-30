package view;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import org.math.plot.Plot2DPanel;

import controller.controller;
import model.observer;

public class graphPanel extends JPanel implements observer {
	private controller ctrl;
	private Plot2DPanel plot;
	private int tam;
	private JLabel fitness;
	private JTextField variables;
	public graphPanel(controller c) {
		ctrl=c;
		ctrl.addObserver(this);
		initGUI();
	}

	private void initGUI() {
		this.validate();
		this.repaint();
		plot = new Plot2DPanel();
		plot.addLegend("SOUTH");
		plot.setPreferredSize(new Dimension(700,600));
		plot.setMinimumSize(new Dimension(700,600));
		plot.setMaximumSize(new Dimension(700,600));
		
		this.setPreferredSize(new Dimension(700,600));
		this.setMinimumSize(new Dimension(700,600));
		this.setMaximumSize(new Dimension(700,600));
		this.add(plot);
		
		fitness=new JLabel("Fitness: ");
		variables=new JTextField("Variables: ");
		variables.setEditable(false);
		this.add(fitness);
		this.add(variables);
		this.setVisible(true);
		
	}
	
	@Override
	public void onFinished(double[][] best, double[][] bestGen, double[][] average, List<Integer> bestVars, int mejorEsperado, int pos) {
		plot.removeAllPlots();
		plot.addLinePlot("Mejor absoluto", best[0], best[1]);
		plot.addLinePlot("Mejor generación", bestGen[0], bestGen[1]);
		plot.addLinePlot("Media generación", average[0], average[1]);
		if(mejorEsperado==0) {
			fitness.setText("Valor buscado: ? Fitness: " + bestVars.get(0)+" Pos: "+pos);
		}
		else {
			fitness.setText("Valor buscado: "+mejorEsperado +" Fitness: " + bestVars.get(0)+" Pos: "+pos);
		}
		String tVariables="Variables:";
		for(int i=1; i < bestVars.size(); i++) {
			tVariables+=" "+bestVars.get(i);
		}
		variables.setText(tVariables);
		this.repaint();
	}
	@Override
	public void onNextGeneration() {
		// TODO Auto-generated method stub
		
	}

}

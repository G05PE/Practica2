package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import controller.controller;
import model.observer;

public class SetingsPanel extends JPanel implements observer{
	
	private controller ctrl;
	private String [] listaMut= {"Insertion", "Exchange", "Inversion",
			 "Heuristic", "Método propio"};
	private String [] listaCruz= {"PMX", "OX", "Variant of OX", "CX",
			 "ERX", "Ordinal Coding","OwnCrossing"};
	private String [] listaSelec= {"Roulette", "Determinist Tournament",
			"Probabilistic Tournament","Stochastic", "Ranking", 
			"Truncation", "Otros"};
	private String [] listaFich= {"ajuste.txt", "datos12.txt", "datos15.txt", "datos30.txt", "tai100a.txt", "tai256c.txt"};
	private JComboBox<String> selectSelect;
	private JComboBox<String> selectCross;
	private JComboBox<String> selectMut;
	private JComboBox<String> selectFile;
	private JTextField tNgenerat;
	private JTextField tCross;
	private JTextField tElite;
	private JTextField tPopul;
	private JTextField tMut;
	private JTextField tTol;
	private Dimension dim1;
	private Dimension dim2;
	private JButton start;
	private JButton reset;
	private double crossPer;
	private double elitePer;
	private double tolPer;
	private double mutPer;
	private JPanel cross1;
	private JPanel mut1;
	private JPanel loadPanel;
	private int popSize;
	private int genNum;
	
	public SetingsPanel(controller ctlr) {
		this.ctrl = ctlr;
		this.setPreferredSize(new Dimension(180,700));
		this.setMinimumSize(new Dimension(180,700));
		this.setMaximumSize(new Dimension(180,700));
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY,2));
		dim1=new Dimension(160,20);
		dim2=new Dimension(75,20);
		initFields();
		initGUI();
	}
	
	
	private void initGUI() {
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));


		//Population----------------------------------------------------
		JLabel lPopSize = new JLabel("Population size");
		setDimLabel(lPopSize, dim1);
		 this.add(lPopSize);
		 this.add(Box.createHorizontalGlue());
		 
		 tPopul=new JTextField(popSize+"");
		 setDimText(tPopul, dim1);
		 tPopul.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setPopSize();
			} 
		 });
		 this.add(tPopul);
		 
		//Generation number---------------------------------------------------
		 JLabel lnGen = new JLabel("Generation number");
		 setDimLabel(lnGen, dim1);
		 this.add(lnGen);
		 this.add(Box.createHorizontalGlue());
			 
		tNgenerat=new JTextField(genNum+"");
		 setDimText(tNgenerat, dim1);
		 tNgenerat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setGenSize();
			} 
		 });
		 
		 this.add(tNgenerat);
		 
		 //Algoritmo de selecciï¿½n----------------------------------------------
		 JPanel selectionPanel=new JPanel(new FlowLayout());
		 BoxLayout box9=new BoxLayout(selectionPanel, BoxLayout.X_AXIS);
		 selectionPanel.setBorder(BorderFactory.createTitledBorder(
				 BorderFactory.createSoftBevelBorder(PROPERTIES), "Selection algorithm"));
		 selectionPanel.setPreferredSize(new Dimension(180, 60));
		 selectionPanel.setMinimumSize(new Dimension(180, 60));
		 selectionPanel.setMaximumSize(new Dimension(180, 60));
		 
		 selectSelect = new JComboBox<String>(listaSelec);
		 selectSelect.setEditable(false);
		 seleccionar(listaSelec[0]);
		 selectSelect.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					seleccionar((String)selectSelect.getSelectedItem());
				}
		 });
		 setDimCombobox(selectSelect, dim1);
		 selectionPanel.add(selectSelect);
		 this.add(selectionPanel);
		 //CrossOver----------------------------------------------------
		 JPanel crossOverPanel=new JPanel(new FlowLayout());
		 crossOverPanel.setPreferredSize(new Dimension(180, 100));
		 crossOverPanel.setMinimumSize(new Dimension(180, 100));
		 crossOverPanel.setMaximumSize(new Dimension(180, 100));
		 cross1=new JPanel();
		 BoxLayout box=new BoxLayout(cross1, BoxLayout.X_AXIS);
		 crossOverPanel.setBorder(BorderFactory.createTitledBorder(
				 BorderFactory.createSoftBevelBorder(PROPERTIES), "CrossOver"));
		 JLabel lCruce = new JLabel("CrossoverB");
		 setDimLabel(lCruce, dim2);
		 cross1.add(lCruce);
		 
		 changeCross(listaCruz);
		 cross1.add(selectCross);
		 crossOverPanel.add(cross1);
	 
		 JPanel cross2=new JPanel();
		 BoxLayout box2=new BoxLayout(cross1, BoxLayout.X_AXIS);
		 JLabel lCruce2 = new JLabel("Crossover %");
		 setDimLabel(lCruce2, dim2);
		 cross2.add(lCruce2);
		 
		 tCross=new JTextField(crossPer+"");
		 setDimText(tCross, dim2);
		 tCross.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setCrossPer();
			} 
		 });
		 cross2.add(tCross);
		 
		 crossOverPanel.add(cross2);
		 this.add(crossOverPanel);
	 
		 //Mutation----------------------------------------------------
		 JPanel mutationPanel=new JPanel(new FlowLayout());
		 mutationPanel.setPreferredSize(new Dimension(180, 100));
		 mutationPanel.setMinimumSize(new Dimension(180, 100));
		 mutationPanel.setMaximumSize(new Dimension(180, 100));
		 mut1=new JPanel();
		 BoxLayout box3=new BoxLayout(mut1, BoxLayout.X_AXIS);
		 mutationPanel.setBorder(BorderFactory.createTitledBorder(
				 BorderFactory.createSoftBevelBorder(PROPERTIES), "Mutation"));
		 JLabel lMut = new JLabel("MutationB");
		 setDimLabel(lMut, dim2);
		 mut1.add(lMut);
		 
		 changeMut(listaMut);
		 mutationPanel.add(mut1);
		 
		 JPanel mut2=new JPanel();
		 BoxLayout box4=new BoxLayout(mut2, BoxLayout.X_AXIS);
		 JLabel lMut2 = new JLabel("Mutation %");
		 setDimLabel(lMut2, dim2);
		 mut2.add(lMut2);
		 
		 tMut=new JTextField("2");
		 setDimText(tMut, dim2);
		 tMut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setMutPer();
			} 
		 });
		 mut2.add(tMut);
		 mutationPanel.add(mut2);
		 this.add(mutationPanel);
			 
		//Elite-----------------------------------------------------
		 JPanel elitePanel=new JPanel();
		 elitePanel.setPreferredSize(new Dimension(180, 60));
		 elitePanel.setMinimumSize(new Dimension(180, 60));
		 elitePanel.setMaximumSize(new Dimension(180, 60));
		 elitePanel.setBorder(BorderFactory.createTitledBorder(
				 BorderFactory.createSoftBevelBorder(PROPERTIES), "Elite"));
		 BoxLayout box5=new BoxLayout(elitePanel, BoxLayout.X_AXIS);
		 JLabel lElite = new JLabel("Elite %");
		 setDimLabel(lElite, dim2);
		 elitePanel.add(lElite);
		 
		 tElite=new JTextField("5");
		 setDimText(tElite, dim2);
		 tElite.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setElitePer();
			} 
		 });
		 elitePanel.add(tElite);
		 this.add(elitePanel);
		 
		//Cargar ficheros
		 loadPanel=new JPanel(new FlowLayout());
		 BoxLayout box8=new BoxLayout(loadPanel, BoxLayout.X_AXIS);
		 loadPanel.setBorder(BorderFactory.createTitledBorder(
				 BorderFactory.createSoftBevelBorder(PROPERTIES), "Load file"));
		 loadPanel.setPreferredSize(new Dimension(180, 60));
		 loadPanel.setMinimumSize(new Dimension(180, 60));
		 loadPanel.setMaximumSize(new Dimension(180, 60));
		 selectFile = new JComboBox<String>(listaFich);
		 selectFile.setEditable(false);
		 setDimCombobox(selectFile, dim1);
		 seleccionarFichero(listaFich[0]);
		 selectFile.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					seleccionarFichero((String)selectFile.getSelectedItem());
				}
		 });
		 loadPanel.add(selectFile);
		 this.add(loadPanel);
		 
		 //Botones-------------------------------------------------
		 this.add(Box.createRigidArea(new Dimension(190, 20)));
		 start = new JButton();
		 start.setIcon(new ImageIcon("icons/start.png"));
		 start.setToolTipText("Start");
		 start.setAlignmentX(JButton.CENTER_ALIGNMENT);
		 start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				start();
			} 
		 });
		 this.add(start);
		 
		 this.add(Box.createRigidArea(new Dimension(190, 20)));
		 reset = new JButton();
		 reset.setIcon(new ImageIcon("icons/reset.png"));
		 reset.setToolTipText("Reset");
		 reset.setAlignmentX(JButton.CENTER_ALIGNMENT);
		 reset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.reset();
				initFields();
				refreshText();
			} 
		 });
		 this.add(reset);
		 
		this.setVisible(true);
	}
	
	protected void seleccionarFichero(String selectedItem) {
		ctrl.seleccionarFichero(selectedItem);
	}

	protected void start() {
		setPopSize();
		setGenSize();
		setCrossPer();
		setElitePer();
		setMutPer();
		ctrl.start();
	}


	protected void setElitePer() {
		try {
			elitePer=Double.parseDouble(tElite.getText());
			ctrl.setElitePercent(elitePer);
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(new JFrame(),
					"Elite per is not a number","Error",JOptionPane.WARNING_MESSAGE);
		}
		
	}


	protected void setMutPer() {
		try {
			mutPer=Double.parseDouble(tMut.getText());
			ctrl.setMutationPercent(mutPer);
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(new JFrame(),
					"Mutation percent is not a number","Error",JOptionPane.WARNING_MESSAGE);
		}
		
	}

	protected void setCrossPer() {
		
		try {
			crossPer=Double.parseDouble(tCross.getText());
			ctrl.setCrossoverPercent(crossPer);
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(new JFrame(),
					"Cross percent is not a number","Error",JOptionPane.WARNING_MESSAGE);
		}
	}

	protected void setGenSize() {
		
		try {
			genNum=Integer.parseInt(tNgenerat.getText());
			ctrl.setGenerationNumber(genNum);
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(new JFrame(),
					"Generation size is not a number","Error",JOptionPane.WARNING_MESSAGE);
		}
	}


	protected void setPopSize() {
		try {
			popSize=Integer.parseInt(tPopul.getText());
			ctrl.setPopulationSize(popSize);
		}
		catch(NumberFormatException e)
		{
			JOptionPane.showMessageDialog(new JFrame(),
					"Population size is not a number","Error",JOptionPane.WARNING_MESSAGE);
		}
		
	}


	private void initFields() {
		crossPer=60;
		elitePer=5;
		tolPer=0.0001;
		mutPer=2;
		popSize=100;
		genNum=100;
	}
	
	public void changeMut(String [] selec) {
		if(selectMut!=null)
			mut1.remove(selectMut);
		 selectMut = new JComboBox<String>(selec);
		 selectMut.setEditable(false);
		 seleccionarMutacion(selec[0]);
		 selectMut.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					seleccionarMutacion((String)selectMut.getSelectedItem());
				}
		 });
		 setDimCombobox(selectMut, dim2);
		 mut1.add(selectMut);
		 mut1.validate();
		 mut1.repaint();
		 this.repaint();
	}
	public void changeCross(String [] selec) {
		if(selectCross!=null)
			cross1.remove(selectCross);
		selectCross = new JComboBox<String>(selec);
		 selectCross.setEditable(false);
		 seleccionarCruce(selec[0]);
		 selectCross.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					seleccionarCruce((String)selectCross.getSelectedItem());
				}
		 });
		 setDimCombobox(selectCross, dim2);
		 cross1.add(selectCross);
		 cross1.validate();
		 cross1.repaint();
		 this.repaint();
	}
	
	public void refreshText() {
		selectSelect.setSelectedIndex(0);
		selectCross.setSelectedIndex(0);
		selectMut.setSelectedIndex(0);
		selectFile.setSelectedIndex(0);
		tNgenerat.setText(genNum+"");
		tCross.setText(crossPer+"");
		tElite.setText(elitePer+"");
		tPopul.setText(popSize+"");
		tMut.setText(mutPer+"");
		this.repaint();
	}
	private void seleccionarCruce(String cruce) {
		switch(cruce) {
		case "PMX":
			ctrl.setCrossFunct(0);
			break;
		case "OX":
			ctrl.setCrossFunct(1);
			break;
		case "Variant of OX":
			ctrl.setCrossFunct(2);
			break;
		case "CX":
			ctrl.setCrossFunct(3);
			break;
		case "ERX":
			ctrl.setCrossFunct(4);
			break;
		case "Ordinal Coding":
			ctrl.setCrossFunct(5);
			break;
		case "OwnCrossing":
			ctrl.setCrossFunct(6);
			break;
		default:
			break;
		}
	}
	
	private void seleccionar(String selectedItem) {
		switch(selectedItem) {
		case "Roulette":
			ctrl.establecerMetodoSeleccion(0);
			break;
		case "Determinist Tournament":
			ctrl.establecerMetodoSeleccion(1);
			break;
		case "Probabilistic Tournament":
			ctrl.establecerMetodoSeleccion(2);
			break;
		case "Stochastic":
			ctrl.establecerMetodoSeleccion(3);
			break;
		case "Truncation":
			ctrl.establecerMetodoSeleccion(4);
			break;
		case "Ranking":
			ctrl.establecerMetodoSeleccion(5);
			break;
		case "Otros":
			ctrl.establecerMetodoSeleccion(6);
			break;
			default:
				break;
		}
	}
	
	private void seleccionarMutacion(String mutacion) {
		switch(mutacion){
		case "Insertion":
			ctrl.setMutationFunct(0);
			break;
		case "Exchange":
			ctrl.setMutationFunct(1);
			break;
		case "Inversion":
			ctrl.setMutationFunct(2);
			break;
		case "Heuristic":
			ctrl.setMutationFunct(3);
			break;
		case "Método propio":
			ctrl.setMutationFunct(4);
			break;
			default:
				break;
		}
	}

	private void setDimLabel(JLabel l, Dimension d) {
		l.setOpaque(true);
		l.setPreferredSize(d);
		l.setMaximumSize(d);
		l.setMinimumSize(d);
		l.setAlignmentX(CENTER_ALIGNMENT);
	}
	private void setDimText(JTextField t, Dimension d) {
		t.setOpaque(true);
		t.setPreferredSize(d);
		t.setMaximumSize(d);
		t.setMinimumSize(d);
		t.setAlignmentX(CENTER_ALIGNMENT);
	}
	private void setDimButton(JButton b, Dimension d) {
		b.setPreferredSize(d);
		b.setMaximumSize(d);
		b.setMinimumSize(d);
		b.setAlignmentX(CENTER_ALIGNMENT);
	}
	private void setDimCombobox(JComboBox<String> c, Dimension d) {
		c.setPreferredSize(d);
		c.setMaximumSize(d);
		c.setMinimumSize(d);
		c.setAlignmentX(CENTER_ALIGNMENT);
	}

	@Override
	public void onFinished(double[][] best, double[][] bestGen, double[][] average, List<Integer> bestVars) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNextGeneration() {
		// TODO Auto-generated method stub
		
	}

}

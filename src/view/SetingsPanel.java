package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import controller.controller;
import model.observer;

public class SetingsPanel extends JPanel implements observer{
	private controller ctrl;
	private JComboBox<String> selectSelect;
	private JComboBox<String> selectCross;
	private JComboBox<String> selectMut;
	private JComboBox<String> selectFunc;
	private JTextField tNgenerat;
	private JTextField tCross;
	private JTextField tElite;
	private JTextField tPopul;
	private JTextField tMut;
	private String[] selecBin = { "Single point", "Uniform"};
	private String[] selecReal= {"Aritmetic","Single point", "Discret"};
	private String [] mutBin= {"Basic"};
	private String [] mutReal= {"Uniform"};
	private JCheckBox check;
	private Dimension dim1;
	private Dimension dim2;
	private JButton start;
	private JButton reset;
	private boolean startOn;
	private double crossPer;
	private double elitePer;
	private double tolPer;
	private double mutPer;
	private JPanel cross1;
	private JPanel mut1;
	private int popSize;
	private int genNum;
	private int cod;
	
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
				try {
					genNum=Integer.parseInt(tNgenerat.getText());
					ctrl.setGenerationNumber(genNum);
				}
				catch(NumberFormatException e)
				{
					JOptionPane.showMessageDialog(new JFrame(),
							"Generation number is not a number","Error",JOptionPane.WARNING_MESSAGE);
				}
			} 
		 });
		 
		 this.add(tNgenerat);
		 
		 //Tolerancia----------------------------------------------------
		 JPanel tolPanel=new JPanel();
		 tolPanel.setPreferredSize(new Dimension(190,30));
		 tolPanel.setMaximumSize(new Dimension(190,30));
		 tolPanel.setMinimumSize(new Dimension(190,30));
		 BoxLayout box7=new BoxLayout(tolPanel, BoxLayout.X_AXIS);
		 JLabel lTol = new JLabel("Tolerance ");
		 setDimLabel(lTol, dim2);
		 tolPanel.add(lTol);
		 
		 JTextField tTol=new JTextField(tolPer+"");
		 setDimText(tTol, dim2);
		 tTol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					tolPer=Double.parseDouble(tTol.getText());
					ctrl.setTolerancePercent(tolPer);
				}
				catch(NumberFormatException e)
				{
					JOptionPane.showMessageDialog(new JFrame(),
							"Tolerance % is not a number","Error",JOptionPane.WARNING_MESSAGE);
				}
			} 
		 });
		 tolPanel.add(tTol);
		 this.add(tolPanel);
		 
		 //Algoritmo de selecci�n----------------------------------------------
		 JLabel lnAlgSel = new JLabel("Selection algorithm");
		 setDimLabel(lnAlgSel, dim1);
		 this.add(lnAlgSel);
		 this.add(Box.createHorizontalGlue());
		 String[] selec = { "Roulette", "Determinist Tournament",
				"Probabilistic Tournament", "Universal Stochastic",
				"Truncation"};
		 selectSelect = new JComboBox<String>(selec);
		 selectSelect.setEditable(false);
		 seleccionar(selec[0]);
		 selectSelect.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					seleccionar((String)selectSelect.getSelectedItem());
				}
		 });
		 setDimCombobox(selectSelect, dim1);
		 this.add(selectSelect);
		 
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
		 changeCross(selecBin);
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
				try {
					crossPer=Double.parseDouble(tCross.getText());
					ctrl.setCrossoverPercent(crossPer);
				}
				catch(NumberFormatException e)
				{
					JOptionPane.showMessageDialog(new JFrame(),
							"Crossover % is not a number","Error",JOptionPane.WARNING_MESSAGE);
				}
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
		 changeMut(mutBin);
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
				try {
					mutPer=Double.parseDouble(tMut.getText());
					ctrl.setMutationPercent(mutPer);
				}
				catch(NumberFormatException e)
				{
					JOptionPane.showMessageDialog(new JFrame(),
							"Mutation % is not a number","Error",JOptionPane.WARNING_MESSAGE);
				}
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
				try {
					elitePer=Double.parseDouble(tElite.getText());
					ctrl.setElitePercent(elitePer);
				}
				catch(NumberFormatException e)
				{
					JOptionPane.showMessageDialog(new JFrame(),
							"Elite % is not a number","Error",JOptionPane.WARNING_MESSAGE);
				}
			} 
		 });
		 elitePanel.add(tElite);
		 this.add(elitePanel);
		 
		//Funciones-----------------------------------------------------------
		 JLabel lFun = new JLabel("Function");
		 setDimLabel(lFun, dim1);
		 this.add(lFun);
		 this.add(Box.createHorizontalGlue());
		 String[] selec4 = { "Function 1", "Function 2", "Function 3", "Function 4"};
		 selectFunc = new JComboBox<String>(selec4);
		 selectFunc.setEditable(false);
		 seleccionarFun(selec4[0]);
		 selectFunc.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					seleccionarFun((String)selectFunc.getSelectedItem());
				}
		 });
		 setDimCombobox(selectFunc, dim1);
		 this.add(selectFunc);
		 
		 //Representacion real checkbox
		 check=new JCheckBox("RealCode");
		 check.setSelected(false);
		 check.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					ctrl.elegirCodificacion(1);
					changeCross(selecReal);
					selectFunc.setEnabled(false);
					changeMut(mutReal);
				}
				else {
					ctrl.elegirCodificacion(0);
					selectFunc.setEnabled(true);
					changeCross(selecBin);
					changeMut(mutBin);
				}
			}
		});
		 check.setVisible(false);
		 this.add(check);
		 //Botones-------------------------------------------------
		 this.add(Box.createRigidArea(new Dimension(190, 40)));
		 start = new JButton();
		 start.setIcon(new ImageIcon("icons/start.png"));
		 start.setToolTipText("Start");
		 start.setAlignmentX(JButton.CENTER_ALIGNMENT);
		 start.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ctrl.start();
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
		ctrl.elegirCodificacion(0);
		changeCross(selecBin);
		changeMut(mutBin);
		selectSelect.setSelectedIndex(0);
		selectCross.setSelectedIndex(0);
		selectMut.setSelectedIndex(0);
		selectFunc.setSelectedIndex(0);
		tNgenerat.setText(genNum+"");
		tCross.setText(crossPer+"");
		tElite.setText(elitePer+"");
		tPopul.setText(popSize+"");
		tMut.setText(mutPer+"");
		selectFunc.setEnabled(true);
		selectMut.setEnabled(true);
		this.repaint();
	}
	private void seleccionarCruce(String cruce) {
		switch(cruce) {
		case "Single point":
			ctrl.setCrossFunct(0);
			break;
		case "Uniform":
			ctrl.setCrossFunct(1);
			break;
		case "Aritmetic":
			ctrl.setCrossFunct(2);
			break;
		case "Discret":
			ctrl.setCrossFunct(3);
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
		case "Universal Stochastic":
			ctrl.establecerMetodoSeleccion(3);
			break;
		case "Truncation":
			ctrl.establecerMetodoSeleccion(4);
			break;
			default:
				break;
		}
	}
	
	private void seleccionarMutacion(String mutacion) {
		switch(mutacion){
		case "Basic":
			ctrl.setMutationFunct(0);
			break;
		case "Uniform":
			ctrl.setMutationFunct(1);
			break;
			default:
				break;
		}
	}
	
	private void seleccionarFun(String fun) {
		ctrl.elegirCodificacion(0);
		if(check!=null) check.setVisible(false);
		switch(fun) {
		case "Function 1":
			ctrl.establecerFuncion(0,0);
			break;
		case "Function 2":
			ctrl.establecerFuncion(1,0);
			break;
		case "Function 3":
			JFrame frame = new JFrame("");
			String [] n = {"1", "2"};
			String intro = (String) JOptionPane.showInputDialog(
			frame, // contenedor padre
			"Select number of variables", // mensaje en la ventana
			"Select", // etiqueta de la ventana
			JOptionPane.QUESTION_MESSAGE, // icono seleccionado
			null, // icono seleccionado por el usuario (Icon)
			n, // opciones para seleccionar
			n[0]);
			try {
				ctrl.establecerFuncion(2, Integer.parseInt(intro));
			}
			catch(NumberFormatException e)
			{
				JOptionPane.showMessageDialog(new JFrame(),
						"The value is not a number","Error",JOptionPane.WARNING_MESSAGE);
			}
			break;
		case "Function 4":
			JFrame frame2 = new JFrame("");
			frame2.setPreferredSize(new Dimension(190, 120));
			frame2.setMaximumSize(new Dimension(190, 120));
			frame2.setMinimumSize(new Dimension(190, 120));
			frame2.setResizable(false);
			JPanel p=new JPanel();
			p.setAlignmentX(CENTER_ALIGNMENT);
			p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
			frame2.setAlwaysOnTop(true);
			JLabel l=new JLabel("Enter number of variables");
			setDimLabel(l, dim1);
			p.add(l);
			JTextField f=new JTextField("1");
			setDimText(f, dim1);
			 f.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
	
						ctrl.establecerFuncion(3, Integer.parseInt(f.getText()));
						frame2.setVisible(false);
					}
					catch(NumberFormatException e)
					{
						JOptionPane.showMessageDialog(new JFrame(),
								"The value is not a number","Error",JOptionPane.WARNING_MESSAGE);
					}
				} 
			 });
			 p.add(f);
			 p.add(Box.createRigidArea(new Dimension(190, 10)));
			 JButton ok=new JButton("Ok");
			 setDimButton(ok, dim2);
			 ok.setAlignmentY(BOTTOM_ALIGNMENT);
			 ok.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						ctrl.establecerFuncion(3, Integer.parseInt(f.getText()));
						frame2.setVisible(false);
					} 
				 });
			 p.add(ok);
			 
			 frame2.setContentPane(p);
			 frame2.pack();
			 frame2.setVisible(true);
			 if(check!=null) check.setVisible(true);
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
	public void changedCode(int codificacion) {
		cod=codificacion;
	}

	@Override
	public void onFinished(double[][] best, double[][] bestGen, double[][] average, List<Double> bestVars) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onNextGeneration() {
		// TODO Auto-generated method stub
		
	}

}

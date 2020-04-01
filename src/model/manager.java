 package model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.annotation.processing.FilerException;
import cruces.*;
import mutacion.exchange;
import mutacion.heuristic;
import mutacion.insercion;
import mutacion.inversion;
import mutacion.metPropio;
import mutacion.mutacion;
import poblacion.individuo;
import poblacion.poblacion;
import seleccion.*;

public class manager {

	private int [] mejores= {50, 224416, 388214, 1818146};
	private int valorMejor;
	private int mejorPos;
	private List<observer> observers;
	private algoritmoSeleccion algSel;
	private algoritmoCruce algCruce;
	private poblacion poblacion;
	private double bestGen [][];
	private double average [][];
	private double best [][];
	private List<Integer> bestVars;;
	private mutacion algMut;
	private funcion funcion;
	private double probElite;
	private double probCruc;
	private double probMut;	
	private int generation;
	private funcion copiaFuncion;
	private elite elite;
	private int maxIter;
	private int tamPob;

	public manager() {
		observers=new ArrayList<observer>();
		bestVars=new ArrayList<Integer>();
		funcion=new funcion();
		elite=new elite();
		iniciarDatos();
	}
	public void iniciarDatos() {
		mejorPos=0;
		probElite=0.05;
		generation=0;
		probCruc=0.6;
		probMut=0.02;
		maxIter=100;
		tamPob=100;
	}
	public void addObserver(observer o) {
		if(!observers.contains(o)) {
			observers.add(o);
		}
	}

	public void iniciarPoblacion() {
		poblacion=new poblacion(tamPob, funcion);
		poblacion.iniciarPoblacion();
		best=new double[2][maxIter];
		bestGen=new double[2][maxIter];
		average=new double[2][maxIter];
		
	}
	public void start() {
		generation=0;
		iniciarPoblacion();
		evaluarPoblacion();
		generation++;
		while(generation < maxIter) {
			elite.escogerElites(poblacion, probElite);
			adaptar();
			seleccion();
			desadaptar();
			reproduccion();
			mutacion();
			elite.incluirElites(poblacion);
			evaluarPoblacion();
			generation++;
		}
		
		for(int i=0; i < observers.size(); i++) {
			observers.get(i).onFinished( best, bestGen, average, bestVars, valorMejor, mejorPos);
		}
	}
	private void desadaptar() {
		funcion.desadaptar(poblacion);
	}
	private void adaptar() {
		funcion.adaptar(poblacion);
	}
	private void seleccion() {
		poblacion=algSel.ini(poblacion, funcion);
	}
	private void mutacion() {
		algMut.mutar(poblacion, probMut);
	}
	private void evaluarPoblacion() {
		evaluarMejor();
		evaluarMedia();
	}
	private void evaluarMedia() {
		average[0][generation]=generation;
		average[1][generation]=poblacion.getAverage();
	}
	
	private void evaluarMejor() {
		best[0][generation]=generation;
		bestGen[0][generation]=generation;
		bestGen[1][generation]=poblacion.getBest();
		if(generation==0 || funcion.best(bestGen[1][generation], best[1][generation-1])) {
			best[1][generation]=bestGen[1][generation];
			mejorPos=generation;
			bestVars.clear();
			bestVars.add((int) best[1][generation]);
			individuo mejor=poblacion.getMejorInd();
			for(int i=0; i < mejor.getSizeCromosoma(); i++) {
				bestVars.add(mejor.getCromosomaAt(i).getFenotipo());
			}
		}
		else
		{
			best[1][generation]=best[1][generation-1];
		}
	}
	private void reproduccion() {
		if(algCruce!=null) {
			algCruce.cruzar(poblacion, probCruc);
		}else {
			System.out.println("No se ha inicializado el algoritmo de cruce");
		}
	}
	
	public void establerMetodoSeleccion(int metodo) {
		switch(metodo)
		{
		case 0: algSel=new algoritmoRuleta();
			break;
		case 1: algSel=new algoritmoTorneoDeter();
			break;
		case 2: algSel=new algoritmoTorneoProb();
			break;
		case 3: algSel=new algoritmoEstocasticoUniv();
			break;
		case 4: algSel=new algoritmoTruncamiento();
			break;
		case 5: algSel=new algoritmoRanking();
			break;
		case 6: algSel = new algotirmoOtro();
			break;
		}
	}
	public double[][] getBest() {
		return best;
	}
	public double[][] getBestGen() {
		return bestGen;
	}
	public double[][] getAverage() {
		return average;
	}
	public void setPopulationSize(int popSize) {
		tamPob=popSize;
	}

	public void setGenerationNumber(int genNum) {
		maxIter=genNum;
	}
	public void setProbCruce(double d) {
		probCruc=d;
	}
	public void setCrossFunct(int i) {
		switch(i) {
		case 0: algCruce = new pmx();
			break;
		case 1: algCruce = new ox();
			break;
		case 2:algCruce = new oxPP();
			break;
		case 3: algCruce = new cx();
			break;
		case 4: algCruce = new erx();
			break;
		case 5: algCruce = new ordinalCoding();
			break;
		case 6: algCruce=new cruces.metPropio();
			break;
		}
	}
	
	public void setMutationFunct(int i) {
		switch(i) {
		case 0: algMut=new insercion();
			break;
		case 1: algMut=new exchange();
			break;		
		case 2: algMut= new inversion();
			break;
		case 3: algMut=new heuristic();
			break;
		case 4: algMut = new metPropio();
			break;
		}
	}
	public void setMutationPercent(double mutPer) {
		probMut=mutPer;
	}
	public void setElitePercent(double d) {
		probElite=d;
	}
	public void reset() {
		iniciarDatos();
	}
	
	private void load(Scanner in, int [][] matrix, int tam) {
		for(int i=0; i < tam; i++) {
			for(int j=0; j < tam; j++) {
				matrix[i][j]=in.nextInt();
			}
		}
	}
	
	public void seleccionarFichero(String fileName) {
		int [][] flujo;
		int [][] distancia;
		try(Scanner in=new Scanner(new File("ficheros/"+fileName));) 
		{
			try{
				int tam=in.nextInt();
				if(tam > 0) {
					flujo=new int[tam][tam];
					distancia=new int[tam][tam];
					save();
					load(in, distancia, tam);
					load(in, flujo, tam);
					funcion.cargarDatos(distancia, flujo, tam);
					if(fileName=="ajuste.txt") {
						valorMejor=mejores[0];
					}if(fileName=="datos12.txt") {
						valorMejor=mejores[1];
					}
					else if(fileName=="datos15.txt") {
						valorMejor=mejores[2];
					}
					else if(fileName=="datos30.txt") {
						valorMejor=mejores[3];
					}
					else {
						valorMejor=0;
					}
				}
				else
				{
					throw new FilerException("Error en la lectura del fichero");
				}
			}catch(FilerException | NumberFormatException e) {
				restore();
				System.err.println("Hay un error en el formato del fichero");
			}
		}
		catch (IOException e) 
		{
			System.err.println("Can´t open the file");
		}
	}
	private void restore() {
		funcion=new funcion(copiaFuncion);
	}
	public void setObservers(List<observer> obs) {
		observers=new ArrayList<observer>();
		for(int i=0; i < obs.size(); i++) {
			observers.add(obs.get(i));
		}
	}
	private void save() {
		copiaFuncion=new funcion(this.funcion);
	}
}


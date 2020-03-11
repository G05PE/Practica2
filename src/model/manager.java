 package model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.FilerException;
import cruces.*;
import poblacion.individuo;
import poblacion.poblacion;
import seleccion.*;

public class manager {

	private List<observer> observers;
	private algoritmoSeleccion algSel;
	private algoritmoCruce algCruce;
	private poblacion poblacion;
	private double bestGen [][];
	private double average [][];
	private double best [][];
	private List<Double> bestVars;;
	private mutacion algMut;
	private funcion funcion;
	private double probToler;
	private double probElite;
	private double probCruc;
	private double probMut;	
	private int generation;
	private manager copia;
	private elite elite;
	private int maxIter;
	private int tamPob;

	public manager() {
		observers=new ArrayList<observer>();
		bestVars=new ArrayList<Double>();
		elite=new elite();
		iniciarDatos();
	}
	public void iniciarDatos() {
		probToler=0.001;
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
		poblacion=new poblacion(tamPob, probToler, funcion);
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
			observers.get(i).onFinished( best, bestGen, average, bestVars);
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
			bestVars.clear();
			bestVars.add(best[1][generation]);
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
		case 0:
			//algSel=new algoritmoRuleta();
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
		case 0:
			//algCruce=new monopunto();
			break;
		}
	}
	
	public void setMutationFunct(int i) {
		switch(i) {
		case 0:
			//algMut=new mutacionBasica();
			break;
		}
	}
	public void setMutationPercent(double mutPer) {
		probMut=mutPer;
	}
	public void setElitePercent(double d) {
		probElite=d;
	}
	public void setTolerancia(double tolPer) {
		probToler=tolPer;
	}
	public void reset() {
		iniciarDatos();
	}
	
	private void load(BufferedReader in, int [][] matrix, int tam) throws FilerException {
		try {
			String basura=in.readLine();//Lee línea vacia
			String [] cadena;
			for(int i=0; i < tam; i++) {
				cadena=in.readLine().split(" ");
				for(int j=0; j < tam; j++) {
					matrix[i][j]=Integer.parseInt(cadena[j]);
				}
			}
		} catch (IOException e) {
			throw  new FilerException("Error en la lectura del fichero");
		}
	}
	
	public void seleccionarFichero(String fileName) {
		int tam;
		int [][] matrix1;
		int [][] matrix2;
		try(BufferedReader inChars = new BufferedReader(new FileReader("ficheros/"+fileName));) 
		{
			try{
				String cadena=inChars.readLine();
				if(Integer.parseInt(cadena) > 0) {
					tam=Integer.parseInt(cadena);
					matrix1=new int[tam][tam];
					matrix2=new int[tam][tam];
					//save();
					load(inChars, matrix1, tam);
					load(inChars, matrix2, tam);
				}
				else
				{
					throw new FilerException("Error en la lectura del fichero");
				}
			}catch(FilerException | NumberFormatException e) {
				//restore();
				System.err.println("Hay un error en el formato del fichero");
			}
		}
		catch (IOException e) 
		{
			System.err.println("Can´t open the file");
		}
	}
	public void setObservers(List<observer> obs) {
		observers=new ArrayList<observer>();
		for(int i=0; i < obs.size(); i++) {
			observers.add(obs.get(i));
		}
	}
	private List<Double> copiarVars(){
		List<Double> nuevo=new ArrayList<Double>();
		for(int i=0; i < bestVars.size(); i++) {
			nuevo.add(new Double(bestVars.get(i)));
		}
		return nuevo;
	}
	private void save() {
		copia=new manager();
		copia.setObservers(observers);
		copia.algSel=algSel.getCopia();
		copia.algCruce.getCopia();
		copia.setPoblacion(new poblacion(this.poblacion));
		copia.setBestGen(copiarArray(bestGen, tamPob));
		copia.setBest(copiarArray(best, tamPob));
		copia.setAverage(copiarArray(average, tamPob));
		copia.setBestVars(copiarVars());
		copia.setAlgMut(algMut.getCopia());
		copia.setFuncion(new funcion(funcion));
		copia.setPrecision(probToler);
		copia.setElitePercent(probElite);
		copia.setProbCruce(probCruc);
		copia.setMutationPercent(probMut);
		copia.setGenerationNumber(maxIter);
		copia.setPopulationSize(tamPob);
	}
	private void setPrecision(double prob) {
		probToler=prob;
	}
	private void setFuncion(funcion copia) {
		funcion=copia;
	}
	private void setAlgMut(mutacion copia) {
		algMut=copia;
	}
	private void setBestVars(List<Double> vars) {
		bestVars=vars;
	}
	private void setBestGen(double[][] array) {
		bestGen=array;
	}
	private void setBest(double[][] array) {
		best=array;
	}
	private void setAverage(double[][] array) {
		average=array;
	}
	private double[][] copiarArray(double[][] viejo, int tam) {
		double[][] nuevo=new double[tam][tam];
		for(int i=0; i < tam; i++) {
			for(int j=0; j < tam; j++) {
				nuevo[i][j]=viejo[i][j];
			}
		}
		return nuevo;
	}
	private void setPoblacion(poblacion poblacion) {
		this.poblacion=poblacion;
	}
}


package controller;

import model.manager;
import model.observer;

public class controller {

	private manager manager;

	public controller(manager m)
	{
		manager=m;
	}
	public void addObserver(observer o) {
		manager.addObserver(o);
	}

	public void establecerMetodoSeleccion(int metodo) {
		manager.establerMetodoSeleccion(metodo);
	}
	public void iniciarPoblacion() {
		manager.iniciarPoblacion();
	}
	public double[][] getBest() {
		return manager.getBest();
	}
	public double[][] getBestGen() {
		return manager.getBestGen();
	}
	public double[][] getAverage() {
		return manager.getAverage();
	}
	public void start() {
		manager.start();
	}
	public void setPopulationSize(int popSize) {
		manager.setPopulationSize(popSize);
	}
	public void setGenerationNumber(int genNum) {
		manager.setGenerationNumber(genNum);
	}
	public void setCrossoverPercent(double p) {
		manager.setProbCruce(p*0.01);
	}
	public void setCrossFunct(int i) {
		manager.setCrossFunct(i);
	}
	public void setMutationFunct(int i) {
		manager.setMutationFunct(i);
	}
	public void setMutationPercent(double mutPer) {
		manager.setMutationPercent(mutPer*0.01);
	}
	public void setElitePercent(double elitePer) {
		manager.setElitePercent(elitePer*0.01);
	}
	public void reset() {
		manager.reset();
	}
	public void seleccionarFichero(String i) {
		manager.seleccionarFichero(i);
	}
}

package cruces;

import poblacion.individuo;
import poblacion.poblacion;

public abstract class algoritmoCruce {
	private int num_sele_cruce;
	private double probCruce;
	private poblacion seleccionados;
	private poblacion reproductores;
	private poblacion descendientes;
	
	public abstract poblacion cruzar(poblacion seleccionados, double prob);
	protected void ini(double prob, poblacion p) {
		seleccionados=p;
		probCruce=prob;
		reproductores = new poblacion(seleccionados.getSize(), seleccionados.getPrecision(), seleccionados.getFuncion());
		descendientes = seleccionados;
	}
	
	protected void seleccionaReproductores() {
		num_sele_cruce=0;
		for(int i = 0; i < getSeleccionados().getSize(); i++) {
			if(Math.random()%1 < getProbCruce()) {
				addReprpoductor(getSeleccionadoConcreto(i));
				num_sele_cruce++;
			}		
		}

		if(num_sele_cruce%2 != 0) {
			borraUltimoReproductor();
			num_sele_cruce--;
		}
	}
	
	public double getProbCruce() {
		return probCruce;
	}
	
	public void setProbCruce(double probCruce) {
		this.probCruce = probCruce;
	}

	protected void setSizeReproductor(int num_sele_cruce) {
		reproductores.setSize(num_sele_cruce);
	}

	
	
	//Setters
	protected void borraUltimoReproductor() {
		reproductores.borraUltimo();
	}
	
	
	protected void addReprpoductor(individuo i) {
		reproductores.addIndividuo(i);
	}
	

	protected void addDescendiente(individuo i) {
		descendientes.addIndividuo(i);
	}
	
	
	
	//Getters
	protected individuo getReproductor(int i) {
		return reproductores.getIndividuo(i);
	}
	protected individuo getReproductorAt(int i) {
		return reproductores.getIndividuo(i);
	}
	protected individuo getSeleccionadoConcreto(int i) {
		return seleccionados.getIndividuo(i);
	}
	protected int getNumSel() {
		return seleccionados.getSize();
	}
	protected poblacion getDescendientes() {
		return descendientes;
	}
	protected void setDescendienteAt(int i, individuo hijo) {	
		descendientes.setIndividuoAt(i, hijo);
	}
	
	protected poblacion getSeleccionados() {
		return seleccionados;
	}
	protected int getReproductoresSize() {
		return num_sele_cruce;
	}
	
	protected individuo getDescendienteAt(int i) {
		return descendientes.getIndividuo(i);
	}
}

package cruces;

import java.util.ArrayList;
import java.util.List;

import poblacion.individuo;
import poblacion.poblacion;

public abstract class algoritmoCruce {
	
	private int num_sele_cruce;
	private double probCruce;
	private poblacion seleccionados;
	private List<Integer> reproductores;
	private poblacion descendientes;
	
	public abstract poblacion cruzar(poblacion seleccionados, double prob);
	
	protected void ini(double prob, poblacion p) {
		seleccionados=p;
		probCruce=prob;
		reproductores = new ArrayList<Integer>();
		descendientes = seleccionados;	
	}
	
	protected void seleccionaReproductores() {
		num_sele_cruce=0;
		for(int i = 0; i < getSeleccionados().getSize(); i++) {
			if(Math.random()%1 < getProbCruce()) {
				reproductores.add(i);
				num_sele_cruce++;
			}		
		}

		if(num_sele_cruce%2 != 0) {
			borraUltimoReproductor();
			num_sele_cruce--;
		}
	}
	
	protected int getSeleCruce() {
		return num_sele_cruce;
	}
	
	public double getProbCruce() {
		return probCruce;
	}
	
	public void setProbCruce(double probCruce) {
		this.probCruce = probCruce;
	}
	
	//Setters
	
	protected void addDescendiente(individuo i) {
		descendientes.addIndividuo(i);
	}	
	
	protected void borraUltimoReproductor() {
		reproductores.remove(num_sele_cruce-1);
	}
	
	protected void setDescendienteAt(int i, individuo hijo) {	
		descendientes.setIndividuoAt(reproductores.get(i), hijo);
	}
	
	protected void setDescendientesSize(int numSel) {
		descendientes.setSize(numSel);
	}
	
	//Getters
	protected individuo getReproductorAt(int i) {
		return seleccionados.getIndividuo(reproductores.get(i));
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

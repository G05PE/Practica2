package model;

import poblacion.individuo;
import poblacion.poblacion;

public class adaptarMin extends adaptacion{

	protected void ajustar(poblacion p) {
		for(int i=0; i < p.getSize(); i++) {
			individuo ind=p.getIndividuo(i);
			ind.setFitness(getLimit()-ind.getFitness());
		}
	}

	protected void establecerLimite(poblacion p) {
		double valor=p.getIndividuo(0).getFitness();
		for(int i=1; i < p.getSize(); i++) {
			if(p.getIndividuo(i).getFitness() > valor) {
				valor=p.getIndividuo(i).getFitness();
			}
		}
		valor=valor * getC();
		setLimit(valor);
	}
	
	public void deshacer(poblacion p) {
		setAdaptado(false);
		double valor;
		for(int i=0; i < p.getSize(); i++) {
			valor=getLimit() - p.getIndividuo(i).getFitness();
			p.getIndividuo(i).setFitness(valor);
		}
	}
}

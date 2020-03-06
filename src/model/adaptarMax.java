package model;

import poblacion.individuo;
import poblacion.poblacion;

public class adaptarMax extends adaptacion{
	
	protected void establecerLimite(poblacion p) {
		double valor=p.getIndividuo(0).getFitness();
		for(int i=1; i < p.getSize(); i++) {
			if(p.getIndividuo(i).getFitness() < valor) {
				valor=p.getIndividuo(i).getFitness();
			}
		}
		valor=valor * getC();
		setLimit(valor);
	}

	@Override
	protected void ajustar(poblacion p) {
		for(int i=0; i < p.getSize(); i++) {
			individuo ind=p.getIndividuo(i);
			ind.setFitness(getLimit() + ind.getFitness());
		}
	}
	
	public void deshacer(poblacion p) {
		setAdaptado(false);
		double valor;
		for(int i=0; i < p.getSize(); i++) {
			valor=p.getIndividuo(i).getFitness() - getLimit();
			p.getIndividuo(i).setFitness(valor);
		}
	}
}

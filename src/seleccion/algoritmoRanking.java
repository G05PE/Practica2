package seleccion;

import model.funcion;
import poblacion.individuo;
import poblacion.poblacion;

public class algoritmoRanking extends algoritmoSeleccion {

	public algoritmoRanking() {
		super("ranking");
	}

	@Override
	public poblacion ini(poblacion p, funcion f) {
		iniSeleccionados(p);
		seleccionar(p, f);
		getSeleccionados().iniBest();
		return getSeleccionados();
	}

	@Override
	public void seleccionar(poblacion p, funcion f) {
		//Ordenamos por fitness
		ordenaFitness(p);
		
		//Array para futuros padres, seleccionamos los dos primeros
		int papisSize = 2;
		addSeleccionado(new individuo(p.getIndividuo(0)));
		addSeleccionado(new individuo(p.getIndividuo(1)));
		
		double[] segments = ranking(p);
		double ultimo = segments[segments.length - 1];
		
		//Bucle para rellenar todas las posiciones deseadas
		while(papisSize < p.getSize()) {
			double rand = (double)(Math.random() * ultimo);
			if(rand <= segments[0]) {
				addSeleccionado(new individuo(p.getIndividuo(0)));
				papisSize++;
			}
			else 
				for(int i = 1; i < papisSize; i++) {
					if(rand > segments[i-1] && rand <= segments[i]) {
						addSeleccionado(new individuo(p.getIndividuo(i)));
						papisSize++;
					}
			}
		}
	}
		
	private double[] ranking(poblacion p) {
		double[] segments = new double[p.getSize()];
		double beta = calcBeta(p);
				
		for(int i = 0; i < segments.length; i++) {
			double prob = (double) i/p.getSize();
			prob = prob * 2 * (beta - 1);
			prob = beta - prob;
			prob = (double) prob*((double)1/p.getSize());
			
			if(i != 0) segments[i]	= segments[i-1] + prob;
			else segments[i] = prob;
		}
		return segments;
	}

	private double calcBeta(poblacion p) {
		double best = 0;
		double media = 0;
		for(int i = 0; i < p.getSize(); i++) {
			media += p.getIndividuo(i).getFitness();
			if(p.getIndividuo(i).getFitness() > best) best = p.getIndividuo(i).getFitness();
		}
		media /= p.getSize();
		
		return best/media;
	}

	private void ordenaFitness(poblacion p) {
		for(int i = 0; i < p.getSize(); i++) {
			for(int j = 0; j < p.getSize(); j++) {
				if(p.getIndividuo(j).getFitness() < p.getIndividuo(i).getFitness()) {
					individuo aux = new individuo(p.getIndividuo(i));
					p.setIndividuoAt(i, p.getIndividuo(j));
					p.setIndividuoAt(j, aux);
				}
			}
		}
	}
	
	
}

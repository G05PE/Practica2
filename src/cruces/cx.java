package cruces;

import genetica.gen;
import poblacion.individuo;
import poblacion.poblacion;

public class cx extends algoritmoCruce {
	private boolean [] colocados;
	@Override
	public poblacion cruzar(poblacion seleccionados, double prob) {
		ini(prob, seleccionados);
		seleccionaReproductores();
		creaDescendientes();
		return getDescendientes();
	}

	private void creaDescendientes() {
		for(int i=0; i < getReproductoresSize(); i+=2) {
			individuo padre1=getReproductorAt(i);
			individuo padre2=getReproductorAt(i+1);
			individuo hijo1=new individuo(padre1);
			individuo hijo2=new individuo(padre2);
			setGenes(hijo1, padre2);
			setGenes(hijo2, padre1);
			hijo1.calcularFitness();
			hijo2.calcularFitness();
			setDescendienteAt(i, hijo1);
			setDescendienteAt(i+1, hijo2);
		}
	}

	private void setGenes(individuo hijo, individuo padre) {
		int pos=0, sig;
		int tam=hijo.getSizeCromosoma();
		colocados=new boolean[tam];
		inicializarColocados(colocados, tam);
		boolean puedoSeguir=true;
		while(puedoSeguir) {
			colocados[pos]=true;
			sig=siguiente(hijo, padre.getCromosomaAt(pos), pos);
			if(sig == pos) {
				puedoSeguir=false;
			}
				pos=sig;
		}
		colocarResto(hijo, padre, tam);
	}
	
	/**Coloca el resto de los genes que quedan del padre en el hijo*/
private void colocarResto(individuo hijo, individuo padre, int tam) {
		for(int i=0; i < tam; i++) {
			if(!colocados[i]) {
				hijo.setGen(i, padre.getCromosomaAt(i));
			}
		}
	}

/**Obtiene la posición del hijo donde está colocado el gen*/
	private int siguiente(individuo hijo, gen gen, int pos) {
		int sig=pos;
		int i=0;
		int tam=hijo.getSizeCromosoma();
		while(i < tam && hijo.getCromosomaAt(i).getGenotipo() != gen.getGenotipo()) {
			i++;
		}
		if(i < tam && !colocados[i]) {
			sig=i;
		}
		return sig;
	}

	private void inicializarColocados(boolean[] colocados, int tam) {
		for(int i=0; i < tam; i++) {
			colocados[i]=false;
		}
	}

}

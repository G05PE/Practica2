package cruces;

import java.util.Random;

import poblacion.individuo;
import poblacion.poblacion;

public class metPropio extends algoritmoCruce {

	@Override
	public poblacion cruzar(poblacion seleccionados, double prob) {
		ini(prob, seleccionados);
		seleccionaReproductores();
		creaDescendientes();
		return getDescendientes();
	}

	private void creaDescendientes() {
		Random rand=new Random();
		int tamCromosoma=getReproductorAt(0).getSizeCromosoma();
		for(int i=0; i < tamCromosoma; i+=2) {
			int punto=rand.nextInt(tamCromosoma/2)+1;
			individuo padre1=getReproductorAt(i);
			individuo padre2=getReproductorAt(i+1);
			individuo hijo1=new individuo(padre1);
			individuo hijo2=new individuo(padre2);
			setGenes(hijo1, padre2, punto);
			setGenes(hijo2, padre1, punto);
			hijo1.calcularFitness();
			hijo2.calcularFitness();
			setDescendienteAt(i, hijo1);
			setDescendienteAt(i+1, hijo2);
		}
	}

	private void setGenes(individuo hijo, individuo padre, int punto) {
		int j=padre.getSizeCromosoma()-1;
		for(int i=0; i < punto; i++) {
			hijo.setGen(i, padre.getCromosomaAt(j));
			j--;
		}
		//Busca en el hijo si se ha colocado algún elemento que ya existia despues de punto
		for(int i=punto; i < hijo.getSizeCromosoma(); i++) {
			int k=0;
			while(k < punto && hijo.getCromosomaAt(i).getFenotipo()!=hijo.getCromosomaAt(k).getFenotipo()) {
				k++;
			}
			if(k < punto) {
				buscarGen(hijo, padre, i, j);
			}
		}
	}

	private void buscarGen(individuo hijo, individuo padre, int i, int punto) {
		int j=punto;
		while(j >= 0 && hijo.existeGen(padre.getCromosomaAt(j))) {
			j--;
		}
		if(j >= 0) {
			hijo.setGen(i, padre.getCromosomaAt(j));
		}
	}

}

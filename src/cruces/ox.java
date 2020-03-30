package cruces;

import java.util.Random;

import genetica.gen;
import poblacion.individuo;
import poblacion.poblacion;

public class ox extends algoritmoCruce {

	@Override
	public poblacion cruzar(poblacion seleccionados, double prob) {
		ini(prob, seleccionados);
		seleccionaReproductores();
		creaDescendientes();
		return getDescendientes();
	}

	private void creaDescendientes() {
		int tamCromosoma=getReproductorAt(0).getSizeCromosoma();
		Random rand=new Random();
		int punto1=rand.nextInt(tamCromosoma);
		int punto2=rand.nextInt(tamCromosoma);
		while(punto1 == punto2) {
			punto2=rand.nextInt(tamCromosoma);
		}
		if(punto1 > punto2) {
			int aux=punto1;
			punto1=punto2;
			punto2=aux;
		}
		
		for(int i=0; i < getReproductoresSize(); i+=2) {
			individuo padre1=getReproductorAt(i);
			individuo padre2=getReproductorAt(i+1);
			individuo hijo1=new individuo(padre1);
			individuo hijo2=new individuo(padre2);
			setGenes(hijo1, padre2, punto1, punto2);
			setGenes(hijo2, padre1, punto1, punto2);
			hijo1.calcularFitness();
			hijo2.calcularFitness();
			setDescendienteAt(i, hijo1);
			setDescendienteAt(i+1, hijo2);
		}
	}

	private int createPunto(int punto2, int tam) {
		Random rand=new Random();
		int punto1=rand.nextInt(tam);
		while(punto1 == punto2) {
			punto2=rand.nextInt(tam);
		}
		if(punto1 > punto2) {
			int aux=punto1;
			punto1=punto2;
			punto2=aux;
		}
		return punto1;
	}

	private void setGenes(individuo hijo, individuo padre, int punto1, int punto2) {
		int contPadre=punto2, contHijo=punto2;
		int tam=padre.getSizeCromosoma();
		while(contHijo != punto1) {
			gen gen=padre.getCromosomaAt(contPadre);
			if(!hijo.existeGen(gen,punto1, contHijo)) {
				hijo.setGen(contHijo, gen);
				contHijo=(contHijo+1)%tam;;
			}
			contPadre=(contPadre+1)%tam;
		}
	}

}

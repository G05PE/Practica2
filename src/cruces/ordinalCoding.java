package cruces;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import genetica.gen;
import poblacion.individuo;
import poblacion.poblacion;

public class ordinalCoding extends algoritmoCruce {
	private List<Integer> listaDinamica;
	@Override
	public poblacion cruzar(poblacion seleccionados, double prob) {
		ini(prob, seleccionados);
		seleccionaReproductores();
		crearListaDinamica();
		creaDescendientes();
		return getDescendientes();
	}

	private void crearListaDinamica() {
		int tam=getSeleccionados().getIndividuo(0).getSizeCromosoma();
		listaDinamica=new ArrayList<Integer>();
		for(int i=0; i < tam; i++) {
			listaDinamica.add(i);
		}
	}

	private void creaDescendientes() {
		for(int i=0; i < getReproductoresSize(); i+=2) {
			List<Integer> padre1=new ArrayList<Integer>();
			List<Integer> padre2=new ArrayList<Integer>();
			copiarGenes(padre1, getReproductorAt(i));
			copiarGenes(padre2, getReproductorAt(i+1));
			codificar(padre1);
			codificar(padre2);
			List<Integer> genHijo1=new ArrayList<Integer>(padre1);
			List<Integer> genHijo2=new ArrayList<Integer>(padre2);
			cruceOrdinal(padre1, padre2, genHijo1,  genHijo2);
			decodificar(genHijo1);
			decodificar(genHijo2);
			individuo hijo1=new individuo(getReproductorAt(i));
			individuo hijo2=new individuo(getReproductorAt(i+1));
			setGenes(hijo1, genHijo1);
			setGenes(hijo2,  genHijo2);
			hijo1.calcularFitness();
			hijo2.calcularFitness();
			setDescendienteAt(i, hijo1);
			setDescendienteAt(i+1, hijo2);
		}
	}
private void decodificar(List<Integer> hijo) {
	List<Integer> copia=new ArrayList<Integer>(listaDinamica);	
	int pos;
	for(int i=0; i < hijo.size(); i++) {
			pos=hijo.get(i);
			hijo.set(i, copia.get(pos));
			copia.remove(pos);
		}
	}

/**Pasa los genes obtenidos por cruce al individuo*/
private void setGenes(individuo hijo, List<Integer> genes) {
		for(int i=0; i < genes.size(); i++) {
			hijo.setGen(i, new gen(genes.get(i)));
		}
	}

/**Obtiene el punto de cruce y cruza los genes*/
private void cruceOrdinal(List<Integer> padre1, List<Integer> padre2, List<Integer> hijo1, List<Integer> hijo2) {
		Random r=new Random();
		int punto=Math.abs(r.nextInt()%padre1.size());
		for(int i=punto; i < padre1.size(); i++) {
			hijo1.set(i, padre2.get(i));
			hijo2.set(i, padre1.get(i));
		}
	}

/**Codifica los genes en función de su posición en la lista dinámica*/
	private void codificar(List<Integer> lista) {
		List<Integer> copia=new ArrayList<Integer>(listaDinamica);
		for(int i=0; i < lista.size(); i++) {
			lista.set(i, getPosicionDinamica(copia, lista.get(i)));
		}
	}
	
/**Obtiene la posición del valor en la copia de la lista dinámica, siempre existe.
 * Tambien elimina el elemento que está en esa posición de la lista */
private Integer getPosicionDinamica(List<Integer> lista, int valor) {
	int i=0;
	while(i < lista.size() && valor!=lista.get(i)) {
		i++;
	}
	lista.remove(i);
	return new Integer(i);
}

/**Pasa los fenotipos de los genes a una lista*/
	private void copiarGenes(List<Integer> genes, individuo padre) {
		for(int i=0; i < padre.getSizeCromosoma(); i++) {
			genes.add(new Integer(padre.getCromosomaAt(i).getFenotipo()));
		}
	}

}

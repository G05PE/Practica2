package cruces;

import java.util.ArrayList;

import poblacion.individuo;
import poblacion.poblacion;

public class erx extends algoritmoCruce {

	ArrayList<ArrayList<Integer>> tablaGenes = new ArrayList<ArrayList<Integer>>();
	
	@Override
	public poblacion cruzar(poblacion seleccionados, double prob) {
		ini(prob, seleccionados);
		seleccionaReproductores();	
		cruzaReproductores();
		
		return getDescendientes();
	}
	
	
	private void cruzaReproductores() {
				
		//Inicializa los hijos
		for(int i = 0; i < getReproductoresSize() - 1; i+=2) {
			individuo padre1 = new individuo();
			individuo padre2 = new individuo();
					
			creaTabla(padre1, padre2);
		}
	}


	private void creaTabla(individuo padre1, individuo padre2) {
		
		int size = padre1.getSizeCromosoma();
		
		//Añadimos los valores diferentes a la tabla
		for(int i = 0; i < size; i++) {
			boolean existe = false;
			int pos = 0;
			
			//Busca cada valor en la tabla
			for(int j = 0; j < tablaGenes.size() && !existe; j++) {
				if(tablaGenes.get(j).get(0) == padre1.getCromosomaAt(i).getGenotipo()) { 
					existe = true;
					pos = j;
				}
			}
			
			//Si existe se comprueban los contiguos
			//Si no existe se genera uno nuevo con los nuevos contiguos
			if(existe) {
				ArrayList<Integer> tabla =  new ArrayList<Integer>();
				tabla = tablaGenes.get(pos);
				
				for(int j = 0; j < tabla.size(); j++) {
					if(!tabla.contains(padre1.getCromosomaAt((i-1)%size).getGenotipo())) 
						tablaGenes.get(pos).add(padre1.getCromosomaAt(i).getGenotipo());
					if(!tabla.contains(padre1.getCromosomaAt((i+1)%size).getGenotipo())) 
						tablaGenes.get(pos).add(padre1.getCromosomaAt(i).getGenotipo());
				}
			}
			else {
				ArrayList<Integer> contiguos = new ArrayList<Integer>();
				contiguos.add(padre1.getCromosomaAt(i).getGenotipo());
				contiguos.add(padre1.getCromosomaAt((i-1)%size).getGenotipo());
				contiguos.add(padre1.getCromosomaAt((i+1)%size).getGenotipo());
				tablaGenes.add(contiguos);
			}		
		}
	}

}

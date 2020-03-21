package mutacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import genetica.gen;
import poblacion.individuo;
import poblacion.poblacion;

public class heuristic extends mutacion{

	
	List<Integer> puntos = new ArrayList<Integer>();
	
	@Override
	public void mutar(poblacion poblacion, double probMutacion) {
		
		Random rand = new Random();
		double prob = Math.random()%1;
		
		
		for(int i = 0; i < poblacion.getSize(); i++) {
			if(prob < probMutacion){
				//Elegimos el numero de elementos a permutar
				int n = rand.nextInt()%poblacion.getIndividuo(i).getSizeCromosoma();
				while(n <= 1) n = rand.nextInt()%poblacion.getIndividuo(i).getSizeCromosoma();
				
				//Escogemos las posiciones y las añadimos a la lista
			 	for(int j = 0; j < n; j++) {
			 		int nuevo = rand.nextInt()%poblacion.getIndividuo(i).getSizeCromosoma();
					if(nuevo < 0)nuevo = -nuevo;
			 		while(puntos.contains(nuevo)) {
			 			nuevo = rand.nextInt()%poblacion.getIndividuo(i).getSizeCromosoma();
						if(n < 0)n = -n;
			 		}
			 		puntos.add(nuevo);
			 	}
			 	
			 	//Creamos las permutaciones y elegimos la mejor dentro de cada invidiuo
			 	individuo solucionAct = new individuo(poblacion.getFuncion());
			 	individuo mejorSolucion = new individuo(poblacion.getFuncion());
			 	permuta_y_selecciona(poblacion.getIndividuo(i), 0, 0, solucionAct, mejorSolucion);
			}
		}		
	}

	private void permuta_y_selecciona(individuo act, int k, double mejorFitness, individuo solucionAct, individuo mejorSolcion) {
	    		
		for(int i = 0; i < puntos.size(); i++) {
        	int pos = puntos.get(i);

			if(!solucionAct.existeGen(act.getCromosomaAt(pos), 0, solucionAct.getSizeCromosoma())) {	
				
		        if (k == puntos.size()) {
		        	double fitnessAct = solucionAct.getFitness();
		        	if(fitnessAct > mejorFitness) {
		        		mejorFitness = fitnessAct;
		        		mejorSolcion = solucionAct;
		        	}
		    
		        } 
		        else {
		        	solucionAct.setGen(i, act.getCromosomaAt(pos));
		        	permuta_y_selecciona(act, k+1, mejorFitness, solucionAct, mejorSolcion);
		        	solucionAct.quitaGen(pos);
		        }
			}
	    }
	}
}

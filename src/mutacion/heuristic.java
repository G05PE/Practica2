package mutacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import poblacion.individuo;
import poblacion.poblacion;

public class heuristic extends mutacion{

	
	List<Integer> puntos = new ArrayList<Integer>();
	List<Boolean> leidos = new ArrayList<Boolean>();
	int aux = 0;

	
	@Override
	public void mutar(poblacion poblacion, double probMutacion) {
		
		Random rand = new Random();
		double prob = Math.random()%1;
		
		
		for(int i = 0; i < poblacion.getSize(); i++) {
			if(prob < probMutacion){
				
				int maxLong = poblacion.getIndividuo(i).getSizeCromosoma();
				
				//Elegimos el numero de elementos a permutar (Recomiendan entre 2 y 3)
				int n = 3;
				double randP = Math.random()%1;
				if(randP <= 0.5) n = 2;
				
				//Escogemos las posiciones y las añadimos a la lista
			 	for(int j = 0; j < n; j++) {
			 		int nuevo = rand.nextInt(maxLong);
			 		while(puntos.contains(nuevo)) nuevo = rand.nextInt(maxLong);
			 		puntos.add(nuevo);
			 		leidos.add(false);
			 	}

			 	
			 	//Creamos las permutaciones y elegimos la mejor dentro de cada invidiuo
			 	individuo solucionAct = poblacion.getIndividuo(i);
			 	individuo mejorSol = poblacion.getIndividuo(i);
			 	permuta_y_selecciona(poblacion.getIndividuo(i), 0, 0, solucionAct, mejorSol);
			 	poblacion.setIndividuoAt(i, mejorSol);
			}
		}		
	}

	//Genera todas las permutaciones posibles y selecciona la mejor (mejorSol)
	private void permuta_y_selecciona(individuo act, int k, double mejorFitness, individuo solucionAct, individuo mejorSol) {
	    		
		//Esquema basico de vuelta atras
		//Recorre k niveles sabiendo los elementos que ha metido en la solucion actual
		//Cuando llega al size requerido pregunta si el fitnes es mejor que el anterior y si lo es lo actualiza
		for(int i = 0; i < puntos.size(); i++) {
        	int pos = puntos.get(i);
        	
			if(!leidos.get(i)) {	
		        if (k == puntos.size() - 1) {
		        	solucionAct.setGen(puntos.get(aux), act.getCromosomaAt(pos));
		        	aux = 0;
		        	double fitnessAct = solucionAct.getFitness();
		        	if(fitnessAct > mejorFitness) {
		        		mejorFitness = fitnessAct;
		        		mejorSol = solucionAct;
		        	}
		        } 
		        
		        else {
		        	leidos.set(i, true);
		        	solucionAct.setGen(puntos.get(aux), act.getCromosomaAt(pos));
		        	aux++;
		        	permuta_y_selecciona(act, k+1, mejorFitness, solucionAct, mejorSol);
		        	leidos.set(i, false);
		        }
			}
	    }
	}
}


package mutacion;

import java.util.ArrayList;
import java.util.Random;

import poblacion.individuo;
import poblacion.poblacion;

public class heuristic extends mutacion{

	ArrayList<Integer> puntos;

	@Override
	public void mutar(poblacion poblacion, double probMutacion) {
		Random rand = new Random();
		double prob = Math.random()%1;
		
		
		for(int i = 0; i < poblacion.getSize(); i++) {
			if(prob < probMutacion){
				
				puntos = new ArrayList<Integer>();
				int maxLong = poblacion.getIndividuo(i).getSizeCromosoma();
				
				//Elegimos el numero de elementos puntos permutar (Recomiendan entre 2 y 3)
				int n = 3;
				if(Math.random()%1 <= 0.5) n = 2;
				
				//Escogemos las posiciones y las añadimos puntos la lista
			 	for(int j = 0; j < n; j++) {
			 		int nuevo = rand.nextInt(maxLong);
			 		while(puntos.contains(nuevo)) nuevo = rand.nextInt(maxLong);
			 		puntos.add(nuevo);
			 	}
			 
			 	
			 	//Genera las permutaciones
				ArrayList<ArrayList<Integer>> permut = new ArrayList<ArrayList<Integer>>();
			 	permut = permut(puntos);
			 	
			 	individuo mutado = new individuo();
			 	int act;
			 	
			 	for(int p = 0; p < permut.size(); p++) {
			 		mutado = poblacion.getIndividuo(i);
			 		for(int q = 0; q < puntos.size(); q++) {
			 			act = permut.get(p).get(q);
			 			mutado.setGen(puntos.get(q), mutado.getCromosomaAt(act));
			 		}
			 		
			 		mutado.calcularFitness();
			 		if(poblacion.getIndividuo(i).getFitness() < mutado.getFitness()) poblacion.setIndividuoAt(i, mutado);
			 	}			 	
			}
		}
	}
	

	private ArrayList<ArrayList<Integer>> permut(ArrayList<Integer> puntos) {

		ArrayList<ArrayList<Integer>> ret= new ArrayList<ArrayList<Integer>>();

		//Caso base
		if(puntos.size() == 1){
			ret.add(new ArrayList<Integer>(puntos));
		}

		else {
			for(int i = 0; i < puntos.size(); i++){
				ArrayList<Integer> aux = new ArrayList<Integer>(puntos);
				int act = puntos.get( i );
				aux.remove( i );
	
				//Crea lo que ya teníamos y le cambia lo nuevo
				ArrayList<ArrayList<Integer>> res = permut(aux);
	
				for(int j = 0; j < res.size(); j++){
					ArrayList<Integer> sig = new ArrayList<Integer>();
					
					sig.add(act);
					sig.addAll(res.get(j) );
					ret.add(sig);
				}
			}
		}
		return ret;
	}
}


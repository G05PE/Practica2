package mutacion;

import java.util.ArrayList;
import java.util.Random;

import poblacion.individuo;
import poblacion.poblacion;

public class heuristic extends mutacion{

	private ArrayList<Integer> puntos;

	@Override
	public void mutar(poblacion poblacion, double probMutacion) {
		
		Random rand = new Random();
		double prob = Math.random()%1;
		
		for(int i = 0; i < poblacion.getSize(); i++) {	
			if(prob < probMutacion){
	
				int maxLong = poblacion.getIndividuo(i).getSizeCromosoma();
				puntos = new ArrayList<Integer>();				
				individuo mutado = new individuo();
				
				//Elegimos el numero de elementos puntos permutar y escogemos las posiciones 
				int n = 3;
				if(Math.random()%1 <= 0.5) n = 2;
			
				//Fuerza a que los puntos sean diferentes
			 	for(int j = 0; j < n; j++) {
			 		int nuevo = rand.nextInt(maxLong);
			 		while(puntos.contains(nuevo)) nuevo = rand.nextInt(maxLong);
			 		puntos.add(nuevo);
			 	}
			 	
			 	//Genera las permutaciones y las prueba
				ArrayList<ArrayList<Integer>> permut = new ArrayList<ArrayList<Integer>>();
			 	permut = permut(puntos);
			 	
			 	for(int j = 0; j < permut.size(); j++) {
			 		mutado = poblacion.getIndividuo(i);
			 		for(int p = 0; p < puntos.size(); p++) {
			 			int act = permut.get(j).get(p);
			 			mutado.setGen(puntos.get(p), mutado.getCromosomaAt(act));
			 		}
			 		
			 		//Descarta las combinaciones que duplican valores
			 		if(!tieneRepetidos(mutado)) {
				 		mutado.calcularFitness();
				 		if(poblacion.getIndividuo(i).getFitness() < mutado.getFitness()) poblacion.setIndividuoAt(i, mutado);
			 		}
			 	}	
			}
		}
	}
	

	//Se asegura de que no tiene genes repetidos
	private boolean tieneRepetidos(individuo mutado) {

		for(int i = 0; i < mutado.getSizeCromosoma(); i++){
			for(int j = i+1; j < mutado.getSizeCromosoma(); j++) {
				if(mutado.getCromosomaAt(i).getGenotipo() == mutado.getCromosomaAt(j).getGenotipo()) return true;
			}
		}
		
		return false;
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


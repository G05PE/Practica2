package mutacion;

import java.util.ArrayList;
import java.util.Random;

import genetica.gen;
import poblacion.individuo;
import poblacion.poblacion;

public class heuristic extends mutacion{

	private ArrayList<Integer> puntos;

	@Override
	public void mutar(poblacion poblacion, double probMutacion) {
		
		Random rand = new Random();
		
		for(int i = 0; i < poblacion.getSize(); i++) {
			double prob = Math.random()%1;
			if(prob < probMutacion){
	
				int maxLong = poblacion.getIndividuo(i).getSizeCromosoma();
				puntos = new ArrayList<Integer>();				
				individuo mutado = new individuo(poblacion.getIndividuo(i));
			 	
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
			 	
			 	for(int j = 1; j < permut.size(); j++) {
			 		for(int p = 0; p < puntos.size(); p++) {
			 			gen genAct= poblacion.getIndividuo(i).getCromosomaAt(permut.get(j).get(p));
			 			mutado.setGen(puntos.get(p), genAct);
			 		}
			 		
			 		mutado.calcularFitness();
			 		double fitnessNormal = poblacion.getIndividuo(i).getFitness();
			 		double fitnessMutado = mutado.getFitness();
			 		
			 		if(fitnessNormal < fitnessMutado) {
			 			poblacion.setIndividuoAt(i, mutado);
			 		}
			 	}	
			 		 
			}
			poblacion.getIndividuo(i).calcularFitness();
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


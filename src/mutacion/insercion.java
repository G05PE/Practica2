package mutacion;

import java.util.Random;

import genetica.gen;
import poblacion.individuo;
import poblacion.poblacion;

public class insercion extends mutacion{

	
	individuo mutado = new individuo();

	@Override
	public void mutar(poblacion poblacion, double probMutacion) {
		
		Random rand = new Random();
		double prob = Math.random()%1;
			
		for(int i = 0; i < poblacion.getSize(); i++) {
			if(prob < probMutacion){
				mutado = poblacion.getIndividuo(i);
				
				int maxLong = mutado.getSizeCromosoma();
				
				//Escogemos el numero de elementos mutados
				int n = rand.nextInt(maxLong);
				
				//Escogemos las n posiciones y mutamos
			 	for(int j = 0; j < n; j++) {
			 		int origen = rand.nextInt(maxLong);			 		
			 		int destino = rand.nextInt(maxLong);
			 		while(destino == origen) destino = rand.nextInt(maxLong);
			 		
			 		//Movemos las posiciones entre origen y destino
			 		gen aux = mutado.getCromosomaAt(origen);
			 		if(origen < destino) {
			 			for(int u = origen + 1; u <= destino; u++) {
				 			mutado.setGen(u-1, mutado.getCromosomaAt(u));
				 		}			 			
			 		}
			 		else {
			 			for(int u = origen -1; u >= destino; u--) {
				 			mutado.setGen(u+1, mutado.getCromosomaAt(u));
				 		}			 			
			 			
			 			//Finalmente insertamos el origen guardado en el destino
			 			mutado.setGen(destino, aux);
			 		}
			 		
			 	}
			 	poblacion.setIndividuoAt(i, mutado);
			}
		}
	}

}

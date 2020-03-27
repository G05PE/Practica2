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
			 		int var1 = rand.nextInt(maxLong);			 		
			 		int var2 = rand.nextInt(maxLong);
			 		while(var2 == var1) var2 = rand.nextInt(maxLong);
			 		
			 		//Movemos las posiciones entre var1 y var2
			 		gen aux = new gen();
			 		aux = mutado.getCromosomaAt(var1);
			 		
			 		if(var1 < var2) {
			 			for(int u = var1 + 1; u <= var2; u++)
				 			mutado.setGen(u-1, mutado.getCromosomaAt(u));
			 		}
			 		else {
			 			for(int u = var1 -1; u >= var2; u--) {
				 			mutado.setGen(u+1, mutado.getCromosomaAt(u));
				 		}			 			
			 			
			 			//Finalmente insertamos el var1 guardado en el var2
			 			mutado.setGen(var2, aux);
			 		}
			 		
			 	}
			 	poblacion.setIndividuoAt(i, mutado);
			}
		}
	}

}

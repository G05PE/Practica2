package mutacion;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import poblacion.poblacion;

public class insercion extends mutacion{

	
	List<Integer> puntos = new ArrayList<Integer>();
	
	@Override
	public void mutar(poblacion poblacion, double probMutacion) {
		
		Random rand = new Random();
		double prob = Math.random()%1;
			
		for(int i = 0; i < poblacion.getSize(); i++) {
			if(prob < probMutacion){
				int maxLong = poblacion.getIndividuo(i).getSizeCromosoma();
				
				//Escogemos el numero de elementos mutados
				int n = rand.nextInt()%maxLong;
				while(n < 2) n = rand.nextInt()%maxLong;
				
				//Escogemos las n posiciones y mutamos
			 	for(int j = 0; j < n; j++) {
			 		int nuevo = rand.nextInt()%maxLong;
			 		while(puntos.contains(nuevo) || nuevo < 0) nuevo = rand.nextInt()%maxLong;
			 		puntos.add(nuevo);
			 		//Mete el elemento en la pos n%3??
			 	}
			}
		}
	}

}

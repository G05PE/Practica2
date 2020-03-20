package mutacion;

import java.util.Random;

import poblacion.poblacion;

public class heuristic extends mutacion{

	@Override
	public void mutar(poblacion poblacion, double probMutacion) {
		
		Random rand = new Random();
		double prob = Math.random()%1;
		
		for(int i = 0; i < poblacion.getSize(); i++) {
			if(prob < probMutacion){
				 	
			}
		}
	}

}

package mutacion;

import java.util.Random;

import genetica.gen;
import poblacion.poblacion;

public class exchange extends mutacion{

	@Override
	public void mutar(poblacion poblacion, double probMutacion) {
		
		for(int i = 0; i < poblacion.getSize(); i++) {
			double prob = Math.random()%1;
			
			if(prob < probMutacion){
				Random rand = new Random();

				int var1 = rand.nextInt(poblacion.getIndividuo(i).getSizeCromosoma());
				int var2 = rand.nextInt(poblacion.getIndividuo(i).getSizeCromosoma());
				
				//Ajustamos los datos
				while(var1 == var2) var2 = rand.nextInt(poblacion.getIndividuo(i).getSizeCromosoma());
				
				gen aux = poblacion.getIndividuo(i).getCromosomaAt(var1);
				poblacion.getIndividuo(i).setGen(var1, poblacion.getIndividuo(i).getCromosomaAt(var2));
				poblacion.getIndividuo(i).setGen(var2, aux);
				poblacion.getIndividuo(i).calcularFitness();
			}
		}
	}

}

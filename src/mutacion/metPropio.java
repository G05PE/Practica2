package mutacion;

import java.util.Random;

import genetica.gen;
import poblacion.individuo;
import poblacion.poblacion;

public class metPropio extends mutacion{

	@Override
	public void mutar(poblacion poblacion, double probMutacion) {

		Random rand=new Random();
		
		for(int i = 0; i < poblacion.getSize(); i++) {
			double valor = Math.random()%1;	
			
			if(valor < probMutacion) {
				
				individuo mutado = new individuo(poblacion.getIndividuo(i));
				int tamCromosoma = poblacion.getIndividuo(i).getSizeCromosoma();
				
				//Tomamos las posiciones necesarias y aseguramos que sean diferentes
				//nuevaPos = ((var1 / tamCromosoma) * 10) % tamCromosoma
				int var1 = rand.nextInt(tamCromosoma);
				int var2 = rand.nextInt(tamCromosoma);
				int nuevaPos = (var1/tamCromosoma)*10;
					
				while(var1 == var2) {
					var1 = rand.nextInt(tamCromosoma);
					nuevaPos = (var1/tamCromosoma)*10;
				}
				
				if(var1 > var2) {
					int var3 = var1;
					var1 = var2;
					var2 = var3;
				}
			
				//Intercambia los puntos entre sí
				gen aux = mutado.getCromosomaAt(nuevaPos);
				gen aux2 = mutado.getCromosomaAt(var2);
				
				mutado.setGen(nuevaPos, mutado.getCromosomaAt(var1));
				mutado.setGen(var2, aux);
				mutado.setGen(var1, aux2);
				
				poblacion.setIndividuoAt(i, mutado);
			}
			poblacion.getIndividuo(i).calcularFitness();
		}
	}

}
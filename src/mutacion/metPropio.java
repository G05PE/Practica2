package mutacion;

import java.util.Random;

import genetica.gen;
import poblacion.individuo;
import poblacion.poblacion;

public class metPropio extends mutacion{


	@Override
	public void mutar(poblacion poblacion, double probMutacion) {
		int var1, var2;
		int tamCromosoma=poblacion.getIndividuo(0).getSizeCromosoma();
		double valor;
		
		for(int i = 0; i < poblacion.getSize(); i++) {
			valor = Math.random()%1;
			if(valor < probMutacion) {
				Random rand=new Random();
				var1 = rand.nextInt(tamCromosoma);
				var2 = rand.nextInt(tamCromosoma);
				
				while(var1 == var2)
					var2 = rand.nextInt(tamCromosoma);
			
				if(var1 > var2) {
					int aux = var1;
					var1 = var2;
					var2 = aux;
				}
				
				muta(poblacion.getIndividuo(i), var1, var2, tamCromosoma);
			}
		}
	}

	//Coge dos puntos, genera un tercero a partir de la siguiente operacion
	//((var1 / tamCromosoma) * 10) % tamCromosoma
	//Ahora intercambia los puntos
	//No se pierden datos ya que si dos puntos coinciden se mantiene el valor en los auxialiares
	private void muta(individuo individuo, int var1, int var2, int tamCromosoma) {
		int nuevaPos = (var1/tamCromosoma)*10;
		gen aux = individuo.getCromosomaAt(nuevaPos%tamCromosoma);
		gen aux2 = individuo.getCromosomaAt(var2);
		
		individuo.setGen(nuevaPos%tamCromosoma, individuo.getCromosomaAt(var1));
		individuo.setGen(var2, aux);
		individuo.setGen(var1, aux2);
	}

}
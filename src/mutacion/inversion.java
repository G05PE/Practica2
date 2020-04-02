package mutacion;

import java.util.Random;

import genetica.gen;
import poblacion.individuo;
import poblacion.poblacion;

public class inversion extends mutacion{

	@Override
	public void mutar(poblacion poblacion, double probMutacion) {
		int punto1=0, punto2=0;
		int tamCromosoma=poblacion.getIndividuo(0).getSizeCromosoma();
		double valor;
		for(int i=0; i < poblacion.getSize(); i++) {
			valor=Math.random()%1;
			if(valor < probMutacion) {
				Random rand=new Random();
				punto1=rand.nextInt(tamCromosoma);
				punto2=rand.nextInt(tamCromosoma);
				while(punto1 == punto2) {
					punto2=rand.nextInt(tamCromosoma);
				}
				if(punto1 > punto2) {
					int aux=punto1;
					punto1=punto2;
					punto2=aux;
				}
				invertir(poblacion.getIndividuo(i), punto1, punto2);
			}
			poblacion.getIndividuo(i).calcularFitness();
		}
	}

	private void invertir(individuo individuo, int punto1, int punto2) {
		int i=punto1, j=punto2;
		gen aux;
		while(i < j) {
			aux=individuo.getCromosomaAt(i);
			individuo.setGen(i, individuo.getCromosomaAt(j));
			individuo.setGen(j, aux);
			i++;
			j--;
		}
	}

}

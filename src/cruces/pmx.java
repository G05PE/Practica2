package cruces;

import java.util.Random;

import poblacion.individuo;
import poblacion.poblacion;

public class pmx extends algoritmoCruce {

	@Override
	public poblacion cruzar(poblacion seleccionados, double prob) {
		ini(prob, seleccionados);
		seleccionaReproductores();	
	
		creaDescendientes();
		return getDescendientes();
	}


	//Elige un tramo de uno de los reproductores y cruza preservando el orden y la posición
	private void creaDescendientes() {
		//Elige aleatoriamente dos puntos de corte
		int var1 = 0, var2 = 0;
		Random rand = new Random();
		
		//Inicializa los hijos
		for(int i = 0; i < getReproductoresSize(); i+=2) {
			
			individuo padre1 = getReproductorAt(i);
			individuo padre2 = getReproductorAt(i+1);
			individuo hijo1 = new individuo(padre1);
			individuo hijo2 = new individuo(padre2);

			
//			hijo1.calcularFitness();
//			hijo2.calcularFitness();

			var1 = rand.nextInt(padre1.getSizeCromosoma());
			var2 = rand.nextInt(padre1.getSizeCromosoma());
			
			//Fuerza que los puntos sean diferentes
			while(var1 == var2) {
				var2 = rand.nextInt(getReproductorAt(i).getSizeCromosoma());
			}
			
			//Los ordenamos (var1 <= var2)
			if(var2 < var1) {
				int aux = var1;
				var1 = var2;
				var2 = aux;
			}
			
			//Cambiamos el tramo [var1, var2]
			for(int u = var1; u < var2; u++){
				hijo1.setGen(u, padre2.getCromosomaAt(u));
				hijo2.setGen(u, padre1.getCromosomaAt(u));
			}
				
			//Cambia la primera parte
			for(int u = 0; u < var1; u++) {
				for(int j = var1; j < var2; j++) {
					if(hijo1.getCromosomaAt(u).getGenotipo() == hijo1.getCromosomaAt(j).getGenotipo()) {
						hijo1.setGen(u, hijo2.getCromosomaAt(j));
					}
					if(hijo2.getCromosomaAt(u).getGenotipo() == hijo2.getCromosomaAt(j).getGenotipo()) {
						hijo2.setGen(u, hijo1.getCromosomaAt(j));					
					}
				}
			}
			
			//Cambia la segunda parte
			for(int u = var2; u < padre1.getCromosoma().size(); u++) {
				for(int j = var1; j < var2; j++) {
					if(hijo1.getCromosomaAt(u).getGenotipo() == hijo1.getCromosomaAt(j).getGenotipo()) {
						hijo1.setGen(u, hijo2.getCromosomaAt(j));
					}
					if(hijo2.getCromosomaAt(u).getGenotipo() == hijo2.getCromosomaAt(j).getGenotipo()) {
						hijo2.setGen(u, hijo1.getCromosomaAt(j));					
					}
				}	
			}
			
			
			setDescendienteAt(i, hijo1);
			setDescendienteAt(i+1, hijo2);
		}
	}
}

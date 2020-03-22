package cruces;

import java.util.Random;

import genetica.gen;
import poblacion.individuo;
import poblacion.poblacion;

public class pmx extends algoritmoCruce {

	@Override
	public poblacion cruzar(poblacion seleccionados, double prob) {
		ini(prob, seleccionados);
		seleccionaReproductores();	
		cruzaReproductores();
		
		return getDescendientes();
	}


	//Elige un tramo de uno de los reproductores y cruza preservando el orden y la posición
	private void cruzaReproductores() {
		//Elige aleatoriamente dos puntos de corte
		int var1 = 0, var2 = 0;
		Random rand = new Random();
				
		//Inicializa los hijos
		for(int i = 0; i < getReproductoresSize() - 1; i+=2) {
			individuo ind1 = getReproductorAt(i);
			individuo ind2 = getReproductorAt(i + 1);

			var1 = rand.nextInt()%getReproductorAt(i).getSizeCromosoma();
			var2 = rand.nextInt()%getReproductorAt(i).getSizeCromosoma();
			while(var1 < 0) var1 = -var1;
			while(var2 < 0) var2 = -var2;
			
			//Fuerza que los puntos sean diferentes
			while(var1 == var2) {
				var2 = rand.nextInt()%getReproductorAt(i).getSizeCromosoma();
				if(var2 < 0) var2 = -var2;
			}
			
			//Los ordenamos (var1 <= var2)
			if(var2 < var1) {
				int aux = var1;
				var1 = var2;
				var2 = aux;
			}
			

			ind1.calcularFitness();
			ind2.calcularFitness();
			
			modCromosomas(var1, var2, ind1, ind2);		
			this.setDescendienteAt(i, ind1);
			this.setDescendienteAt(i+1, ind2);
		}
	}


	private void modCromosomas(int var1, int var2, individuo ind1, individuo ind2) {
		//Intercambia las dos subcadenas entre [var1, var2]
		for(int u = var1; u < var2; u++){
			gen aux = ind1.getCromosomaAt(u);
			ind1.setGen(u, ind2.getCromosomaAt(u));
			ind2.setGen(u, aux);
		}
			
		//Cambia la primera parte
		for(int u = 0; u < var1; u++) {
			for(int v = var1; v < var2; v++) {
				if(ind1.getCromosomaAt(u) == ind1.getCromosomaAt(v)) {
					ind1.setGen(u, ind2.getCromosomaAt(v));
				}
				if(ind2.getCromosomaAt(u) == ind2.getCromosomaAt(v)) {
					ind2.setGen(u, ind1.getCromosomaAt(v));					
				}
			}
		}
		
		//Cambia la segunda parte
		for(int u = var2; u < ind1.getCromosoma().size(); u++) {
			for(int v = var1; v < var2; v++) {
				if(ind1.getCromosomaAt(u) == ind1.getCromosomaAt(v)) {
					ind1.setGen(u, ind2.getCromosomaAt(v));
				}
				if(ind2.getCromosomaAt(u) == ind2.getCromosomaAt(v)) {
					ind2.setGen(u, ind1.getCromosomaAt(v));					
				}
			}	
		}
	}
}

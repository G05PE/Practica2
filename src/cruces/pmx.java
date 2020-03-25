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
	
		for(int i = 0; i < getReproductoresSize() - 1; i+=2) {
			individuo ind1 = new individuo(getReproductorAt(i));
			individuo ind2 = new individuo(getReproductorAt(i + 1));
			
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
				for(int v = var1; v < var2; v++) {
					if(ind1.getCromosomaAt(u).getGenotipo() == ind1.getCromosomaAt(v).getGenotipo()) {
						ind1.setGen(u, ind2.getCromosomaAt(v));
					}
					if(ind2.getCromosomaAt(u).getGenotipo() == ind2.getCromosomaAt(v).getGenotipo()) {
						ind2.setGen(u, ind1.getCromosomaAt(v));					}
				}
			}
			
			//Intercambia las dos subcadenas entre [var1, var2]
			for(int u = var1; u != var2; u++){
				gen aux = new gen(ind1.getCromosomaAt(u));
				ind1.setGen(u, ind2.getCromosomaAt(u));
				ind2.setGen(u, aux);
			}
				
			//Cambia la segunda parte
			for(int u = var2; u < ind1.getCromosoma().size(); u++) {
				for(int v = var1; v < var2; v++) {
					if(ind1.getCromosomaAt(u).getGenotipo() == ind1.getCromosomaAt(v).getGenotipo()) {
						ind1.setGen(u, ind2.getCromosomaAt(v));
					}
					if(ind2.getCromosomaAt(u).getGenotipo() == ind2.getCromosomaAt(v).getGenotipo()) {
						ind2.setGen(u, ind1.getCromosomaAt(v));					}
					}	
				}
						
			this.setDescendienteAt(i, ind1);
			this.setDescendienteAt(i+1, ind2);
		}
	}
}

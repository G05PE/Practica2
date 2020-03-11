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
		//CruzaReproductores
		return getDescendientes();
	}

	//Elige un tramo de uno de los reproductores y
	//cruza preservando el orden y la posición de la mayor
	//cantidad posible de elementos del otro.
	private void cruzaReproductores() {
		//Elige aleatoriamente dos puntos de corte
		Random rand = new Random();
		int var1 = rand.nextInt()%getReproductoresSize();
		int var2 = rand.nextInt()%getReproductoresSize();
		
		//Fuerza que los puntos sean diferentes
		while(var1 == var2)var2 = rand.nextInt()%getReproductoresSize();
		
		//Hacemos los puntos positivos y los ordenamos (var1 <= var2)
		if(var1 < 0) var1 = -var1;
		if(var2 < 0) var2 = -var2;
		if(var2 < var1) {
			int aux = var1;
			var1 = var2;
			var2 = aux;
		}
		
		//Rellenamos todos los reproductores
		for(int i = 0; i < getReproductoresSize(); i++) {
			this.addDescendiente(getReproductor(i));	
		}
		
		
		//Intercambia las dos subcadenas entre dichos puntos y mete los descendientes
		for(int i = 0; i < getReproductoresSize(); i++) {
			individuo ind1 = getReproductorAt(i);
			individuo ind2 = getReproductorAt(i + 1);
			
			for(int j = var1; j < var2 - var1; j++){
				gen aux = ind1.getCromosomaAt(j);
				ind1.setGen(j, ind2.getCromosomaAt(j));
				ind2.setGen(j, aux);
			}
			
			this.addDescendiente(ind1);
			this.addDescendiente(ind2);
		}
	}
	
}

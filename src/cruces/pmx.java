package cruces;

import java.util.Random;

import poblacion.individuo;
import poblacion.poblacion;

public class pmx extends algoritmoCruce {
	
	private boolean [] h1;
	private boolean [] h2;
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
		int cont=0;
		Random rand = new Random();
		//Inicializa los hijos
		for(int i = 0; i < getReproductoresSize(); i+=2) {
			
			individuo padre1 = getReproductorAt(i);
			individuo padre2 = getReproductorAt(i+1);
			individuo hijo1 = new individuo(padre1);
			individuo hijo2 = new individuo(padre2);
			int tam=padre1.getSizeCromosoma();
			h1=new boolean[tam];
			h2=new boolean[tam];
			inicializarArray(h1, tam);
			inicializarArray(h2, tam);
			var1 = rand.nextInt(tam);
			var2 = rand.nextInt(tam);
			
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
			
			
			for(int j=0; j < var1; j++) {
				for(int k=var1; k < var2; k++) {
					if(hijo1.getCromosomaAt(j).getGenotipo()==hijo1.getCromosomaAt(k).getGenotipo()) {
						h1[j]=false;
					}
					if(hijo2.getCromosomaAt(j).getGenotipo()==hijo2.getCromosomaAt(k).getGenotipo()) {
						h2[j]=false;
					}
				}
			}
			
			for(int j=var2; j < tam; j++) {
				for(int k=var1; k < var2; k++) {
					if(hijo1.getCromosomaAt(j).getGenotipo()==hijo1.getCromosomaAt(k).getGenotipo()) {
						h1[j]=false;
					}
					if(hijo2.getCromosomaAt(j).getGenotipo()==hijo2.getCromosomaAt(k).getGenotipo()) {
						h2[j]=false;
					}
				}
			}
			
			
			int cont1=var1, cont2=var1;
			for(int j=0; j < tam; j++) {
				if(!h1[j]) {
					while(hijo1.existeGen(padre1.getCromosomaAt(cont1))) {
						cont1++;
					}
					hijo1.setGen(j, padre1.getCromosomaAt(cont1));
					cont1++;
				}
				if(!h2[j]) {
					while(hijo2.existeGen(padre2.getCromosomaAt(cont2))) {
						cont2++;
					}
					hijo2.setGen(j, padre2.getCromosomaAt(cont2));
					cont2++;
				}
			}
			
			hijo1.calcularFitness();
			hijo2.calcularFitness();
			setDescendienteAt(i, hijo1);
			setDescendienteAt(i+1, hijo2);
		}
	}

	private void inicializarArray(boolean[] h, int tam) {
		for(int i=0; i < tam; i++) {
			h[i]=true;
		}
	}
}

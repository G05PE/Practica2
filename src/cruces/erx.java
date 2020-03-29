package cruces;

import java.util.ArrayList;

import poblacion.individuo;
import poblacion.poblacion;

public class erx extends algoritmoCruce {

	ArrayList<ArrayList<Integer>> tablaGenes;
	private boolean [] h1;
	private boolean [] h2;

	@Override
	public poblacion cruzar(poblacion seleccionados, double prob) {
		ini(prob, seleccionados);
		seleccionaReproductores();	
		creaDescendientes();

		return getDescendientes();
	}


	private void creaDescendientes() {
		for(int i = 0; i < getReproductoresSize(); i+=2) {
			int tam = getReproductorAt(i).getSizeCromosoma();
			individuo padre1 = getReproductorAt(i);
			individuo padre2 = getReproductorAt(i+1);
			individuo hijo1 = new individuo(padre1);
			individuo hijo2 = new individuo(padre2);			
			h1=new boolean[tam];
			h2=new boolean[tam];
			inicializarArray(h1, tam);
			inicializarArray(h2, tam);

			
			tablaGenes = new ArrayList<ArrayList<Integer>>();
			creaTabla(padre1, padre2, tam);

			//Elegimos el primer gen
			if(Math.random()%1 < 0.5) {
				hijo1.setGen(0, padre1.getCromosomaAt(0));
				hijo2.setGen(0, padre2.getCromosomaAt(0));
			}
			else {
				hijo1.setGen(0, padre2.getCromosomaAt(1));
				hijo2.setGen(0, padre1.getCromosomaAt(0));
			}
			
			//Resto de genes
			int cont1 = 0, cont2 = 0;
			for(int j = 1; j < tam; j++) {
				//Busca los contiguos y coge el que menos conexiones tenga
				ArrayList<ArrayList<Integer>> contiguos = buscaContiguos();
				if(contiguos.size() < contiguos.size())
					//Mete el primero
					hijo1.setGen(j, gen);
				else if ((contiguos.size() > contiguos.get(1).size()));
					//Mete el segundo
					hijo1.setGen(j, contiguos.get(1));
				else {
					if(Math.random()%1 <= 0.5)//Mete el primero
					else //Mete el segundo
				}
				
			}
			for(int j = 1; j < tam; j++) {
				ArrayList<Integer> contiguos = buscaContiguos();
			}
			
			
		}
	}

	private ArrayList<ArrayList<Integer>> buscaContiguos() {
		
	}


	private void creaTabla(individuo padre1, individuo padre2, int size) {	

		//Añadimos los valores diferentes a la tabla
		for(int i = 0; i < size; i++) {
			boolean existe = false;
			int pos = 0;

			//Busca cada valor en la tabla
			for(int j = 0; j < tablaGenes.size() && !existe; j++) {
				if(tablaGenes.get(j).get(0) == padre1.getCromosomaAt(i).getGenotipo()) { 
					existe = true;
					pos = j;
				}
			}

			//Si existe se comprueban los contiguos
			//Si no existe se genera uno nuevo con los nuevos contiguos
			if(existe) {
				ArrayList<Integer> tabla =  new ArrayList<Integer>();
				tabla = tablaGenes.get(pos);

				for(int j = 0; j < tabla.size(); j++) {
					if(!tabla.contains(padre1.getCromosomaAt((i-1)%size).getGenotipo())) 
						tablaGenes.get(pos).add(padre1.getCromosomaAt(i).getGenotipo());
					if(!tabla.contains(padre1.getCromosomaAt((i+1)%size).getGenotipo())) 
						tablaGenes.get(pos).add(padre1.getCromosomaAt(i).getGenotipo());
				}
			}
			else {
				
				int anterior = (i-1);
				int siguiente = (i+1);
				
				//Manera cutre pero no me funciona el modulo
				if(anterior == -1)anterior = size-1;
				if(siguiente == size) siguiente = 0;
				else if(siguiente == size + 1) {
					siguiente = 1;
				}
				

				
				ArrayList<Integer> contiguos = new ArrayList<Integer>();
				contiguos.add(padre1.getCromosomaAt(i).getGenotipo());
				contiguos.add(padre1.getCromosomaAt(anterior).getGenotipo());
				contiguos.add(padre1.getCromosomaAt(siguiente).getGenotipo());
				tablaGenes.add(contiguos);
			}		
		}
	}

	private void inicializarArray(boolean[] h, int tam) {
		for(int i=0; i < tam; i++) {
			h[i]=true;
		}
	}
}



package cruces;

import java.util.ArrayList;

import genetica.gen;
import poblacion.individuo;
import poblacion.poblacion;

public class erx extends algoritmoCruce {

	private ArrayList<ArrayList<gen>> tablaGenes;
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
			
			//Marcaje
			h1=new boolean[tam];
			h2=new boolean[tam];
			inicializarArray(h1, tam);
			inicializarArray(h2, tam);
			
			tablaGenes = new ArrayList<ArrayList<gen>>();
			creaTabla(padre1, padre2, tam);
			
			
			if(Math.random()%1 < 0.5) {				
				hijo1.setGen(0, padre1.getCromosomaAt(0));
				hijo2.setGen(0, padre2.getCromosomaAt(0));
			}
			else {
				hijo1.setGen(0, padre2.getCromosomaAt(0));
				hijo2.setGen(0, padre1.getCromosomaAt(0));
			}
			
			h1[0] = false;
			h2[0] = false;
		
			for(int j = 1; j < tam; j++) {
				gen nuevo1 = buscaSiguienteGen(hijo1, j - 1);
				hijo1.setGen(i, nuevo1);
				marca(hijo1, nuevo1, 1);
				
				gen nuevo2 = buscaSiguienteGen(hijo2, j - 1);
				hijo2.setGen(i, nuevo2);
				marca(hijo2, nuevo2, 2);
			}
		}
	}


	private void marca(individuo hijo, gen nuevo, int numHijo) {
		for(int i = 0; i < hijo.getSizeCromosoma(); i++) {
			if(hijo.getCromosomaAt(i) == nuevo) {
				if(numHijo == 1) h1[i] = false;
				else h2[i] = false;
			}
		}
	}


	private gen buscaSiguienteGen(individuo hijo, int cont) {
		
		//Busco los genes contiguos al actual
		ArrayList<gen> contiguos = new ArrayList<gen>();
		ArrayList<Integer> contSize = new ArrayList<Integer>();
		
		boolean found = false;
		for(int i = 0; i < tablaGenes.size() && !found; i++) {
			if(tablaGenes.get(i).get(0) == hijo.getCromosomaAt(cont)) {
				for(int j = 1; j < tablaGenes.get(i).size(); j++) {
					contiguos.add(tablaGenes.get(i).get(j));
				}
				found = true;
			}
		}
		//Mete el size de los genes en contSize
		for(int i = 0; i < contiguos.size(); i++) {
			for(int j = 0; j < tablaGenes.size(); j++) {
				if(tablaGenes.get(j).get(0) == contiguos.get(i)) {
					contSize.add(tablaGenes.get(j).size()-1);
				}
			}
		}
		
		
		//Busco el mejor
		int longMin = hijo.getSizeCromosoma();
		gen ret = new gen();
		
		for(int i = 0; i < contiguos.size(); i++) {
			if(contSize.get(i) < longMin) {
				longMin = contSize.get(i);
				ret = contiguos.get(i);
			}
			else if (contSize.get(i) == longMin) {
				if(Math.random()%1 >= 0.5) { //Elección parcial entre los valores con la misma longitud
					longMin = contSize.get(i);
					ret = contiguos.get(i);
				}
			}
		}
		return ret;
	}


	@SuppressWarnings("unlikely-arg-type")
	private void creaTabla(individuo padre1, individuo padre2, int size) {	

		//Añadimos los valores diferentes a la tabla
		for(int i = 0; i < size; i++) {
			boolean existe = false;
			int pos = 0;

			//Busca cada valor en la tabla
			for(int j = 0; j < tablaGenes.size() && !existe; j++) {
				if(tablaGenes.get(j).get(0).getGenotipo() == padre1.getCromosomaAt(i).getGenotipo()) { 
					existe = true;
					pos = j;
				}
			}

			//Si existe se comprueban los contiguos
			//Si no existe se genera uno nuevo con los nuevos contiguos
			if(existe) {
				ArrayList<gen> tabla =  new ArrayList<gen>();
				tabla = tablaGenes.get(pos);

				for(int j = 0; j < tabla.size(); j++) {
					if(!tabla.contains(padre1.getCromosomaAt((i-1)%size).getGenotipo())) 
						tablaGenes.get(pos).add(padre1.getCromosomaAt(i));
					if(!tabla.contains(padre1.getCromosomaAt((i+1)%size).getGenotipo())) 
						tablaGenes.get(pos).add(padre1.getCromosomaAt(i));
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
				

				
				ArrayList<gen> contiguos = new ArrayList<gen>();
				contiguos.add(padre1.getCromosomaAt(i));
				contiguos.add(padre1.getCromosomaAt(anterior));
				contiguos.add(padre1.getCromosomaAt(siguiente));
				tablaGenes.add(contiguos);
			}		
		}
	}

	private void inicializarArray(boolean[] h, int tam) {
		for(int i = 0; i < tam; i++) {
			h[i]=true;
		}
	}
}



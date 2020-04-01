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
			
			tablaGenes = new ArrayList<ArrayList<gen>>();			
			creaTabla(padre1, tam);
			creaTabla(padre2, tam);
			
			//Marcaje
			h1=new boolean[tam];
			h2=new boolean[tam];
			inicializarArray(h1, tam);
			inicializarArray(h2, tam);
			
			if(Math.random()%1 < 0.5) {				
				hijo1.setGen(0, padre1.getCromosomaAt(0));
				hijo2.setGen(0, padre2.getCromosomaAt(0));
			}
			else {
				hijo1.setGen(0, padre2.getCromosomaAt(0));
				hijo2.setGen(0, padre1.getCromosomaAt(0));
			}
			
		
			for(int j = 1; j < tam; j++) {
				gen nuevo1 = new gen(buscaSiguienteGen(padre1, j - 1, 1));
				hijo1.setGen(j, nuevo1);
				marca(hijo1, nuevo1, 1);
				
				/*gen nuevo2 = new gen(buscaSiguienteGen(padre2, j - 1, 2));
				hijo2.setGen(j, nuevo2);
				marca(hijo2, nuevo2, 2);*/
			}
			
			setDescendienteAt(i, hijo1);
			setDescendienteAt(i+1, hijo2);
		}
	}
	
	private void marca(individuo hijo, gen nuevo, int numHijo) {
		for(int i = 0; i < hijo.getSizeCromosoma(); i++) {
			if(hijo.getCromosomaAt(i) == nuevo) {
				if(numHijo == 1) h1[i] = false;
				else if (numHijo == 2) h2[i] = false;
			}
		}
	}
	
	private void inicializarArray(boolean[] h, int tam) {
		for(int i = 0; i < tam; i++) {
			h[i]=true;
		}
	}

	private gen buscaSiguienteGen(individuo padre, int cont, int numHijo) {
		
		//Busco los genes contiguos al actual
		ArrayList<gen> contiguos = new ArrayList<gen>();
		ArrayList<Integer> contSize = new ArrayList<Integer>();
		
		boolean found = false;
		for(int i = 0; i < tablaGenes.size() && !found; i++) {
			if(tablaGenes.get(i).get(0) == padre.getCromosomaAt(cont)) {
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
		int i = 0, minSize = contSize.get(0);	
		gen ret = new gen();

		//Devuelve el priemero no utilizado (tiene que tener al menos un contiguo)
		for(int j = 0; i < 1; j++) {
			int pos = buscaPosHijo(padre, contiguos.get(j)); //Posicion en el array hijo de contiguos.get(i);
			if((numHijo == 1 && h1[pos]) || (numHijo == 2 && h2[pos])) {
				ret = new gen(contiguos.get(i));	
				minSize = contSize.get(i);
				i++;
			}
			j++;
		}
		
		
		//Comprueba si existen posbilidades mejores
		for(; i < contSize.size(); i++) {
			int pos = buscaPosHijo(padre, contiguos.get(i));
			
			//Se comprueba siempre que el nuevo no se haya usado
			if((numHijo == 1 && h1[pos]) || (numHijo == 2 && h2[pos])) {
				if(minSize > contSize.get(i)) {
					minSize = contSize.get(i);
					ret = new gen(contiguos.get(i));
				}
				else if (contSize.get(i) == minSize) {
					if(Math.random()%1 >= 0.5) { //Elección parcial entre los valores con la misma longitud
						minSize = contSize.get(i);
						ret = new gen(contiguos.get(i));
					}	
				}
			}
		}
		return ret;
	}


	private int buscaPosHijo(individuo padre, gen buscado) {
		for(int i = 0; i < padre.getSizeCromosoma(); i++) {
			if(padre.getCromosomaAt(i).getGenotipo() == buscado.getGenotipo()) {
				return i;
			}
		}
		return -1;
	}


	private void creaTabla(individuo padre, int size) {	
	
		for(int i = 0; i < size; i++) {
			boolean existe = false;
			int pos = 0;

			//Busca cada valor en la tabla
			for(int j = 0; j < tablaGenes.size() && !existe; j++) {
				if(tablaGenes.get(j).get(0).getGenotipo() == padre.getCromosomaAt(i).getGenotipo()) { 
					existe = true;
					pos = j;
				}
			}

			//Si existe se comprueban los contiguos
			//Si no existe se genera uno nuevo con los nuevos contiguos
			if(existe) {
				ArrayList<gen> listaGenes =  new ArrayList<gen>();
				listaGenes= tablaGenes.get(pos);
				
				gen anterior = padre.getCromosomaAt(((i-1) < 0)?(i - 1 +size):(i-1)%size);
				gen siguiente = padre.getCromosomaAt((i+1)%size);
				
				boolean bAnt = false, bSig = false;
				for(int k = 0; k < listaGenes.size(); k++) {
					if(listaGenes.get(k).getGenotipo() == anterior.getGenotipo()) {
						bAnt = true;
					}
					if(listaGenes.get(k).getGenotipo() == siguiente.getGenotipo()) {
						bSig = true;
					}
				}
				
				if(!bAnt) tablaGenes.get(pos).add(anterior);
				if(!bSig) tablaGenes.get(pos).add(siguiente);
			
			}
			else {
				
				int anterior = ((i-1) < 0)?(i - 1 +size):(i-1)%size;
				int siguiente = (i+1);
				
				//Manera cutre pero no me funciona el modulo
				if(anterior == -1)anterior = size-1;
				if(siguiente == size) siguiente = 0;
				else if(siguiente == size + 1) {
					siguiente = 1;
				}
				

				
				ArrayList<gen> contiguos = new ArrayList<gen>();
				contiguos.add(padre.getCromosomaAt(i));
				contiguos.add(padre.getCromosomaAt(anterior));
				contiguos.add(padre.getCromosomaAt(siguiente));
				tablaGenes.add(contiguos);
			}		
		}
	}
}



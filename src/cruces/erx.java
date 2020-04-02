package cruces;

import java.util.ArrayList;

import genetica.gen;
import poblacion.individuo;
import poblacion.poblacion;

public class erx extends algoritmoCruce {

	private ArrayList<ArrayList<gen>> tablaGenes;
	
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
			
			//Inicializa los hijos
			individuo hijo1 = new individuo(padre1);
			individuo hijo2 = new individuo(padre2);		
			hijo1.cromosomaVacio();
			hijo2.cromosomaVacio();
			
			//Crea la tabla de genes
			tablaGenes = new ArrayList<ArrayList<gen>>();			
			creaTabla(padre1, tam);
			creaTabla(padre2, tam);
		
			//Introduce el primer elemento
			if(Math.random()%1 < 0.5) {				
				hijo1.add(padre1.getCromosomaAt(0));
				hijo2.add(padre2.getCromosomaAt(0));
			}
			else {
				hijo1.add(padre2.getCromosomaAt(0));
				hijo2.add(padre1.getCromosomaAt(0));
			}
			
		
			//Resto de elementos
			for(int j = 1; j < tam; j++) {
				gen nuevo1 = new gen(buscaSiguienteGen(padre1, hijo1, j - 1));
				hijo1.add(nuevo1);
				
				gen nuevo2 = new gen(buscaSiguienteGen(padre2, hijo2, j - 1));
				hijo2.add(nuevo2);
			}
			hijo1.calcularFitness();
			hijo2.calcularFitness();
			setDescendienteAt(i, hijo1);
			setDescendienteAt(i+1, hijo2);
		}
	}
	
	private gen buscaSiguienteGen(individuo padre, individuo hijo, int cont) {
		
		ArrayList<gen> contiguos = new ArrayList<gen>();
		ArrayList<Integer> contSize = new ArrayList<Integer>();
		
		
		//Creamos la tabla con todos los contiguos
		
		//En contiguos deben aparecer los genes contiguos al hijo anterior
		boolean found = false;
		for(int i = 0; i < tablaGenes.size() && !found; i++) {
			if(tablaGenes.get(i).get(0).getGenotipo() == hijo.getCromosomaAt(cont).getGenotipo()) {
				for(int j = 1; j < tablaGenes.get(i).size(); j++) {
					contiguos.add(tablaGenes.get(i).get(j));
				}
				found = true;
			}
		}

		//Quitamos los contiguos que ya están en el hijo
		for(int i = 0; i < contiguos.size(); i++) {
			found = false;
			for(int j = 0; j < hijo.getSizeCromosoma(); j++) {
				if(contiguos.get(i).getGenotipo() == hijo.getCromosomaAt(j).getGenotipo())
					found = true;
			}
			if(found) {
				contiguos.remove(i);
			}
		}
		
		//Calcula las longitudes de los que quedan
		for(int i = 0; i < contiguos.size(); i++) {
			for(int j = 0; j < tablaGenes.size(); j++) {
				if(tablaGenes.get(j).get(0).getGenotipo() == contiguos.get(i).getGenotipo()) {
					contSize.add(tablaGenes.get(j).size() - 1);
				}
			}
		}
		
		
		//Devuelve el priemero no utilizado
		int minSize = contSize.get(0);	
		gen ret = new gen(contiguos.get(0));
		minSize = contSize.get(0);
	
		//Comprueba si existen posbilidades mejores (aquí no pueden entrar repetidos)
		for(int i = 0; i < contSize.size(); i++) {
			int pos = buscaPosHijo(hijo, contiguos.get(i));
			
			//Se comprueba siempre que el nuevo no se haya usado
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
		
		return ret;
	}


	//
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



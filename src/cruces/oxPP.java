package cruces;

import java.util.Random;
import poblacion.individuo;
import poblacion.poblacion;

public class oxPP extends algoritmoCruce{

	@Override
	public poblacion cruzar(poblacion seleccionados, double prob) {
		ini(prob, seleccionados);
		seleccionaReproductores();
		creaDescendientes();
		return getDescendientes();
	}

	private void creaDescendientes() {
		int tamCromosoma=getReproductorAt(0).getSizeCromosoma();
		Random rand=new Random();
		int punto1=rand.nextInt(tamCromosoma);
		int punto2=rand.nextInt(tamCromosoma);
		while(punto1 == punto2) {
			punto2=rand.nextInt(tamCromosoma);
		}
		if(punto1 > punto2) {
			int aux=punto1;
			punto1=punto2;
			punto2=aux;
		}
		
		for(int i=0; i < getReproductoresSize(); i+=2) {
			individuo padre1=getReproductorAt(i);
			individuo padre2=getReproductorAt(i+1);
			individuo hijo1=new individuo(padre1);
			individuo hijo2=new individuo(padre2);
			setGenes(hijo1, padre1, padre2, punto1, punto2);
			setGenes(hijo2, padre2, padre1, punto1, punto2);
			hijo1.calcularFitness();
			hijo2.calcularFitness();
			setDescendienteAt(i, hijo1);
			setDescendienteAt(i+1, hijo2);
		}
	}

	private void setGenes(individuo hijo, individuo padre1, individuo padre2, int punto1, int punto2) {
		int tam=padre1.getSizeCromosoma();
		boolean [] colocados=new boolean[tam];
		inicializarArray(colocados, tam);
		//Se colocan los dos genes del padre2
		hijo.setGen(punto1, padre2.getCromosomaAt(punto1));
		hijo.setGen(punto2, padre2.getCromosomaAt(punto2));
		//Se marcan las posiciones donde hay genes repetidos
		int cont=0;
		for(int i=punto2; cont < tam; i=(i+1)%tam) {
			for(int k=(i+1)%tam; k !=punto2; k=(k+1)%tam) {
				if(hijo.getCromosomaAt(i).getGenotipo()==hijo.getCromosomaAt(k).getGenotipo()) {
					if(k!=punto1){
						colocados[k]=false;
					}
					else {
						colocados[i]=false;
					}
				}
			}
			cont++;
		}
		//En las posiciones repetidas se colocan los genes del padre1
		int contPadre=(punto2+1)%tam;
		for(int j=0; j < tam; j++) {
			if(!colocados[j]) {
				while(hijo.existeGen(padre1.getCromosomaAt(contPadre))) {
					contPadre=(contPadre+1)%tam;
				}
				hijo.setGen(j, padre1.getCromosomaAt(contPadre));
				contPadre=(contPadre+1)%tam;
			}
		}
	}
	
	private void inicializarArray(boolean[] h, int tam) {
		for(int i=0; i < tam; i++) {
			h[i]=true;
		}
	}

}

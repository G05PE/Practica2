package cruces;

import poblacion.individuo;
import poblacion.poblacion;

public class erx extends algoritmoCruce {

	@Override
	public poblacion cruzar(poblacion seleccionados, double prob) {
		ini(prob, seleccionados);
		seleccionaReproductores();	
		cruzaReproductores();
		
		return getDescendientes();
	}
	
	
	private void cruzaReproductores() {
				
		//Inicializa los hijos
		for(int i = 0; i < getReproductoresSize() - 1; i+=2) {
			individuo ind1 = new individuo();
			individuo ind2 = new individuo();
					
		}
	}
	

}

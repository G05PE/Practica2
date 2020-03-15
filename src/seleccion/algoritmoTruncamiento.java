package seleccion;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import model.funcion;
import poblacion.individuo;
import poblacion.poblacion;

public class algoritmoTruncamiento extends algoritmoSeleccion{

	public algoritmoTruncamiento() {
		super("truncamiento");
	}
	@Override
	public void seleccionar(poblacion p, funcion f) {
		List<individuo> aux=new ArrayList<individuo>();
		//Copia los individuos 
		for(int i=0; i < p.getSize(); i++) {
			aux.add(p.getIndividuo(i));
		}
		//Ordena los individuos
		aux.sort(new Comparator<individuo>() {

			@Override
			public int compare(individuo i1, individuo i2) {
				return new Double(i1.getFitness()).compareTo(new Double(i2.getFitness()));
			}});
		
		Random r=new Random();
		int last=aux.size()-1;
		//Cantidad de individuos a elegir
		int tam=0;
		while(tam==0) {
			tam=Math.abs(r.nextInt() % aux.size());
		}
		for(int i=0; i < aux.size();) {
			for(int j=0; j < tam && i < aux.size(); j++) {
				addSeleccionado(new individuo(aux.get(last-j)));
				i++;
			}
		}
	}

	@Override
	public poblacion ini(poblacion p, funcion f) {
		iniSeleccionados(p);
		seleccionar(p, f);
		getSeleccionados().iniBest();
		return getSeleccionados();
	}

}

package seleccion;

import java.util.ArrayList;
import java.util.List;
import poblacion.individuo;
import poblacion.poblacion;
import model.funcion;

public abstract class algoritmoSeleccion {
	private poblacion seleccionados;
	
	public abstract void seleccionar(poblacion p, funcion f);
	public abstract poblacion ini(poblacion p, funcion f);
	
	public void addSeleccionado(individuo i) {
		seleccionados.addIndividuo(i);
	}
	
	public poblacion getSeleccionados() {
		return seleccionados;
	}
	
	public void iniSeleccionados(int tam, double prec, funcion f) {
		seleccionados=new poblacion(tam, prec, f);
	}
}

package seleccion;

import java.util.ArrayList;
import java.util.List;
import poblacion.individuo;
import poblacion.poblacion;
import model.funcion;

public abstract class algoritmoSeleccion {
	private poblacion seleccionados;
	private String name;
	
	public algoritmoSeleccion(String name) {
		this.name=name;
	}
	public algoritmoSeleccion getCopia() {
		switch(name) {
		case "":
			return null;//retornar una copia nueva
		}
		return null;
	}
	public abstract void seleccionar(poblacion p, funcion f);
	public abstract poblacion ini(poblacion p, funcion f);
	
	public String getName() {
		return name;
	}
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

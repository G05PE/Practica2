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
		case "estocastico":
			return new algoritmoEstocasticoUniv();
		case "ruleta":
			return new algoritmoRuleta();
		case "torneoDeterminista":
			return new algoritmoTorneoDeter();
		case "torneoProbabilistico":
			return new algoritmoTorneoProb();
		case "truncamiento":
			return new algoritmoTruncamiento();
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
	
	public void iniSeleccionados(poblacion vieja) {
		seleccionados=new poblacion(vieja, false);
	}
}

package seleccion;
import java.util.Random;

import model.funcion;
import poblacion.poblacion;

public class algoritmoTorneoDeter extends algoritmoTorneo{

	public algoritmoTorneoDeter() {
		super("torneoDeter");
		// TODO Auto-generated constructor stub
	}

	public poblacion ini(poblacion p, funcion f) {
		iniSeleccionados(p);
		seleccionar(p, f);
		getSeleccionados().iniBest();
		return getSeleccionados();
	}

}

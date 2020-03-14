package seleccion;
import java.util.Random;
import model.funcion;
import poblacion.poblacion;

public class algoritmoTorneoDeter extends algoritmoTorneo{

	public algoritmoTorneoDeter() {
		super("torneoDeterminista");
	}
	
	public poblacion ini(poblacion p, funcion f) {
		iniSeleccionados(p.getSize(), p.getFuncion());
		seleccionar(p, f);
		getSeleccionados().iniBest();
		return getSeleccionados();
	}

}

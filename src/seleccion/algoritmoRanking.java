package seleccion;

import java.util.ArrayList;
import java.util.List;

import model.funcion;
import poblacion.poblacion;

public class algoritmoRanking extends algoritmoSeleccion {

	private double totalFitness;
	private List<Double> fitnessIndiv;
	
	private List<Double> probSeleccion;

	
	
	public algoritmoRanking(String name) {
		super("ranking");
		totalFitness=0;
		probSeleccion=new ArrayList<Double>();
	}

	@Override
	public poblacion ini(poblacion p, funcion f) {
		probSeleccion=new ArrayList<Double>();
		iniSeleccionados(p);
		seleccionar(p, f);
		getSeleccionados().iniBest();
		return getSeleccionados();
	}

		
	private void calcularTotalFitness(poblacion p) {
		for(int i=0; i < p.getSize(); i++) {
			fitnessIndiv[i] = p.getIndividuo(i).getFitness();
			totalFitness += p.getIndividuo(i).getFitness();
		}
	}

	@Override
	public void seleccionar(poblacion p, funcion f) {
	
	}
}

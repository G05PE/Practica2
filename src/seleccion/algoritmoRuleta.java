package seleccion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.funcion;
import poblacion.individuo;
import poblacion.poblacion;

public class algoritmoRuleta extends algoritmoSeleccion{
	private double totalFitness;
	private List<Double> probSeleccion;
	
	public algoritmoRuleta() {
		super("ruleta");
		probSeleccion=new ArrayList<Double>();
	}
	public poblacion ini(poblacion p, funcion f) {
		probSeleccion=new ArrayList<Double>();
		iniSeleccionados(p);
		asignarProbabilidades(p);
		seleccionar(p, f);
		getSeleccionados().iniBest();
		return getSeleccionados();
	}
	public void seleccionar(poblacion p, funcion f) {
		Random r=new Random();
		for(int j=0; j < p.getSize(); j++) {
			double valor=r.nextDouble() % 1;
			int i=0;
			while(i < p.getSize() && valor > probSeleccion.get(i)) {
				i++;
			}
			if(i > 0 && probSeleccion.get(i-1) > valor) i--;
			addSeleccionado(new individuo(p.getIndividuo(i)));
		}
	}

	public void asignarProbabilidades(poblacion p) {
		calcularTotalFitness(p);
		for(int i=0; i < p.getSize(); i++) {
			double prob=0;
			prob=p.getIndividuo(i).getFitness()/totalFitness;
			if(i > 0) {
				prob +=probSeleccion.get(i-1);
			}
			probSeleccion.add(prob);
		}
	}

	private void calcularTotalFitness(poblacion p) {
		totalFitness=0;
		for(int i=0; i < p.getSize(); i++) {
			totalFitness += p.getIndividuo(i).getFitness();
		}
	}
}

package seleccion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import model.funcion;
import poblacion.individuo;
import poblacion.poblacion;
public class algoritmoEstocasticoUniv extends algoritmoSeleccion {

	private double totalFitness;
	private List<Double> probSeleccion;
	private double distancia;
	
	public algoritmoEstocasticoUniv() {
		super("estocastico");
		probSeleccion=new ArrayList<Double>();
	}
	public poblacion ini(poblacion p, funcion f) {
		probSeleccion=new ArrayList<Double>();
		iniSeleccionados(p);
		asignarProbabilidades(p);
		distancia=(double)1/p.getSize();
		seleccionar(p, f);
		getSeleccionados().iniBest();
		return getSeleccionados();
	}
	@Override
	public void seleccionar(poblacion p, funcion f) {
		int j=0;
		Random rand=new Random();
		double r;
		for(int k=0; k < p.getSize();) {
			r=rand.nextDouble()%distancia;
			j=0;
			while(j < p.getSize()) {
				while(j < p.getSize() && r > probSeleccion.get(j)) {
					j++;
				}
				if(j < p.getSize()) {
					if(j > 0 &&  probSeleccion.get(j-1) > r) j--;
					addSeleccionado(new individuo(p.getIndividuo(j)));
					r+=distancia;
					k++;
				}
			}
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

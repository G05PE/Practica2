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
			//fitnessIndiv[i] = p.getIndividuo(i).getFitness();
			totalFitness += p.getIndividuo(i).getFitness();
		}
	}

	@Override
	public void seleccionar(poblacion p, funcion f) {
	
	}
}



/**PSEUDOCODIGO DEL PROFESOR*/
/*
 * public Individual[] ranking(Individual[] initPop){
	Individual[] sortedPop = SortIndividual.selectionSort(initPop);
	Individual[] futureParents = new Individual[sortedPop.size()]
	
	futureParents[0] = sortedPop[0];
	futureParents[1] = sortedPop[1];
	int numOfParents = 2;

	double[] fitnessSegments = rankPopulation();
	double entireSegment = fitnessSegments[fitnessSegments.size() - 1]

	while(numOfParents < futureParents.size()){
		double x = (double)(Math.random()*entireSegment);
		if(x <= fitnessSegments[0]){
			futureParents[numOfParents] = sortedPop[0];
			numOfParents++;
		}
		else{
			for(int i = 1; i < futureParents.size(); i++){
				if(x > fitnessSegments[i-1] && x <= fitnessSegments[i]){
					futureParents[numOfParents] = sortedPop[i];
					numOfParents++;
				}
			}
			return futureParents;
		}
	}
}

*/
 
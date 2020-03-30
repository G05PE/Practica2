package seleccion;

import model.funcion;
import poblacion.poblacion;

public class algotirmoOtro extends algoritmoSeleccion {

	public algotirmoOtro() {
		super("otro");
	}

	@Override
	public void seleccionar(poblacion p, funcion f) {
		// TODO Auto-generated method stub
	}

	@Override
	public poblacion ini(poblacion p, funcion f) {
		// TODO Auto-generated method stub
		return null;
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
 
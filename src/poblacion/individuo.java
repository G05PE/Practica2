package poblacion;

import java.util.ArrayList;
import java.util.List;
import genetica.gen;
import model.funcion;

public class individuo {
	
	private List<gen> cromosoma;
	private double fitness;
	private funcion f;
	
	public individuo(double precision, funcion f) {
		this.f=f;
		cromosoma=new ArrayList<gen>();
		crearGenes(f, precision);
		calcularFitness(); 
	}
	
	public individuo(individuo ind) {
		this.f=ind.getFuncion();
		cromosoma=new ArrayList<gen>();
		for(int i=0; i<ind.getCromosoma().size(); i++) {
			cromosoma.add(new gen(ind.getCromosoma().get(i)));
		}
		fitness=ind.getFitness();
	}

	/**Crea todos los genes establecidos por la funcion, los aï¿½ade a la lista de
	 * genes y establece sus fenotipos*/
	public void crearGenes(funcion f, double precision) {
		/*for(int i=0; i < f.getSize(); i++) {
			double tam=Math.floor(log2(1 +  
					(f.getMaxX(i)-f.getMinX(i)) / precision )) + 1;
			cromosoma.add(new gen(f, precision, tam));
			fenotipos.add(calcularFenotipo(i));
		}*/
	}
	
	/**Recalcula los fenotipos de todos los genes*/
	public void recalcularFenotipos() {
		for(int i=0; i < cromosoma.size(); i++) {
			calcularFenotipo(i);
		}
	}
	
	/**Calcula el fenotipo del gen i, y establece dicho valor en el gen*/
	public double calcularFenotipo(int i) {
		double fenotipo=0.0;
		/*double tam=cromosoma.get(i).getTam();
		fenotipo=f.getMinX(i) + bin2dec((List<Boolean>) cromosoma.get(i).getGenotipo())*
				(f.getMaxX(i)-f.getMinX(i))/(Math.pow(2, tam)-1);
				*/
		cromosoma.get(i).setFenotipo(fenotipo);
		return fenotipo;
	}
	/**
	 * Calcula el fenotipo de una posición
	 *  especifica despues de haber iniciado el individuo*/
	public void recalcularFenotipo(int i) {
		calcularFenotipo(i);
	}
	
	/**Transforma un nï¿½mero en base 2 a un nï¿½mero en base 10*/
	public double bin2dec(List<Boolean> binario) {
		int res=0;
		for(int i=binario.size()-1; i >=0 ; i--) {
			if(binario.get(i)) {
				res+=Math.pow(2, i);
			}
		}
		return res;
	}
	
	public double log2(double x) {
		return Math.log(x) / Math.log(2);
	}

	public double getFitness() {
		return fitness;
	}
	
	
	public void calcularFitness() {
		List<Double> fen=new ArrayList<Double>();
		for(int i=0; i < cromosoma.size(); i++) {
			fen.add(cromosoma.get(i).getFenotipo());
		}
		this.fitness=f.calcularFuncion(fen);
	}
	public List<gen> getCromosoma(){
			return cromosoma;
	}
	public funcion getFuncion() {
		return f;
	}
	
	public gen getCromosomaAt(int i) {
		return cromosoma.get(i);
	}
	
	public void setGen(int i, gen gen) {
		this.cromosoma.set(i, gen);
	}
	
	public void setFenotipoAt(int i, double valor) {
		cromosoma.get(i).setFenotipo(valor);
	}
	
	public int getSizeCromosoma(){
		return cromosoma.size();
	}

	public void setFitness(double d) {
		fitness=d;
	}

}

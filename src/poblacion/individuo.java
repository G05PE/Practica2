package poblacion;

import java.util.ArrayList;
import java.util.List;
import genetica.gen;
import model.funcion;

public class individuo {
	
	private List<gen> cromosoma;
	private double fitness;
	private funcion f;
	
	public individuo(funcion f) {
		this.f=f;
		cromosoma=new ArrayList<gen>();
		crearGenes(f);
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

	/**Crea todos los genes establecidos por la funcion, los a�ade a la lista de
	 * genes y establece sus fenotipos*/
	public void crearGenes(funcion f) {
		/*for(int i=0; i < f.getSize(); i++) {
			double tam=Math.floor(log2(1 +  
					(f.getMaxX(i)-f.getMinX(i)) / precision )) + 1;
			cromosoma.add(new gen(f, precision, tam));
			fenotipos.add(calcularFenotipo(i));
		}*/
	}
	
	/**Transforma un n�mero en base 2 a un n�mero en base 10*/
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
		List<Integer> fen=new ArrayList<Integer>();
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
	
	public int getSizeCromosoma(){
		return cromosoma.size();
	}

	public void setFitness(double d) {
		fitness=d;
	}

}

package poblacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

	//Constructor vacio
	public individuo() {}

	/**Crea todos los genes*/
	public void crearGenes(funcion f) {
		int valor, tam=f.getSize();
		for(int i=0; i < tam; i++ ) {
			valor=random(tam);
			while(existeGen(valor, i))  {
				valor=random(tam);
			}
			cromosoma.add(new gen(valor));
		}
	}
	
	private int random(int max) {
		Random r=new Random();
		int valor;
		valor=Math.abs(r.nextInt() % max);
		return valor;
	}
	private boolean existeGen(int valor, int i) {
		boolean existe=false;
		int j=i-1;
		if(j >=0) {
			while(j >= 0 && cromosoma.get(j).getFenotipo() != valor) {
				j--;
			}
			existe= (j >=0);
		}
		return existe;
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
	
	public boolean existeGen(gen gen) {
		int i=0;
		while(i < getSizeCromosoma() && 
				cromosoma.get(i).getGenotipo() != 
				gen.getGenotipo()) {
			i++;
		}
		if(i == getSizeCromosoma()) {
			return false;
		}
		return true;
	}
	
	public boolean existeGen(gen gen, int ini, int fin) {
		int i=ini;
		while(i != fin && 
				cromosoma.get(i).getGenotipo() != 
				gen.getGenotipo()) {
			i=(i+1)%getSizeCromosoma();
		}
		if(i==fin) {
			return false;
		}
		return true;
	}

	public void quitaGen(int pos) {
		cromosoma.remove(pos);
	}

	public void cromosomaVacio() {
		cromosoma = new ArrayList<gen>();
	}

	public void add(gen elem) {
		cromosoma.add(elem);
	}
}

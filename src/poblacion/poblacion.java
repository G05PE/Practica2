package poblacion;

import java.util.ArrayList;
import java.util.List;
import model.funcion;

public class poblacion {
	
	private List<individuo> poblacion;
	private int tam=0;
	private funcion fun;
	private double best;
	
	public poblacion(int tam, funcion f) {
		poblacion=new ArrayList<individuo>();
		this.tam=tam;
		fun=f;
		this.fun=f;
	}
	
	public poblacion(poblacion old) {
		copiarPoblacion(old);
		tam=old.getSize();
		fun=old.getFuncion();
		best=old.getBest();
	}

	private void copiarPoblacion(poblacion old) {
		poblacion=new ArrayList<individuo>();
		for(int i=0; i < old.getSize(); i++) {
			poblacion.add(new individuo(old.getIndividuo(i)));
		}
	}

	public void iniciarPoblacion() {
		
		for(int i=0; i < tam; i++) {
			individuo cromosoma=new individuo(fun);
			poblacion.add(cromosoma);
		}
		iniBest();
	}
	
	public individuo getIndividuo(int i) {
		return poblacion.get(i);
	}
	
	public void setIndividuos(List<individuo> nuevos) {
		poblacion=nuevos;
	}
	
	public int getSize() {
		return tam;
	}

	public double getBest() {
		iniBest();
		for(int i=0; i < poblacion.size(); i++) {
			if(fun.best(poblacion.get(i).getFitness(), best)){
				best=poblacion.get(i).getFitness();
			}
		}
		return best;
	}

	public double getAverage() {
		double total=0;
		for(int i=0; i < poblacion.size(); i++) {
			total+=poblacion.get(i).getFitness();
		}
		return total/poblacion.size();
	}
	
	public void borraUltimo() {
		poblacion.remove(poblacion.size()-1);
	}

	public funcion getFuncion() {
		return fun;
	}
	public void iniBest() {
		best=poblacion.get(0).getFitness();
	}
	public void addIndividuo(individuo i) {
		poblacion.add(i);
	}

	public void setIndividuoAt(int i, individuo individuo) {
		poblacion.set(i, new individuo(individuo));
	}

	public void setSize(int num_sele_cruce) {
		this.tam = num_sele_cruce;
	}

	public individuo getMejorInd() {
		individuo mejor=poblacion.get(0);
		for(int i=0; i < poblacion.size(); i++) {
			if(fun.best(poblacion.get(i).getFitness(), mejor.getFitness())){
				mejor=poblacion.get(i);
			}
		}
		return mejor;
		
	}
}

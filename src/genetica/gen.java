package genetica;

import java.util.ArrayList;
import model.funcion;
import java.util.List;
import java.util.Random;

public class gen {
	
	private List<Integer> genotipo;
	private double fenotipo;
	private funcion funcion;
	private double tam;
	private Random rand;
	
	public gen() {}
	public gen(funcion f, double precision, double tam) {
		funcion=f;
		this.tam=tam;
		generarGen();
	}
	
	public gen(gen gen) {
		rand=new Random();
		fenotipo=gen.getFenotipo();
		funcion=gen.getFuncion();
		tam=gen.getSizeGenotipo();
		genotipo=new ArrayList<Integer>();
		for(int i=0; i < gen.getGenotipo().size(); i++) {
			genotipo.add(new Integer(gen.getGenotipo().get(i)));
		}
	}
	
	private funcion getFuncion() {
		return funcion;
	}
	public int getSizeGenotipo(){
		return genotipo.size();
	}

	public void generarGen() {
		/*genotipo=new ArrayList<Integer>();
		rand=new Random();
		for(int i=0; i < tam; i++) {
			genotipo.add(new Integer(rand.nextInt()));
		}*/
	}
	public double getFenotipo() {
		return fenotipo;
	}
	
	public List<Integer> getGenotipo() {
		return genotipo;
	}
	public void setFenotipo(double d) {
		fenotipo=d;
	}
}

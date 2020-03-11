package genetica;

import java.util.ArrayList;
import model.funcion;
import java.util.List;
import java.util.Random;

public class gen {
	
	private int genotipo;
	private int fenotipo;
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
		genotipo=gen.getGenotipo();
	}
	
	private funcion getFuncion() {
		return funcion;
	}
	public int getSizeGenotipo(){
		return 1;
	}

	public void generarGen() {
		/*genotipo=new ArrayList<Integer>();
		rand=new Random();
		for(int i=0; i < tam; i++) {
			genotipo.add(new Integer(rand.nextInt()));
		}*/
	}
	public int getFenotipo() {
		return fenotipo;
	}
	
	public int getGenotipo() {
		return genotipo;
	}
	public void setFenotipo(int d) {
		fenotipo=d;
		genotipo=fenotipo;
	}
}

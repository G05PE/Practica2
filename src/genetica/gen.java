package genetica;

public class gen {
	
	private int genotipo;
	private int fenotipo;
	
	public gen() {}
	public gen(int valor) {
		genotipo=valor;
		fenotipo=valor;
	}
	
	public gen(gen gen) {
		fenotipo=gen.getFenotipo();
		genotipo=gen.getGenotipo();
	}
	public int getSizeGenotipo(){
		return 1;
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

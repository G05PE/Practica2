package cruces;

import poblacion.poblacion;

public abstract class mutacion {
	private String name;
	public mutacion(String name) {
		this.name=name;
	}
  public abstract poblacion mutar(poblacion poblacion, double probMutacion);

  public mutacion getCopia() {
		switch(name) {
		case "":
			return null;//retornar una copia nueva
		}
		return null;
	}
}

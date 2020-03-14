package mutacion;

import poblacion.poblacion;

public abstract class mutacion {
	
	public mutacion() {}
	
  public abstract poblacion mutar(poblacion poblacion, double probMutacion);

  public mutacion getCopia() {
		switch(name) {
		case "exchange":
			return new exchange();
			break;
		case "insercion":
			return new insercion();
			break;
		/*case "heuristic":
			return new heuristic();
			break;
		case "insercion":
			return new insercion();
			break;
		case "investment":
			return new investment();
			break;
		case "propio":
			return new metPropio();
			break;*/
		}
		return null;
	}
}

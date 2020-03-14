package mutacion;

import poblacion.poblacion;

public abstract class mutacion {
	
	public mutacion() {}
	
  public abstract poblacion mutar(poblacion poblacion, double probMutacion);
  
}

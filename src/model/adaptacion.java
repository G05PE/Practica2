package model;

import poblacion.poblacion;

public abstract class adaptacion {
	private final double c=1.05;
	private double limit;
	private boolean adaptado;
	
	public adaptacion(adaptacion viejo) {
		limit=viejo.getLimit();
		adaptado=viejo.getAdaptado();
	}
	public adaptacion() {
		// TODO Auto-generated constructor stub
	}
	public void setLimit(double l) {
		limit=l;
	}
	public double getLimit() {
		return limit;
	}
	public double getC() {
		return c;
	}
	public boolean getAdaptado() {
		return adaptado;
	}
	public void setAdaptado(boolean a) {
		adaptado=a;
	}
	
	public void adaptar(poblacion p) {
		adaptado=true;
		establecerLimite(p);
		ajustar(p);
	}
	
	public abstract void deshacer(poblacion p);
	protected abstract void ajustar(poblacion p);
	protected abstract void establecerLimite(poblacion p);
}

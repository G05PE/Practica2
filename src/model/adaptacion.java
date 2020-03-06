package model;

import poblacion.poblacion;

public abstract class adaptacion {
	private final double c=0.9;
	private double limit;
	private boolean adaptado;
	
	public void setLimit(double l) {
		limit=l;
		adaptado=false;
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

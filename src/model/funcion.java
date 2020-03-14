package model;

import java.util.List;

import poblacion.individuo;
import poblacion.poblacion;

public class funcion {
	
	private adaptarMin adaptador;
	private int [][] flujos;
	private int [][] distancias;
	private int tam;
	
	public funcion() {
		adaptador=new adaptarMin();
	}

	public funcion(funcion viejo) {
		adaptador=new adaptarMin(viejo.getAdaptador());
		tam=viejo.getSize();
		distancias=copiarArray(viejo.getDistancias(), tam);
		flujos=copiarArray(viejo.getFlujos(), tam);
	}
	private adaptacion getAdaptador() {
		return adaptador;
	}

	private int[][] getFlujos() {
		return flujos;
	}

	private int [][] getDistancias() {
		return distancias;
	}

	public int getSize() {
		return tam;
	}

	private int[][] copiarArray(int[][] viejo, int tam) {
		int[][] nuevo=new int[tam][tam];
		for(int i=0; i < tam; i++) {
			for(int j=0; j < tam; j++) {
				nuevo[i][j]=viejo[i][j];
			}
		}
		return nuevo;
	}
	public void cargarDatos(int [][] dist, int [][] fluj, int tam) {
		distancias=dist;
		flujos=fluj;
		this.tam=tam;
	}
	
	public int calcularFuncion(List<Integer> fenotipos) {
		int resultado=0;
		if(distancias!=null && flujos != null) {
			for(int i=0; i < tam; i++) {
				for(int j=0; j < tam; j++) {
					resultado+=distancias[i][j]*flujos[fenotipos.get(i)][fenotipos.get(j)];
				}
			}
		}
		else
		{
			System.err.println("No se han cargado los datos, no se puede calcular el resultado.");
		}
		return resultado;
	}
	
	public boolean best(double fitness, double fitness2) {
		if(adaptador.getAdaptado()) {
			return fitness > fitness2;
		}
		else {
			return fitness < fitness2;
		}
	}

	public void addElite(List<individuo> objetivo, List<individuo> fuente, double tamElite) {
		for(int i=0; i < tamElite; i++) {
			objetivo.add(new individuo(fuente.get(i)));
		}
	}

	public void desadaptar(poblacion poblacion) {
		adaptador.deshacer(poblacion);
	}

	public void adaptar(poblacion poblacion) {
		adaptador.adaptar(poblacion);
	}

	public boolean worst(double fitness, double fitness2) {
		if(adaptador.getAdaptado()) {
			return fitness < fitness2;
		}
		else {
			return fitness > fitness2;
		}
	}

}

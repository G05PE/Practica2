package model;

import java.util.ArrayList;
import java.util.List;
import view.observer;
public class manager {
	
	private List<observer> observers;
	private int maxIter;
	private int tamPob;
	
	public manager() {
		observers = new ArrayList<observer>();
		iniciarDatos();
	}
	
	public void iniciarDatos() {
	}
	
	public void addObserver(observer o) {
		if (!observers.contains(o)) {
			observers.add(o);
		}
	}
}

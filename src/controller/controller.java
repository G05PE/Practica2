package controller;
import model.manager;
import view.observer;

public class controller {
	
	private manager manager;

	public controller(manager m) {
		manager = m;
	}

	public void addObserver(observer o) {
		manager.addObserver(o);
	}
}

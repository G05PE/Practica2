package seleccion;

import java.util.Random;

import model.funcion;
import poblacion.individuo;
import poblacion.poblacion;

public class algoritmoTorneoProb extends algoritmoTorneo{
	
	public algoritmoTorneoProb() {
		super("torneoProb");
		// TODO Auto-generated constructor stub
	}

	private double p;
	
	@Override
	public void luchar(funcion fun) {
		Random r=new Random();
		double intervalo=r.nextDouble()%1;
		if(intervalo > p) {
			super.luchar(fun);//gana el mejor
		}
		else{//gana el peor
			int ganador=0;
			for(int i=1; i < getK(); i++) {
				if(fun.worst(getFromRing(i).getFitness(), getFromRing(ganador).getFitness())){
					ganador=i;
				}
			}
			addSeleccionado(new individuo(getFromRing(ganador)));		
			ganador=0;
			clearRing();
		}
	}

	@Override
	public poblacion ini(poblacion pob, funcion fun) {
		p=Math.abs(Math.random()%1);
		iniSeleccionados(pob);
		seleccionar(pob, fun);
		getSeleccionados().iniBest();
		return getSeleccionados();
	}
}

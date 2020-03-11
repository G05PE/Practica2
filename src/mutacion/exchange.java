package mutacion;

import java.util.Random;

import poblacion.individuo;
import poblacion.poblacion;

public class exchange extends mutacion{

	@Override
	public poblacion mutar(poblacion poblacion, double probMutacion) {
		Random rand = new Random();
		int var1 = rand.nextInt()%poblacion.getSize();
		int var2 = rand.nextInt()%poblacion.getSize();
		
		individuo aux = poblacion.getIndividuo(var1);
		poblacion.setIndividuoAt(var1, poblacion.getIndividuo(var1));
		poblacion.setIndividuoAt(var2, aux);
		
		return poblacion;
	}

}

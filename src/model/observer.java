package model;

import java.util.List;

public interface observer {
	public void onFinished(double[][] best, double[][] bestGen, double[][] average, List<Double> bestVars);
	public void onNextGeneration();
}

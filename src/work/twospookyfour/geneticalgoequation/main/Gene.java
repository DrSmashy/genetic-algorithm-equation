package work.twospookyfour.geneticalgoequation.main;

public interface Gene<T> {
	
	public void randomize();
	
	public T getData();
	
	public void setData(T data);
	
	public String toString();
	
	public double evaluate();
	
	public double calculateFitness(double target);
	
	public void mutate();
	
	public Gene<?> copy();
}

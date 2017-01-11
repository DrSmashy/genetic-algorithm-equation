package work.twospookyfour.geneticalgoequation.main;

public class Chromosome<T> {
	private Gene<T> gene;
	private double fitness = 0.0d;
	
	public Chromosome(Gene<T> gene) {
		this.gene = gene;
	}
	
	public Chromosome(Chromosome<T> c) {
		setFitness(c.getFitness());
		gene = (Gene<T>) c.getGene().copy();
	}
	
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	public double getFitness() {
		return fitness;
	}
	
	public Gene<T> getGene() {
		return gene;
	}
	
	public void setGene(Gene<T> gene) {
		this.gene = gene;
	}
	
	public void randomizeGene() {
		gene.randomize();
	}
	
	public double evaluate() {
		return getGene().evaluate();
	}
	
	public void mutate() {
		getGene().mutate();
	}
	
	public double calculateFitness(double target) {
		return getGene().calculateFitness(target);
	}
	
	public String toString() {
		return "\nfitness: " + getFitness() + "\n" + getGene();
	}
}

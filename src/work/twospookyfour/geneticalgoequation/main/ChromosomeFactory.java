package work.twospookyfour.geneticalgoequation.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class ChromosomeFactory {
	
	Chromosome<String>[] pop;
	
	Chromosome<String> makeRandomChromosome() {
		Chromosome<String> c = new Chromosome<String>(new EquationChromosomeGene());
		c.randomizeGene();
		return c;
	}
	
	public void start() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		pop = new Chromosome[Main.POOL_SIZE];
		double target;
		int genCount;
		boolean solutionFound;
		Chromosome<String> bestSolution;
		
        
		while (true) {
			
			System.out.println("Enter Target");
	        try {
				target = Double.parseDouble(br.readLine());
			} catch (NumberFormatException e) {
				continue;
			} catch (IOException e) {
				continue;
			}
	        
	        for (int i = 0; i < pop.length; ++i) {
	        	pop[i] = makeRandomChromosome();
	        }
	        bestSolution = pop[0];
	        
	        genCount = 0;
	        solutionFound = false;
	        
	        while (!solutionFound) {
	        	double totalFitness = 0;
	        	
	        	//update fitness
	        	for (int i = 0; i < pop.length; ++i) {
	        		pop[i].setFitness(pop[i].calculateFitness(target));
	        		
	        		if (pop[i].getFitness() > bestSolution.getFitness()) {

	        			bestSolution = new Chromosome<String>(pop[i]);
	        			
	        			System.out.println(bestSolution + " = " + bestSolution.getGene().evaluate());
	        		}
	        		
	        		totalFitness += pop[i].getFitness();
	        	}
	        	
	        	//do the genetics
	        	Chromosome[] temp = new Chromosome[Main.POOL_SIZE];
	        	
	        	int cPop = 0;
	        	
	        	while (cPop < Main.POOL_SIZE) {
	        		Chromosome<String> off1 = new Chromosome<String>(
	        				roulette(totalFitness));
	        		Chromosome<String> off2 = new Chromosome<String>(
	        				roulette(totalFitness));
	        		
	        		crossover(off1, off2);
	        		
	        		off1.mutate();
	        		off2.mutate();
	        		
	        		temp[cPop++] = off1;
	        		temp[cPop++] = off2;
	        	}
	        	
	        	pop = temp;
	        	++genCount;
	        	
	        	if (genCount > Main.MAX_GENS) {
	        		//System.out.println("No perfect solutions found.");
	        		System.out.println("Best solution:");
	        		System.out.println(bestSolution + " = " + bestSolution.evaluate());
	        		solutionFound = true;
	        	}
	        	
	        	
	        }
	        
		}
	}
	
	private void crossover(Chromosome<String> c1, Chromosome<String> c2) {
		Random r = new Random();
		if (r.nextDouble() < Main.CROSS_RATE) {
			
			int crossover = (int) r.nextDouble() * Main.CHROMO_LEN;

			c1.getGene()
				.setData(
						c1.getGene().getData().substring(0, crossover)
						+ c2.getGene().getData().substring(crossover, Main.CHROMO_LEN));
			
			c2.getGene()
				.setData(
					c2.getGene().getData().substring(0, crossover)
					+ c1.getGene().getData().substring(crossover, Main.CHROMO_LEN));
		}
	}
	
	private EquationChromosomeGene roulette(double totalFitness) {
		Random r = new Random();
		double slice = (double) (r.nextDouble() * totalFitness);
		
		double fitnessSoFar = 0;
		
		for (int i = 0; i < Main.POOL_SIZE; ++i) {
			fitnessSoFar += pop[i].getFitness();
			
			//if the fitness so far > random number return the chromosome at this point
			if (fitnessSoFar >= slice) {
				return (EquationChromosomeGene) pop[i].getGene();
			}
		}
		
		return new EquationChromosomeGene();
	}
}

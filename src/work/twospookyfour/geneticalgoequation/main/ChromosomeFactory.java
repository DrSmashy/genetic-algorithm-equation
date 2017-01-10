package work.twospookyfour.geneticalgoequation.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

public class ChromosomeFactory {
	
	Chromosome[] pop;
	
	Chromosome makeRandomChromosome() {
		Chromosome c = new Chromosome();
		c.setData(c.returnRandomData());
		return c;
	}
	
	public void start() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		pop = new Chromosome[Main.POOL_SIZE];
		double target;
		int genCount;
		boolean solutionFound;
		
        
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
	        	pop[i] = new Chromosome();
	        }
	        
	        genCount = 0;
	        solutionFound = false;
	        
	        while (!solutionFound) {
	        	double totalFitness = 0;
	        	
	        	for (int i = 0; i < pop.length; ++i) {
	        		pop[i].setFitness(pop[i].calculateFitness(target));
	        		
	        		totalFitness += pop[i].getFitness();
	        	}
	        }
	        
		}
	}
	
	private void crossover(Chromosome c1, Chromosome c2) {
		Random r = new Random();
		if (r.nextInt((int)(1 / Main.MUT_RATE)) < 1) {
			int crossover = (int) r.nextDouble() * Main.CHROMO_LEN;
			c1.setData(
					c1.getData().substring(0, crossover) + c2.getData().substring(crossover, Main.CHROMO_LEN)
					);
			c2.setData(
					c2.getData().substring(0, crossover) + c1.getData().substring(crossover, Main.CHROMO_LEN)
					);
		}
	}
	
	private Chromosome roulette(double totalFitness) {
		Random r = new Random();
		double slice = (double) (r.nextDouble() * totalFitness);
		
		double fitnessSoFar = 0;
		
		for (int i = 0; i < Main.POOL_SIZE; ++i) {
			fitnessSoFar += pop[i].getFitness();
			
			//if the fitness so far > random number return the chromo at this point
			if (fitnessSoFar >= slice) {
				return pop[i];
			}
		}
		
		return null;
	}
}

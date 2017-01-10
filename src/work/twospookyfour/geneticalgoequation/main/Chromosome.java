package work.twospookyfour.geneticalgoequation.main;

import java.util.Random;

public class Chromosome {
	private String data;
	private double fitness = 0.0d;
	
	public Chromosome() {
		setData(returnRandomData());
	}
	
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	public double getFitness() {
		return fitness;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public String returnRandomData() {
		String s = "";
		Random r = new Random();
		for (int i = 0; i < Main.CHROMO_LEN; ++i) {
			s += r.nextInt(2);
		}
		return s;
	}
	
	public String parseChromosome() {
		String parsedChromo = "";
		String finalValidChromo = "";
		
		//copy valid genes to parsedChromo
		for (int i = 0; i < data.length(); i += Main.BITS_PER_GENE) {
			String gene = data.substring(i, i + Main.BITS_PER_GENE);
			
			//If the gene's value is not valid, then ignore it.
			if (isValidGene(gene)) {
				parsedChromo += gene;
			}
			
		}
		
		/*
		 * Iterate over the chromosome to make sure that it
		 * follows the number -> operator pattern.
		 */
		
		boolean number = true;
		
		for (int i = 0; i < parsedChromo.length(); i += Main.BITS_PER_GENE) {
			int value = Integer.parseInt(parsedChromo.substring(i, i + Main.BITS_PER_GENE), 2);
			
			if (number && value < 10) {
				finalValidChromo += parsedChromo.substring(i, i + Main.BITS_PER_GENE);
				number = !number;
			} else if (!number && value >= 10) {
				
				finalValidChromo += parsedChromo.substring(i, i + Main.BITS_PER_GENE);
				number = !number;
			}
		}
		
		//Skip validation if there is zero or one symbols in the chromosome
		if (finalValidChromo.length() == 0 || finalValidChromo.length() == 4) {
			return finalValidChromo;
		}
		
		//remove the last gene if it is a operator
		if (Integer.parseInt(
				finalValidChromo.substring(finalValidChromo.length() - Main.BITS_PER_GENE, finalValidChromo.length()), 2)
				>= 10) {
			finalValidChromo = finalValidChromo.substring(0, finalValidChromo.length() - Main.BITS_PER_GENE);
		}
		
		//check for divide by zero and fix it if it is found
		for (int i = 0; i < finalValidChromo.length() - Main.BITS_PER_GENE; i += Main.BITS_PER_GENE) {
			int first = Integer.parseInt(finalValidChromo.substring(i, i + Main.BITS_PER_GENE), 2);
			int second = Integer.parseInt(finalValidChromo.substring(i + Main.BITS_PER_GENE, i + (Main.BITS_PER_GENE * 2)), 2);
			
			if (first == 13 && second == 0) {
				char[] c = finalValidChromo.toCharArray();
				
				c[i] = '1';
				c[i + 1] = '0';
				c[i + 2] = '1';
				c[i + 3] = '0';
				finalValidChromo = String.copyValueOf(c);
				break;
			}
		}
		
		
		return finalValidChromo;
	}
	
	public int[] convertToSymbols() {
		String chromo = parseChromosome();
		int[] a = new int[chromo.length() / Main.BITS_PER_GENE];
		for (int i = 0; i < a.length; ++i) {
			a[i] = Integer.parseInt(chromo.substring(i*Main.BITS_PER_GENE, (i + 1)*Main.BITS_PER_GENE), 2);
		}
		
		return a;
	}
	
	public boolean isValidGene(String gene) {
		return Main.CONV_ARRAY.length > Integer.parseInt(gene, 2);
	}
	
	public double evaluate() {
		int[] a = convertToSymbols();
		double result = a[0];
		
		for (int i = 1; i < a.length; i += 2) {
			switch (a[i]) {
				case 10:
					result += a[i + 1];
					break;
				case 11:
					result -= a[i + 1];
					break;
				/*case 12:
					result *= a[i + 1];
					break;
				case 13:
					result /= a[i + 1];
					break;*/
			}
		}
		
		return result;
	}
	
	public double calculateFitness(double target) {
		double result = evaluate();
		if (result == target) {
			return 999.9d;
		}
		
		return 1 / Math.abs(target - result);
	}
	
	public String toString() {
		String etc = "";
		int[] a = convertToSymbols();
		
		for (int i = 0; i < a.length; ++i) {
			etc +=  "" + Main.CONV_ARRAY[a[i]] + " ";
		}
		
		return data + "\nfitness: " + getFitness() + " \n" + etc;
	}
	
	public void mutate() {
		StringBuilder s = new StringBuilder(data);
		Random r = new Random();
		for (int i = 0; i < s.length(); ++i) {
			if (r.nextDouble() < Main.MUT_RATE) {
				s.setCharAt(i, s.charAt(i) == '1' ? '0' : '1');
			}
		}
		
		data = s.toString();
	}
}

package work.twospookyfour.geneticalgoequation.main;

public class Main {
	
	public static final char[] CONV_ARRAY =  {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '-','*', '/'};
	
	public static final int BITS_PER_GENE = 4;
	
	public static final int CHROMO_LEN = 4;
	
	public static final double CROSS_RATE = 0.7;
	
	public static final double MUT_RATE = 0.001;
	
	public static final int POOL_SIZE = 40;
	
	
	public static void main(String[] args) {
		ChromosomeFactory f = new ChromosomeFactory();
		f.start();
	}
	
}
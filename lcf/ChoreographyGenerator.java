import java.util.Random;
import java.util.HashSet;
import java.util.Arrays;

/*
 * Class for generating random choreographies, based on some parameters.
 */
public class ChoreographyGenerator {

    private final int LENGTH, MAX_PROCESSES, MAX_IFS, MAX_PROCEDURES;
    private int messageCounter, labelCounter, ifCounter, numProcesses, numProcedures;
    private String[] processNames, procedureNames;
    private Random generator;

    /*
     * A constructor with some default values, for testing purposes.
     */
    private ChoreographyGenerator() {
	this.generator = new Random();
	this.LENGTH = 30;
	this.MAX_PROCESSES = 5;
	this.numProcesses = 5;
	this.processNames = new String[]{"p","q","r","s","t"};
	this.MAX_IFS = 0;
	this.MAX_PROCEDURES = 0;
	this.numProcedures = 0;
	this.procedureNames = new String[]{};
	reset();
    }

    /*
     * The real constructor.
     * - seed: initial seed for the random number generator
     * - length: maximum length of each procedure/main body
     * - maxProcesses: maximum number of processes used
     * - maxIfs: maximum number of conditional instructions (globally)
     * - maxProcedures: maximum number of procedure definitions (excluding main)
     * Most parameters are bounds because we do not check for duplicates.
     * This constructor also initializes a set of process and procedure names that are
     * shared among all generated choreographies.
     */
    public ChoreographyGenerator(long seed, int length, int maxProcesses, int maxIfs, int maxProcedures) {
	this.generator = new Random(seed);
	this.LENGTH = length;
	this.MAX_PROCESSES = maxProcesses;
	this.MAX_IFS = maxIfs;
	this.MAX_PROCEDURES = maxProcedures;
	init();
    }

    /*
     * The same, but without the seed.
     */
    public ChoreographyGenerator(int length, int maxProcesses, int maxIfs, int maxProcedures) {
	this.generator = new Random();
	this.LENGTH = length;
	this.MAX_PROCESSES = maxProcesses;
	this.MAX_IFS = maxIfs;
	this.MAX_PROCEDURES = maxProcedures;
	init();
    }

    /*
     * Initializes the auxiliary attributes.
     */
    private void init() {
	// we generate process names randomly as strings of lowercase letters
	HashSet<String> auxNames = new HashSet<String>();
	int bound = Math.toIntExact(Math.round(Math.ceil(Math.log(MAX_PROCESSES)/Math.log(26))));
	for (int i=0; i<MAX_PROCESSES; i++) {
	    String name = "";
	    for (int j=0; j<bound; j++)
		name += (char)(generator.nextInt(26)+97);
	    auxNames.add(name);
	}
	this.processNames = (String[]) auxNames.toArray(new String[0]);
	this.numProcesses = processNames.length;

	// we generate procedure names as strings of uppercase letters
	auxNames = new HashSet<String>();
	if (MAX_PROCEDURES > 0) {
	    bound = Math.toIntExact(Math.round(Math.ceil(Math.log(MAX_PROCEDURES)/Math.log(26))));
	    for (int i=0; i<MAX_PROCEDURES; i++) {
		String name = "";
		for (int j=0; j<bound; j++)
		    name += (char)(generator.nextInt(26)+65);
		auxNames.add(name);
	    }
	}
	this.procedureNames = (String[]) auxNames.toArray(new String[0]);
	this.numProcedures = procedureNames.length;
    }

    /*
     * Resets the internal counters, so that a new choreography can be generated.
     */
    private void reset() {
	this.messageCounter = 0;
	this.labelCounter = 0;
	this.ifCounter = 0;
    }

    /*
     * The method we're interested in.
     */
    public Choreography generate() {
	Choreography c = new Choreography();

	// control how the if statements are distributed among procedures
	int[] ifArray = new int[numProcedures+1];
	for (int i=0; i<=numProcedures; i++)
	    ifArray[i] = 0;
	for (int i=0; i<MAX_IFS; i++)
	    ifArray[generator.nextInt(numProcedures+1)]++;

	c.addProcedure("main",generateBody(ifArray[0]));

	int ifIndex = 1;
	for (String name:procedureNames) {
	    c.addProcedure(name,generateBody(ifArray[ifIndex]));
	    ifIndex++;
	}
	return c;

	// int numIfs = MAX_IFS;
	// int ifsNow = generator.nextInt(numIfs+1);
	// numIfs = numIfs - ifsNow;
	// // System.out.println("Using "+ifsNow+" ifs in main, "+numIfs+" remaining.");
	// c.addProcedure("main",generateBody(ifsNow));

	// for (String name:procedureNames) {
	//     ifsNow = generator.nextInt(numIfs+1);
	//     numIfs = numIfs - ifsNow;
	//     // System.out.println("Using "+ifsNow+" ifs in "+name+", "+numIfs+" remaining.");
	//     c.addProcedure(name,generateBody(ifsNow));
	// }
	// return c;
    }

    /*
     * The key ingredient: generating a procedure body.
     */
    private ChoreographyNode generateBody(int numIfs) {
	return generateBody(LENGTH, numIfs);
    }

    /*
     * Parameters:
     * - size is the [exact] number of actions in the body of this procedure
     * - numIfs is the [maximum] number of if statements in the body of this procedure
     * Because of the use of random numbers, it is possible that there are less if statements
     * than allowed. The likelihood decreases as size/numIfs increases.
     */
    private ChoreographyNode generateBody(int size, int numIfs) {
	if (size == 0) {
	    int endType = generator.nextInt(numProcedures+1);
	    if (endType == numProcedures)
		return new TerminationNode();
	    else
		return new CallNode(procedureNames[endType]);
	}

	else {
	    if (generator.nextDouble()*size < numIfs) {
		int thenSize = generator.nextInt(size+1);
		int ifsThen = Math.min(generator.nextInt(numIfs),thenSize); // ifsThen <= thenSize
		if (numIfs-ifsThen > size-thenSize) // too many ifs on else branch
		    ifsThen = numIfs+thenSize-size;
		String decider = processNames[generator.nextInt(numProcesses)];
		String condition = "c" + (++ifCounter);
		// System.out.println("Splitting "+numIfs+" into "+ifsThen+" (then) and "+(numIfs-ifsThen-1)+" (else).");
		return new ConditionalNode(decider, condition, generateBody(thenSize,ifsThen),generateBody(size-thenSize,numIfs-ifsThen-1));
	    }
	    else {
		String sender = processNames[generator.nextInt(numProcesses)];
		String receiver = processNames[generator.nextInt(numProcesses)];
		while (receiver.equals(sender))
		    receiver = processNames[generator.nextInt(numProcesses)];
		if (generator.nextBoolean()) {
		    String message = "m" + (++messageCounter);
		    return new CommunicationNode(sender, receiver, message, generateBody(size-1,numIfs));
		}
		else {
		    String label = "l" + (++labelCounter);
		    return new SelectionNode(sender, receiver, label, generateBody(size-1,numIfs));
		}
	    }
	}
    }

    /*
     * For testing purposes only.
     */
    public static void main(String[] args) {
	/*
	Random generator = new Random();
	generator.setSeed(123);
	for (int i=0; i<10; i++)
	    System.out.println(""+generator.nextDouble());
	*/

	ChoreographyGenerator // g1 = new ChoreographyGenerator(),
	    g2 = new ChoreographyGenerator(123,15,4,5,2);
	//g3 = new ChoreographyGenerator(15,4,5,2);

	// System.out.println("g1:");
	// System.out.println(Arrays.toString(g1.processNames));
	// System.out.println(Arrays.toString(g1.procedureNames));
	// System.out.println(g1.generateBody().toString());
	// System.out.println(g1.generateBody().toString());
	// System.out.println(g1.generateBody().toString());
	// System.out.println(g1.generate().toString());
	// System.out.println();

	System.out.println("g2:");
	// System.out.println(Arrays.toString(g2.processNames));
	// System.out.println(Arrays.toString(g2.procedureNames));
	// System.out.println(g2.generateBody().toString());
	// System.out.println(g2.generateBody().toString());
	// System.out.println(g2.generateBody().toString());
	System.out.println(g2.generate().toString());
	System.out.println();

	// System.out.println("g3:");
	// System.out.println(Arrays.toString(g3.processNames));
	// System.out.println(Arrays.toString(g3.procedureNames));
	// System.out.println(g3.generateBody().toString());
	// System.out.println(g3.generateBody().toString());
	// System.out.println(g3.generateBody().toString());
	// System.out.println(g3.generate().toString());
	// System.out.println();
    }
}

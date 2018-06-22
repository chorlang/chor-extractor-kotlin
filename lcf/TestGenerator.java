import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/*
 * Here we generate the tests we want.
 *
 * Cheat sheet:
 * ChoreographyGenerator(int length, int numProcesses, int numIfs, int numProcedures)
 *
 * Statistical info:
 * with n procs, the chance that consecutive communications are independent is (n-2)(n-3)/(n(n-1))
 * 4 <-> 1/6
 * 5 <-> 30%
 * 6 <-> 2/5
 * 7 <-> 10/21
 *
 * Comments:
 * - When procedures are involved, we may get dead code; this implies that e.g. the number of ifs
 *   may be smaller than expected. I added some static analysis to eliminate this situation and only
 *   generate tests with the required parameters.
 * - We're currently not amending the choreographies. Amending will increase the size, but this seems
 *   unavoidable.
 */
public class TestGenerator {

    private static final int NUMBER_OF_TESTS = 50;
    private static int generatedTests = 0,
        badTests = 0;
    private static final String LOG_FILE = "summary.log";

    private static void makeALotOfTests(int length, int numProcesses, int numIfs, int numProcedures, BufferedWriter logFile)
	throws IOException, GeneratorException {

	// standard filename
	String testFileName = "chor-"+length+"-"+numProcesses+"-"+numIfs+"-"+numProcedures;
	logFile.write("Generating file "+testFileName);
	logFile.newLine();

	// seed info
        ChoreographyGenerator tester = new ChoreographyGenerator(length, numProcesses, numIfs, numProcedures);
        logFile.write("Seed (for reproducibility): "+tester.getSeed());
	logFile.newLine();

	// actual generation of choreographies
	BufferedWriter writer = new BufferedWriter(new FileWriter("tests/"+testFileName));
        for (int j=0; j<NUMBER_OF_TESTS; j++) {
            // currently not amending for readability
            Choreography c = tester.generate();
            generatedTests++;
            while (c.hasDeadCode()) {
                c = tester.generate();
                generatedTests++;
                badTests++;
            }
            writer.write(c.toString());
	    writer.newLine();
        }
	writer.close();
    }

    /*
     * Pretty-printing of headers & stuff
     */
    private static void niceWrite(BufferedWriter file, String message) throws IOException {
	file.write("+");
	for (int i=0; i<message.length()+2; i++)
	    file.write("-");
	file.write("+");
	file.newLine();
	file.write("| "+message+" |");
	file.newLine();
	file.write("+");
	for (int i=0; i<message.length()+2; i++)
	    file.write("-");
	file.newLine();
    }

    public static void main(String[] args) throws IOException, GeneratorException {
        // ChoreographyGenerator tester = new ChoreographyGenerator(10,6,4,1);
        // Choreography c = tester.generate();
        // System.out.println(c.toString()+"\n");
        // System.out.println(c.amend().toString()+"\n");
        // System.exit(0);

	BufferedWriter logFile = new BufferedWriter(new FileWriter(LOG_FILE));

	niceWrite(logFile,"STARTING GENERATION...");
	logFile.newLine();

	niceWrite(logFile,"Test 1: communications only, increasing lengths");
        for (int i=10; i<=100; i+=10)
            makeALotOfTests(i,6,0,0,logFile);
	logFile.newLine();

        niceWrite(logFile,"Test 2: communications and ifs, fixed length, increasing number of ifs");
        for (int i=10; i<=50; i+=10)
            makeALotOfTests(50,6,i,0,logFile);
	logFile.newLine();

	niceWrite(logFile,"Test 3: inserting recursion; two varying parameters - #ifs and #procs");
        for (int i=0; i<=5; i++)
            for (int j=0; j<=5; j++)
                makeALotOfTests(10,5,i,j,logFile);
	logFile.newLine();

	niceWrite(logFile,"Test 4: communications only, fixed length, increasing number of processes");
        for (int i=5; i<=100; i+=5)
            makeALotOfTests(40,i,0,0,logFile);
	logFile.newLine();

        niceWrite(logFile,"Generated "+generatedTests+" tests, of which "+badTests+" contain dead code.");
	logFile.close();
    }

}

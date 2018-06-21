/*
 * Here we generate the tests we want.
 *
 * Cheat sheet:
 * ChoreographyGenerator(int length, int maxProcesses, int maxIfs, int maxProcedures)
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

    private static void makeALotOfTests(int length, int maxProcesses, int maxIfs, int maxProcedures) throws GeneratorException {
        ChoreographyGenerator tester = new ChoreographyGenerator(length, maxProcesses, maxIfs, maxProcedures);
        for (int j=0; j<NUMBER_OF_TESTS; j++) {
            // currently not amending for readability
            Choreography c = tester.generate();
            generatedTests++;
            while (c.hasDeadCode()) {
                c = tester.generate();
                generatedTests++;
                badTests++;
            }
            System.out.println(c.toString());
        }
        System.out.println("Seed (for reproducibility): "+tester.getSeed());
    }

    public static void main(String[] args) throws GeneratorException {
        ChoreographyGenerator tester = new ChoreographyGenerator(100,6,10,5);
        System.out.println(tester.generate().toString()+"\n");
        System.exit(0);

        // Test 1: communications only, increasing lengths
        for (int i=10; i<=100; i+=10)
            makeALotOfTests(i,6,0,0);

        // Test 2: communications and ifs, fixed length, increasing number of ifs
        for (int i=10; i<=100; i+=10)
            makeALotOfTests(100,6,i,0);

        // Test 3: inserting recursion; two varying parameters - #ifs and #procs
        for (int i=0; i<5; i++)
            for (int j=0; j<5; j++)
                makeALotOfTests(10,5,i,j);

        // Test 4: communications only, fixed length, increasing number of processes
        for (int i=5; i<=100; i+=5)
            makeALotOfTests(40,i,0,0);

        System.out.println("Generated "+generatedTests+" tests, of which "+badTests+" contain dead code.");
    }

}

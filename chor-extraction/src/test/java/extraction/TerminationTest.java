package extraction;

import ast.cc.interfaces.CCNode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class TerminationTest extends Assert {

    private ChoreographyExtraction np;

    @Before
    public void setUp() throws Exception {
        np = new ChoreographyExtraction();
    }

    @DataPoints
    public static Object[][] data = new Object[][]{

            /* simple termination */
            {
                    "p {main{stop}} | q {main{stop}}", "stop"
            },

            /* recursive termination */
            {
                    "p {main{stop}} | q {def X {stop} main{X}}", "stop"
            }
    };

    @Theory
    public void testProject(final Object... testData) throws Exception {
        System.out.println("\n" + "Test: " + testData[0]);
        CCNode graph = np.extractChoreography((String) testData[0]);

        //assertEquals(testData[1], graph.toString());
    }
}
package extraction;

import ast.cc.interfaces.CCNode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class SelectionTest extends Assert {

    private ChoreographyExtraction np;

    @Before
    public void setUp() throws Exception {
        np = new ChoreographyExtraction();
    }

    @DataPoints
    public static Object[][] data = new Object[][]{

            {
                    "p { main {q+R; q!<e>; q?; stop}} | " +
                            "q { main {p&{R: p?; p!<u1>; stop, L: p?; p!<u2>; stop}}} | " +
                            "r { main {stop}}",
                    "p->q[R]; p.e->q; q.u1->p; stop"
            }
            ,
            {
                    "p { main {q+L; q!<e>; q?; stop}} | " +
                            "q { main {p&{R: p?; p!<u1>; stop, L: p?; p!<u2>; stop}}} | " +
                            "r { main {stop}}",
                    "p->q[R]; p.e->q; q.u1->p; stop"
            },

            {
                    "p { def X {q+R; q!<e>; X} main{X}} | " +
                            "q { def Y {p&{R: p?; Y, L: p?; Y}} main{Y}}",
                    "p->q[R]; p.e->q; q.u1->p; stop"
            }

    };

    @Theory
    public void testProject(final Object... testData) throws Exception {
        System.out.println("\n" + "Test: " + testData[0]);
        CCNode graph = np.extractChoreography((String) testData[0]);

        //assertEquals(testData[1], graph.toString());
    }
}
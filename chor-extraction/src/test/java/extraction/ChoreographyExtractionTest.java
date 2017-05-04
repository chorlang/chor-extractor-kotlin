package extraction;

import ast.cc.interfaces.CCNode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.lang.reflect.InvocationTargetException;

@RunWith(Theories.class)
public class ChoreographyExtractionTest extends Assert{

    private ChoreographyExtraction np;

    @Before
    public void setUp() throws Exception {
        np = new ChoreographyExtraction();
    }

    @DataPoints
    public static Object[][] data = new Object[][]{
            {
                    "p { main {q!<e>; q?; stop}} " +
                    "| q { main {p?; p!<u>; stop}} " +
                    "| r { main {stop}}",
                    "p.e->q; q.u->p; stop"
            },
            {
                    "p { main {q+R; q!<e>; q?; stop}} | " +
                    "q { main {p&{R: p?; p!<u1>; stop, L: p?; p!<u2>; stop}}} | " +
                    "r { main {stop}}",
                    "p->q[R]; p.e->q; q.u1->p; stop"
            },
            {
                    "p { main {if p.e then q+R; q!<e>; q?; stop else q+L; q!<e>; q?; stop}} | " +
                    "q { main {p&{R: p?; p!<u1>; stop, L: p?; p!<u2>; stop}}} | " +
                    "r { main {stop}}",
                    "if p.e then p->q[R]; p.e->q; q.u1->p; stop else p->q[L]; p.e->q; q.u2->p; stop"
            }

    };

    @Theory
    public void testProject(final Object... testData) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        np.parse((String) testData[0]);
        CCNode graph = np.extract();
        assertEquals(testData[1], graph.toString());
        System.out.println("Network: " + testData[0] +"\n Graph: " + graph.toString());
    }
}
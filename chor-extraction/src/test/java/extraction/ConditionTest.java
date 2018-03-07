package extraction;

import ast.cc.interfaces.CCNode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class ConditionTest extends Assert {

    private ChoreographyExtraction np;

    @Before
    public void setUp() throws Exception {
        np = new ChoreographyExtraction();
    }

    @DataPoints
    public static Object[][] data = new Object[][]{

            /* simple condition */
            {
                    "p { main {if e then q!<e1>; stop else q!<e2>; stop}} " +
                            "| q { main {p?; stop}} " +
                            "| r { main {stop}}",
                    "p.e->q; q.u->p; stop"
            },

            /* condition with selection */
            {
                    "p { main {if e then q+R; q!<e>; q?; stop else q+L; q!<e>; q?; stop}} | " +
                            "q { main {p&{R: p?; p!<u1>; stop, L: p?; p!<u2>; stop}}} | " +
                            "r { main {stop}}",
                    "if p.e then p->q[R]; p.e->q; q.u1->p; stop else p->q[L]; p.e->q; q.u2->p; stop"
            },

            /* condition inside condition */
            {
                    "p { main {if e then if u then q!<e1>; stop else q!<e2>; stop else q!<e3>; stop}} " +
                            "| q { main {p?; stop}} " +
                            "| r { main {stop}}",
                    "p.e->q; q.u->p; stop"
            },

             /* condition with procedure */
            {
                    "p {def X {if e then q!<u>;stop else q!<o>;stop} main {X}} " +
                            "| q {def Y{p?;stop} main {Y}} " +
                            "| r { main {stop}}",
                    "p.e->q; q.u->p; stop"
            },

            /* condition with recursive procedure */
            {
                    "p {def X {if e then q!<u>;X else q!<o>;X} main {X}} " +
                            "| q {def Y{p?;Y} main {Y}} " +
                            "| r { main {stop}}",
                    "p.e->q; q.u->p; stop"
            },

            /* condition with selection with recursion*/
            {
                    "p { def X {if e then q+R; q!<e>; q?; stop else q+L; q!<e>; q?; stop} main {X}} | " +
                            "q { def X {p&{R: p?; p!<u1>; stop, L: p?; p!<u2>; stop}} main {X}} | " +
                            "r { main {stop}}",
                    "if p.e then p->q[R]; p.e->q; q.u1->p; stop else p->q[L]; p.e->q; q.u2->p; stop"
            }
    };

    @Theory
    public void testProject(final Object... testData) throws Exception {
        System.out.println("\n" + "Test: " + testData[0]);
        CCNode program = np.extractChoreography((String) testData[0]);
        System.out.println(program.toString());

        //assertEquals(testData[1], graph.toString());
    }
}
package extraction;

import ast.cc.interfaces.CCNode;
import org.junit.Assert;
import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class ChoreographyExtractionTest extends Assert {

    private ChoreographyExtraction np;

    /*@Before
    public void setUp() throws Exception {
        np = new ChoreographyExtraction();
    }*/

    @DataPoints
    public static Object[][] data = new Object[][]{
            /*{
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
            },
            {
                    "p { def X {q!<e>; stop} main {X}} " +
                            "| q { def X {p?; stop} main {X}} " +
                            "| r { main {stop}}",
                    "p.e->q; stop"
            },
            {
                    "p {main{stop}}", "stop"
            },
            {
                    "p {main{stop}} | q {main{stop}}", "stop"
            },
            {
                    "p {main{stop}} | q {def X {stop} main{X}}", "stop"
            },
            {
                    "p {main{q?;stop}} | q {main{p!<e>;stop}}", "q.e->p; stop"
            },
            {
                    "p {main{q?;stop}} | q { def X {p!<e>;stop} main{X}}", "q.e->p; stop"
            },
            {
                    "p {main{q!<e>;stop}} | q { def X {p?;stop} main{X}}", "p.e->q; stop"
            },
            {
                    "p { def X {q!<e>;X} main{X}} | q { def X {p?;X} main{X}}", "p.e->q; stop"
            },
            {
                    "p { def X {q?;X} main{X}} | q { def X {p!<e>;X} main{X}}", "q.e->p; stop"
            }
            ,
            {
                    "p { def X {q+R; q!<e>; q?;X} main{X}} | q { def X {p&{R: p?; p!<u1>; stop, L: p?; p!<u2>;X}} main{X}}", "p->q[R]; p.e->q; q.u1->p; stop"
            },
            {
                    "p { def X {if p.e then q+R; q!<e>; q?; stop else q+L; q!<e>; q?; stop} main {X}} | " +
                            "q { def X {p&{R: p?; p!<u1>; stop, L: p?; p!<u2>; stop}} main {X}} | " +
                            "r { main {stop}}",
                    "if p.e then p->q[R]; p.e->q; q.u1->p; stop else p->q[L]; p.e->q; q.u2->p; stop"
            },
            {
                    "p {def X {q!<e1>; q&{L: q!<e2>;X, R: stop}} main {q!<e3>; X}} | q {def Y {p?;p?; if q.e1 then p+L;Y else p+R;stop} main {Y}}",
                    "p.e3->q; p.e1->q; if q.e1 then q->p[L]; stop else q->p[R]; stop"
            },*/
            {
                    "p { main {q!<true==false>; q?; stop}} " +
                            "| q { main {p?; p!<false&&(true||true)&&false>; stop}} " +
                            "| r { main {stop}}",
                    "p.e->q; q.u->p; stop"
            }

    };

    @Theory
    public void testProject(final Object... testData) throws Exception {
        np = new ChoreographyExtraction((String) testData[0]);
        CCNode graph = np.extract();
        assertEquals(testData[1], graph.toString());
        System.out.println("Network: " + testData[0] + "\nGraph: " + graph.toString());
    }
}
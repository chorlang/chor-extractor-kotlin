package epp;

import org.junit.Assert;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class EndpointProjectionTest extends Assert {

    private EndPointProjection epp;

    @org.junit.Before
    public void setUp() throws Exception {
        epp = new EndPointProjection();
    }

    @DataPoints
    public static Object[][] data = new Object[][] {

            {
                " def X {Y} def Y { p.e->q; stop } main {q.e->p;X}",
                    "p{X{Y} Y{q!<e>; stop} main {q?; X}} | q{X{Y} Y{p?; stop} main {p!<e>; X}}"
            },
            //termination
            /*/ { "main { stop }", "" },                                             //termination
            // { "def X { p.e->q;stop } main { X }", "p is def X =q!<e>; stop in X | q is def X =p?; stop in X" }, //procedure definition/invocation
            //{ "stop", "" },                                             //termination
            //{ "p.e->q;stop", "p is q!<e>; stop | q is p?; stop" },      //communication
            //{ "p->q[l];stop", "p is q + l; stop | q is p&{l: stop}" },  //selectoffer
            { "def X = p.e->q;stop in X", "p is def X =q!<e>; stop in X | q is def X =p?; stop in X" }, //procedure definition/invocation
            /{ "if p.e then p.e->q;stop else p.e->q;stop", "p is if p.e then q!<e>; stop else q!<e>; stop | q is p?; stop" }, //condition
            //{ "if p.e then " +
                    "if q.e2 then p.e1 -> q; stop else p.e1 -> q; stop" +
                    " else if q.e2 then p.e3 -> q; stop else p.e3 -> q; stop",
                    "p is if p.e then q!<e1>; stop else q!<e3>; stop | q is if q.e2 then p?; stop else p?; stop"
            },
            //{ "if p.e then " +
                    "p -> q[L]; p.e -> q; q.x -> r; r.z -> q; stop" +
                    " else p -> q[R]; q.y -> r; r.z -> q; q.u -> p; stop",
                    "p is if p.e then q + L; q!<e>; stop else q + R; q?; stop |" +
                            " q is p&{R: r!<y>; r?; p!<u>; stop, L: p?; r!<x>; r?; stop} |" +
                            " r is q?; q!<z>; stop"
            },
            { "if p.e then " +
                    "p -> q[L]; p.e -> q; q -> r[L1]; r.z1 -> q; stop" +
                    " else p -> q[R]; q -> r[R1]; r.z2 -> q; q.u -> p; stop",
                    "p is if p.e then q + L; q!<e>; stop else q + R; q?; stop |" +
                            " q is p&{R: r + R1; r?; p!<u>; stop, L: p?; r + L1; r?; stop} |" +
                            " r is q&{L1: q!<z1>; stop, R1: q!<z2>; stop}"
            }*/
    };

    @Theory
    public void testProjection(final Object... testData) throws MergingException {
        String test = (String) testData[0];
        final String actual = epp.project(test).toString();
        assertEquals(test, testData[1], actual);
        System.out.println("Test: " + test);
        System.out.println("Output: " + testData[1]);
    }
}
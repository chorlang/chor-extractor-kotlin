package epp;

import org.junit.Assert;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class PositiveTests extends Assert {

    private EndPointProjection epp;

    @org.junit.Before
    public void setUp() throws Exception {
        epp = new EndPointProjection();
    }

    @DataPoints
    public static Object[][] data = new Object[][] {
            { "stop", "" },                                             //termination
            { "p.e->q;stop", "p is q!<e>; stop | q is p?; stop" },      //communication
            { "p->q[l];stop", "p is q + l; stop | q is p&{l: stop}" },  //selection
            { "def X = p.e->q;stop in X", "p is def X =q!<e>; stop in X | q is def X =p?; stop in X" }, //procedure definition/invocation
            { "if p.e then p.e->q;stop else p.e->q;stop", "p is if p.e then q!<e>; stop else q!<e>; stop | q is p?; stop" }, //condition
    };

    @Theory
    public void testProjection(final Object... testData) throws MergingException {
        String test = (String) testData[0];
        final String actual = epp.projectToString(test);
        assertEquals(test, testData[1], actual);
        System.out.println("Test: " + test);
        System.out.println("Output: " + testData[1]);

    }
}
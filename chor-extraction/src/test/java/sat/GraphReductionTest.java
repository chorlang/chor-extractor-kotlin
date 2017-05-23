package sat;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class GraphReductionTest {
    @DataPoints
    public static Object[][] data = new Object[][]{
            /*{
                            "p {def X {q!<e1>; q&{L: q!<e2>;X, R: stop}} main {q!<e3>; X}} | " +
                            "q {def Y {p?;p?; if q.e1 then p+L;Y else p+R;stop} main {Y}}",
                            "p.e3->q; p.e1->q; if q.e1 then q->p[L]; stop else q->p[R]; stop"
            },*/
            {
                            "p {def X {q!<e1>; q&{L: q!<e2>;X, R: q!<e4>;X}} main {q!<e3>; X}} | " +
                            "q {def Y {p?;p?; if q.e1 then p+L;Y " +
                                    "else p+R; p?; if q.e4 then if q.e1 then Y else Y else Y} main {Y}}",
                            "p.e3->q; p.e1->q; if q.e1 then q->p[L]; stop else q->p[R]; stop"
            }

    };


    @Theory
    public void testreduction(final Object... testData) throws Exception {
        GraphReduction gr = new GraphReduction((String) testData[0]);
    }

}
package sat;

import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

@RunWith(Theories.class)
public class GraphReductionTest {
    @DataPoints
    public static Object[][] data = new Object[][]{
             {
                            "p {def X {q!<e1=true&&e3>; q&{L: q!<e2=false>;X, R: stop}} main {q!<e3=false>; X}} | " +
                            "q {def Y {p?;p?; if q.e1 then p+L;Y else p+R;stop} main {Y}}",
                            //Test name
                            "Test on atom expressions"
            },
            {
                            "p { main {q!<e1 = true>; q!<e2=false && ( false || e1 )>; q?; stop}} | " +
                            "q { main {p?; p?; if q.e1!=e2 then p!<e3=true>; stop else p!<e3=false>; stop}} | " +
                            "r { main {stop}}",
                             //Test name
                            "Test on complex expressions"
            },
            {
                            "p {def X {q!<e1=true||false>; q&{L: q!<e2=e1&&true>;X, R: q!<e4=false==true>;X}} main {q!<e3=~(e1||~e2&&false)>; X}} | " +
                            "q {def Y {p?;p?; if q.e1&&~e3 then p+L;Y else p+R; p?; if q.e2 then if q.e1 then s!<v1=true!=false>;Y else s!<v2=true>;Y else Y} main {Y}} | " +
                            "s {def X{q?;X} main {X}}",
                            //Test name
                            "Test on complex expressions with exception \"process not found\""
            },

    };


    @Theory
    public void testreduction(final Object... testData) throws Exception {
        try {
            System.out.println((String) testData[1]);
            GraphReduction gr = new GraphReduction((String) testData[0]);
            System.out.println("Reducted choreography: " + gr.getReductedGraph() + "\n");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
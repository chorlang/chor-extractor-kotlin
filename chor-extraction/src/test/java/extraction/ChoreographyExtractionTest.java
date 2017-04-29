package extraction;

import ast.cc.interfaces.CCNode;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class ChoreographyExtractionTest {

    private ChoreographyExtraction np;
    @Before
    public void setUp() throws Exception {
        np = new ChoreographyExtraction();
    }

    @Test
    public void testParse() {
        np.parse("p is q!<e>; stop | q is p?; stop | r is stop");
    }

    @Test
    public void testProject() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        //np.parse("p { main {q!<e>; q?; stop}} | q { main {p?; p!<u>; stop}} | r { main {stop}}");
        //np.parse("p { main {q+R; q!<e>; q?; stop}} | q { main {p&{R: p?; p!<u1>; stop, L: p?; p!<u2>; stop}}} | r { main {stop}}");
        np.parse("p { main {if p.e then q+R; q!<e>; q?; stop else q+L; q!<e>; q?; stop}} | q { main {p&{R: p?; p!<u1>; stop, L: p?; p!<u2>; stop}}} | r { main {stop}}");
        //DirectedGraph graph =
        CCNode node = np.extract();
        System.out.println(node.toString());
        //System.out.println( graph.toString() );
    }
}
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
        np.parse("p { main {q!<e>; stop}} | q { main {p?; stop}} | r { main {stop}}");
        //DirectedGraph graph =
        CCNode node = np.extract();
        node.toString();
        //System.out.println( graph.toString() );
    }
}
package extraction;

import org.jgrapht.DirectedGraph;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;

public class ChoreographyProjectionTest {

    private ChoreographyProjection np;
    @Before
    public void setUp() throws Exception {
        np = new ChoreographyProjection();
    }

    @Test
    public void testParse() {
        np.parse("p is q!<e>; stop | q is p?; stop | r is stop");
    }

    @Test
    public void testProject() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        np.parse("p is q!<e>; stop | q is p?; stop | r is stop");
        DirectedGraph graph =  np.project();
        graph.toString();
    }
}
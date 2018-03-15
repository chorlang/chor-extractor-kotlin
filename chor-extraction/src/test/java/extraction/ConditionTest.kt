package extraction

import ast.cc.interfaces.CCNode
import org.junit.Assert
import org.junit.Before
import org.junit.experimental.theories.DataPoints
import org.junit.experimental.theories.Theories
import org.junit.experimental.theories.Theory
import org.junit.runner.RunWith

@RunWith(Theories::class)
class ConditionTest : Assert() {

    @Theory
    @Throws(Exception::class)
    fun test(vararg testData: Any) {
        println("\n" + "Test Network: " + testData[0])
        val np = ChoreographyExtraction(testData[0] as String)
        val program = np?.extractChoreography()
        println("Choreography: " + program!!.toString())

        Assert.assertEquals(testData[1], program.toString())
    }

    companion object {
        @DataPoints
        @JvmField

        var data = arrayOf(

                /* simple condition */
                arrayOf<Any>
                ("p { main {if e then q!<e1>; stop else q!<e2>; stop}} " +
                        "| q { main {p?; stop}} " +
                        "| r { main {stop}}",

                        "main { if e then p.e1->q; 0 else p.e2->q; 0 }"),

                /* condition with selection */
                arrayOf<Any>("p { main {if e then q+R; q!<e>; q?; stop else q+L; q!<e>; q?; stop}} | " +
                        "q { main {p&{R: p?; p!<u1>; stop, L: p?; p!<u2>; stop}}} | " +
                        "r { main {stop}}",

                        "main { if e then p->q[R]; p.e->q; q.u1->p; 0 else p->q[L]; p.e->q; q.u2->p; 0 }"),

                /* condition inside condition */
                arrayOf<Any>("p { main {if e then if u then q!<e1>; stop else q!<e2>; stop else q!<e3>; stop}} " +
                        "| q { main {p?; stop}} " +
                        "| r { main {stop}}",

                        "main { if e then if u then p.e1->q; 0 else p.e2->q; 0 else p.e3->q; 0 }"),

                /* condition with procedure */
                arrayOf<Any>("p {def X {if e then q!<u>;stop else q!<o>;stop} main {X}} " +
                        "| q {def Y{p?;stop} main {Y}} " +
                        "| r { main {stop}}",

                        "main { if e then p.u->q; 0 else p.o->q; 0 }"),


                /* condition with recursive procedure */
                arrayOf<Any>
                        ("p {def X {if e then q!<u>;X else q!<o>;X} main {X}} " +
                        "| q {def Y{p?;Y} main {Y}} " +
                        "| r { main {stop}}",

                        "def X1 { if e then p.u->q; X1 else p.o->q; X1 } main { X1 }"),


                /* condition with selection with recursion*/
                arrayOf<Any>("p { def X {if e then q+R; q!<e>; q?; stop else q+L; q!<e>; q?; stop} main {X}} | " +
                        "q { def X {p&{R: p?; p!<u1>; stop, L: p?; p!<u2>; stop}} main {X}} | " +
                        "r { main {stop}}",

                        "main { if e then p->q[R]; p.e->q; q.u1->p; 0 else p->q[L]; p.e->q; q.u2->p; 0 }"))

    }
}
package ce

import ce.ChoreographyExtraction
import org.junit.Assert
import org.junit.Test
import org.junit.experimental.theories.Theories
import org.junit.runner.RunWith


@RunWith(Theories::class)
class NbTest : Assert() {

    @Test
    fun nbtst1(){
        val test = "" +
                "a {def X " +
                    "{if e " +
                        "then b+win; c+lose; b?; c?; d!<free>; X " +
                        "else b+lose; c+win; b?; c?; d!<free>; X} " +
                    "main {X}} |" +
                "b {def X " +
                    "{a&{" +
                        "win: c!<lose>; a!<sig>; X, " +
                        "lose: c?; a!<sig>; X}} " +
                    "main {X}} |" +
                "c {def X " +
                    "{d!<busy>; a&{" +
                        "win: b!<lose>; a!<msg>; X, " +
                        "lose: b?; a!<msg>; X}} " +
                    "main {X}} |" +
                "d {def X " +
                    "{c?; a?; X} " +
                    "main {X}}"
        val args = arrayOf("-c", test)
        ChoreographyExtraction.main(args)

        val res =
                "def X1 { " +
                        "if a.e " +
                            "then a->b[win]; c.busy->d; a->c[lose]; b.lose->c; b.sig->a; c.msg->a; a.free->d; X1 " +
                            "else a->b[lose]; c.busy->d; a->c[win]; c.lose->b; b.sig->a; c.msg->a; a.free->d; X1 " +
                        "} " +
                "main {X1}"
    }

    @Test
    fun nbtst1x2(){
        val test =
                "a {def X {if e then b+win; c+lose; b?; c?; d!<free>; X else b+lose; c+win; b?; c?; d!<free>; X} main {X}} |" +
                "b {def X {a&{win: c!<lose>; a!<sig>; X, lose: c?; a!<sig>; X}} main {X}} |" +
                "c {def X {d!<busy>; a&{win: b!<lose>; a!<msg>; X, lose: b?; a!<msg>; X}} main {X}} |" +
                "d {def X {c?; a?; X} main {X}} | " +

                "a3 {def X {b3!<o>; b3?; X} main {X}} |" +
                "b3 {def X {a3?; a3!<u>; X} main {X}} |" +


              "a2 {def X {if e then b2+win; c2+lose; b2?; c2?; d2!<free>; X else b2+lose; c2+win; b2?; c2?; d2!<free>; X} main {X}} |" +
                "b2 {def X {a2&{win: c2!<lose>; a2!<sig>; X, lose: c2?; a2!<sig>; X}} main {X}} |" +
                "c2 {def X {d2!<busy>; a2&{win: b2!<lose>; a2!<msg>; X, lose: b2?; a2!<msg>; X}} main {X}} |" +
                "d2 {def X {c2?; a2?; X} main {X}} "

        val args = arrayOf("-c", test)
        ChoreographyExtraction.main(args)

        val res =
                "def X1 { a2.o->b2; b2.u->a2; X1 } " +
                "def X2 { a2.o->b2; b2.u->a2; X2 } " +
                "main {if a.e " +
                        "then a2.o->b2; b2.u->a2; a->b[win]; c.busy->d; a->c[lose]; X1 " +
                        "else a2.o->b2; b2.u->a2; a->b[lose]; c.busy->d; a->c[win]; X2" +
                      "}"

        /*"def X1 { a->b[win]; c.busy->d; a->c[lose]; a2.o->b2; b2.u->a2; b.lose->c; X2 } " +
        "def X2 { b.sig->a; c.msg->a; a.free->d; if a.e then X1 else a->b[lose]; c.busy->d; a->c[win]; a2.o->b2; b2.u->a2; c.lose->b; X2 } " +
        "def X3 { a->b[lose]; c.busy->d; a->c[win]; a2.o->b2; b2.u->a2; c.lose->b; X4 } " +
        "def X4 { b.sig->a; c.msg->a; a.free->d; if a.e then a->b[win]; c.busy->d; a->c[lose]; a2.o->b2; b2.u->a2; b.lose->c; X4 else X3 } " +
        "main {if a.e then a2.o->b2; b2.u->a2; X1 else a2.o->b2; b2.u->a2; X3}"

        "def X1 { if a.e then if a2.e then a2->b2[win]; a->b[win]; a3.o->b3; b3.u->a3; c.busy->d; a->c[lose]; b.lose->c; b.sig->a; c.msg->a; a.free->d; c2.busy->d2; a2->c2[lose]; b2.lose->c2; b2.sig->a2; c2.msg->a2; a2.free->d2; X1 else a2->b2[lose]; a->b[win]; a3.o->b3; b3.u->a3; c.busy->d; a->c[lose]; b.lose->c; b.sig->a; c.msg->a; a.free->d; c2.busy->d2; a2->c2[win]; c2.lose->b2; b2.sig->a2; c2.msg->a2; a2.free->d2; X1 else if a2.e then a2->b2[win]; a->b[lose]; a3.o->b3; b3.u->a3; c.busy->d; a->c[win]; c.lose->b; b.sig->a; c.msg->a; a.free->d; c2.busy->d2; a2->c2[lose]; b2.lose->c2; b2.sig->a2; c2.msg->a2; a2.free->d2; X1 else a2->b2[lose]; a->b[lose]; a3.o->b3; b3.u->a3; c.busy->d; a->c[win]; c.lose->b; b.sig->a; c.msg->a; a.free->d; c2.busy->d2; a2->c2[win]; c2.lose->b2; b2.sig->a2; c2.msg->a2; a2.free->d2; X1 } main {X1}"
        */
    }

    @Test
    fun tst(){
        val test =
                "a1 {def X {b1!<1>; b1?; X} main {X}} |" +
                "b1 {def X {a1?; a1!<0>; X} main {X}} |" +
                "a2 {def X {b2!<1>; b2?; X} main {X}} |" +
                "b2 {def X {a2?; a2!<0>; X} main {X}} |" +
                "a3 {def X {b3!<1>; b3?; X} main {X}} |" +
                "b3 {def X {a3?; a3!<0>; X} main {X}} |" +
                "a4 {def X {b4!<1>; b4?; X} main {X}} |" +
                "b4 {def X {a4?; a4!<0>; X} main {X}}"
        val args = arrayOf("-c", test)
        ChoreographyExtraction.main(args)

        val res = "def X1 { a2.1->b2; b2.0->a2; a3.1->b3; b3.0->a3; a4.1->b4; b4.0->a4; a1.1->b1; b1.0->a1; X1 } main {X1}"
    }
}



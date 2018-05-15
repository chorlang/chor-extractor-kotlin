package ce

import np.NetworkProjection
import org.junit.Assert
import org.junit.Test

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

        val actual = ChoreographyExtraction.main(args)
        val expected =
                "def X1 { " +
                    "if a.e " +
                        "then a->b[win]; c.busy->d; a->c[lose]; b.lose->c; b.sig->a; c.msg->a; a.free->d; X1 " +
                        "else a->b[lose]; c.busy->d; a->c[win]; c.lose->b; b.sig->a; c.msg->a; a.free->d; X1 " +
                "} main {X1}"

        assertEquals(expected, actual)
    }

    @Test
    fun nbtst1X2(){

        val test =
                "a1 {def X {if e then b1+win; c1+lose; b1?; c1?; d1!<free>; X else b1+lose; c1+win; b1?; c1?; d1!<free>; X} main {X}} |" +
                "b1 {def X {a1&{win: c1!<lose>; a1!<sig>; X, lose: c1?; a1!<sig>; X}} main {X}} |" +
                "c1 {def X {d1!<busy>; a1&{win: b1!<lose>; a1!<msg>; X, lose: b1?; a1!<msg>; X}} main {X}} |" +
                "d1 {def X {c1?; a1?; X} main {X}} | " +
                "a2 {def X {if e then b2+win; c2+lose; b2?; c2?; d2!<free>; X else b2+lose; c2+win; b2?; c2?; d2!<free>; X} main {X}} |" +
                "b2 {def X {a2&{win: c2!<lose>; a2!<sig>; X, lose: c2?; a2!<sig>; X}} main {X}} |" +
                "c2 {def X {d2!<busy>; a2&{win: b2!<lose>; a2!<msg>; X, lose: b2?; a2!<msg>; X}} main {X}} |" +
                "d2 {def X {c2?; a2?; X} main {X}} "
        val args = arrayOf("-c", test)

        ChoreographyExtraction.main(args)
    }

    @Test
    fun mult2bit(){
        val test =
                "a { def X {b?;b!<0>;b?;b!<1>;X} main {b!<0>;b!<1>;X}} | " +
                        "b { def Y {a?;a!<ack0>;a?;a!<ack1>;Y} main {Y}}"

        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "def X1 { (a.1->b, b.ack0->a); (a.0->b, b.ack1->a); X1 } main {a.0->b; X1}"

        assertEquals(expected, actual)
    }

    @Test
    fun mult2bitX2(){
        val test =
                "a { def X {b?;b!<0>;b?;b!<1>;X} main {b!<0>;b!<1>;X}} | " +
                "b { def Y {a?;a!<ack0>;a?;a!<ack1>;Y} main {Y}} | " +
                "c { def X {d?;d!<0>;d?;d!<1>;X} main {d!<0>;d!<1>;X}} | " +
                "d { def Y {c?;c!<ack0>;c?;c!<ack1>; Y} main {Y}}"

        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected =
                "def X1 { " +
                        "(a.1->b, b.ack0->a); " +
                        "(a.0->b, b.ack1->a); " +
                        "(c.1->d, d.ack0->c); " +
                        "(a.1->b, b.ack0->a); " +
                        "(a.0->b, b.ack1->a); " +
                        "(c.0->d, d.ack1->c); " +
                        "X1 } " +
                "main {a.0->b; c.0->d; X1}"

        assertEquals(expected, actual)
    }

    @Test
    fun mult2bit3p(){
        val test =
                "a { def X {b!<0>;b?;b!<1>;b?;b!<2>;b?;X} main {X}} | " +
                "b { def Y {a!<ack0>;a?;a!<ack1>;a?;a!<ack2>;a?;Y} main {Y}}"

        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "def X1 { (a.0->b, b.ack0->a); (a.1->b, b.ack1->a); (a.2->b, b.ack2->a); X1 } main {X1}"

        assertEquals(expected, actual)
    }

    @Test
    fun mult2bit3pX2(){
        val test =
                "a { def X {b!<0>;b?;b!<1>;b?;b!<2>;b?;X} main {X}} | " +
                "b { def Y {a!<ack0>;a?;a!<ack1>;a?;a!<ack2>;a?;Y} main {Y}} | " +
                "c { def X {d!<0>;d?;d!<1>;d?;d!<2>;d?;X} main {X}} | " +
                "d { def Y {c!<ack0>;c?;c!<ack1>;c?;c!<ack2>;c?;Y} main {Y}}"

        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "def X1 { (a.1->b, b.ack1->a); (a.2->b, b.ack2->a); (c.0->d, d.ack0->c); (a.0->b, b.ack0->a); (a.1->b, b.ack1->a); (a.2->b, b.ack2->a); (c.1->d, d.ack1->c); (a.0->b, b.ack0->a); (a.1->b, b.ack1->a); (a.2->b, b.ack2->a); (c.2->d, d.ack2->c); (a.0->b, b.ack0->a); X1 } main {(a.0->b, b.ack0->a); X1}"

        assertEquals(expected, actual)
    }

    @Test
    fun bargain(){
        val test =
                "a { def X {b!<hag>; b?; if price then b+deal; c+deal; b!<happy>; c!<info>; X else b+nodeal; c+nodeal; X} main {X}} | " +
                "b { def Y {a?; a!<price>; a&{deal: a?; Y, nodeal: Y}} main {Y}} | " +
                "c { def Z {a&{deal: a?; Z, nodeal: Z}} main {Z}}"


        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "def X1 { a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; a.info->c; X1 else a->b[nodeal]; a->c[nodeal]; X1 } main {X1}"

        assertEquals(expected, actual)
    }

    @Test
    fun bargain2x(){
        val test =
                "a { def X {b!<hag>; b?; if price then b+deal; c+deal; b!<happy>; c!<info>; X else b+nodeal; c+nodeal; X} main {X}} | " +
                "b { def Y {a?; a!<price>; a&{deal: a?; Y, nodeal: Y}} main {Y}} | " +
                "c { def Z {a&{deal: a?; Z, nodeal: Z}} main {Z}} | " +
                "d { def X {e!<hag>; e?; if price then e+deal; f+deal; e!<happy>; f!<info>; X else e+nodeal; f+nodeal; X} main {X}} | " +
                "e { def Y {d?; d!<price>; d&{deal: d?; Y, nodeal: Y}} main {Y}} | " +
                "f { def Z {d&{deal: d?; Z, nodeal: Z}} main {Z}}"


        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected =
                "def X1 { a->c[deal]; a.happy->b; a.info->c; X2 } " +
                "def X2 { a.hag->b; X3 } " +
                "def X3 { b.price->a; if a.price then a->b[deal]; d.hag->e; a->c[deal]; a.happy->b; a.info->c; a.hag->b; b.price->a; if a.price then e.price->d; a->b[deal]; if d.price then d->e[deal]; a->c[deal]; a.happy->b; a.info->c; a.hag->b; b.price->a; if a.price then d->f[deal]; a->b[deal]; a->c[deal]; a.happy->b; a.info->c; a.hag->b; b.price->a; if a.price then a->b[deal]; d.happy->e; a->c[deal]; a.happy->b; a.info->c; a.hag->b; b.price->a; if a.price then d.info->f; a->b[deal]; X1 else a->b[nodeal]; d.info->f; a->c[nodeal]; a.hag->b; X3 else a->b[nodeal]; d.happy->e; a->c[nodeal]; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d.info->f; a.info->c; a.hag->b; X3 else d.info->f; a->b[nodeal]; a->c[nodeal]; X2 else a->b[nodeal]; d->f[deal]; a->c[nodeal]; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; a.info->c; d.happy->e; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d.info->f; a.info->c; a.hag->b; X3 else a->b[nodeal]; d.info->f; a->c[nodeal]; a.hag->b; X3 else a->b[nodeal]; a->c[nodeal]; d.happy->e; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d.info->f; a.info->c; a.hag->b; X3 else a->b[nodeal]; d.info->f; a->c[nodeal]; a.hag->b; X3 else d->e[nodeal]; a->c[deal]; a.happy->b; a.info->c; a.hag->b; b.price->a; if a.price then d->f[nodeal]; a->b[deal]; X1 else a->b[nodeal]; d->f[nodeal]; a->c[nodeal]; a.hag->b; X3 else a->b[nodeal]; e.price->d; if d.price then d->e[deal]; a->c[nodeal]; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d->f[deal]; a.info->c; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; a.info->c; d.happy->e; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d.info->f; a.info->c; a.hag->b; X3 else a->b[nodeal]; d.info->f; a->c[nodeal]; a.hag->b; X3 else a->b[nodeal]; a->c[nodeal]; d.happy->e; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d.info->f; a.info->c; a.hag->b; X3 else a->b[nodeal]; d.info->f; a->c[nodeal]; a.hag->b; X3 else d->f[deal]; a->b[nodeal]; a->c[nodeal]; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d.happy->e; a.info->c; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; d.info->f; a.happy->b; a.info->c; X2 else a->b[nodeal]; d.info->f; a->c[nodeal]; a.hag->b; X3 else a->b[nodeal]; d.happy->e; a->c[nodeal]; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d.info->f; a.info->c; a.hag->b; X3 else d.info->f; a->b[nodeal]; a->c[nodeal]; X2 else d->e[nodeal]; a->c[nodeal]; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d->f[nodeal]; a.info->c; a.hag->b; X3 else d->f[nodeal]; a->b[nodeal]; a->c[nodeal]; X2 else a->b[nodeal]; d.hag->e; a->c[nodeal]; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; e.price->d; if d.price then d->e[deal]; a.info->c; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; d->f[deal]; a.happy->b; a.info->c; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d.happy->e; a.info->c; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; d.info->f; a.happy->b; a.info->c; X2 else a->b[nodeal]; d.info->f; a->c[nodeal]; a.hag->b; X3 else a->b[nodeal]; d.happy->e; a->c[nodeal]; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d.info->f; a.info->c; a.hag->b; X3 else d.info->f; a->b[nodeal]; a->c[nodeal]; X2 else a->b[nodeal]; d->f[deal]; a->c[nodeal]; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; a.info->c; d.happy->e; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d.info->f; a.info->c; a.hag->b; X3 else a->b[nodeal]; d.info->f; a->c[nodeal]; a.hag->b; X3 else a->b[nodeal]; a->c[nodeal]; d.happy->e; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d.info->f; a.info->c; a.hag->b; X3 else a->b[nodeal]; d.info->f; a->c[nodeal]; a.hag->b; X3 else d->e[nodeal]; a.info->c; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; d->f[nodeal]; a.happy->b; a.info->c; X2 else a->b[nodeal]; d->f[nodeal]; a->c[nodeal]; a.hag->b; X3 else e.price->d; a->b[nodeal]; if d.price then d->e[deal]; a->c[nodeal]; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d->f[deal]; a.info->c; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; a.info->c; d.happy->e; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d.info->f; a.info->c; a.hag->b; X3 else a->b[nodeal]; d.info->f; a->c[nodeal]; a.hag->b; X3 else a->b[nodeal]; a->c[nodeal]; d.happy->e; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d.info->f; a.info->c; a.hag->b; X3 else a->b[nodeal]; d.info->f; a->c[nodeal]; a.hag->b; X3 else d->f[deal]; a->b[nodeal]; a->c[nodeal]; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d.happy->e; a.info->c; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; d.info->f; a.happy->b; a.info->c; X2 else a->b[nodeal]; d.info->f; a->c[nodeal]; a.hag->b; X3 else a->b[nodeal]; d.happy->e; a->c[nodeal]; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d.info->f; a.info->c; a.hag->b; X3 else d.info->f; a->b[nodeal]; a->c[nodeal]; X2 else d->e[nodeal]; a->c[nodeal]; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d->f[nodeal]; a.info->c; a.hag->b; X3 else d->f[nodeal]; a->b[nodeal]; a->c[nodeal]; X2 } " +
                "def X4 { a->c[nodeal]; X5 } " +
                "def X5 { a.hag->b; X6 } " +
                "def X6 { b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; X7 else a->b[nodeal]; d.hag->e; a->c[nodeal]; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; e.price->d; if d.price then d->e[deal]; a.info->c; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; d->f[deal]; a.happy->b; a.info->c; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d.happy->e; a.info->c; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; d.info->f; a.happy->b; a.info->c; X5 else a->b[nodeal]; d.info->f; a->c[nodeal]; a.hag->b; X6 else a->b[nodeal]; d.happy->e; a->c[nodeal]; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d.info->f; a.info->c; a.hag->b; X6 else d.info->f; a->b[nodeal]; X4 else a->b[nodeal]; d->f[deal]; a->c[nodeal]; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; a.info->c; d.happy->e; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d.info->f; a.info->c; a.hag->b; X6 else a->b[nodeal]; d.info->f; a->c[nodeal]; a.hag->b; X6 else a->b[nodeal]; a->c[nodeal]; d.happy->e; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d.info->f; a.info->c; a.hag->b; X6 else a->b[nodeal]; d.info->f; a->c[nodeal]; a.hag->b; X6 else d->e[nodeal]; a.info->c; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; d->f[nodeal]; a.happy->b; a.info->c; X5 else a->b[nodeal]; d->f[nodeal]; a->c[nodeal]; a.hag->b; X6 else e.price->d; a->b[nodeal]; if d.price then d->e[deal]; a->c[nodeal]; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d->f[deal]; a.info->c; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; a.info->c; d.happy->e; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d.info->f; a.info->c; a.hag->b; X6 else a->b[nodeal]; d.info->f; a->c[nodeal]; a.hag->b; X6 else a->b[nodeal]; a->c[nodeal]; d.happy->e; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d.info->f; a.info->c; a.hag->b; X6 else a->b[nodeal]; d.info->f; a->c[nodeal]; a.hag->b; X6 else d->f[deal]; a->b[nodeal]; a->c[nodeal]; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d.happy->e; a.info->c; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; d.info->f; a.happy->b; a.info->c; X5 else a->b[nodeal]; d.info->f; a->c[nodeal]; a.hag->b; X6 else a->b[nodeal]; d.happy->e; a->c[nodeal]; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d.info->f; a.info->c; a.hag->b; X6 else d.info->f; a->b[nodeal]; X4 else d->e[nodeal]; a->c[nodeal]; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d->f[nodeal]; a.info->c; a.hag->b; X6 else d->f[nodeal]; a->b[nodeal]; X4 } " +
                "def X7 { d.hag->e; a.info->c; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; e.price->d; if d.price then d->e[deal]; a.happy->b; a.info->c; a.hag->b; b.price->a; if a.price then a->b[deal]; d->f[deal]; a->c[deal]; a.happy->b; a.info->c; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; d.happy->e; a.happy->b; a.info->c; a.hag->b; b.price->a; if a.price then a->b[deal]; d.info->f; a->c[deal]; a.happy->b; X7 else a->b[nodeal]; d.info->f; a->c[nodeal]; a.hag->b; X6 else a->b[nodeal]; d.happy->e; a->c[nodeal]; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d.info->f; a.info->c; a.hag->b; X6 else d.info->f; a->b[nodeal]; X4 else a->b[nodeal]; d->f[deal]; a->c[nodeal]; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; a.info->c; d.happy->e; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d.info->f; a.info->c; a.hag->b; X6 else a->b[nodeal]; d.info->f; a->c[nodeal]; a.hag->b; X6 else a->b[nodeal]; a->c[nodeal]; d.happy->e; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d.info->f; a.info->c; a.hag->b; X6 else a->b[nodeal]; d.info->f; a->c[nodeal]; a.hag->b; X6 else d->e[nodeal]; a.happy->b; a.info->c; a.hag->b; b.price->a; if a.price then a->b[deal]; d->f[nodeal]; a->c[deal]; a.happy->b; X7 else a->b[nodeal]; d->f[nodeal]; a->c[nodeal]; a.hag->b; X6 else a->b[nodeal]; e.price->d; if d.price then d->e[deal]; a->c[nodeal]; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d->f[deal]; a.info->c; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; a.info->c; d.happy->e; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d.info->f; a.info->c; a.hag->b; X6 else a->b[nodeal]; d.info->f; a->c[nodeal]; a.hag->b; X6 else a->b[nodeal]; a->c[nodeal]; d.happy->e; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d.info->f; a.info->c; a.hag->b; X6 else a->b[nodeal]; d.info->f; a->c[nodeal]; a.hag->b; X6 else d->f[deal]; a->b[nodeal]; a->c[nodeal]; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d.happy->e; a.info->c; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; d.info->f; a.happy->b; a.info->c; X5 else a->b[nodeal]; d.info->f; a->c[nodeal]; a.hag->b; X6 else a->b[nodeal]; d.happy->e; a->c[nodeal]; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d.info->f; a.info->c; a.hag->b; X6 else d.info->f; a->b[nodeal]; X4 else d->e[nodeal]; a->c[nodeal]; a.hag->b; b.price->a; if a.price then a->b[deal]; a->c[deal]; a.happy->b; d->f[nodeal]; a.info->c; a.hag->b; X6 else d->f[nodeal]; a->b[nodeal]; X4 } " +
                "main {a.hag->b; b.price->a; if a.price then a->b[deal]; X1 else a->b[nodeal]; X4}"

        assertEquals(expected, actual)
    }

    @Test
    fun filter(){
        val test =
                "filter {" +
                        "def X {data!<newFilterRequest>; Y} " +
                        "def Y {data&{" +
                            "item:  data?; if itemToBeFiltered then data!<ok>; Y else data!<remove>; Y," +
                            "noItem: data?; X}}" +
                        "main {X}} | " +
                "data {" +
                        "def X {filter?; Y} " +
                        "def Y {if itemToBeFiltered " +
                            "then filter+item; filter!<itemToBeFiltered>; filter?; Y " +
                            "else filter+noItem; filter!<noMoreItems>; X} " +
                        "main {X}}"


        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected =
                "def X1 { filter.newFilterRequest->data; X2 } " +
                "def X2 { if data.itemToBeFiltered then data->filter[item]; data.itemToBeFiltered->filter; if filter.itemToBeFiltered then filter.ok->data; X2 else filter.remove->data; X2 else data->filter[noItem]; data.noMoreItems->filter; X1 } " +
                "main {X1}"

        assertEquals(expected, actual)
    }

    @Test
    fun filter2x(){
        val test =
                "filter1 {" +
                        "def X {data1!<newFilterRequest>; Y} " +
                        "def Y {data1&{" +
                        "item: data1?; if itemToBeFiltered then data1!<ok>; Y else data1!<remove>; Y," +
                        "noItem: data1?; X}}" +
                        "main {X}} | " +
                "data1 {" +
                        "def X {filter1?; Y} " +
                        "def Y {if itemToBeFiltered " +
                        "then filter1+item; filter1!<itemToBeFiltered>; filter1?; Y " +
                        "else filter1+noItem; filter1!<noMoreItems>; X} " +
                        "main {X}} | " +
                "filter2 {" +
                        "def X {data2!<newFilterRequest>; Y} " +
                        "def Y {data2&{" +
                        "item:  data2?; if itemToBeFiltered then data2!<ok>; Y else data2!<remove>; Y," +
                        "noItem: data2?; X}}" +
                        "main {X}} | " +
                "data2 {" +
                        "def X {filter2?; Y} " +
                        "def Y {if itemToBeFiltered " +
                        "then filter2+item; filter2!<itemToBeFiltered>; filter2?; Y " +
                        "else filter2+noItem; filter2!<noMoreItems>; X} " +
                        "main {X}}"


        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected = "def X1 { filter1.newFilterRequest->data1; X2 } def X10 { filter1.remove->data1; X11 } def X11 { if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; X12 else data1->filter1[noItem]; data1.noMoreItems->filter1; X14 } def X12 { data2->filter2[item]; if filter1.itemToBeFiltered then filter1.ok->data1; X13 else filter1.remove->data1; data2.itemToBeFiltered->filter2; if filter2.itemToBeFiltered then if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; filter2.ok->data2; if data2.itemToBeFiltered then X12 else if filter1.itemToBeFiltered then filter1.ok->data1; if data1.itemToBeFiltered then data1->filter1[item]; data2->filter2[noItem]; data1.itemToBeFiltered->filter1; data2.noMoreItems->filter2; X3 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 else filter1.remove->data1; if data1.itemToBeFiltered then data1->filter1[item]; data2->filter2[noItem]; data1.itemToBeFiltered->filter1; data2.noMoreItems->filter2; X3 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 else data1->filter1[noItem]; data1.noMoreItems->filter1; filter2.ok->data2; if data2.itemToBeFiltered then filter1.newFilterRequest->data1; X11 else filter1.newFilterRequest->data1; if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; if filter1.itemToBeFiltered then data2->filter2[noItem]; filter1.ok->data1; data2.noMoreItems->filter2; X2 else data2->filter2[noItem]; filter1.remove->data1; data2.noMoreItems->filter2; X2 else data1->filter1[noItem]; data2->filter2[noItem]; data1.noMoreItems->filter1; data2.noMoreItems->filter2; X1 else if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; filter2.remove->data2; if data2.itemToBeFiltered then X12 else if filter1.itemToBeFiltered then filter1.ok->data1; if data1.itemToBeFiltered then data1->filter1[item]; data2->filter2[noItem]; data1.itemToBeFiltered->filter1; data2.noMoreItems->filter2; X3 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 else filter1.remove->data1; if data1.itemToBeFiltered then data1->filter1[item]; data2->filter2[noItem]; data1.itemToBeFiltered->filter1; data2.noMoreItems->filter2; X3 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 else data1->filter1[noItem]; data1.noMoreItems->filter1; filter2.remove->data2; if data2.itemToBeFiltered then filter1.newFilterRequest->data1; X11 else filter1.newFilterRequest->data1; if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; if filter1.itemToBeFiltered then data2->filter2[noItem]; filter1.ok->data1; data2.noMoreItems->filter2; X2 else data2->filter2[noItem]; filter1.remove->data1; data2.noMoreItems->filter2; X2 else data1->filter1[noItem]; data2->filter2[noItem]; data1.noMoreItems->filter1; data2.noMoreItems->filter2; X1 } def X13 { data2.itemToBeFiltered->filter2; if filter2.itemToBeFiltered then if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; filter2.ok->data2; if data2.itemToBeFiltered then X12 else if filter1.itemToBeFiltered then filter1.ok->data1; if data1.itemToBeFiltered then data1->filter1[item]; data2->filter2[noItem]; data1.itemToBeFiltered->filter1; data2.noMoreItems->filter2; X3 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 else filter1.remove->data1; if data1.itemToBeFiltered then data1->filter1[item]; data2->filter2[noItem]; data1.itemToBeFiltered->filter1; data2.noMoreItems->filter2; X3 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 else data1->filter1[noItem]; data1.noMoreItems->filter1; filter2.ok->data2; if data2.itemToBeFiltered then filter1.newFilterRequest->data1; X11 else filter1.newFilterRequest->data1; if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; if filter1.itemToBeFiltered then data2->filter2[noItem]; filter1.ok->data1; data2.noMoreItems->filter2; X2 else data2->filter2[noItem]; filter1.remove->data1; data2.noMoreItems->filter2; X2 else data1->filter1[noItem]; data2->filter2[noItem]; data1.noMoreItems->filter1; data2.noMoreItems->filter2; X1 else if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; filter2.remove->data2; if data2.itemToBeFiltered then X12 else if filter1.itemToBeFiltered then filter1.ok->data1; if data1.itemToBeFiltered then data1->filter1[item]; data2->filter2[noItem]; data1.itemToBeFiltered->filter1; data2.noMoreItems->filter2; X3 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 else filter1.remove->data1; if data1.itemToBeFiltered then data1->filter1[item]; data2->filter2[noItem]; data1.itemToBeFiltered->filter1; data2.noMoreItems->filter2; X3 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 else data1->filter1[noItem]; data1.noMoreItems->filter1; filter2.remove->data2; if data2.itemToBeFiltered then filter1.newFilterRequest->data1; X11 else filter1.newFilterRequest->data1; if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; if filter1.itemToBeFiltered then data2->filter2[noItem]; filter1.ok->data1; data2.noMoreItems->filter2; X2 else data2->filter2[noItem]; filter1.remove->data1; data2.noMoreItems->filter2; X2 else data1->filter1[noItem]; data2->filter2[noItem]; data1.noMoreItems->filter1; data2.noMoreItems->filter2; X1 } def X14 { data2->filter2[item]; filter1.newFilterRequest->data1; data2.itemToBeFiltered->filter2; if filter2.itemToBeFiltered then if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; filter2.ok->data2; if data2.itemToBeFiltered then if filter1.itemToBeFiltered then filter1.ok->data1; X11 else X10 else if filter1.itemToBeFiltered then filter1.ok->data1; if data1.itemToBeFiltered then data1->filter1[item]; data2->filter2[noItem]; data1.itemToBeFiltered->filter1; data2.noMoreItems->filter2; X3 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 else filter1.remove->data1; if data1.itemToBeFiltered then data1->filter1[item]; data2->filter2[noItem]; data1.itemToBeFiltered->filter1; data2.noMoreItems->filter2; X3 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 else data1->filter1[noItem]; data1.noMoreItems->filter1; filter2.ok->data2; if data2.itemToBeFiltered then X14 else filter1.newFilterRequest->data1; if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; if filter1.itemToBeFiltered then data2->filter2[noItem]; filter1.ok->data1; data2.noMoreItems->filter2; X2 else data2->filter2[noItem]; filter1.remove->data1; data2.noMoreItems->filter2; X2 else data1->filter1[noItem]; data2->filter2[noItem]; data1.noMoreItems->filter1; data2.noMoreItems->filter2; X1 else if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; filter2.remove->data2; if data2.itemToBeFiltered then if filter1.itemToBeFiltered then filter1.ok->data1; X11 else X10 else if filter1.itemToBeFiltered then filter1.ok->data1; if data1.itemToBeFiltered then data1->filter1[item]; data2->filter2[noItem]; data1.itemToBeFiltered->filter1; data2.noMoreItems->filter2; X3 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 else filter1.remove->data1; if data1.itemToBeFiltered then data1->filter1[item]; data2->filter2[noItem]; data1.itemToBeFiltered->filter1; data2.noMoreItems->filter2; X3 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 else data1->filter1[noItem]; data1.noMoreItems->filter1; filter2.remove->data2; if data2.itemToBeFiltered then X14 else filter1.newFilterRequest->data1; if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; if filter1.itemToBeFiltered then data2->filter2[noItem]; filter1.ok->data1; data2.noMoreItems->filter2; X2 else data2->filter2[noItem]; filter1.remove->data1; data2.noMoreItems->filter2; X2 else data1->filter1[noItem]; data2->filter2[noItem]; data1.noMoreItems->filter1; data2.noMoreItems->filter2; X1 } def X15 { data2.noMoreItems->filter2; X2 } def X16 { filter1.newFilterRequest->data1; if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; X17 else data2->filter2[item]; data1->filter1[noItem]; data1.noMoreItems->filter1; data2.itemToBeFiltered->filter2; if filter2.itemToBeFiltered then filter1.newFilterRequest->data1; filter2.ok->data2; X1 else filter1.newFilterRequest->data1; filter2.remove->data2; X1 } def X17 { if filter1.itemToBeFiltered then data2->filter2[item]; filter1.ok->data1; data2.itemToBeFiltered->filter2; if filter2.itemToBeFiltered then if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; filter2.ok->data2; if data2.itemToBeFiltered then X17 else if filter1.itemToBeFiltered then filter1.ok->data1; if data1.itemToBeFiltered then data1->filter1[item]; data2->filter2[noItem]; data1.itemToBeFiltered->filter1; data2.noMoreItems->filter2; if filter1.itemToBeFiltered then filter1.ok->data1; X2 else filter1.remove->data1; X2 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 else filter1.remove->data1; if data1.itemToBeFiltered then data1->filter1[item]; data2->filter2[noItem]; data1.itemToBeFiltered->filter1; data2.noMoreItems->filter2; if filter1.itemToBeFiltered then filter1.ok->data1; X2 else filter1.remove->data1; X2 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 else data1->filter1[noItem]; data1.noMoreItems->filter1; filter2.ok->data2; if data2.itemToBeFiltered then X16 else filter1.newFilterRequest->data1; if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; if filter1.itemToBeFiltered then data2->filter2[noItem]; filter1.ok->data1; data2.noMoreItems->filter2; X2 else data2->filter2[noItem]; filter1.remove->data1; data2.noMoreItems->filter2; X2 else data1->filter1[noItem]; data2->filter2[noItem]; data1.noMoreItems->filter1; data2.noMoreItems->filter2; X1 else if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; filter2.remove->data2; if data2.itemToBeFiltered then X17 else if filter1.itemToBeFiltered then filter1.ok->data1; if data1.itemToBeFiltered then data1->filter1[item]; data2->filter2[noItem]; data1.itemToBeFiltered->filter1; data2.noMoreItems->filter2; if filter1.itemToBeFiltered then filter1.ok->data1; X2 else filter1.remove->data1; X2 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 else filter1.remove->data1; if data1.itemToBeFiltered then data1->filter1[item]; data2->filter2[noItem]; data1.itemToBeFiltered->filter1; data2.noMoreItems->filter2; if filter1.itemToBeFiltered then filter1.ok->data1; X2 else filter1.remove->data1; X2 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 else data1->filter1[noItem]; data1.noMoreItems->filter1; filter2.remove->data2; if data2.itemToBeFiltered then X16 else filter1.newFilterRequest->data1; if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; if filter1.itemToBeFiltered then data2->filter2[noItem]; filter1.ok->data1; data2.noMoreItems->filter2; X2 else data2->filter2[noItem]; filter1.remove->data1; data2.noMoreItems->filter2; X2 else data1->filter1[noItem]; data2->filter2[noItem]; data1.noMoreItems->filter1; data2.noMoreItems->filter2; X1 else data2->filter2[item]; filter1.remove->data1; data2.itemToBeFiltered->filter2; if filter2.itemToBeFiltered then if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; filter2.ok->data2; if data2.itemToBeFiltered then X17 else if filter1.itemToBeFiltered then filter1.ok->data1; if data1.itemToBeFiltered then data1->filter1[item]; data2->filter2[noItem]; data1.itemToBeFiltered->filter1; data2.noMoreItems->filter2; if filter1.itemToBeFiltered then filter1.ok->data1; X2 else filter1.remove->data1; X2 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 else filter1.remove->data1; if data1.itemToBeFiltered then data1->filter1[item]; data2->filter2[noItem]; data1.itemToBeFiltered->filter1; data2.noMoreItems->filter2; if filter1.itemToBeFiltered then filter1.ok->data1; X2 else filter1.remove->data1; X2 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 else data1->filter1[noItem]; data1.noMoreItems->filter1; filter2.ok->data2; if data2.itemToBeFiltered then X16 else filter1.newFilterRequest->data1; if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; if filter1.itemToBeFiltered then data2->filter2[noItem]; filter1.ok->data1; data2.noMoreItems->filter2; X2 else data2->filter2[noItem]; filter1.remove->data1; data2.noMoreItems->filter2; X2 else data1->filter1[noItem]; data2->filter2[noItem]; data1.noMoreItems->filter1; data2.noMoreItems->filter2; X1 else if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; filter2.remove->data2; if data2.itemToBeFiltered then X17 else if filter1.itemToBeFiltered then filter1.ok->data1; if data1.itemToBeFiltered then data1->filter1[item]; data2->filter2[noItem]; data1.itemToBeFiltered->filter1; data2.noMoreItems->filter2; if filter1.itemToBeFiltered then filter1.ok->data1; X2 else filter1.remove->data1; X2 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 else filter1.remove->data1; if data1.itemToBeFiltered then data1->filter1[item]; data2->filter2[noItem]; data1.itemToBeFiltered->filter1; data2.noMoreItems->filter2; if filter1.itemToBeFiltered then filter1.ok->data1; X2 else filter1.remove->data1; X2 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 else data1->filter1[noItem]; data1.noMoreItems->filter1; filter2.remove->data2; if data2.itemToBeFiltered then X16 else filter1.newFilterRequest->data1; if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; if filter1.itemToBeFiltered then data2->filter2[noItem]; filter1.ok->data1; data2.noMoreItems->filter2; X2 else data2->filter2[noItem]; filter1.remove->data1; data2.noMoreItems->filter2; X2 else data1->filter1[noItem]; data2->filter2[noItem]; data1.noMoreItems->filter1; data2.noMoreItems->filter2; X1 } def X2 { if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; X3 else data1->filter1[noItem]; filter2.newFilterRequest->data2; if data2.itemToBeFiltered then data1.noMoreItems->filter1; X16 else data1.noMoreItems->filter1; filter1.newFilterRequest->data1; if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; if filter1.itemToBeFiltered then data2->filter2[noItem]; filter1.ok->data1; data2.noMoreItems->filter2; X2 else data2->filter2[noItem]; filter1.remove->data1; data2.noMoreItems->filter2; X2 else data2->filter2[noItem]; data1->filter1[noItem]; data1.noMoreItems->filter1; data2.noMoreItems->filter2; X1 } def X3 { if filter1.itemToBeFiltered then filter2.newFilterRequest->data2; if data2.itemToBeFiltered then X4 else filter1.ok->data1; if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; data2->filter2[noItem]; if filter1.itemToBeFiltered then filter1.ok->data1; data2.noMoreItems->filter2; X2 else filter1.remove->data1; X9 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 else filter2.newFilterRequest->data2; if data2.itemToBeFiltered then X10 else filter1.remove->data1; if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; data2->filter2[noItem]; if filter1.itemToBeFiltered then filter1.ok->data1; X15 else filter1.remove->data1; data2.noMoreItems->filter2; X2 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 } def X4 { filter1.ok->data1; X5 } def X5 { if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; X6 else data1->filter1[noItem]; data1.noMoreItems->filter1; X8 } def X6 { data2->filter2[item]; if filter1.itemToBeFiltered then filter1.ok->data1; data2.itemToBeFiltered->filter2; if filter2.itemToBeFiltered then if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; filter2.ok->data2; if data2.itemToBeFiltered then X6 else if filter1.itemToBeFiltered then filter1.ok->data1; if data1.itemToBeFiltered then data1->filter1[item]; data2->filter2[noItem]; data1.itemToBeFiltered->filter1; data2.noMoreItems->filter2; X3 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 else filter1.remove->data1; if data1.itemToBeFiltered then data1->filter1[item]; data2->filter2[noItem]; data1.itemToBeFiltered->filter1; data2.noMoreItems->filter2; X3 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 else data1->filter1[noItem]; data1.noMoreItems->filter1; filter2.ok->data2; if data2.itemToBeFiltered then filter1.newFilterRequest->data1; X5 else filter1.newFilterRequest->data1; if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; if filter1.itemToBeFiltered then data2->filter2[noItem]; filter1.ok->data1; data2.noMoreItems->filter2; X2 else data2->filter2[noItem]; filter1.remove->data1; data2.noMoreItems->filter2; X2 else data1->filter1[noItem]; data2->filter2[noItem]; data1.noMoreItems->filter1; data2.noMoreItems->filter2; X1 else if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; filter2.remove->data2; if data2.itemToBeFiltered then X6 else if filter1.itemToBeFiltered then filter1.ok->data1; if data1.itemToBeFiltered then data1->filter1[item]; data2->filter2[noItem]; data1.itemToBeFiltered->filter1; data2.noMoreItems->filter2; X3 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 else filter1.remove->data1; if data1.itemToBeFiltered then data1->filter1[item]; data2->filter2[noItem]; data1.itemToBeFiltered->filter1; data2.noMoreItems->filter2; X3 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 else data1->filter1[noItem]; data1.noMoreItems->filter1; filter2.remove->data2; if data2.itemToBeFiltered then filter1.newFilterRequest->data1; X5 else filter1.newFilterRequest->data1; if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; if filter1.itemToBeFiltered then data2->filter2[noItem]; filter1.ok->data1; data2.noMoreItems->filter2; X2 else data2->filter2[noItem]; filter1.remove->data1; data2.noMoreItems->filter2; X2 else data1->filter1[noItem]; data2->filter2[noItem]; data1.noMoreItems->filter1; data2.noMoreItems->filter2; X1 else filter1.remove->data1; X7 } def X7 { data2.itemToBeFiltered->filter2; if filter2.itemToBeFiltered then if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; filter2.ok->data2; if data2.itemToBeFiltered then X6 else if filter1.itemToBeFiltered then filter1.ok->data1; if data1.itemToBeFiltered then data1->filter1[item]; data2->filter2[noItem]; data1.itemToBeFiltered->filter1; data2.noMoreItems->filter2; X3 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 else filter1.remove->data1; if data1.itemToBeFiltered then data1->filter1[item]; data2->filter2[noItem]; data1.itemToBeFiltered->filter1; data2.noMoreItems->filter2; X3 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 else data1->filter1[noItem]; data1.noMoreItems->filter1; filter2.ok->data2; if data2.itemToBeFiltered then filter1.newFilterRequest->data1; X5 else filter1.newFilterRequest->data1; if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; if filter1.itemToBeFiltered then data2->filter2[noItem]; filter1.ok->data1; data2.noMoreItems->filter2; X2 else data2->filter2[noItem]; filter1.remove->data1; data2.noMoreItems->filter2; X2 else data1->filter1[noItem]; data2->filter2[noItem]; data1.noMoreItems->filter1; data2.noMoreItems->filter2; X1 else if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; filter2.remove->data2; if data2.itemToBeFiltered then X6 else if filter1.itemToBeFiltered then filter1.ok->data1; if data1.itemToBeFiltered then data1->filter1[item]; data2->filter2[noItem]; data1.itemToBeFiltered->filter1; data2.noMoreItems->filter2; X3 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 else filter1.remove->data1; if data1.itemToBeFiltered then data1->filter1[item]; data2->filter2[noItem]; data1.itemToBeFiltered->filter1; data2.noMoreItems->filter2; X3 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 else data1->filter1[noItem]; data1.noMoreItems->filter1; filter2.remove->data2; if data2.itemToBeFiltered then filter1.newFilterRequest->data1; X5 else filter1.newFilterRequest->data1; if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; if filter1.itemToBeFiltered then data2->filter2[noItem]; filter1.ok->data1; data2.noMoreItems->filter2; X2 else data2->filter2[noItem]; filter1.remove->data1; data2.noMoreItems->filter2; X2 else data1->filter1[noItem]; data2->filter2[noItem]; data1.noMoreItems->filter1; data2.noMoreItems->filter2; X1 } def X8 { data2->filter2[item]; filter1.newFilterRequest->data1; data2.itemToBeFiltered->filter2; if filter2.itemToBeFiltered then if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; filter2.ok->data2; if data2.itemToBeFiltered then if filter1.itemToBeFiltered then X4 else filter1.remove->data1; X5 else if filter1.itemToBeFiltered then filter1.ok->data1; if data1.itemToBeFiltered then data1->filter1[item]; data2->filter2[noItem]; data1.itemToBeFiltered->filter1; data2.noMoreItems->filter2; X3 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 else filter1.remove->data1; if data1.itemToBeFiltered then data1->filter1[item]; data2->filter2[noItem]; data1.itemToBeFiltered->filter1; data2.noMoreItems->filter2; X3 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 else data1->filter1[noItem]; data1.noMoreItems->filter1; filter2.ok->data2; if data2.itemToBeFiltered then X8 else filter1.newFilterRequest->data1; if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; if filter1.itemToBeFiltered then data2->filter2[noItem]; filter1.ok->data1; data2.noMoreItems->filter2; X2 else data2->filter2[noItem]; filter1.remove->data1; data2.noMoreItems->filter2; X2 else data1->filter1[noItem]; data2->filter2[noItem]; data1.noMoreItems->filter1; data2.noMoreItems->filter2; X1 else if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; filter2.remove->data2; if data2.itemToBeFiltered then if filter1.itemToBeFiltered then X4 else filter1.remove->data1; X5 else if filter1.itemToBeFiltered then filter1.ok->data1; if data1.itemToBeFiltered then data1->filter1[item]; data2->filter2[noItem]; data1.itemToBeFiltered->filter1; data2.noMoreItems->filter2; X3 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 else filter1.remove->data1; if data1.itemToBeFiltered then data1->filter1[item]; data2->filter2[noItem]; data1.itemToBeFiltered->filter1; data2.noMoreItems->filter2; X3 else data1->filter1[noItem]; data1.noMoreItems->filter1; data2->filter2[noItem]; filter1.newFilterRequest->data1; data2.noMoreItems->filter2; X2 else data1->filter1[noItem]; data1.noMoreItems->filter1; filter2.remove->data2; if data2.itemToBeFiltered then X8 else filter1.newFilterRequest->data1; if data1.itemToBeFiltered then data1->filter1[item]; data1.itemToBeFiltered->filter1; if filter1.itemToBeFiltered then data2->filter2[noItem]; filter1.ok->data1; data2.noMoreItems->filter2; X2 else data2->filter2[noItem]; filter1.remove->data1; data2.noMoreItems->filter2; X2 else data1->filter1[noItem]; data2->filter2[noItem]; data1.noMoreItems->filter1; data2.noMoreItems->filter2; X1 } def X9 { data2.noMoreItems->filter2; X2 } main {X1}"

        assertEquals(expected, actual)
    }
}



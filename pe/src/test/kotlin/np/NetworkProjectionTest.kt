package np

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest

class NetworkProjectionTest{

    @Test
    fun tst1(){
        val test = "def S { if (s.c1) then (h -> c[l12]; s.m10 -> h; S) else (x.m11 -> h; h.m12 -> a; h -> f[l13]; s.m13 -> x; s.m14 -> c; c.m15 -> h; h.m16 -> a; h -> c[l14]; h.m17 -> c; c -> f[l15]; c -> h[l16]; x -> f[l17]; if (c.c2) then (if (f.c3) then (if (s.c4) then (D) else (f.m18 -> c; S)) else (D)) else (S)) }" +
                "def D { s.m19 -> h; h.m20 -> a; f -> s[l18]; f -> s[l19]; c.m21 -> x; if (h.c5) then (h.m22 -> s; if (x.c6) then (a.m23 -> c; h.m24 -> x; s.m25 -> f; if (a.c7) then (if (h.c8) then (if (a.c9) then (if (c.c10) then (E) else (s -> a[l20]; J)) else (D)) else (S)) else (0)) else (J)) else (c -> f[l21]; s.m26 -> h; s -> f[l22]; s.m27 -> c; f -> x[l23]; h -> c[l24]; s -> h[l25]; c.m28 -> a; c.m29 -> a; 0) }" +
                "def T { h -> f[l26]; a.m30 -> s; f.m31 -> h; c -> h[l27]; if (s.c11) then (x.m32 -> h; x -> f[l28]; x -> s[l29]; c.m33 -> x; f.m34 -> a; if (s.c12) then (if (s.c13) then (S) else (f.m35 -> h; h.m36 -> s; 0)) else (c -> s[l30]; f -> x[l31]; E)) else (x -> a[l32]; x.m37 -> h; s -> c[l33]; 0) }" +
                "def E { a -> c[l34]; h.m38 -> a; c.m39 -> x; x -> a[l35]; c -> h[l36]; f.m40 -> a; a.m41 -> f; s.m42 -> f; x -> a[l37]; s.m43 -> c; s.m44 -> h; if (c.c14) then (c -> f[l38]; f -> h[l39]; x.m45 -> s; x -> a[l40]; if (h.c15) then (c -> x[l41]; if (a.c16) then (if (f.c17) then (E) else (h.m46 -> s; T)) else (S)) else (h.m47 -> c; 0)) else (S) }" +
                "def J { a -> f[l42]; f.m48 -> s; s.m49 -> a; s -> f[l43]; a -> s[l44]; h.m50 -> f; x -> a[l45]; f.m51 -> s; c -> h[l46]; if (a.c18) then (c -> s[l47]; D) else (h -> a[l48]; x.m52 -> a; D) } " +
                "main { x -> a[l1]; a -> f[l2]; s -> x[l3]; x.m1 -> a; f.m2 -> s; c -> a[l4]; c.m3 -> x; f.m4 -> c; s.m5 -> c; s -> h[l5]; a -> c[l6]; a.m6 -> s; c -> h[l7]; c -> h[l8]; a.m7 -> c; a.m8 -> c; s.m9 -> h; f -> h[l9]; h -> s[l10]; f -> c[l11]; T }"

        val actual = NetworkProjection.project(test).toString()
        val expected = "p{def X{Y} def Y{q!<e>; stop} main {q?; X}} | q{def X{Y} def Y{p?; stop} main {p!<e>; X}}"

        assertEquals(expected, actual)
    }

    @Test
    fun tst2(){ //termination
        val test = "main { stop }"

        val actual = NetworkProjection.project(test).toString()
        val expected = ""

        assertEquals(expected, actual)
    }

    @Test
    fun tst3(){ //finite interaction with procedure invocation
        val test = "def X { p.e->q;stop } main { X }"

        val actual = NetworkProjection.project(test).toString()
        val expected = "p{def X{q!<e>; stop} main {X}} | q{def X{p?; stop} main {X}}"

        assertEquals(expected, actual)
    }

    @Test
    fun tst4(){ //finite interaction
        val test = "main {p.e->q;stop}"

        val actual = NetworkProjection.project(test).toString()
        val expected = "p{main {q!<e>; stop}} | q{main {p?; stop}}"

        assertEquals(expected, actual)
    }

    @Test
    fun tst5(){ //finite interaction
        val test = "main {p->q[l];stop}"

        val actual = NetworkProjection.project(test).toString()
        val expected = "p{main {q + l; stop}} | q{main {p&{l: stop}}}"

        assertEquals(expected, actual)
    }

    @Test
    fun tst6(){ //procedure definition/invocation
        val test = "main {if p.e then p.e->q;stop else p.e->q; stop}"

        val actual = NetworkProjection.project(test).toString()
        val expected = "p{main {if e then q!<e>; stop else q!<e>; stop}} | q{main {p?; stop}}"

        assertEquals(expected, actual)
    }

    @Test
    fun tst7(){ //condition
        val test = "main {if p.e then if q.e2 then p.e1 -> q; stop else p.e1 -> q; stop else if q.e2 then p.e3 -> q; stop else p.e3 -> q; stop}"

        val actual = NetworkProjection.project(test).toString()
        val expected = "p{main {if e then q!<e1>; stop else q!<e3>; stop}} | q{main {if e2 then p?; stop else p?; stop}}"

        assertEquals(expected, actual)
    }

    @Test
    fun tst8(){
        val test = "main {if p.e then p -> q[L]; p.e -> q; q.x -> r; r.z -> q; stop " +
                "else p -> q[R]; q.y -> r; r.z -> q; q.u -> p; stop}"

        val actual = NetworkProjection.project(test).toString()
        val expected = "p{main {if e then q + L; q!<e>; stop else q + R; q?; stop}} | " +
                "q{main {p&{R: r!<y>; r?; p!<u>; stop, L: p?; r!<x>; r?; stop}}} | " +
                "r{main {q?; q!<z>; stop}}"

        assertEquals(expected, actual)
    }

    @Test
    fun tst9(){
        val test = "main {if p.e then p -> q[L]; p.e -> q; q -> r[L1]; r.z1 -> q; stop " +
                "else p -> q[R]; q -> r[R1]; r.z2 -> q; q.u -> p; stop}"

        val actual = NetworkProjection.project(test).toString()
        val expected = "p{main {if e then q + L; q!<e>; stop else q + R; q?; stop}} | " +
                "q{main {p&{R: r + R1; r?; p!<u>; stop, L: p?; r + L1; r?; stop}}} | " +
                "r{main {q&{L1: q!<z1>; stop, R1: q!<z2>; stop}}}"

        assertEquals(expected, actual)
    }

    @Test
    fun tst10(){
        val test = "def X {q.e->p; if p.e then p->q[ok]; q->r[ok]; X else p->q[ko]; q->r[ko]; Y } " +
                "def Y {q.e->r; if r.e then r->q[ok]; r->p[ok]; q.e->r; stop else r->q[ko]; r->p[ko]; Y}" +
                "main {p.e->q;X}"

        val actual = NetworkProjection.project(test).toString()
        val expected = "p{" +
                "def X{q?; if e then q + ok; X else q + ko; Y} " +
                "def Y{r&{ko: Y, ok: stop}} " +
                "main {q!<e>; X}} | " +
                "q{" +
                "def X{p!<e>; p&{ko: r + ko; Y, ok: r + ok; X}} " +
                "def Y{r!<e>; r&{ko: Y, ok: r!<e>; stop}} " +
                "main {p?; X}} | " +
                "r{" +
                "def X{q&{ko: Y, ok: X}} " +
                "def Y{q?; if e then q + ok; p + ok; q?; stop else q + ko; p + ko; Y} " +
                "main {X}}"

        assertEquals(expected, actual)
    }

    @Test
    fun tst11(){
        val test = "def X {if p.e " +
                "then p->q[ok]; p->r[ok]; if r.e " +
                "then q.e->p; r->p[ok];r->q[ok];p.e->q;X " +
                "else q.e->p; r->p[ko];r->q[ko];r.u->q;Y " +
                "else p->q[ko]; p->r[ko]; if q.e " +
                "then q->p[ok];q->r[ok];p.e->q;X " +
                "else q->p[ko];q->r[ko];Z } " +
                "def Y {p.e->q; X}" +
                "def Z {p.e->q; Y}" +
                "main {q.i->r; p.e->q; X}"

        val actual = NetworkProjection.project(test).toString()
        val expected = "p{" +
                "def X{if e then q + ok; r + ok; q?; r&{ko: Y, ok: q!<e>; X} else q + ko; r + ko; q&{ko: Z, ok: q!<e>; X}} " +
                "def Y{q!<e>; X} " +
                "def Z{q!<e>; Y} " +
                "main {q!<e>; X}} | " +
                "q{" +
                "def X{p&{ko: if e then p + ok; r + ok; p?; X else p + ko; r + ko; Z, ok: p!<e>; r&{ko: r?; Y, ok: p?; X}}} " +
                "def Y{p?; X} " +
                "def Z{p?; Y} " +
                "main {r!<i>; p?; X}} | " +
                "r{" +
                "def X{p&{ko: q&{ko: Z, ok: X}, ok: if e then p + ok; q + ok; X else p + ko; q + ko; q!<u>; Y}} " +
                "def Y{X} " +
                "def Z{Y} main {q?; X}}"

        assertEquals(expected, actual)
    }

    @Test //(expected = MergingProjection.MergingException::class)
    fun tst12() {
        val test =
                "def X1 { " +
                        "p.e->q; if p.e " +
                        "then X2 " +
                        "else X4 } " +
                        "def X2 { " +
                        "p->q[ok]; p->r[ok]; q.e->p; if r.e " +
                        "then r->p[ok]; r->q[ok]; X1 " +
                        "else r->p[ko]; r->q[ko]; r.u->q; p.e->q; if p.e " +
                        "then X2 " +
                        "else X3 } " +
                        "def X3 { " +
                        "p->q[ko]; p->r[ko]; if q.e " +
                        "then q->p[ok]; q->r[ok]; X1 " +
                        "else q->p[ko]; q->r[ko]; p.e->q; p.e->q; if p.e " +
                        "then X2 " +
                        "else X3 } " +
                        "def X4 { " +
                        "p->q[ko]; p->r[ko]; if q.e " +
                        "then q->p[ok]; q->r[ok]; X1 " +
                        "else q->p[ko]; q->r[ko]; p.e->q; p.e->q; if p.e " +
                        "then X5 " +
                        "else X4 } " +
                        "def X5 { " +
                        "p->q[ok]; p->r[ok]; q.e->p; if r.e " +
                        "then r->p[ok]; r->q[ok]; X1 " +
                        "else r->p[ko]; r->q[ko]; r.u->q; p.e->q; if p.e " +
                        "then X5 " +
                        "else X4 } " +
                        "main {q.i->r; X1}"



        Assertions.assertThrows(MergingProjection.MergingException::class.java
        ) { NetworkProjection.project(test) }

    }
}

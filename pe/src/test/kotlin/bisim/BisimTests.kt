package bisim

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest

class BisimTests{

    @Test
    fun same1(){
        val test = "def X {Y} def Y { p.e->q; stop } main {q.e->p;X}"
        assert( bisimilar(test,test) )
    }

    @Test
    fun same2(){
        val test =
                "def T { p -> u[l3]; d -> o[l4]; K } " +
                "def K { o.m3 -> u; o.m4 -> v; o -> p[l5]; p.m5 -> u; K } " +
                "main { u.m1 -> v; o -> v[l1]; v -> u[l2]; u.m2 -> d; T }"
        assert( bisimilar(test,test) )
    }

    @Test
    fun same3(){ //termination
        val test = "main { stop }"

        assert( bisimilar(test,test) )
    }

    @Test
    fun same4()
    {
        val test = "def X { p.e->q;stop } main { X }"
        assert( bisimilar(test,test) )
    }

    @Test
    fun same5(){ //finite interaction
        val test = "main {p.e->q;stop}"
        assert( bisimilar(test,test) )
    }

    @Test
    fun same6(){ //finite interaction
        val test = "main {p->q[l];stop}"

        assert( bisimilar(test,test) )
    }

    @Test
    fun same7()
    {
        val test = "main {if p.e then p.e->q;stop else p.e->q; stop}"

        assert( bisimilar(test,test) )
    }

    @Test
    fun same8()
    {
        val test = "main {if p.e then if q.e2 then p.e1 -> q; stop else p.e1 -> q; stop else if q.e2 then p.e3 -> q; stop else p.e3 -> q; stop}"

        assert( bisimilar(test,test) )
    }

    @Test
    fun same9()
    {
        val test = "main {if p.e then p -> q[L]; p.e -> q; q.x -> r; r.z -> q; stop " +
                "else p -> q[R]; q.y -> r; r.z -> q; q.u -> p; stop}"
        assert( bisimilar(test,test) )
    }

    @Test
    fun same10(){
        val test = "main {if p.e then p -> q[L]; p.e -> q; q -> r[L1]; r.z1 -> q; stop " +
                "else p -> q[R]; q -> r[R1]; r.z2 -> q; q.u -> p; stop}"

        assert( bisimilar(test,test) )
    }

    @Test
    fun same11(){
        val test = "def X {q.e->p; if p.e then p->q[ok]; q->r[ok]; X else p->q[ko]; q->r[ko]; Y } " +
                "def Y {q.e->r; if r.e then r->q[ok]; r->p[ok]; q.e->r; stop else r->q[ko]; r->p[ko]; Y}" +
                "main {p.e->q;X}"

        assert( bisimilar(test,test) )
    }

    @Test
    fun same12(){
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

        assert( bisimilar(test,test) )
    }

    @Test // This is unmergeable
    fun same13() {
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



        assert( bisimilar(test,test) )
    }

    @Test
    fun same14(){
        val test = "def X {Y} def Y { p.e->q; stop } main {q.e->p;X} || def X {Y} def Y { r.e->s; stop } main {s.e->r;X}"

        assert( bisimilar(test,test) )
    }

    @Test
    fun diffExpr()
    {
        val c1 = "main { p.e1 -> q; stop }"
        val c2 = "main { p.e2 -> q; stop }"

        assertFalse( bisimilar(c1,c2) )
    }

    @Test
    fun diffProcs()
    {
        val c1 = "main { p.e -> q; stop }"
        val c2 = "main { p.e -> q2; stop }"

        assertFalse( bisimilar(c1,c2) )
    }

    @Test
    fun diffLabel()
    {
        val c1 = "main { p -> q[l1]; stop }"
        val c2 = "main { p -> q[l2]; stop }"

        assertFalse( bisimilar(c1,c2) )
    }

    @Test
    fun swap1()
    {
        val c1 = "main { p1.e1 -> q1; p2.e2 -> q2; stop }"
        val c2 = "main { p2.e1 -> q2; p1.e2 -> q1; stop }"

        // e should be e2 in c2 and vice versa

        assertFalse( bisimilar(c1,c2) )
    }

    @Test
    fun swap2()
    {
        val c1 = "main { p1.e1 -> q1; p2.e2 -> q2; stop }"
        val c2 = "main { p2.e2 -> q2; p1.e1 -> q1; stop }"

        assert( bisimilar(c1,c2) )
    }

    @Test
    fun swap3()
    {
        val c1 = "main { p1.e1 -> q1; p1.e2 -> q2; stop }"
        val c2 = "main { p1.e2 -> q2; p1.e1 -> q1; stop }"

        assertFalse( bisimilar(c1,c2) )
    }

    @Test
    fun swap4()
    {
        val c1 = "main { p1.e1 -> q1; q1.e3 -> r; p2.e2 -> q2; stop }"
        val c2 = "main { p2.e2 -> q2; q1.e3 -> r; p1.e1 -> q1; stop }"

        assertFalse( bisimilar(c1,c2) )
    }

    @Test
    fun swap5()
    {
        val c1 = "main { p1.e1 -> q1; p2.e2 -> q2; p2.e2 -> q2; stop }"
        val c2 = "main { p2.e2 -> q2; p2.e2 -> q2; p1.e1 -> q1; stop }"

        assert( bisimilar(c1,c2) )
    }

    @Test
    fun swapCondCom()
    {
        val c1 = "main { if p.e then r.e -> s; stop else r.e -> s; stop }"
        val c2 = "main { r.e -> s; if p.e then stop else stop }"

        assert( bisimilar(c1,c2) )
    }

    @Test
    fun swapCondWrong()
    {
        val c1 = "main { if s.e then r.e -> s; stop else r.e -> s; stop }"
        val c2 = "main { r.e -> s; if s.e then stop else stop }"

        assertFalse( bisimilar(c1,c2) )
    }

    @Test
    fun swapCondCond()
    {
        val c1 = "main { if p.e then if q.e then stop else stop else if q.e then stop else stop }"
        val c2 = "main { if q.e then if p.e then stop else stop else if p.e then stop else stop }"

        assert( bisimilar(c1,c2) )
    }

    @Test
    fun swapCondCondWrong()
    {
        val c1 = "main { if p.e1 then if q.e2 then stop else stop else if q.e2 then stop else stop }"
        val c2 = "main { if q.e1 then if p.e1 then stop else stop else if p.e1 then stop else stop }"

        assertFalse( bisimilar(c1,c2) )
    }

    @Test
    fun swapCondCondWithProcedure()
    {
        val c1 = "def X { if q.e then stop else stop } main { if p.e then X else X }"
        val c2 = "main { if q.e then if p.e then stop else stop else if p.e then stop else stop }"

        assert( bisimilar(c1,c2) )
    }

    @Test
    fun swapCondCondWithProcedureInfinite()
    {
        val c1 = "def X { if q.e then X else X } main { if p.e then X else X }"
        val c2 = "def X { if q.e then X else X } main { if q.e then if p.e then X else X else if p.e then X else X }"

        assert( bisimilar(c1,c2) )
    }

    @Test
    fun swapCondCondMutuallyRecursive()
    {
        val c1 = "def Y { if p.e then X else X } def X { if q.e then Y else Y } main { if p.e then X else X }"
        val c2 = "def X { if p.e then Y else Y } def Y { if q.e then X else X } main { if q.e then if p.e then Y else Y else X }"

        assert( bisimilar(c1,c2) )
    }

    @Test
    fun swapCondCondComMutuallyRecursive()
    {
        val c1 = "def Y { if p.e then X else X } def X { r.e -> s; if q.e then Y else Y } main { if p.e then X else X }"
        val c2 = "def X { r.e -> s; if p.e then Y else Y } def Y { if q.e then X else X } main { if q.e then if p.e then r.e -> s; Y else r.e -> s; Y else X }"

        assert( bisimilar(c1,c2) )
    }

    @Test
    fun unfolding()
    {
        val c1 = "def X { p.e -> q; p.e -> q; X } main { X }"
        val c2 = "def X { p.e -> q; p.e -> q; p.e -> q; X } main { X }"

        assert( bisimilar(c1,c2) )
    }

//    Stack overflows
//    @Test
//    fun unfolding2()
//    {
//        val c1 = "def X { p.e -> q; p.e -> q; Y } def Y { r.e -> s; X } main { X }"
//        val c2 = "def X { p.e -> q; p.e -> q; p.e -> q; Y } def Y { r.e -> s; X } main { X }"
//
////        assertFalse( similar(c2,c1) )
////        assertFalse( similar(c1,c2) )
//
////        assertFalse( bisimilar(c1,c2) )
//    }

    @Test // This is unmergeable
    fun mix() {
        val c1 =
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

        val c2 =
                "def X1 { " +
                        "p.e->q; if p.e " +
                        "then X2 " +
                        "else X4 } " +
                        "def X2 { " +
                        "p->q[ok]; p->r[ok]; if r.e " +
                        "then  q.e->p; r->p[ok]; r->q[ok]; X1 " +
                        "else q.e->p; r->p[ko]; r->q[ko]; r.u->q; p.e->q; if p.e " +
                        "then X2 " +
                        "else X3 } " +
                        "def X3 { " +
                        "p->q[ko];  if q.e " +
                        "then p->r[ko]; q->p[ok]; q->r[ok]; X1 " +
                        "else p->r[ko]; q->p[ko]; q->r[ko]; p.e->q; p.e->q; if p.e " +
                        "then X2 " +
                        "else X3 } " +
                        "def X4 { " +
                        "p->q[ko]; if q.e " +
                        "then p->r[ko]; q->p[ok]; q->r[ok]; X1 " +
                        "else p->r[ko]; q->p[ko]; q->r[ko]; p.e->q; p.e->q; if p.e " +
                        "then X5 " +
                        "else X4 } " +
                        "def X5 { " +
                        "p->q[ok]; p->r[ok]; if r.e " +
                        "then q.e->p; r->p[ok]; r->q[ok]; X1 " +
                        "else q.e->p; r->p[ko]; r->q[ko]; r.u->q; p.e->q; if p.e " +
                        "then X5 " +
                        "else X4 } " +
                        "main {q.i->r; X1}"

        assert( bisimilar(c1,c2) )
    }
}

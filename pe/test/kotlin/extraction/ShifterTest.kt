package extraction

import org.junit.jupiter.api.Test
import util.refactor.NetworkShifter


class ShifterTest {
    companion object {
        private fun shift(orig:String) {
            val result1 = NetworkShifter.compute(orig, 0.2)
            val result2 = NetworkShifter.compute(orig, 0.4)
            val result3 = NetworkShifter.compute(orig, 0.7)
            println("Original: $orig\nResult(0.2): $result1\nResult(0.4): $result2\nResult(0.7):$result3")
        }
    }

    @Test
    fun ex2() {
        val orig = "c { def X {a!<pwd>; a&{ok: s?; stop, ko: X}} main {X}} | " +
                "a { def X {c?; s?; if s then c+ok; s+ok; stop else c+ko; s+ko; X} main {X}} | " +
                "s { def X {a!<s>; a&{ok: c!<t>; stop, ko:X}} main {X}}"
        shift(orig)
    }

    @Test
    fun ex4() {
        val test = "p { def X {q!<e1>; X} main {X}} | " +
                "q { def Y {p?; Y} main {Y}} | " +
                "r { def Z {s!<e2>; Z} main {Z}} | " +
                "s { def W {r?; W} main {W}}"
        shift(test)
    }

    @Test
    fun l1() {
        val test = "p { def X {q!<e1>; q!<e2>; q!<e3>; q!<e4>; q!<e5>; X} main {X}} | " +
                "q { def Y {1?; 2?; q!<e1>; 3?; q!<e2>; Y} main {p?; Y}}"
        shift(test)
    }

    @Test
    fun buyerSeller() {
        val test =
                "buyer{main{seller!<quote>; seller?; if ok then seller+accept; seller?; stop else seller+reject; stop}} | " +
                        "shipper{main{seller&{" +
                        "send: seller?; seller!<t>; stop," +
                        "wait: stop}}} | " +
                        "seller{main{buyer?; buyer!<quote>; buyer&{" +
                        "accept: shipper+send; shipper!<deliv>; shipper?; buyer!<details>; stop, " +
                        "reject: shipper+wait; stop}}}"
        shift(test)
    }

    @Test
    fun buyerSellerRec(){
        val test =
                "buyer{def X {seller?; if ok then seller+accept; seller?; stop else seller+reject; X} main {seller!<quote>; X}} | " +
                        "shipper{def X {seller&{" +
                        "send: seller?; seller!<t>; stop," +
                        "wait: X}} main {X}} | " +
                        "seller{def X {buyer!<quote>; buyer&{" +
                        "accept: shipper+send; shipper!<deliv>; shipper?; buyer!<details>; stop, " +
                        "reject: shipper+wait; X}} main {buyer?; X}}"

        shift(test)
    }

    @Test
    fun instrumentControlling() {
        val test =
                "user{def X{instrument+move; instrument+photo; instrument+quit; stop} " +
                        "main {operator!<high>; operator&{" +
                        "ok: X," +
                        "no: stop}}} | " +
                        "operator{main{user?; if ok then user+ok; instrument+ok; stop else user+no; instrument+no; stop}} | " +
                        "instrument{def X{user&{" +
                        "move: X," +
                        "photo: X," +
                        "quit: stop}} main{ operator&{" +
                        "ok: X, " +
                        "no: stop}}}"

        shift(test)
    }

    @Test
    fun C30() {
//        val test = """
//            s{
//	def P{D}
//	def D{v&{R: v&{R: stop, L: v?; H}, L: n&{R: v&{R: stop, L: v?; H}, L: v&{R: stop, L: v?; H}}}}
//	def H{x!<m7>; n&{R: j + l4; v?; P, L: D}}
//	main {j&{R: v&{R: x&{R: H, L: v?; P}, L: H}, L: v?; P}}
//	}
//|
//v{
//	def P{s!<m5>; n + l3; D}
//	def D{if c5 then if c6 then n + L; j + L; x + L; s + L; n&{R: n + L; j + L; x + L; s + L; s!<m6>; H, L: n + L; j + L; x + L; s + L; s!<m6>; H} else n + R; j + R; x + R; s + R; n + L; j + L; x + L; s + L; s!<m6>; H else if c6 then n + L; j + L; x + L; s + L; n&{R: n + R; j + R; x + R; s + R; stop, L: n + R; j + R; x + R; s + R; stop} else n + R; j + R; x + R; s + R; n + R; j + R; x + R; s + R; stop}
//	def H{n&{R: j!<m8>; x + l2; P, L: n + l3; D}}
//	main {j + l1; j?; x!<m2>; j&{R: if c3 then n + L; j + L; x + L; s + L; H else n + R; j + R; x + R; s + R; x&{R: H, L: x + l2; P}, L: x + l2; P}}
//	}
//|
//x{
//	def P{v&{l2: D}}
//	def D{v&{R: v&{R: stop, L: s?; H}, L: n&{R: v&{R: stop, L: s?; H}, L: v&{R: stop, L: s?; H}}}}
//	def H{n&{R: j!<m3>; n!<m4>; P, L: D}}
//	main {v?; if c2 then j&{R: v&{R: if c4 then n + L; j + L; v + L; s + L; j!<m3>; n!<m4>; P else n + R; j + R; v + R; s + R; s?; H, L: s?; H}, L: j!<m3>; n!<m4>; P} else j&{R: v&{R: if c4 then n + L; j + L; v + L; s + L; j!<m3>; n!<m4>; P else n + R; j + R; v + R; s + R; s?; H, L: s?; H}, L: j!<m3>; n!<m4>; P}}
//	}
//|
//j{
//	def P{D}
//	def D{v&{R: v&{R: stop, L: H}, L: n&{R: v&{R: stop, L: H}, L: v&{R: stop, L: H}}}}
//	def H{n&{R: s&{l4: v?; x?; P}, L: D}}
//	main {v&{l1: v!<m1>; if c1 then n + L; x + L; v + L; s + L; x?; P else n + R; x + R; v + R; s + R; v&{R: x&{R: H, L: x?; P}, L: H}}}
//}
//|
//n{
//	def P{x?; D}
//	def D{v&{l3: v&{R: v&{R: stop, L: H}, L: if c7 then j + L; x + L; v + L; s + L; v&{R: stop, L: H} else j + R; x + R; v + R; s + R; v&{R: stop, L: H}}}}
//	def H{if c8 then j + L; x + L; v + L; s + L; D else j + R; x + R; v + R; s + R; P}
//	main {j&{R: v&{R: x&{R: H, L: P}, L: H}, L: P}}
//}
//        """.trimIndent()
        val test = """
            s{
	def D{v&{R: v&{R: stop, L: v?; H}, L: n&{R: v&{R: stop, L: v?; H}, L: v&{R: stop, L: v?; H}}}}
	def H{x!<m7>; n&{R: j + l4; v?; D, L: D}}
	main {j&{R: v&{R: x&{R: H, L: v?; D}, L: H}, L: v?; D}}
	}
|
v{
	def P{s!<m5>; n + l3; D}
	def D{if c5 then if c6 then n + L; j + L; x + L; s + L; n&{R: n + L; j + L; x + L; s + L; s!<m6>; H, L: n + L; j + L; x + L; s + L; s!<m6>; H} else n + R; j + R; x + R; s + R; n + L; j + L; x + L; s + L; s!<m6>; H else if c6 then n + L; j + L; x + L; s + L; n&{R: n + R; j + R; x + R; s + R; stop, L: n + R; j + R; x + R; s + R; stop} else n + R; j + R; x + R; s + R; n + R; j + R; x + R; s + R; stop}
	def H{n&{R: j!<m8>; x + l2; P, L: n + l3; D}}
	main {j&{R: if c3 then n + L; j + L; x + L; s + L; H else n + R; j + R; x + R; s + R; x&{R: H, L: x + l2; P}, L: x + l2; P}}
	}
|
x{
	def P{v&{l2: D}}
	def D{v&{R: v&{R: stop, L: s?; H}, L: n&{R: v&{R: stop, L: s?; H}, L: v&{R: stop, L: s?; H}}}}
	def H{n&{R: j!<m3>; n!<m4>; P, L: D}}
	main {if c2 then j&{R: v&{R: if c4 then n + L; j + L; v + L; s + L; j!<m3>; n!<m4>; P else n + R; j + R; v + R; s + R; s?; H, L: s?; H}, L: j!<m3>; n!<m4>; P} else j&{R: v&{R: if c4 then n + L; j + L; v + L; s + L; j!<m3>; n!<m4>; P else n + R; j + R; v + R; s + R; s?; H, L: s?; H}, L: j!<m3>; n!<m4>; P}}
	}
|
j{
	def D{v&{R: v&{R: stop, L: H}, L: n&{R: v&{R: stop, L: H}, L: v&{R: stop, L: H}}}}
	def H{n&{R: s&{l4: v?; x?; D}, L: D}}
	main {if c1 then n + L; x + L; v + L; s + L; x?; D else n + R; x + R; v + R; s + R; v&{R: x&{R: H, L: x?; D}, L: H}}
}
|
n{
	def P{x?; D}
	def D{v&{l3: v&{R: v&{R: stop, L: H}, L: if c7 then j + L; x + L; v + L; s + L; v&{R: stop, L: H} else j + R; x + R; v + R; s + R; v&{R: stop, L: H}}}}
	def H{if c8 then j + L; x + L; v + L; s + L; D else j + R; x + R; v + R; s + R; P}
	main {j&{R: v&{R: x&{R: H, L: P}, L: H}, L: P}}
}
        """.trimIndent()
        println( Extraction.extractChoreography(test).toString() )
    }
}
package bench

import extraction.Extraction
import extraction.ExtractionStrategy
import org.openjdk.jmh.annotations.*
import java.util.*
import java.util.concurrent.TimeUnit

@Warmup(iterations = 1)
@Measurement(iterations = 1)
@Fork(1)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)


open class UnmarkedThenRandom {
    @Benchmark
    fun runningExample(){
        val test = "a {def X " +
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
        Extraction.extractChoreography(test, ExtractionStrategy.UnmarkedThenRandom).toString()
    }

    @Benchmark
    fun runningExample2x(){
        val test =
                "a1 {def X {if e then b1+win; c1+lose; b1?; c1?; d1!<free>; X else b1+lose; c1+win; b1?; c1?; d1!<free>; X} main {X}} |" +
                        "b1 {def X {a1&{win: a1!<sig>; X, lose: a1!<sig>; X}} main {X}} |" +
                        "c1 {def X {d1!<busy>; a1&{win: a1!<msg>; X, lose: a1!<msg>; X}} main {X}} |" +
                        "d1 {def X {c1?; a1?; X} main {X}} | " +
                        "a2 {def X {if e then b2+win; c2+lose; b2?; c2?; d2!<free>; X else b2+lose; c2+win; b2?; c2?; d2!<free>; X} main {X}} |" +
                        "b2 {def X {a2&{win: a2!<sig>; X, lose: a2!<sig>; X}} main {X}} |" +
                        "c2 {def X {d2!<busy>; a2&{win: a2!<msg>; X, lose: a2!<msg>; X}} main {X}} |" +
                        "d2 {def X {c2?; a2?; X} main {X}}"
        Extraction.extractChoreography(test, ExtractionStrategy.UnmarkedThenRandom).toString()
    }

    @Benchmark
    fun twoBit(){
        val test =
                "a { def X {b?; b!<0>;b?;b!<1>;X} main {b!<0>;b!<1>;X}} | " +
                        "b { def Y {a?;a!<ack0>;a?;a!<ack1>;Y} main {Y}}"

        Extraction.extractChoreography(test, ExtractionStrategy.UnmarkedThenRandom).toString()
    }

    @Benchmark
    fun twoBit2x(){
        val test =
                "a { def X {b?;b!<0>;b?;b!<1>;X} main {b!<0>;b!<1>;X}} | " +
                        "b { def Y {a?;a!<ack0>;a?;a!<ack1>;Y} main {Y}} | " +
                        "c { def X {d?;d!<0>;d?;d!<1>;X} main {d!<0>;d!<1>;X}} | " +
                        "d { def Y {c?;c!<ack0>;c?;c!<ack1>; Y} main {Y}}"

        Extraction.extractChoreography(test, ExtractionStrategy.UnmarkedThenRandom).toString()
    }

    @Benchmark
    fun bargain(){
        val test =
                "a { def X {if notok then b+hag; b?; X else b+happy; c!<info>; stop} main {X}} | " +
                        "b { def Y {a&{hag: a!<price>; Y, happy: stop}} main {Y}} | " +
                        "c { main {a?; stop}}"

        Extraction.extractChoreography(test, ExtractionStrategy.LongestFirst, ArrayList(listOf("c"))).toString()
    }

    @Benchmark
    fun bargain2x(){
        val test =
                "a { def X {b!<hag>; b?; if price then b+deal; b!<happy>; c!<info>; X else b+nodeal; X} main {X}} | " +
                        "b { def Y {a?; a!<price>; a&{deal: a?; Y, nodeal: Y}} main {Y}} | " +
                        "c { def Z {a?; Z} main {Z}} | " +
                        "d { def X {e!<hag>; e?; if price then e+deal; e!<happy>; f!<info>; X else e+nodeal; X} main {X}} | " +
                        "e { def Y {d?; d!<price>; d&{deal: d?; Y, nodeal: Y}} main {Y}} | " +
                        "f { def Z {d?; Z} main {Z}}"


        Extraction.extractChoreography(test, ExtractionStrategy.LongestFirst, ArrayList(listOf("c", "f"))).toString()
    }

    @Benchmark
    fun health(){
        val test =
                "hs{def X{p?; ss!<subscribed>; ss&{" +
                        "ok: p+subscribed; as!<account>; as?; t!<fwd>; t?; X, " +
                        "nok: p+notSubscribed; X}} main{X}} | " +
                        "p{def X{hs!<sendData>; hs&{subscribed: es?; X, notSubscribed: X}} main{X}} | " +
                        "ss{def X{hs?; if ok then hs+ok; X else hs+nok; X} main{X}} | " +
                        "as{def X{hs?; hs!<logCreated>; X} main{X}} | " +
                        "t{def X{hs?; hs!<fwdOk>; es!<helpReq>; X} main{X}} | " +
                        "es{def X{t?; p!<provideService>; X} main{X}}"

        Extraction.extractChoreography(test, ExtractionStrategy.LongestFirst, ArrayList(listOf("as", "t", "es"))).toString()
    }

    @Benchmark
    fun health2x(){
        val test =
                "hs1{def X{p1?; ss1!<subscribed>; ss1&{ok: p1+subscribed; as1!<account>; as1?; t1!<fwd>; t1?; X, nok: p1+notSubscribed; X}} main{X}} | " +
                        "p1{def X{hs1!<sendData>; hs1&{subscribed: es1?; X, notSubscribed: X}} main{X}} | " +
                        "ss1{def X{hs1?; if ok then hs1+ok; X else hs1+nok; X} main{X}} | " +
                        "as1{def X{hs1?; hs1!<logCreated>; X} main{X}} | " +
                        "t1{def X{hs1?; hs1!<fwdOk>; es1!<helpReq>; X} main{X}} | " +
                        "es1{def X{t1?; p1!<provideService>; X} main{X}} | " +
                        "hs2{def X{p2?; ss2!<subscribed>; ss2&{ok: p2+subscribed; as2!<account>; as2?; t2!<fwd>; t2?; X, nok: p2+notSubscribed; X}} main{X}} | " +
                        "p2{def X{hs2!<sendData>; hs2&{subscribed: es2?; X, notSubscribed: X}} main{X}} | " +
                        "ss2{def X{hs2?; if ok then hs2+ok; X else hs2+nok; X} main{X}} | " +
                        "as2{def X{hs2?; hs2!<logCreated>; X} main{X}} | " +
                        "t2{def X{hs2?; hs2!<fwdOk>; es2!<helpReq>; X} main{X}} | " +
                        "es2{def X{t2?; p2!<provideService>; X} main{X}}"

        Extraction.extractChoreography(test, ExtractionStrategy.LongestFirst, ArrayList(listOf("as1", "t1", "es1", "as2", "t2", "es2"))).toString()
    }

    @Benchmark
    fun filter(){
        val test =
                "filter {" +
                        "def X {data!<newFilterRequest>; Y} " +
                        "def Y {data&{" +
                        "item: data?; if itemToBeFiltered then data!<ok>; Y else data!<remove>; Y," +
                        "noItem: X}}" +
                        "main {X}} | " +
                        "data {" +
                        "def X {filter?; Y} " +
                        "def Y {if itemToBeFiltered " +
                        "then filter+item; filter!<itemToBeFiltered>; filter?; Y " +
                        "else filter+noItem; X} " +
                        "main {X}}"


        Extraction.extractChoreography(test, ExtractionStrategy.UnmarkedThenRandom).toString()
    }

    @Benchmark
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


        Extraction.extractChoreography(test, ExtractionStrategy.UnmarkedThenRandom).toString()
    }

    @Benchmark
    fun logistic(){
        val test =
                "supplier {" +
                        "def X {shipper?; Y} " +
                        "def Y {if needToShip " +
                        "then shipper+item; X " +
                        "else shipper+done; retailer!<UpdatePOandDeliverySchedule>; retailer?; retailer?; retailer!<FinalizedPOandDeliverySchedule>; stop}" +
                        "main { retailer!<PlannedOrderVariations>; retailer?; retailer?; Y}" + "} | " +
                        "retailer {" +
                        "main {" +
                        "supplier?; supplier!<OrderDeliveryVariations>; supplier!<DeliverCheckPointRequest>; " +
                        "supplier?; supplier!<POandDeliveryScheduleMods>; shipper!<ConfirmationofDeliverySchedule>; " +
                        "supplier!<AcceptPOandDeliverySchedule>; supplier?; stop}} |" +
                        "shipper {" +
                        "def X{supplier!<DeliveryItem>; Y} " +
                        "def Y {supplier&{item: X, done: retailer?; stop}}" +
                        "main{Y}}"

        Extraction.extractChoreography(test, ExtractionStrategy.LongestFirst, ArrayList(listOf("retailer"))).toString()
    }

    @Benchmark
    fun logistic2(){
        val test =
                "supplier {" +
                        "def X {shipper?; consignee?; Y} " +
                        "def Y {if needToShip " +
                        "then shipper+item; consignee+item; X " +
                        "else shipper+done; consignee+done; " +
                        "retailer!<UpdatePOandDeliverySchedule>; retailer?; retailer?; retailer!<FinalizedPOandDeliverySchedule>; stop}" +
                        "main { retailer!<PlannedOrderVariations>; retailer?; retailer?; Y}" +
                        "} | " +
                        "retailer {main {" +
                        "supplier?; supplier!<OrderDeliveryVariations>; supplier!<DeliverCheckPointRequest>; " +
                        "supplier?; supplier!<POandDeliveryScheduleMods>; shipper!<ConfirmationofDeliverySchedule>; " +
                        "supplier!<AcceptPOandDeliverySchedule>; supplier?; stop}} |" +
                        "consignee {" +
                        "def X{supplier!<DeliveryItem>; Z} " +
                        "def Z {supplier&{item: X, done: stop}}" +
                        "main{Z}} | " +
                        "shipper {" +
                        "def X{supplier!<DeliveryItem>; Z} " +
                        "def Z {supplier&{item: X, done: retailer?; stop}}" +
                        "main{Z}}"

        Extraction.extractChoreography(test, ExtractionStrategy.LongestFirst, ArrayList(listOf("retailer"))).toString()
    }

    @Benchmark
    fun cloudSystem(){
        val test =
                "cl{" +
                        "def X{int!<connect>; int?; Y} " +
                        "def Y{if access then appli+awaitcl; appli!<access>; Y else int!<logout>; appli+syncLogout; appli?; X} " +
                        "main {X}} | " +
                        "appli{" +
                        "def X{int?; Y} " +
                        "def Y{cl&{awaitcl: cl?; Y, syncLogout: db!<log>; cl!<syncLog>; X}} " +
                        "main {X}} | " +
                        "int{" +
                        "def X{cl?; appli!<setup>; cl!<syncAccess>; cl?; X} " +
                        "main {X}} | " +
                        "db{" +
                        "def X{appli?; X} " +
                        "main {X}}"

        Extraction.extractChoreography(test, ExtractionStrategy.LongestFirst, ArrayList(listOf("db","int"))).toString()
    }

    @Benchmark
    fun cloudSystem2x(){
        val test =
                "cl1{def X{int1!<connect>; int1?; Y} def Y{if access then appli1+awaitcl; appli1!<access>; Y else int1!<logout>; appli1+syncLogout; appli1?; X} main {X}} | " +
                        "appli1{def X{int1?; Y} def Y{cl1&{awaitcl: cl1?; Y, syncLogout: db1!<log>; cl1!<syncLog>; X}} main {X}} | " +
                        "int1{def X{cl1?; appli1!<setup>; cl1!<syncAccess>; cl1?; X} main {X}} | " +
                        "db1{def X{appli1?; X} main {X}} | " +
                        "cl2{def X{int2!<connect>; int2?; Y} def Y{if access then appli2+awaitcl; appli2!<access>; Y else int2!<logout>; appli2+syncLogout; appli2?; X} main {X}} | " +
                        "appli2{def X{int2?; Y} def Y{cl2&{awaitcl: cl2?; Y, syncLogout: db2!<log>; cl2!<syncLog>; X}} main {X}} | " +
                        "int2{def X{cl2?; appli2!<setup>; cl2!<syncAccess>; cl2?; X} main {X}} | " +
                        "db2{def X{appli2?; X} main {X}}"

        Extraction.extractChoreography(test, ExtractionStrategy.LongestFirst, ArrayList(listOf("db1","int1","db2","int2"))).toString()
    }

    @Benchmark
    fun sanitaryAgency(){
        val test =
                "citizen{" +
                        "def X{" +
                        "sanagency!<request>; sanagency?; sanagency!<provInf>; sanagency&{" +
                        "refusal: X, " +
                        "acceptance: coop?; bank!<paymentPrivateFee>; X}} " +
                        "main{X}" +
                        "} | " +
                        "sanagency{" +
                        "def X{" +
                        "citizen?; citizen!<askInfo>; citizen?; if infoProved " +
                        "then citizen+acceptance; coop!<req>; bank!<paymentPublicFee>; bank?; X " +
                        "else citizen+refusal; X }" +
                        "main {X}} | " +
                        "coop{def X{" +
                        "sanagency?; " +
                        "if fine " +
                        "then citizen!<provT>; bank+recMoneyPossT; bank?; X " +
                        "else citizen!<provM>; bank+recMoneyPossM; bank?; X} " +
                        "main{X}} | " +
                        "bank{" +
                        "def X{ coop&{" +
                        "recMoneyPossT: coop!<paymentT>; Y, " +
                        "recMoneyPossM: coop!<paymentM>; Y}} " +
                        "def Y{citizen?; sanagency?; sanagency!<done>; X} " +
                        "main{X}}"

        Extraction.extractChoreography(test, ExtractionStrategy.LongestFirst, ArrayList(listOf("coop","bank"))).toString()
    }

    @Benchmark
    fun sanitaryAgency2x(){
        val test =
                "citizen{" +
                        "def X{" +
                        "sanagency!<request>; sanagency?; sanagency!<provInf>; sanagency&{" +
                        "refusal: X, " +
                        "acceptance: coop?; bank!<paymentPrivateFee>; X}} " +
                        "main{X}" +
                        "} | " +
                        "sanagency{" +
                        "def X{" +
                        "citizen?; citizen!<askInfo>; citizen?; if infoProved " +
                        "then citizen+acceptance; coop!<req>; bank!<paymentPublicFee>; bank?; X " +
                        "else citizen+refusal; X }" +
                        "main {X}} | " +
                        "coop{def X{" +
                        "sanagency?; " +
                        "if fine " +
                        "then citizen!<provT>; bank+recMoneyPossT; bank?; X " +
                        "else citizen!<provM>; bank+recMoneyPossM; bank?; X} " +
                        "main{X}} | " +
                        "bank{" +
                        "def X{ coop&{" +
                        "recMoneyPossT: coop!<paymentT>; Y, " +
                        "recMoneyPossM: coop!<paymentM>; Y}} " +
                        "def Y{citizen?; sanagency?; sanagency!<done>; X} " +
                        "main{X}}" +
                        " | citizen2{" +
                        "def X{" +
                        "sanagency2!<request>; sanagency2?; sanagency2!<provInf>; sanagency2&{" +
                        "refusal: X, " +
                        "acceptance: coop2?; bank2!<paymentPrivateFee>; X}} " +
                        "main{X}" +
                        "} | " +
                        "sanagency2{" +
                        "def X{" +
                        "citizen2?; citizen2!<askInfo>; citizen2?; if infoProved " +
                        "then citizen2+acceptance; coop2!<req>; bank2!<paymentPublicFee>; bank2?; X " +
                        "else citizen2+refusal; X }" +
                        "main {X}} | " +
                        "coop2{def X{" +
                        "sanagency2?; " +
                        "if fine " +
                        "then citizen2!<provT>; bank2+recMoneyPossT; bank2?; X " +
                        "else citizen2!<provM>; bank2+recMoneyPossM; bank2?; X} " +
                        "main{X}} | " +
                        "bank2{" +
                        "def X{ coop2&{" +
                        "recMoneyPossT: coop2!<paymentT>; Y, " +
                        "recMoneyPossM: coop2!<paymentM>; Y}} " +
                        "def Y{citizen2?; sanagency2?; sanagency2!<done>; X} " +
                        "main{X}}"

        Extraction.extractChoreography(test, ExtractionStrategy.UnmarkedThenRandom, ArrayList(listOf("coop","bank","coop2","bank2"))).toString()
    }
}
package ce

import org.junit.Assert
import org.junit.Test

class NbTest : Assert() {
    @Test
    fun nbtst1(){
        val test = "" +
                "a {def Y {c?; d!<free>; X} " +
                "def X " +
                "{if e " +
                "then b+win; c+lose; b?; Y " +
                "else b+lose; c+win; b?; Y} " +
                "main {X}} |" +
                "b {def X " +
                "{a&{" +
                "win: a!<sig>; X, " +
                "lose: a!<sig>; X}} " +
                "main {X}} |" +
                "c {def X " +
                "{d!<busy>; a&{" +
                "win: a!<msg>; X, " +
                "lose: a!<msg>; X}} " +
                "main {X}} |" +
                "d {def X " +
                "{c?; a?; X} " +
                "main {X}}"
        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected =
                "def X1 { if a.e then a->b[win]; c.busy->d; a->c[lose]; b.sig->a; c.msg->a; a.free->d; X1 else a->b[lose]; c.busy->d; a->c[win]; b.sig->a; c.msg->a; a.free->d; X1 } main {X1}"

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
                "a { def X {b?; b!<0>;b?;b!<1>;X} main {b!<0>;b!<1>;X}} | " +
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

    @Test (expected = NetworkExtraction.MulticomException::class)
    fun mult3bit(){
        val test =
                "a { def X {b?; b!<0>;b?;b!<1>;b?;b!<2>;X} main {b!<0>;b!<1>;b!<2>; X}} | " +
                        "b { def Y {a!<ack0>;a?;a!<ack1>;a?;a!<ack2>;a?;Y} main {a?;Y}}"

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
                "a { def X {if notok then b+hag; b?; X else b+happy; c!<info>; stop} main {X}} | " +
                        "b { def Y {a&{hag: a!<price>; Y, happy: stop}} main {Y}} | " +
                        "c { main {a?; stop}}"

        val args = arrayOf("-c", test, "-l", "c")

        val actual = ChoreographyExtraction.main(args)
        val expected = "def X1 { if a.notok then a->b[hag]; b.price->a; X1 else a->b[happy]; a.info->c; stop } main {X1}"

        assertEquals(expected, actual)
    }

    @Test
    fun bargain2x(){
        val test =
                "a { def X {b!<hag>; b?; if price then b+deal; b!<happy>; c!<info>; X else b+nodeal; X} main {X}} | " +
                        "b { def Y {a?; a!<price>; a&{deal: a?; Y, nodeal: Y}} main {Y}} | " +
                        "c { def Z {a?; Z} main {Z}} | " +
                        "d { def X {e!<hag>; e?; if price then e+deal; e!<happy>; f!<info>; X else e+nodeal; X} main {X}} | " +
                        "e { def Y {d?; d!<price>; d&{deal: d?; Y, nodeal: Y}} main {Y}} | " +
                        "f { def Z {d?; Z} main {Z}}"


        val args = arrayOf("-c", test, "-l", "c, f")

        val actual = ChoreographyExtraction.main(args)
        //assertEquals(expected, actual)
    }

    @Test
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

        val args = arrayOf("-c", test, "-l", "as, t, es")

        val actual = ChoreographyExtraction.main(args)
        val expected = "def X1 { p.sendData->hs; X2 } def X2 { hs.subscribed->ss; if ss.ok then ss->hs[ok]; hs->p[subscribed]; hs.account->as; as.logCreated->hs; hs.fwd->t; t.fwdOk->hs; t.helpReq->es; es.provideService->p; p.sendData->hs; X2 else ss->hs[nok]; hs->p[notSubscribed]; X1 } main {X1}"

        assertEquals(expected, actual)
    }

    @Test
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


        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected =
                "def X1 { filter.newFilterRequest->data; X2 } def X2 { if data.itemToBeFiltered then data->filter[item]; data.itemToBeFiltered->filter; if filter.itemToBeFiltered then filter.ok->data; X2 else filter.remove->data; X2 else data->filter[noItem]; X1 } main {X1}"

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

        //assertEquals(expected, actual)
    }

    @Test
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

        val args = arrayOf("-c", test, "-l", "retailer")

        val actual = ChoreographyExtraction.main(args)
        val expected =
                "def X1 { supplier->shipper[item]; shipper.DeliveryItem->supplier; if supplier.needToShip then X1 else supplier->shipper[done]; supplier.UpdatePOandDeliverySchedule->retailer; retailer.POandDeliveryScheduleMods->supplier; retailer.ConfirmationofDeliverySchedule->shipper; retailer.AcceptPOandDeliverySchedule->supplier; supplier.FinalizedPOandDeliverySchedule->retailer; stop } main {supplier.PlannedOrderVariations->retailer; retailer.OrderDeliveryVariations->supplier; retailer.DeliverCheckPointRequest->supplier; if supplier.needToShip then X1 else supplier->shipper[done]; supplier.UpdatePOandDeliverySchedule->retailer; retailer.POandDeliveryScheduleMods->supplier; retailer.ConfirmationofDeliverySchedule->shipper; retailer.AcceptPOandDeliverySchedule->supplier; supplier.FinalizedPOandDeliverySchedule->retailer; stop}"

        assertEquals(expected, actual)
    }

    @Test
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



        val args = arrayOf("-c", test, "-l", "retailer")

        val actual = ChoreographyExtraction.main(args)
        val expected =
                "def X1 { supplier->shipper[item]; supplier->consignee[item]; shipper.DeliveryItem->supplier; consignee.DeliveryItem->supplier; if supplier.needToShip then X1 else supplier->shipper[done]; supplier->consignee[done]; supplier.UpdatePOandDeliverySchedule->retailer; retailer.POandDeliveryScheduleMods->supplier; retailer.ConfirmationofDeliverySchedule->shipper; retailer.AcceptPOandDeliverySchedule->supplier; supplier.FinalizedPOandDeliverySchedule->retailer; stop } main {supplier.PlannedOrderVariations->retailer; retailer.OrderDeliveryVariations->supplier; retailer.DeliverCheckPointRequest->supplier; if supplier.needToShip then X1 else supplier->shipper[done]; supplier->consignee[done]; supplier.UpdatePOandDeliverySchedule->retailer; retailer.POandDeliveryScheduleMods->supplier; retailer.ConfirmationofDeliverySchedule->shipper; retailer.AcceptPOandDeliverySchedule->supplier; supplier.FinalizedPOandDeliverySchedule->retailer; stop}"

        assertEquals(expected, actual)
    }

    @Test
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

        val args = arrayOf("-c", test, "-l", "db, int")

        val actual = ChoreographyExtraction.main(args)
        val expected =
                "def X1 { cl.connect->int; int.setup->appli; int.syncAccess->cl; if cl.access then X2 else cl.logout->int; cl->appli[syncLogout]; appli.log->db; appli.syncLog->cl; X1 } def X2 { cl->appli[awaitcl]; cl.access->appli; if cl.access then X2 else cl.logout->int; cl->appli[syncLogout]; appli.log->db; appli.syncLog->cl; X1 } main {X1}"

        assertEquals(expected, actual)
    }

    @Test
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

        val args = arrayOf("-c", test, "-l", "coop, bank")

        val actual = ChoreographyExtraction.main(args)
        val expected =
                "def X1 { citizen.request->sanagency; X2 } def X2 { sanagency.askInfo->citizen; citizen.provInf->sanagency; if sanagency.infoProved then sanagency->citizen[acceptance]; sanagency.req->coop; if coop.fine then coop.provT->citizen; coop->bank[recMoneyPossT]; bank.paymentT->coop; citizen.paymentPrivateFee->bank; sanagency.paymentPublicFee->bank; bank.done->sanagency; citizen.request->sanagency; X2 else coop.provM->citizen; coop->bank[recMoneyPossM]; bank.paymentM->coop; citizen.paymentPrivateFee->bank; sanagency.paymentPublicFee->bank; bank.done->sanagency; citizen.request->sanagency; X2 else sanagency->citizen[refusal]; X1 } main {X1}"

        assertEquals(expected, actual)
    }

    @Test
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

        val args = arrayOf("-c", test, "-l", "coop, bank, coop2, bank2")

        val actual = ChoreographyExtraction.main(args)
        val expected =
<<<<<<< HEAD
                "def X1 { citizen.request->sanagency; X2 } def X2 { sanagency.askInfo->citizen; citizen.provInf->sanagency; if sanagency.infoProved then sanagency->citizen[acceptance]; sanagency.req->coop; if coop.fine then coop.provT->citizen; coop->bank[recMoneyPossT]; bank.paymentT->coop; citizen.paymentPrivateFee->bank; sanagency.paymentPublicFee->bank; bank.done->sanagency; citizen.request->sanagency; X2 else coop.provM->citizen; coop->bank[recMoneyPossM]; bank.paymentM->coop; citizen.paymentPrivateFee->bank; sanagency.paymentPublicFee->bank; bank.done->sanagency; citizen.request->sanagency; X2 else sanagency->citizen[refusal]; X1 } main {X1}"
=======
                "def X1 { citizen.provInf->sanagency; if sanagency.infoProved then sanagency->citizen[acceptance]; sanagency.req->coop; if coop.fine then coop.provT->citizen; coop->bank[recMoneyPossT]; bank.paymentT->coop; citizen.paymentPrivateFee->bank; sanagency.paymentPublicFee->bank; bank.done->sanagency; citizen2.request->sanagency2; sanagency2.askInfo->citizen2; citizen2.provInf->sanagency2; if sanagency2.infoProved then sanagency2->citizen2[acceptance]; sanagency2.req->coop2; if coop2.fine then coop2.provT->citizen2; coop2->bank2[recMoneyPossT]; bank2.paymentT->coop2; citizen2.paymentPrivateFee->bank2; sanagency2.paymentPublicFee->bank2; bank2.done->sanagency2; citizen.request->sanagency; sanagency.askInfo->citizen; X1 else coop2.provM->citizen2; coop2->bank2[recMoneyPossM]; bank2.paymentM->coop2; citizen2.paymentPrivateFee->bank2; sanagency2.paymentPublicFee->bank2; bank2.done->sanagency2; citizen.request->sanagency; sanagency.askInfo->citizen; X1 else sanagency2->citizen2[refusal]; citizen.request->sanagency; sanagency.askInfo->citizen; X1 else coop.provM->citizen; coop->bank[recMoneyPossM]; bank.paymentM->coop; citizen.paymentPrivateFee->bank; sanagency.paymentPublicFee->bank; bank.done->sanagency; citizen2.request->sanagency2; sanagency2.askInfo->citizen2; citizen2.provInf->sanagency2; if sanagency2.infoProved then sanagency2->citizen2[acceptance]; sanagency2.req->coop2; if coop2.fine then coop2.provT->citizen2; coop2->bank2[recMoneyPossT]; bank2.paymentT->coop2; citizen2.paymentPrivateFee->bank2; sanagency2.paymentPublicFee->bank2; bank2.done->sanagency2; citizen.request->sanagency; sanagency.askInfo->citizen; X1 else coop2.provM->citizen2; coop2->bank2[recMoneyPossM]; bank2.paymentM->coop2; citizen2.paymentPrivateFee->bank2; sanagency2.paymentPublicFee->bank2; bank2.done->sanagency2; citizen.request->sanagency; sanagency.askInfo->citizen; X1 else sanagency2->citizen2[refusal]; citizen.request->sanagency; sanagency.askInfo->citizen; X1 else sanagency->citizen[refusal]; citizen2.request->sanagency2; sanagency2.askInfo->citizen2; citizen2.provInf->sanagency2; if sanagency2.infoProved then sanagency2->citizen2[acceptance]; sanagency2.req->coop2; if coop2.fine then coop2.provT->citizen2; coop2->bank2[recMoneyPossT]; bank2.paymentT->coop2; citizen2.paymentPrivateFee->bank2; sanagency2.paymentPublicFee->bank2; bank2.done->sanagency2; citizen.request->sanagency; sanagency.askInfo->citizen; X1 else coop2.provM->citizen2; coop2->bank2[recMoneyPossM]; bank2.paymentM->coop2; citizen2.paymentPrivateFee->bank2; sanagency2.paymentPublicFee->bank2; bank2.done->sanagency2; citizen.request->sanagency; sanagency.askInfo->citizen; X1 else sanagency2->citizen2[refusal]; citizen.request->sanagency; sanagency.askInfo->citizen; X1 } main {citizen.request->sanagency; sanagency.askInfo->citizen; X1}"
>>>>>>> 21ee86862919a4e92f2ae6a7ebf370e0e3ede290

        assertEquals(expected, actual)
    }


    @Test
    fun buyerSeller(){
        val test =
                "buyer{main{seller!<quote>; seller?; if ok then seller+accept; seller?; stop else seller+reject; stop}} | " +
                        "shipper{main{seller&{" +
                        "send: seller?; seller!<t>; stop," +
                        "wait: stop}}} | " +
                        "seller{main{buyer?; buyer!<quote>; buyer&{" +
                        "accept: shipper+send; shipper!<deliv>; shipper?; buyer!<details>; stop, " +
                        "reject: shipper+wait; stop}}}"

        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected =
                "main {buyer.quote->seller; seller.quote->buyer; if buyer.ok then buyer->seller[accept]; seller->shipper[send]; seller.deliv->shipper; shipper.t->seller; seller.details->buyer; stop else buyer->seller[reject]; seller->shipper[wait]; stop}"
        assertEquals(expected, actual)
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

        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected =
                "def X1 { if buyer.ok then buyer->seller[accept]; seller->shipper[send]; seller.deliv->shipper; shipper.t->seller; seller.details->buyer; stop else buyer->seller[reject]; seller->shipper[wait]; seller.quote->buyer; X1 } main {buyer.quote->seller; seller.quote->buyer; X1}"
        assertEquals(expected, actual)
    }


    @Test
    fun twoBuyersProtocol(){
        val test =
                "buyer1{def X {seller!<book>; seller?; buyer2!<quote>; X} main {X}} | " +
                        "buyer2{def X {seller?; buyer1?; if ok then seller+accept; seller!<address>; seller?; X else seller+decline; X} main {X}} | " +
                        "seller{def X {buyer1?; buyer1!<quote>; buyer2!<quote>; buyer2&{accept: buyer2?; buyer2!<date>; X, decline: X}} main {X}}"

        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected =
                "def X1 { buyer1.book->seller; seller.quote->buyer1; X2 } def X2 { seller.quote->buyer2; buyer1.quote->buyer2; if buyer2.ok then buyer2->seller[accept]; buyer2.address->seller; seller.date->buyer2; buyer1.book->seller; seller.quote->buyer1; X2 else buyer2->seller[decline]; X1 } main {X1}"
        assertEquals(expected, actual)
    }

    @Test
    fun streamingProtocol(){
        val test =
                "kernel{def X{data?; key?; consumer!<xor>; X} main{X}} | " +
                        "data{def X{kernel!<data>; X} main{X}} | " +
                        "key{def X{kernel!<data>; X} main{X}} | " +
                        "consumer{def X{kernel?; X} main{X}}"

        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected =
                "def X1 { data.data->kernel; key.data->kernel; kernel.xor->consumer; X1 } main {X1}"
        assertEquals(expected, actual)
    }


    @Test (expected = NetworkExtraction.NoPossibleActionsException::class)
    fun InstrumentControllingFail(){
        val test =
                "user{def X{instrument+move; instrument+photo; instrument+quit; stop} " +
                        "main {operator!<high>; operator&{" +
                        "ok: X," +
                        "no: stop}}} | " +
                        "operator{main{user?; if ok then user+ok; stop else user+no; stop}} | " +
                        "instrument{def X{user&{" +
                        "move: X," +
                        "photo: X," +
                        "quit: stop}} main{X}}"

        val args = arrayOf("-c", test)
        ChoreographyExtraction.main(args)
    }

    @Test
    fun InstrumentControlling(){
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

        val args = arrayOf("-c", test)

        val actual = ChoreographyExtraction.main(args)
        val expected =
                "main {user.high->operator; if operator.ok then operator->user[ok]; operator->instrument[ok]; user->instrument[move]; user->instrument[photo]; user->instrument[quit]; stop else operator->user[no]; operator->instrument[no]; stop}"
        assertEquals(expected, actual)
    }
}
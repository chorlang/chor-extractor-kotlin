package util

import ast.cc.interfaces.CCVisitor
import ast.cc.interfaces.CCNode
import ast.cc.nodes.*
import javax.naming.OperationNotSupportedException

class UsedProcesses : CCVisitor<Set<String>> {

    override fun visit(n:Termination):Set<String> {
        return HashSet()
    }

    override fun visit(n:ProcedureInvocation):Set<String> {
        return HashSet()
    }

    override fun visit(n:Condition):Set<String> {
        return n.thenChoreography.accept(this) + n.elseChoreography.accept(this) + n.process
    }

    override fun visit(n:CommunicationSelection):Set<String> {
        return n.continuation.accept(this) + n.node.sender + n.node.receiver
    }

    override fun visit(n:Multicom):Set<String> {
        var ret = n.continuation.accept(this)
        n.actions.forEach { ret = ret + it.sender + it.receiver }
        return ret
    }

    override fun visit(n:ProcedureDefinition):Set<String> {
        throw OperationNotSupportedException()
    }

    override fun visit(n:Choreography):Set<String> {
        throw OperationNotSupportedException()
    }

    override fun visit(n:Program):Set<String> {
        throw OperationNotSupportedException()
    }

    companion object {
        private fun freeProcessNames(n: CCNode):Set<String> {
            return n.accept(UsedProcesses())
        }

        //    private HashMap<String,HashSet<String>> usedProcesses() {
//        Set<String> keys = procedures.keySet();
//
//        // first: maps from body to process names and procedure calls in it
//        HashMap<String,HashSet<String>> calls = new HashMap<String,HashSet<String>>();
//        for (String name:keys)
//        calls.put(name,UsedProcedures.run(procedures.get(name)));
//
//        // second: iteratively compute a fixpoint...
//        HashMap<String,HashSet<String>> usedProcesses = new HashMap<String,HashSet<String>>(),
//        auxUsedProcesses = new HashMap<String,HashSet<String>>();
//
//        for (String name:keys)
//        auxUsedProcesses.put(name,UsedProcesses.run(procedures.get(name)));
//        while (!usedProcesses.equals(auxUsedProcesses)) {
//            for (String name:keys)
//            usedProcesses.put(name,auxUsedProcesses.get(name));

//            for (String name:keys) {
//                HashSet<String> processSet = usedProcesses.get(name);
//                for (String called:calls.get(name))
//                processSet.addAll(usedProcesses.get(called));
//                auxUsedProcesses.put(name,processSet);
//            }
//        }
//
//        return usedProcesses;
//    }


        fun usedProcesses(n:Choreography):Map<String,Set<String>> {
            val calls = HashMap<String,Set<String>>()
            n.procedures.forEach { calls[it.procedure] = UsedProcedures.usedProcedures(it.choreography) }
            val oldUsedProcesses = HashMap<String,Set<String>>()
            val newUsedProcesses = HashMap<String,Set<String>>()
            n.procedures.forEach {
                newUsedProcesses[it.procedure] = freeProcessNames(it.choreography)
            }

            while( !oldUsedProcesses.equals(newUsedProcesses) ) {
                n.procedures.forEach { oldUsedProcesses[it.procedure] = newUsedProcesses[it.procedure]!! }
                n.procedures.forEach { procedure ->
                    calls[procedure.procedure]!!.forEach { call ->
                        try {
                            newUsedProcesses[procedure.procedure] = newUsedProcesses[procedure.procedure]!! + oldUsedProcesses[call]!!
                        } catch( e:kotlin.KotlinNullPointerException ){
                            e.printStackTrace()
                        }
                    }
                }
            }

            return oldUsedProcesses
        }
    }
}
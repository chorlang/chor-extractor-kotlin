package ast.sp.labels


sealed class ExtractionLabel(var flipped: Boolean = false){
    abstract fun copy(): ExtractionLabel

    sealed class ConditionLabel(open val process: String, open val expression: String) : ExtractionLabel(){

        class ThenLabel(override val process: String, override val expression: String) : ConditionLabel(process, expression){
            override fun copy() = ThenLabel(process, expression)

            override fun toString() = "if $process.$expression then "
        }
        class ElseLabel(override val process: String, override val expression: String) : ConditionLabel(process, expression){
            override fun copy() = ElseLabel(process, expression)

            override fun toString() = "if $process.$expression else "
        }
    }

    sealed class InteractionLabel(open val sender: String, open val receiver: String, open val expression: String) : ExtractionLabel(){
        class CommunicationLabel(override val sender: String, override val receiver: String, override val expression: String): InteractionLabel(sender, receiver, expression){
            override fun copy() = CommunicationLabel(sender, receiver, expression)

            override fun toString(): String = sender
        }
        class SelectionLabel(override val sender: String, override val receiver: String, val label: String): InteractionLabel(sender, receiver, label){
            override fun copy() = SelectionLabel(sender, receiver, expression)

            override fun toString() = "$sender->$receiver[$label]"
        }
    }

    class MulticomLabel(val labels: ArrayList<InteractionLabel>): ExtractionLabel(){
        override fun copy() = MulticomLabel(labels.map { it.copy() }.toList() as ArrayList<InteractionLabel>)

        override fun toString(): String = labels.joinToString(separator =  ", ", prefix = "(", postfix = ")")
    }
}
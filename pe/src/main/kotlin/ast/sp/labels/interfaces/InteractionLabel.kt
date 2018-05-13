package ast.sp.labels.interfaces

abstract class InteractionLabel(val snd: String, val rcv: String, val expr: String, flipped: Boolean) : ExtractionLabel(flipped) {
}

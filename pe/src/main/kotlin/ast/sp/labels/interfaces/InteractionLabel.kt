package ast.sp.labels.interfaces

abstract class InteractionLabel (sender: String, receiver: String, flipped: Boolean): ExtractionLabel(flipped) {
    open val sender = sender
    open val receiver = receiver
}

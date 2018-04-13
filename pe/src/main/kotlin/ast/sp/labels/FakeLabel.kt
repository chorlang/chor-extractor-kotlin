package ast.sp.labels

import ast.sp.labels.interfaces.ExtractionLabel

class FakeLabel(val procedure: String, val label: ExtractionLabel, flipped: Boolean): ExtractionLabel(flipped)

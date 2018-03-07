package ast.sp.labels

import ast.sp.interfaces.ExtractionLabel

class FakeLabel(val procedure: String, val label: ExtractionLabel, flipped: Boolean): ExtractionLabel(flipped)

package extraction

import ast.cc.interfaces.ChoreographyBody
import ast.cc.interfaces.Interaction
import ast.cc.nodes.*
import ast.sp.interfaces.Behaviour
import ast.sp.labels.ExtractionLabel
import ast.sp.labels.ExtractionLabel.*
import ast.sp.labels.ExtractionLabel.ConditionLabel.ElseLabel
import ast.sp.labels.ExtractionLabel.ConditionLabel.ThenLabel
import ast.sp.labels.ExtractionLabel.InteractionLabel.SelectionLabel
import ast.sp.labels.ExtractionLabel.InteractionLabel.SendingLabel
import ast.sp.nodes.*
import org.jgrapht.graph.DirectedPseudograph

typealias ProcessMap = HashMap<String, ProcessTerm>
typealias GraphNode = Pair<Network, InteractionLabel>
typealias Marking = HashMap<ProcessName, Boolean>
typealias NodeHash = Int

class NetworkExtraction {

}
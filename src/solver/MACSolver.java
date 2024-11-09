package solver;

import java.util.*;
import modelling.*;

public class MACSolver extends AbstractSolver{

    public MACSolver(Set<Variable> variables, Set<Constraint> constrain) {
        super(variables, constrain);
    }
 
    @Override
    public Map<Variable, Object> solve() {
        Map<Variable, Object> partialAssignment = new HashMap<>();
        LinkedList<Variable> unassignedVariables = new LinkedList<>(this.getVariables());
        Map<Variable, Set<Object> > variableDomains = new HashMap<>();
        for (Variable variable : this.getVariables()) {
            variableDomains.put(variable, new HashSet<>(variable.getDomain()));
        }
        Map<Variable, Object> solution = this.mac(partialAssignment, unassignedVariables, variableDomains);
        return solution;
    }

    private Map<Variable, Object> mac(Map<Variable, Object> InstantiationPartiel, LinkedList<Variable> variablesNonInstancier, Map<Variable, Set<Object>> domains) {
        if (variablesNonInstancier.isEmpty()) {
            return InstantiationPartiel;
        } 
        else {
            ArcConsistency arcConsistency = new ArcConsistency(this.getConstrain());
            if (!arcConsistency.ac1(domains)) {
                return null;
            }
            Variable xi = variablesNonInstancier.removeFirst();
            for (Object vi : domains.get(xi)) {
                Map<Variable, Object> nouvelInstantiation = new HashMap<>(InstantiationPartiel);
                nouvelInstantiation.put(xi, vi);
                if (this.isConsistent(nouvelInstantiation)) {
                    Map<Variable, Object> resultat = this.mac(nouvelInstantiation, variablesNonInstancier, domains);
                    if (resultat != null) {
                        return resultat;
                    }
                }
            }
            variablesNonInstancier.addFirst(xi);
            return null;
        }
    }
}

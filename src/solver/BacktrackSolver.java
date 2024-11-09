package solver;

import java.util.*;
import modelling.Constraint;
import modelling.Variable;

public class BacktrackSolver extends AbstractSolver{

    public BacktrackSolver(Set<Variable> variables, Set<Constraint> constrain) {
        super(variables, constrain);
    }

     @Override
    public Map<Variable, Object> solve() {
        Map<Variable, Object> instencePartiel = new HashMap<>();
        LinkedList<Variable> variableNonInstancier = new LinkedList<>(this.getVariables());
        Map<Variable, Object> solution = this.backTrack(instencePartiel, variableNonInstancier);
        return solution;
    }

    private Map<Variable, Object> backTrack(Map<Variable, Object> instencePartiel, LinkedList<Variable> variableNonInstancier) {
        if (variableNonInstancier.isEmpty()) {
            return instencePartiel;
        }
        Variable xi = variableNonInstancier.poll();
        for (Object vi : xi.getDomain()) {
            instencePartiel.put(xi, vi);
            if (this.isConsistent(instencePartiel)) {
                Map<Variable, Object> result = this.backTrack(instencePartiel, variableNonInstancier);
                if (result != null) {
                    return result; 
                }
            }
            instencePartiel.remove(xi);
        }
        variableNonInstancier.addFirst(xi);
        return null;
    }
    
    
}

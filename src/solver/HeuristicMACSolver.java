package solver;
import java.util.*;
import modelling.*;

public class HeuristicMACSolver extends AbstractSolver{

    private VariableHeuristic variableHeuristic;
    private ValueHeuristic valueHeuristic;

    public HeuristicMACSolver(Set<Variable> variables, Set<Constraint> constraints, VariableHeuristic variableHeuristic, ValueHeuristic valueHeuristic) {
        super(variables, constraints);
        this.variableHeuristic = variableHeuristic;
        this.valueHeuristic = valueHeuristic;
    }

    @Override
    public Map<Variable, Object> solve() {
        Map<Variable, Object> partialAssignment = new HashMap<>();
        LinkedList<Variable> unassignedVariables = new LinkedList<>(this.getVariables());
        Map<Variable, Set<Object>> variableDomains = new HashMap<>();
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
            Set<Variable> setNonInstancier = new HashSet<>(variablesNonInstancier); 
            Variable xi = this.variableHeuristic.best(setNonInstancier, domains);
            List<Object> valeurHeuristic = this.valueHeuristic.ordering(xi, domains.get(xi));
            //on retire xi car sinon ça pose problème lors de l'asignement d'une valeur a xi
            variablesNonInstancier.remove(xi);
            for (Object vi : valeurHeuristic) {
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
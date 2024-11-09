package solver;
import modelling.*;
import java.util.*;

public abstract class AbstractSolver implements Solver{

    private Set<Variable> variables;

    private Set<Constraint> constrain;

    public AbstractSolver(Set<Variable> variables,Set<Constraint> constrain) {
        this.variables = variables;
        this.constrain = constrain;
    }

    public boolean isConsistent(Map<Variable, Object> affectation) {
        for (Constraint constraint : this.constrain) {
           this.variables = constraint.getScope();
            if (affectation.keySet().containsAll(this.variables)) {
                if (!constraint.isSatisfiedBy(affectation)) {
                    return false;
                }
            }
        }
        return true;
    }
    
     public Set<Variable> getVariables() {
        return this.variables;
    }

    public Set<Constraint> getConstrain() {
        return this.constrain;
    }
}

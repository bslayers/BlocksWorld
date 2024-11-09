package solver;
import modelling.*;
import java.util.*;

public class NbConstraintsVariableHeuristic implements VariableHeuristic{

    private Set<Constraint> constraints;

    private boolean most; 
    
    public NbConstraintsVariableHeuristic(Set<Constraint> constraints, boolean most){
        this.constraints = constraints;
        this.most = most;
    }

    @Override
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domains) {
        Variable bestVariable = null; // On créer une variable qui stockera la meilleur variable ou null si aucune est trouvée
        int countVariable; //pour le nombres de contraintes qui sont associés a la meilleurs variable
        //selon si this.most est true ou false on initialise a la plus petite ou a la plus grande valeur possible
        if (this.most){
            countVariable = Integer.MIN_VALUE;
        } 
        else {
            countVariable = Integer.MAX_VALUE;
        }

        for (Variable variable : variables) {
            int countVariableActuel = 0; // pour compter le nombres de contrainte associé a la variable actuel

            for (Constraint constraint : this.constraints) {
                if (constraint.getScope().contains(variable)) {
                    countVariableActuel++;
                }
            }
            //selon this.most et la condition associé on met a jour la meilleurs variable et le meilleur compte
            if ((this.most && countVariableActuel > countVariable) || (!this.most && countVariableActuel < countVariable)) {
                countVariable = countVariableActuel;
                bestVariable = variable;
            }
        }
        return bestVariable;
    }
}

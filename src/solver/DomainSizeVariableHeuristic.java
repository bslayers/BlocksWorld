package solver;
import java.util.*;

import modelling.*;

public class DomainSizeVariableHeuristic implements VariableHeuristic{

    private boolean greatest;
    
    public DomainSizeVariableHeuristic(boolean greatest){
        this.greatest = greatest;
    }

    @Override
    public Variable best(Set<Variable> variables, Map<Variable, Set<Object>> domains) {
        Variable bestVariable = null; // On crée une variable qui stockera la meilleure variable ou null si aucune n'est trouvée
        int tailleDomaine; //pour la taille du domaine associé a la meilleure variable
        //selon si this.greatest est true ou false on initialise a la plus petite ou a la plus grande valeur possible
        if (this.greatest) {
            tailleDomaine = Integer.MIN_VALUE;
        } else {
            tailleDomaine = Integer.MAX_VALUE;
        }

        for (Variable variable : variables) {
            Set<Object> domain = domains.get(variable);

            if (domain != null) {
                int domainSize = domain.size();
                //selon this.greatest et la condition associé on met a jour la meilleur variable et la taille du domaine associé
                if ((this.greatest && domainSize > tailleDomaine) || (!this.greatest && domainSize < tailleDomaine)) {
                    tailleDomaine = domainSize;
                    bestVariable = variable;
                }
            }
        }
        return bestVariable;
    }


}

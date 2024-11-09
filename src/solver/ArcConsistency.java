package solver;
import java.util.*;

import modelling.*;

public class ArcConsistency {
    
    private Set<Constraint> contrainte;

    public ArcConsistency(Set<Constraint> contrainte){
        this.contrainte = contrainte;
    }

    public boolean enforceNodeConsistency(Map<Variable, Set<Object>> domains){
        Map<Variable, Set<Object>> deleteMap =  new HashMap<>();
        for (Variable v : domains.keySet()) {
            Set<Object> setToDelete = new HashSet<>();
            for (Object o :domains.get(v)){
                for (Constraint constrainte :  this.contrainte){
                    if (constrainte.getScope().size() == 1 && constrainte.getScope().contains(v)) {
                        Map<Variable, Object> node = new HashMap<>();
                        node.put(v, o);
                        if (!constrainte.isSatisfiedBy(node)) {
                          setToDelete.add(o);
                        }
                    }
                }
            }
            if (!setToDelete.isEmpty()) {
                deleteMap.put(v, setToDelete);
            }
        }
        for (Variable variable : deleteMap.keySet()) {
            domains.get(variable).removeAll(deleteMap.get(variable));
        }

        for (Variable variable : domains.keySet()) {
            if (domains.get(variable).isEmpty()) {
                return false;
            }
        }
        return true;
    }
 
    public boolean revise(Variable v1, Set<Object> d1,Variable v2, Set<Object> d2){
        boolean del = false;
        Set<Object> delete = new HashSet<>();

        for (Object vi : d1) {
            boolean viable = false;
            for (Object vj : d2) {
                boolean toutSatisfait = true;
                for (Constraint c : this.contrainte) {
                    Map<Variable, Object> n = new HashMap<>();
                    n.put(v1, vi);
                    n.put(v2, vj);

                    if (c.getScope().size() == 2 && c.getScope().containsAll(Set.of(v1, v2))){
                        if (!c.isSatisfiedBy(n)) {
                            toutSatisfait = false;
                            break;
                        }
                    }

                    
                }
                if (toutSatisfait) {
                    viable = true;
                    break;
                }
            }
            if (!viable) {
                delete.add(vi);
                del = true;
            }
        }
        d1.removeAll(delete);

        return del;
    }

    public boolean ac1(Map<Variable,Set<Object>> domains){
        if (!this.enforceNodeConsistency(domains)){
            return false;
        }
        
        boolean change;
        do{
            change = false;
            for (Variable var1 : domains.keySet()) {
                for (Variable var2 : domains.keySet()) {
                  if (!var1.equals(var2) && this.revise(var1, domains.get(var1), var2, domains.get(var2))) {
                    change = true;
                  }
                }
              }
        }while(change);

        for (Variable v : domains.keySet()){
            if (domains.get(v).isEmpty()){
                return false;
            }
        }
        return true;
    }
}

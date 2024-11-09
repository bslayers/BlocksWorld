package heuristique;
import modelling.Variable;

import java.util.*;
public class BasicGoal implements Goal{

    private Map<Variable, Object> instanciationPartiel;

    public BasicGoal(Map<Variable, Object> instanciationPartiel) {
        this.instanciationPartiel = instanciationPartiel;
    }

    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> etat) {
        for (Variable variable : this.instanciationPartiel.keySet()) {
            Object valeur = this.instanciationPartiel.get(variable);
            if (!etat.containsKey(variable) || !etat.get(variable).equals(valeur)) {
                return false;
            }
        }
        return true;
    }

}

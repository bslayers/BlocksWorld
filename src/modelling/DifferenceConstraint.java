package modelling;
import java.util.*;

public class DifferenceConstraint implements Constraint {

    private Variable v1;

    private Variable v2;
    
    public DifferenceConstraint(Variable v1, Variable v2){
        this.v1 = v1;
        this.v2 = v2;
    }

    @Override
    public Set<Variable> getScope(){
        Set<Variable> scope = new HashSet<>(); 
        scope.add(this.v1);
        scope.add(this.v2);
        return scope;
    }
    
    @Override
    public boolean isSatisfiedBy(Map<Variable, Object> instance) {
        if (!instance.containsKey(this.v1) || !instance.containsKey(this.v2)) {
            throw new IllegalArgumentException("Toutes les variables ne sont pas instanciées.");
        }
        Object valeur1 = instance.get(this.v1);
        Object valeur2 = instance.get(this.v2);
        return !valeur1.equals(valeur2);
    }

    public Variable getV1() {
        return this.v1;
    }

    public Variable getV2() {
        return this.v2;
    }

    @Override
    public String toString() {
        return "DifferenceConstraint: v1=" + this.v1 + ", v2=" + this.v2;
    }
    
}

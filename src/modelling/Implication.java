package modelling;
import java.util.*;

public class Implication implements Constraint{
   
    private Variable v1;
    private Set<Object> s1;
    private Variable v2;
    private Set<Object> s2;

    public Implication(Variable v1, Set<Object> s1, Variable v2, Set<Object> s2) {
        this.v1 = v1;
        this.s1 = s1;
        this.v2 = v2;
        this.s2 = s2;
    }

    @Override
    public Set<Variable> getScope() {
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
        return !this.s1.contains(valeur1) || this.s2.contains(valeur2);
    }

    public Variable getV1() {
        return this.v1;
    }

    public Set<Object> getS1() {
        return this.s1;
    }

    public Variable getV2() {
        return this.v2;
    }

    public Set<Object> getS2() {
        return this.s2;
    }
    
}

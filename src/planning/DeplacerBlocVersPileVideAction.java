package planning;

import modelling.Variable;
import java.util.HashMap;
import java.util.Map;
import blocksworld.BlocksWorldVariable;

public class DeplacerBlocVersPileVideAction extends BasicAction {
    private int b;
    private int p;

    public DeplacerBlocVersPileVideAction(BlocksWorldVariable blocksWorldVariable, int b, int p, int cost) {
        super(creerPreconditions(blocksWorldVariable, b, p), creerEffets(blocksWorldVariable, b, p), cost);
        this.b = b;
        this.p = p;
    }

    private static Map<Variable, Object> creerPreconditions(BlocksWorldVariable blocksWorldVariable, int b, int p) {
        Variable onb = new Variable("on" + b, blocksWorldVariable.domainBlock(b));
        Variable fixedb = new Variable("fixed" + b, blocksWorldVariable.booleanDomain());
        Variable freePilep = new Variable("free" + p, blocksWorldVariable.booleanDomain());

        Map<Variable, Object> precondition = new HashMap<>();
        precondition.put(onb, -(p + 1)); // pour que le bloc sois sur la pile
        precondition.put(fixedb, false);
        precondition.put(freePilep, true);

        return precondition;
    }

    private static Map<Variable, Object> creerEffets(BlocksWorldVariable blocksWorldVariable, int b, int p) {
        Variable onb = new Variable("on" + b, blocksWorldVariable.domainBlock(b));
        Variable fixedb = new Variable("fixed" + b, blocksWorldVariable.booleanDomain());
        Variable freePilep = new Variable("free" + p, blocksWorldVariable.booleanDomain());
        Map<Variable, Object> effets = new HashMap<>();
        effets.put(onb, p);
        effets.put(fixedb, false);
        effets.put(freePilep, false);
        return effets;
    }
    
    @Override
    public String toString() {
        return "Déplacer le bloc " + this.b + " vers la pile vide " + this.p;
    }
}
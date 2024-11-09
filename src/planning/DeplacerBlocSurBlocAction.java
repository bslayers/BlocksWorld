package planning;
import modelling.*;
import java.util.*;
import blocksworld.*;

public class DeplacerBlocSurBlocAction extends BasicAction {
    private int b;
    private int bPrime;

    public DeplacerBlocSurBlocAction(BlocksWorldVariable blocksWorldVariable, int b, int bPrime, int cost) {
        super(creerPreconditions(blocksWorldVariable, b, bPrime), creerEffets(blocksWorldVariable, b, bPrime), cost);
        this.b = b;
        this.bPrime = bPrime;
    }

    private static Map<Variable, Object> creerPreconditions(BlocksWorldVariable blocksWorldVariable, int b, int bPrime) {
        Variable onb = new Variable("on" + b, blocksWorldVariable.domainBlock(b));
        Variable onbPrime = new Variable("on" + bPrime, blocksWorldVariable.domainBlock(bPrime));
        Variable fixedb = new Variable("fixed" + b, blocksWorldVariable.booleanDomain());
        Variable fixedbPrime = new Variable("fixed" + bPrime, blocksWorldVariable.booleanDomain());
        Map<Variable, Object> precondition = new HashMap<>();
        precondition.put(onb, bPrime);
        precondition.put(onbPrime, b);
        precondition.put(fixedb, false);
        precondition.put(fixedbPrime, false);
        return precondition;
    }

    private static Map<Variable, Object> creerEffets(BlocksWorldVariable blocksWorldVariable, int b, int bPrime) {
        Variable onb = new Variable("on" + b, blocksWorldVariable.domainBlock(b));
        Variable fixedb = new Variable("fixed" + b, blocksWorldVariable.booleanDomain());
        Variable fixedbPrime = new Variable("fixed" + bPrime, blocksWorldVariable.booleanDomain());
        Map<Variable, Object> effets = new HashMap<>();
        effets.put(onb, bPrime);
        effets.put(fixedb, false);
        effets.put(fixedbPrime, true);
        return effets;
    }

    @Override
    public String toString() {
        return "Déplacer le bloc " + this.b + " sur le bloc " + this.bPrime;
    }
}
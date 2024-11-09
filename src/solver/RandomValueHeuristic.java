package solver;
import java.util.*;
import modelling.*;

public class RandomValueHeuristic implements ValueHeuristic {

    private Random random;

    public RandomValueHeuristic(Random random) {
        this.random = random;
    }

    @Override
    public List<Object> ordering(Variable variable, Set<Object> domain) {
        List<Object> domainList = new ArrayList<>(domain);
        Collections.shuffle(domainList, this.random);
        return domainList;
    }

    
}

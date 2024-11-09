package heuristique;
import java.util.*;
import modelling.Variable;
import planning.*;
public interface Planner {
    
    public List<Action> plan();

    public Map<Variable, Object> getInitialState();

    public Set<Action> getActions();

    public Goal getGoal();

}

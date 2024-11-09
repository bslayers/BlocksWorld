package heuristique;
import java.util.*;
import modelling.Variable;

public interface Heuristic {
    
    public float estimate(Map<Variable,Object> etat);
}

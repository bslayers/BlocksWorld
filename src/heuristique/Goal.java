package heuristique;
import modelling.Variable;
import java.util.*;

public interface Goal{

    public boolean isSatisfiedBy(Map<Variable,Object> etat);

}

package planning;
import java.util.*;

import modelling.Variable;

public interface Action{

    public boolean isApplicable(Map<Variable,Object> p);

    public Map<Variable, Object> successor(Map<Variable, Object> e);

    public int getCost();

}

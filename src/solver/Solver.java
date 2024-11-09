package solver;
import modelling.Variable;
import java.util.*;

public interface Solver {
    
    public Map<Variable,Object> solve();
    
}

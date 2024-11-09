package modelling;
import java.util.*;

public class BooleanVariable extends Variable{

    public BooleanVariable(String name){
        super(name,Set.of(true,false));
    }

    @Override
    public String toString() {
        return "BooleanVariable{name='" + this.getName() + "', domain=" + this.getDomain() + "}";
    }
}

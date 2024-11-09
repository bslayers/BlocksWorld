package blocksworld;
import modelling.Variable;

public class Block {
    private Variable variable;
    private boolean fixed;

    public Block(Variable variable, boolean fixed){
        this.variable = variable;
        this.fixed = fixed;
    }

    public Block(Variable variable){
        this(variable, false);
    }

    public Variable getVariable() {
        return this.variable;
    }

    public String getVariableName(){
        return this.variable.getName();
    }
  
    public boolean isFixed() {
        return this.fixed;
    }

    public void setFixed(boolean fixed) {
        this.fixed = fixed;
    }
}
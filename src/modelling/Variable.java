package modelling;

import java.util.*;

public class Variable{

    private String name;

    private Set<Object> domain;

    public Variable(String name, Set<Object> domain){
        this.name = name;
        this.domain = domain;
    }

    public String getName(){
        return this.name;
    }

    public Set<Object> getDomain(){
        return this.domain;
    }

    @Override
    public boolean equals(Object o){
        if (this == o){
            return true;
        }
        if (o == null){
            return false;
        }
        Variable variable = (Variable) o;
        return Objects.equals(this.name, variable.name);
    }

    @Override
    public int hashCode(){
        return this.name.hashCode();

    }
    
    @Override
    public String toString() {
        return "Variable{name='" + this.name + "', domain=" + this.domain + "}";
    }

}

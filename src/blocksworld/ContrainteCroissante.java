package blocksworld;
import java.util.*;
import modelling.*;

public class ContrainteCroissante {
    private Set<Constraint> constraint;
    private BlockWorldConstraint blockWorldConstraint;

    public ContrainteCroissante(BlockWorldConstraint blockWorldConstraint) {
        this.constraint = new HashSet<>();
        this.blockWorldConstraint = blockWorldConstraint;
    }

    public void creationsContrainteCroissante() {
        int blocks = this.blockWorldConstraint.getBlocks();
        for (int i = 0; i < blocks; i++) {
            for (int j = i + 1; j < blocks; j++) {
                this.contrainte(blocks,i, j);
            }
        }
    }

    public void contrainte(int blocksI, int blocki1, int blocki2) {
        Variable onbi = this.creeVariable("on", blocki1);
        Variable onbj = this.creeVariable("on", blocki2);
        Variable freebi = new BooleanVariable("free" + blocki1);
        Variable freebj = new BooleanVariable("free" + blocki2);
    
        if (blocki2 > blocki1){
            Set<Object> s1 = new HashSet<>();
            s1.add(blocksI);
            // Met la contrainte à true, ce qui signifie que le bloc est libre
            Set<Object> s2 = new HashSet<>();
            s2.add(true);
            this.ajouterImplication(onbi, s1, freebi, s2);
            this.ajouterImplication(onbj, s1, freebj, s2);
        }
    }
    
    //créer une variable
    private Variable creeVariable(String nom, int i) {
        return new Variable(nom + i, this.domainBLock(i));
    }

    //ajoute dans this.constraint une implication
    public void ajouterImplication(Variable v1, Set<Object> s1, Variable v2, Set<Object> s2) {
        this.constraint.add(new Implication(v1, s1, v2, s2));
    }


    //mit en private pour etre sur de pas causer de problème dans les autre class
    private Set<Object> domainBLock(int blocki) {
        int blocks = this.blockWorldConstraint.getBlocks();
        Set<Object> domain = new HashSet<>();
        //i est ajouté de façon negative pour représenter les piles
        for (int i = 1; i <= blocks; i++) {
            domain.add(-i);
        }
        //i est cette fois positifs ou nul pour représenter les blocs
        for (int i = 0; i < blocks; i++) {
            if (i != blocki) {
                domain.add(i);
            }
        }
        return domain;
    }

    public int getBlocks() {
        return this.blockWorldConstraint.getBlocks();
    }

    public Set<Constraint> getConstrainte() {
        return this.constraint;
    }

    @Override
    public String toString() {
        String result = "Contraintes Croissante :\n";
    
        for (Constraint c : this.constraint) {
            if (c instanceof Implication implication) {
                result += "Implication Constraint: v1=" + implication.getV1() +", s1=" + implication.getS1() +", v2=" + implication.getV2() +", s2=" + implication.getS2() + "\n";
            } 
            else if (c instanceof DifferenceConstraint differenceConstraint) {
                result += "Difference Constraint: v1=" + differenceConstraint.getV1() +", v2=" + differenceConstraint.getV2() + "\n";
            } 
        }
    
        return result;
    }
}

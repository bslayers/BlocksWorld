package blocksworld;
import java.util.*;
import modelling.*;

public class ContrainteReguliaire {
    private Set<Constraint> constraint;
    private BlockWorldConstraint blockWorldConstraint;

    public ContrainteReguliaire(BlockWorldConstraint blockWorldConstraint) {
        this.constraint = new HashSet<>();
        this.blockWorldConstraint = blockWorldConstraint;
    }

    //Créer les contrainte réguliaire
    public void creationsContrainteReguliaire() {
        int piles = this.blockWorldConstraint.getPiles();
        for (int i = 0; i < piles; i++) {
            this.contrainteEcartPile(i);
        }
    }
    
    //Pour chaque pair de blocs dans une pile on créer une contrainte d'implication entre les 2 blocs pour assurer que l'ecart
    //entre les numéros des 2 blocs sont consécutifs
    public void contrainteEcartPile(int pileI) {
        int piles = this.blockWorldConstraint.getPiles();
        Variable[] variables = new Variable[piles];
        for (int i = 0; i < piles; i++) {
            variables[i] = this.creeVariable("on", i);
        }
        
        for (int i = 0; i < piles; i++) {
            for (int j = i + 1; j < piles; j++) {
                this.ecartContrainte(pileI, variables[i], variables[j]);
            }
        }
    }

    public void ecartContrainte(int pileI, Variable onbi, Variable onbj) {
        Set<Object> s1 = new HashSet<>();
        s1.add(onbi);
        s1.add(onbj);
        s1.add(pileI);
        Set<Object> s2 = new HashSet<>();
        s2.add(true);
        if (!s1.isEmpty() && !s2.isEmpty()) {
            this.ajouterImplication(onbi, s1, onbj, s2);
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
        Set<Object> domain = new HashSet<>();
        //i est ajouté de façon negative pour représenter les piles
        for (int i = 1; i <= this.blockWorldConstraint.getPiles(); i++) {
            domain.add(-i);
        }
        //i est cette fois positifs ou nul pour représenter les blocs
        for (int i = 0; i < this.blockWorldConstraint.getPiles(); i++) {
            if (i != blocki) {
                domain.add(i);
            }
        }
        return domain;
    }

    public Set<Constraint> getConstrainte() {
        return this.constraint;
    }

    @Override
    public String toString() {
        String result = "Contraintes Régulières :\n";
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
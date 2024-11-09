package blocksworld;
import java.util.*;
import modelling.*;

public class BlockWorldConstraint {
    private int blocks;
    private int piles;
    public Set<Constraint> constraints;

    public BlockWorldConstraint(int blocks, int piles){
        this.blocks = blocks;
        this.piles = piles;
        this.constraints = new HashSet<Constraint>();
        this.contrainte();
    }

    public void contrainte() {
        this.blocsDifferentsConstraint();
        this.blocsFixedConstraint();
        this.blocsFreeConstraint();
    }

    public void blocsDifferentsConstraint() {
        for (int i = 0; i < this.blocks; i++) {
            for (int j = i + 1; j < this.blocks; j++) {
                Variable onbi = new Variable("on" + i, this.domainBlock(i));
                Variable onbj = new Variable("on" + j, this.domainBlock(j));
                // on ajoute une DifferenceConstraint a contraint pour etre sur que les 2 variables n'est pas la meme valeur
                this.constraints.add(new DifferenceConstraint(onbi, onbj));
            }
        }
    }

    public void blocsFixedConstraint() {
        for (int i = 0; i < this.blocks; i++) {
            Variable onbi = new Variable("on" + i, this.domainBlock(i));
            Variable fixedbi = new BooleanVariable("fixed" + i);
            Set<Object> s1 = new HashSet<>();
            s1.add(i);

            Set<Object> s2 = new HashSet<>();
            // Mis à true car indeplaçable
            s2.add(true);

            this.constraints.add(new Implication(onbi, s1, fixedbi, s2));
        }
    }

    public void blocsFreeConstraint() {
        for (int i = 0; i < this.blocks; i++) {
            Variable onbi = new Variable("on" + i, this.domainBlock(i));

            for (int j = 0; j < this.piles; j++) {
                Variable freepj = new BooleanVariable("free" + j);
                Set<Object> s1 = new HashSet<>();
                //met une valeur qui correspond a -(p+1)
                s1.add(-(j + 1));
                //donc mit a faux
                Set<Object> s2 = new HashSet<>();
                s2.add(false);
    
                this.constraints.add(new Implication(onbi, s1, freepj, s2));
            }
        }
    }

    public Set<Object> domainBlock(int blocki) {
        Set<Object> domain = new HashSet<>();
        // i est ajouté de façon négative pour représenter les piles
        for (int i = 1; i <= this.piles; i++) {
            domain.add(-i);
        }

        // i est cette fois positif ou nul pour représenter les blocs
        for (int i = 0; i < this.blocks; i++) {
            if (i != blocki) {
                domain.add(i);
            }
        }

        return domain;
    }

    public void ajouterContrainte(Constraint constraint){
        this.constraints.add(constraint);
    }

    public Set<Constraint> getConstrainte(){
        return this.constraints;
    }

    public int getDomainValue(Variable variable) {
        Set<Object> domain = variable.getDomain();
        for (Object value : domain) {
            if (value instanceof Integer) {
                return (Integer) value;
            }
        }
        return 0;
    }

    public List<Variable> getVariablesDomain(int pile) { 
        List<Variable> variablesInDomain = new ArrayList<>(); 
        for (Constraint c : this.constraints) { 
            if (c instanceof Implication implication) { 
                Variable v1 = implication.getV1(); 
                Set<Object> s1 = implication.getS1(); 
                Variable v2 = implication.getV2(); 
                Set<Object> s2 = implication.getS2(); 
                if (s1.contains(pile)) { 
                    variablesInDomain.add(v1); 
                } 
                if (s2.contains(pile)) { 
                    variablesInDomain.add(v2); 
                } 
            } 
        } 
        return variablesInDomain; 
    }

    public int getBlocks() {
        return this.blocks;
    }

    public int getPiles() {
        return this.piles;
    }
    
    public Set<Object> getDomain(Variable variable) {
        return variable.getDomain();
    }

    @Override
    public String toString() {
        String ch = "Contraintes BlockWorldConstraint :\n";

        for (Constraint constraint : this.constraints) {
            ch += "Contrainte : ";
            
            if (constraint instanceof DifferenceConstraint diffConstraint) {
                ch += "DifferenceConstraint: v1=" + diffConstraint.getV1() + ", v2=" + diffConstraint.getV2() + "\n";
            } 
            else if (constraint instanceof Implication implication) {
                ch += "Implication: v1=" + implication.getV1() + ", s1=" + implication.getS1() +", v2=" + implication.getV2() + ", s2=" + implication.getS2() + "\n";
            } 
            else {
                ch += " " + constraint + "\n";
            }
        }

        return ch;
    }
}

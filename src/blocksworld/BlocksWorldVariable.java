package blocksworld;
import java.util.*;
import modelling.*;

public class BlocksWorldVariable {
    private int blocks;
    private int piles;
    private Set<Block> domains;
    private Set<Set<Block>> pile;
    private Map<String, Block> domainMap;
    private Map<Block, Variable> variableMap;

    public BlocksWorldVariable(int blocks, int piles) {
        this.blocks = blocks;
        this.piles = piles;
        this.domains = new HashSet<>();
        this.pile = new HashSet<>();
        this.domainMap = new HashMap<>();
        this.variableMap = new HashMap<>();
    }

    public void ajouterVariable(Block variable) {
        this.domains.add(variable);
        this.domainMap.put(variable.getVariableName(), variable);
        this.variableMap.put(variable, variable.getVariable());
    }

 
    public Set<Block> ajouterTout() {
        this.ajouterToutBlock();
        this.ajouterToutesPiles();
        return this.domains;
    }
    
    // ajoute le nombre de block mis en paramètre
    public void ajouterToutBlock() {
        for (int i = 0; i < this.blocks; i++) {
            Variable onVariable = new Variable("on" + i, this.domainBlock(i));
            Variable fixedVariable = new Variable("fixed" + i, this.booleanDomain());
            this.ajouterVariable(new Block(onVariable, false));
            this.ajouterVariable(new Block(fixedVariable, true));
        }
    }

    // même chose mais pour les piles
    public void ajouterToutesPiles() {
        Set<Block> pileBlocks = new HashSet<>();
        for (int i = 0; i < this.piles; i++) {
            // on ajoute un HashSet<Block> pour représenter les blocs de la pile qu'on est en train de faire
            this.pile.add(pileBlocks);
            // les piles sont mal faites
            this.ajouterVariable(new Block(new Variable("free" + i, this.booleanDomain()), false));
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
    
    public Set<Object> booleanDomain() {
        Set<Object> domain = new HashSet<>();
        domain.add(true);
        domain.add(false);
        return domain;
    }

    public Block getBlock(Variable variable) {
        return this.domainMap.get(variable.getName());
    }

    // Récupère les valeurs de domaine de la variable donnée
    public Variable getVariable(Block block) {
        return this.variableMap.get(block);
    }

    //Permet de récuperer le dommaine d'une variable
    public Set<Object> getDomainValue(Variable variable) {
        return variable.getDomain();
    }

    public int getNbBlock() {
        return this.blocks;
    }

    public int getNbPile() {
        return this.piles;
    }

    public Set<Variable> getDomainsVariable() {
        Set<Variable> variableDomains = new HashSet<>();
        for (Block block : this.domains) {
            variableDomains.add(block.getVariable());
        }
        return variableDomains;
    }

    @Override
    public String toString() {
        String ch = "Voici les domaines : ";
        for (Block domain : this.domains) {
            ch += " " + domain.getVariableName();
        }
        ch += "\n";
        for (Block block : this.domains) {
            ch += "Variable: " + block.getVariableName();
            ch += ", Valeur: ";
            if (block.isFixed()) {
                ch += "Fixé à " + block.getVariable().getDomain();
            } else {
                ch += "Non fixé";
            }
            ch += "\n";
        }
        return ch;
    }
}
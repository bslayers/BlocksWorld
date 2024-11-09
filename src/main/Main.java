package main;
import modelling.*;
import java.util.*;
import blocksworld.*;
//import bwmodel.*;
//import bwui.*;
//javac -d build .\src\blocksworld\*.java .\src\modelling\*.java .\src\planning\*.java .\src\main\*.java
//javac -d build src/blocksworld/*.java src/modelling/*.java src/planning/*.java src/heuristique/*.java src/solver/*.java src/main/*.java
//java -cp build Main
//javac -cp .:lib/blocksworld.jar src/blocksworld/*.java src/modelling/*.java src/planning/*.java src/heuristique/*.java src/solver/*.java src/main/*.java -d build 

public class Main {

    public static void main(String[] args) {

        BlocksWorldVariable blocksWorldVariable = new BlocksWorldVariable(3, 3);
        blocksWorldVariable.ajouterTout();
        System.out.println(blocksWorldVariable.toString());
        
        BlockWorldConstraint blockWorldConstraint = new BlockWorldConstraint(3, 3);
        System.out.println(blockWorldConstraint.toString());

        ContrainteReguliaire contrainteReguliaire = new ContrainteReguliaire(blockWorldConstraint);
        contrainteReguliaire.creationsContrainteReguliaire();
        System.out.println(contrainteReguliaire.toString());
        boolean estReguliere = testReguliere(blockWorldConstraint, contrainteReguliaire);
        System.out.println("\nEst régulière : " + estReguliere);

        ContrainteCroissante contrainteCroissante = new ContrainteCroissante(blockWorldConstraint);
        contrainteCroissante.creationsContrainteCroissante();
        System.out.println(contrainteCroissante.toString());
        boolean satisfaitContraintesCroissantes = testContraintesCroissantes(contrainteCroissante);
        System.out.println("Satisfait les contraintes croissantes : " + satisfaitContraintesCroissantes);
    }

    private static boolean testReguliere(BlockWorldConstraint blockWorldConstraint, ContrainteReguliaire contrainteReguliaire) {
        int piles = blockWorldConstraint.getPiles();
        for (int pile = 0; pile < piles; pile++) {
            List<Variable> variablesInPile = blockWorldConstraint.getVariablesDomain(pile);
            if (variablesInPile.size() > 1) {
                Variable firstBlock = variablesInPile.get(0);
                int firstBlockValue = blockWorldConstraint.getDomainValue(firstBlock);
                for (int i = 1; i < variablesInPile.size(); i++) {
                    Variable nextBlock = variablesInPile.get(i);
                    int nextBlockValue = blockWorldConstraint.getDomainValue(nextBlock);
                    if (Math.abs(nextBlockValue - firstBlockValue) != Math.abs(i - 1)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private static boolean testContraintesCroissantes(ContrainteCroissante contrainteCroissante) {
        Set<Constraint> contraintesCroissantes = contrainteCroissante.getConstrainte();
        for (Constraint c : contraintesCroissantes) {
            if (c instanceof Implication implication) {
                Set<Object> s1 = implication.getS1();
                Set<Object> s2 = implication.getS2();
                if (!s1.isEmpty() && !s2.isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }
}

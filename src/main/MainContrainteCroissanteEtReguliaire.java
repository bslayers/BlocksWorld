package main;
import modelling.*;
import solver.*;
import blocksworld.*;
import java.util.*;

public class MainContrainteCroissanteEtReguliaire {
    public static void main(String[] args) {
        BlocksWorldVariable blocksWorldVariable = new BlocksWorldVariable(3, 3);
        blocksWorldVariable.ajouterTout();
        System.out.println("Contrainte Croissante et Réguliaire:");
        BlockWorldConstraint constraints = new BlockWorldConstraint(blocksWorldVariable.getNbBlock(), blocksWorldVariable.getNbPile());
        ContrainteReguliaire contrainteReguliaire = new ContrainteReguliaire(constraints);
        contrainteReguliaire.creationsContrainteReguliaire();
        ContrainteCroissante contrainteCroissante = new ContrainteCroissante(constraints);
        contrainteCroissante.creationsContrainteCroissante();
        Set<Constraint> combinedConstraints = new HashSet<>();
        combinedConstraints.addAll(contrainteReguliaire.getConstrainte());
        combinedConstraints.addAll(contrainteCroissante.getConstrainte());
        String result = ""; 
        for (Constraint c : combinedConstraints) { 
            if (c instanceof Implication implication) { 
                result += "Implication Constraint: v1=" + implication.getV1() +", s1=" + implication.getS1() +", v2=" + implication.getV2() +", s2=" + implication.getS2() + "\n"; 
            }  
            else if (c instanceof DifferenceConstraint differenceConstraint) { 
                result += "Difference Constraint: v1=" + differenceConstraint.getV1() +", v2=" + differenceConstraint.getV2() + "\n"; 
            }  
        } 
        System.out.println(result); 
        System.out.println("================================");

        Solver macSolver = new MACSolver(blocksWorldVariable.getDomainsVariable(), combinedConstraints);
        System.out.println("Solveur : MACSolver");
        printsolver(macSolver);

        Solver backtrackSolver = new BacktrackSolver(blocksWorldVariable.getDomainsVariable(), combinedConstraints);
        System.out.println("Solveur : BacktrackSolver");
        printsolver(backtrackSolver);

        VariableHeuristic variableHeuristic = new DomainSizeVariableHeuristic(true);
        ValueHeuristic valueHeuristic = new RandomValueHeuristic(new Random());
        Solver heuristicMACSolver = new HeuristicMACSolver(blocksWorldVariable.getDomainsVariable(), combinedConstraints, variableHeuristic, valueHeuristic);
        System.out.println("Solveur : HeuristicMACSolver");
        printsolver(heuristicMACSolver);
    }

    public static void printsolver(Solver solver) {
        long debutTemps = System.currentTimeMillis();
        Map<Variable, Object> solution = solver.solve();
        long finTemps= System.currentTimeMillis();
        System.out.println("Temps : " + (finTemps - debutTemps) + " ms");
        if (solution != null) {
            System.out.println("Solution trouvée :");
            System.out.println(solution);
        } else {
            System.out.println("Aucune solution trouvée.");
        }
        System.out.println("================================");
    }
}

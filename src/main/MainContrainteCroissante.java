package main;

import modelling.*;
import solver.*;
import blocksworld.*;
import java.util.*;

public class MainContrainteCroissante {
    public static void main(String[] args) {
        BlocksWorldVariable blocksWorldVariable = new BlocksWorldVariable(3, 3);
        blocksWorldVariable.ajouterTout();
        BlockWorldConstraint constraints = new BlockWorldConstraint(blocksWorldVariable.getNbBlock(), blocksWorldVariable.getNbPile());
        ContrainteCroissante contrainteCroissante = new ContrainteCroissante(constraints);
        contrainteCroissante.creationsContrainteCroissante();
        System.out.println(contrainteCroissante.toString());
        System.out.println("================================");

        Solver macSolver = new MACSolver(blocksWorldVariable.getDomainsVariable(), contrainteCroissante.getConstrainte());
        System.out.println("Solveur : MACSolver");
        printsolver(macSolver);

        Solver backtrackSolver = new BacktrackSolver(blocksWorldVariable.getDomainsVariable(), contrainteCroissante.getConstrainte());
        System.out.println("Solveur : BacktrackSolver");
        printsolver(backtrackSolver);
        
        VariableHeuristic variableHeuristic = new DomainSizeVariableHeuristic(true);
        ValueHeuristic valueHeuristic = new RandomValueHeuristic(new Random());
        Solver heuristicMACSolver = new HeuristicMACSolver(blocksWorldVariable.getDomainsVariable(), contrainteCroissante.getConstrainte(), variableHeuristic, valueHeuristic);
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
package main;
import modelling.*;
import java.util.*;
import heuristique.*;
import blocksworld.*;
import planning.*;
//javac -d build .\src\blocksworld\*.java .\src\modelling\*.java .\src\planning\*.java .\src\main\*.java
//javac -d build src/blocksworld/*.java src/modelling/*.java src/planning/*.java src/main/*.java 
//java -cp build Main

public class MainPlanner {

    public static void main(String[] args) {
        BlockWorldConstraint blockWorldConstraint = new BlockWorldConstraint(3, 3);
        System.out.println(blockWorldConstraint.toString());
        Planificateur planificateur = new Planificateur(blockWorldConstraint.getBlocks(), blockWorldConstraint.getPiles());
        Set<Action> actions = planificateur.genererActions();
        System.out.println("Actions générées :");
        for (Action action : actions) {
            System.out.println(action);
        }
        Map<Variable, Object> etatInitial = new HashMap<>();
        etatInitial.put(new Variable("on0", new HashSet<>(Arrays.asList(1))), 0);
        etatInitial.put(new Variable("on1", new HashSet<>(Arrays.asList(2))), 1);
        etatInitial.put(new Variable("free0", new HashSet<>(Arrays.asList(false))), true);
        etatInitial.put(new Variable("free1", new HashSet<>(Arrays.asList(false))), true);

        Map<Variable, Object> etatFinal = new HashMap<>();
        etatFinal.put(new Variable("on0", new HashSet<>(Arrays.asList(1))), 2);
        etatFinal.put(new Variable("on1", new HashSet<>(Arrays.asList(2))), 3);

        Map<Variable, Object> instanciationPartiel = new HashMap<>();
        instanciationPartiel.put(new Variable("on0", new HashSet<>(Arrays.asList(1))), 2);
        instanciationPartiel.put(new Variable("on1", new HashSet<>(Arrays.asList(2))), 3);
        Goal but = new BasicGoal(instanciationPartiel);

        AStarPlanner aStarPlannerDivision = new AStarPlanner(etatInitial, actions, but, new DivisionHeuristic(etatFinal,blockWorldConstraint.getPiles()));
        aStarPlannerDivision.activateNodeCount(true);
        // analyse le temps que ça prends pour trouver un plan
        long debutAStar = System.currentTimeMillis();
        List<Action> planAStar = aStarPlannerDivision.plan();
        long finAStar = System.currentTimeMillis();

        System.out.println("Planificateur A* MalPlacerHeuristic:");
        System.out.println("Temps de calcul : " + (finAStar - debutAStar) + " ms");
        System.out.println("Nombre de noeuds explorés : " + aStarPlannerDivision.getNbNoeudExplorer());
        if (planAStar != null) {
            System.out.println("Plan trouvé :");
            for (Action action : planAStar) {
                System.out.println(action);
            }
        } 
        else {
            System.out.println("Aucun plan trouvé.");
        }
    }

}
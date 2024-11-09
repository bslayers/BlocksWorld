package heuristique;

import java.util.*;
import modelling.Variable;

public class DivisionHeuristic implements Heuristic {
    private Map<Variable,Object> but;
    private int nbPiles;

    public DivisionHeuristic(Map<Variable,Object> but, int nbPiles) {
        this.but = but;
        this.nbPiles = nbPiles;
    }

    public float estimate(Map<Variable,Object> state) {
        float count = 0;
        for (Variable variable : state.keySet()) {
            Object valeur = state.get(variable);
            if (valeur instanceof Integer) {
                int positionActuel = (int) valeur;
                int positionFinal = (int) this.but.get(variable);
                int xActuel = positionActuel % this.nbPiles;
                int yActuel = positionActuel / this.nbPiles;
                int x = positionFinal % this.nbPiles;
                int y = positionFinal / this.nbPiles;
                count += Math.sqrt(Math.pow(xActuel - x, 2) + Math.pow(yActuel - y, 2)); // calcule de la distance euclidienne
            }
        }
        return count;
    }
}
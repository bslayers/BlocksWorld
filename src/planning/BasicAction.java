package planning;
import java.util.*;
import modelling.Variable;

public class BasicAction implements Action{

    private Map<Variable, Object> precondition;

    private Map<Variable, Object> effect;

    private int cost;

    public BasicAction(Map<Variable, Object> precondition, Map<Variable, Object> effect,int cost){
        this.precondition = precondition;
        this.effect = effect;
        this.cost = cost;
    }

    @Override
    public boolean isApplicable(Map<Variable,Object> etat){
        for (Variable variable : this.precondition.keySet()) { // permet de récupérer la clé de la précondition
            Object valeur = this.precondition.get(variable); // récupère la valeur
            if (!etat.containsKey(variable) || !etat.get(variable).equals(valeur)) { //permet de vérifier si la l'état actuel n'a pas la clé ou la valleur !=précondition
                return false;
            }
        }
        return true;
    }

    @Override
    public Map<Variable, Object> successor(Map<Variable, Object> etat){
        Map<Variable, Object> succ = new HashMap<>(etat); // création d'un nouvel état pour avoir action suivante
        for (Variable variable : this.effect.keySet()) {
            Object valeur = this.effect.get(variable);
            succ.put(variable, valeur);
        }
        return succ;

    }

    public int getCost(){
        return this.cost;
    }

}

package heuristique;
import java.util.*;
import modelling.Variable;
import planning.*;


public class AStarPlanner implements Planner {

    private Map<Variable, Object> initialState;

    private Set<Action> actions;

    private Goal but;

    private Heuristic heuristic;

    private boolean nodeCountingEnabled = false;

    private int nbNoeudExplorer = 0;

    public AStarPlanner(Map<Variable, Object> initialState, Set<Action> actions, Goal but, Heuristic heuristic) {
        this.initialState = initialState;
        this.actions = actions;
        this.but = but;
        this.heuristic = heuristic;
    }

    public void activateNodeCount(boolean activate) {
        this.nodeCountingEnabled = activate;
    }

    public int getNbNoeudExplorer() {
        return this.nbNoeudExplorer;
    }

    @Override
    public List<Action> plan() {
        Map<Map<Variable, Object>, Action> plan = new HashMap<>();
        Map<Map<Variable, Object>, Map<Variable, Object>> father = new HashMap<>();
        Map<Map<Variable, Object>, Float> distance = new HashMap<>();
        Map<Map<Variable, Object>, Float> value = new HashMap<>();
        Set<Map<Variable, Object>> open = new HashSet<>();

        open.add(this.initialState);
        father.put(this.initialState, null);
        distance.put(this.initialState, 0f);
        value.put(this.initialState, this.heuristic.estimate(this.initialState));

        while (!open.isEmpty()) {
            Map<Variable, Object> instantiation = this.argMin(open, value);

            if (this.nodeCountingEnabled) {
                this.nbNoeudExplorer++;
            }

            if (this.but.isSatisfiedBy(instantiation)) {
                return this.getBFSPlan(plan, instantiation, father);
            }
            open.remove(instantiation);
            for (Action action : this.actions) {
                if (action.isApplicable(instantiation)) {
                    Map<Variable, Object> next = action.successor(instantiation);
                    if (!distance.containsKey(next) || distance.get(next) == Float.POSITIVE_INFINITY) {
                        distance.put(next, Float.POSITIVE_INFINITY);
                    }
                    if (distance.get(instantiation) + action.getCost() < distance.get(next)) {
                        distance.put(next, distance.get(instantiation) + action.getCost());
                        value.put(next, distance.get(next) + this.heuristic.estimate(next));
                        father.put(next, instantiation);
                        plan.put(next, action);
                        open.add(next);
                    }
                }
            }
        }
        return null;
    }

    private Map<Variable, Object> argMin(Set<Map<Variable, Object>> open, Map<Map<Variable, Object>, Float> distance) {
        float min = Float.MAX_VALUE;
        Map<Variable, Object> instantiation = null;
        for (Map<Variable, Object> node : open) {
            float cout = distance.get(node);
            if (cout < min) {
                instantiation = node;
                min = cout;
            }
        }
        return instantiation;
    }

    private List<Action> getBFSPlan(Map<Map<Variable, Object>, Action> plan, Map<Variable, Object> but, Map<Map<Variable, Object>, Map<Variable, Object>> father) {
        List<Action> bfsPlan = new ArrayList<>();
        Map<Variable, Object> etatActuel = but;
        while (plan.containsKey(etatActuel)) {
            Action action = plan.get(etatActuel);
            bfsPlan.add(action);
            etatActuel = father.get(etatActuel);
        }
        Collections.reverse(bfsPlan);
        return bfsPlan;
    }

    @Override
    public Map<Variable, Object> getInitialState() {
        return this.initialState;
    }

    @Override
    public Set<Action> getActions() {
        return this.actions;
    }

    @Override
    public Goal getGoal() {
        return this.but;
    }
}
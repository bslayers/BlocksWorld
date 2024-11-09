package blocksworld;

import planning.*;
import java.util.HashSet;
import java.util.Set;

public class Planificateur {
    private BlocksWorldVariable blocksWorldVariable;

    public Planificateur(int nbBlocs, int nbPiles) {
        this.blocksWorldVariable = new BlocksWorldVariable(nbBlocs, nbPiles);
    }

    public Set<Action> genererActions() {
        Set<Action> actions = new HashSet<>();
        int nbBlocs = this.blocksWorldVariable.getNbBlock();
        int nbPiles = this.blocksWorldVariable.getNbPile();

        for (int bloc = 0; bloc < nbBlocs; bloc++) {
            for (int autreBloc = 0; autreBloc < nbBlocs; autreBloc++) {
                if (bloc != autreBloc) {
                    actions.add(new DeplacerBlocSurBlocAction(this.blocksWorldVariable, bloc, autreBloc, 1));
                    actions.add(new DeplacerBlocSurPileAction(this.blocksWorldVariable, bloc, autreBloc, 1));
                }
            }

            for (int pile = 0; pile < nbPiles; pile++) {
                actions.add(new DeplacerBlocSousPileAction(this.blocksWorldVariable, bloc, pile, 1));
                actions.add(new DeplacerBlocVersPileVideAction(this.blocksWorldVariable, bloc, pile, 1));
            }
        }

        return actions;
    }

    @Override
    public String toString() {
        String ch = "Planificateur\n";
        ch += "Nombre de blocs : " + this.blocksWorldVariable.getNbBlock() + "\n";
        ch += "Nombre de piles : " + this.blocksWorldVariable.getNbPile() + "\n";
        ch += "Actions générées :\n";

        Set<Action> actions = this.genererActions();
        for (Action action : actions) {
            ch += action.toString() + "\n";
        }

        return ch;
    }
}
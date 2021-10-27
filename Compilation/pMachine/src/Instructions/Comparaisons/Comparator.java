package Instructions.Comparaisons;

import Instructions.Instruction;
import Machine.PMachine;

/**
 * Classe abstraite qui implémente un comparateur pour la PMachine
 *
 * @author jordan
 * @since 27-03-2020
 */
public abstract class Comparator implements Instruction {

    /**
     * Fonction requise pour utiliser l'interface {@link Instruction}
     *
     * @param pMachine la machine qui interprète le langage pascal
     */
    @Override
    public void exec(PMachine pMachine) {
        int x = pMachine.pop();
        int y = pMachine.pop();

        pMachine.push(binaryComparator(x,y) ?1:0);
        pMachine.next();
    }

    /**
     * Méthode abstraite qui compare deux entiers et
     * retourne un booléen
     *
     * @param x le première entier
     * @param y le second entier
     * @return le résultat de la comparaison
     */
    public abstract boolean binaryComparator(int x, int y);
}

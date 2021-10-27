package Instructions.Operations;

import Instructions.Instruction;
import Machine.PMachine;


/**
 * Classe abstraite qui implémente un opérateur binaire pour la PMachine
 *
 * @author jordan
 * @since 27-03-2020
 */
public abstract class BinaryOperation implements Instruction {

    /**
     * Fonction requise pour utiliser l'interface {@link Instruction}
     *
     * L'instruction effectue une opération binaire entre les deux
     * éléments en tête de pile
     *
     * @param pMachine la machine qui interprète le langage pascal
     */
    public void exec(PMachine pMachine){
        int y = pMachine.pop();
        int x = pMachine.pop();
        pMachine.push( binaryOperation(x,y));
        pMachine.next();
    }


    /**
     * Fonction abstraite qui implémente le type d'opération binaire
     *
     * @param x l'opérande "gauche"
     * @param y l'opérande "droite"
     *
     * @return le résultat de l'opération binaire
     */
    public abstract int binaryOperation(int x, int y);


    /**
     * Convertit l'instruction en chaîne de caractères
     *
     * @return la chaîne de caractères
     */
    public abstract String toString();

}

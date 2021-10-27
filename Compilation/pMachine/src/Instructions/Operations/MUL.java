package Instructions.Operations;

import Instructions.Instruction;


/**
 * Classe qui implémente l'opérateur *
 * pour la PMachine
 *
 * Implémente la classe {@link Instruction}
 *
 * @author jordan
 * @since 20-04-2020
 */
public class MUL extends BinaryOperation {


    /**
     * Fonction qui implémente la multiplication
     *
     * @param x l'opérande "gauche"
     * @param y l'opérande "droite"
     *
     * @return le résultat de l'opération binaire
     */
    @Override
    public int binaryOperation(int x, int y) { return x*y; }


    /**
     * Convertit l'instruction en chaîne de caractères
     *
     * @return la chaîne de caractères
     */
    @Override
    public String toString() { return "MUL"; }
}

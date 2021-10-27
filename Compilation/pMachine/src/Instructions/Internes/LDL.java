package Instructions.Internes;

import Instructions.Instruction;
import Machine.PMachine;


/**
 * Classe qui implémente l'ajout d'un élément sur la pile
 * (pour les procédures) pour la PMachine
 *
 * Implémente la classe {@link Instruction}
 *
 * @author jordan
 * @since 20-04-2020
 */
public class LDL implements Instruction {

    private int param;


    /**
     * Constructeur à un paramètre
     *
     * @param param la position du paramètre dans la fonction
     */
    public LDL(int param){
        this.param = param;
    }


    /**
     * Fonction requise pour utiliser l'interface {@link Instruction}
     *
     * L'instruction met en tête de pile l'adresse du paramètre
     *
     * @param pMachine la machine qui interprète le langage pascal
     */
    @Override
    public void exec(PMachine pMachine) {
        pMachine.push(param + pMachine.getMainShift());
        pMachine.next();
    }


    /**
     * Convertit l'instruction en chaîne de caractères
     *
     * @return la chaîne de caractères
     */
    @Override
    public String toString() {
        return "LDL " + param ;
    }
}

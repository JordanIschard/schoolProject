package Instructions.Internes;

import Instructions.Instruction;
import Machine.PMachine;


/**
 * Classe qui implémente le retour procédure
 * pour la PMachine
 *
 * Implémente la classe {@link Instruction}
 *
 * @author jordan
 * @since 20-04-2020
 */
public class RET implements Instruction {

    private int param;


    /**
     * Constructeur à un paramètre
     *
     * @param param le nombre de paramètre de la procédure
     */
    public RET(int param){
        this.param = param;
    }


    /**
     * Fonction requise pour utiliser l'interface {@link Instruction}
     *
     * L'instruction vide la pile des paramètres et retourne à l'adresse mise en tête de pile
     * préemptivement
     *
     * @param pMachine la machine qui interprète le langage pascal
     */
    @Override
    public void exec(PMachine pMachine) {

        int adr = pMachine.pop();
        for (int i = 0; i < param; i++) {
            pMachine.pop();
        }

        pMachine.setMainShift(pMachine.memSize() - (param + 1));
        pMachine.goTo(adr);
    }


    /**
     * Convertit l'instruction en chaîne de caractères
     *
     * @return la chaîne de caractères
     */
    @Override
    public String toString() {
        return "RET ";
    }
}

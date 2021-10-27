package Instructions.Internes;

import Instructions.Instruction;
import Machine.PMachine;


/**
 * Classe qui implémente l'arrêt
 * de la PMachine
 *
 * Implémente la classe {@link Instruction}
 *
 * @author jordan
 * @since 20-04-2020
 */
public class HLT implements Instruction {

    /**
     * Constructeur sans paramètre
     */
    public HLT(){}

    /**
     * Fonction requise pour utiliser l'interface {@link Instruction}
     *
     * L'instruction arrête l'exécution de la PMachine
     *
     * @param pMachine la machine qui interprète le langage pascal
     */
    @Override
    public void exec(PMachine pMachine){ pMachine.stop(); }


    /**
     * Convertit l'instruction en chaîne de caractères
     *
     * @return la chaîne de caractères
     */
    @Override
    public String toString(){ return "HLT"; }
}
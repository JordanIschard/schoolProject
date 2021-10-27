package Instructions.Internes;

import Instructions.Instruction;
import Machine.PMachine;


/**
 * Classe qui implémente l'allocation d'espace mémoire
 * pour la PMachine
 *
 * Implémente la classe {@link Instruction}
 *
 * @author jordan
 * @since 20-04-2020
 */
public class INC implements Instruction {

    private int shift;


    /**
     * Constructeur à un paramètre
     *
     * @param shift la taille de l'allocation mémoire
     */
    public INC(int shift) {
        this.shift = shift;
    }


    /**
     * Met à jour la taille de l'allocation mémoire
     *
     * @param shift nouvelle allocation mémoire
     */
    public void increment(int shift){ this.shift += shift; }


    /**
     * Fonction requise pour utiliser l'interface {@link Instruction}
     *
     * L'instruction alloue une taille mémoire pour les variables
     *
     * @param pMachine la machine qui interprète le langage pascal
     */
    @Override
    public void exec(PMachine pMachine){
        for (int i = 0; i < shift; i++)
            pMachine.push(0);
        pMachine.next();
    }


    /**
     * Convertit l'instruction en chaîne de caractères
     *
     * @return la chaîne de caractères
     */
    @Override
    public String toString(){ return "INC " +shift; }

}

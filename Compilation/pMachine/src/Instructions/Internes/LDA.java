package Instructions.Internes;

import Instructions.Instruction;
import Machine.PMachine;


/**
 * Classe qui implémente l'ajout d'une adresse sur la pile
 * pour la PMachine
 *
 * Implémente la classe {@link Instruction}
 *
 * @author jordan
 * @since 20-04-2020
 */
public class LDA implements Instruction {

    private int x;


    /**
     * Constructeur à un paramètre
     *
     * @param element l'adresse
     */
    public LDA(int element) {
        this.x = element;
    }


    /**
     * Fonction requise pour utiliser l'interface {@link Instruction}
     *
     * L'instruction met en tête de pile l'adresse
     *
     * @param pMachine la machine qui interprète le langage pascal
     */
    @Override
    public void exec(PMachine pMachine){
        pMachine.push(x);
        pMachine.next();
    }


    /**
     * Convertit l'instruction en chaîne de caractères
     *
     * @return la chaîne de caractères
     */
    @Override
    public String toString(){
        return "LDA " +x;
    }
}
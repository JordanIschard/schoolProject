package Instructions.Internes;

import Instructions.Instruction;
import Machine.PMachine;


/**
 * Classe qui implémente l'affichage
 * pour la PMachine
 *
 * Implémente la classe {@link Instruction}
 *
 * @author jordan
 * @since 20-04-2020
 */
public class PRN implements Instruction {


    /**
     * Constructeur sans paramètre
     */
    public PRN(){}


    /**
     * Fonction requise pour utiliser l'interface {@link Instruction}
     *
     * L'instruction affiche l'élément en tête de pile
     *
     * @param pMachine la machine qui interprète le langage pascal
     */
    @Override
    public void exec(PMachine pMachine){
        System.out.println("> "+pMachine.pop());
        pMachine.next();
    }


    /**
     * Convertit l'instruction en chaîne de caractères
     *
     * @return la chaîne de caractères
     */
    @Override
    public String toString(){
        return "PRN";
    }
}
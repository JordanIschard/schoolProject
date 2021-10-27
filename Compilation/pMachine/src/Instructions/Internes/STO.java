package Instructions.Internes;

import Instructions.Instruction;
import Machine.PMachine;


/**
 * Classe qui implémente le stockage d'un élément en tête de pile
 * dans l'adresse placé en seconde tête pour la PMachine
 *
 * Implémente la classe {@link Instruction}
 *
 * @author jordan
 * @since 20-04-2020
 */
public class STO implements Instruction {

    private int size;


    /**
     * Constructeur à un paramètre
     *
     * @param size la taille de l'élément à stocker
     */
    public STO(int size){
        this.size = size;
    }


    /**
     * Fonction requise pour utiliser l'interface {@link Instruction}
     *
     * L'instruction prend l'élément en tête de la pile, une adresse et
     * stocke cette élément à l'adresse récupérée
     *
     * @param pMachine la machine qui interprète le langage pascal
     */
    @Override
    public void exec(PMachine pMachine){
        int[] vals = new int[size];

        for (int i=0; i < size; i++){
            vals[size-1-i] = pMachine.pop();
        }
        int adr = pMachine.pop();

        for (int i=0; i < size; i++){
            pMachine.replace(adr+i,vals[i]);
        }

        pMachine.next();
    }


    /**
     * Convertit l'instruction en chaîne de caractères
     *
     * @return la chaîne de caractères
     */
    @Override
    public String toString(){ return "STO " + size; }
}
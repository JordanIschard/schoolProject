package Instructions.Internes;

import Instructions.Instruction;
import Machine.PMachine;


/**
 * Classe qui implémente le remplacement d'une adresse
 * par sa valeur sur la pile pour la PMachine
 *
 * Implémente la classe {@link Instruction}
 *
 * @author jordan
 * @since 20-04-2020
 */
public class LDV implements Instruction {

    private int size;


    /**
     * Constructeur à un paramètre
     *
     * @param size la taille de l'élément que l'on veut recopier en tête de pile
     */
    public LDV(int size){
        this.size = size;
    }


    /**
     * Fonction requise pour utiliser l'interface {@link Instruction}
     *
     * L'instruction prend en tête de pile une adresse et recopie son contenu à la place
     *
     * @param pMachine la machine qui interprète le langage pascal
     */
    @Override
    public void exec(PMachine pMachine){
        int adr = pMachine.pop();
        int val;
        for (int i= 0; i <size; i++) {
            val = pMachine.get(adr+i);
            pMachine.push(val);
        }

        pMachine.next();
    }


    /**
     * Convertit l'instruction en chaîne de caractères
     *
     * @return la chaîne de caractères
     */
    @Override
    public String toString(){
        return "LDV " + size;
    }
}
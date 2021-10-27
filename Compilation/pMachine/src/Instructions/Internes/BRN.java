package Instructions.Internes;

import Instructions.Instruction;
import Machine.PMachine;

/**
 * Classe qui implémente le saut inconditionnel
 * pour la PMachine
 *
 * Implémente la classe {@link Instruction}
 *
 * @author jordan
 * @since 20-04-2020
 */
public class BRN implements Instruction {

    private int adr;



    /**
     * Constructeur sans paramètre
     */
    public BRN(){}


    /**
     * Constructeur à un paramètre
     *
     * @param adr l'adresse à laquelle on va "sauter"
     */
    public BRN(int adr){ this.adr = adr; }


    /**
     * Met à jour l'adresse du saut
     *
     * @param adr la nouvelle adresse du saut
     */
    public void setJump(int adr){ this.adr = adr; }


    /**
     * Fonction requise pour utiliser l'interface {@link Instruction}
     *
     * L'instruction va mofifier la position de la tête de lecture
     * des instructions pour la mettre sur l'adresse demandée
     *
     * @param pMachine la machine qui interprète le langage pascal
     */
    @Override
    public void exec(PMachine pMachine){ pMachine.goTo(adr); }


    /**
     * Convertit l'instruction en chaîne de caractères
     *
     * @return la chaîne de caractères
     */
    public String toString(){
        return "BRN " +adr;
    }
}
package Instructions.Internes;


import Instructions.Instruction;
import Machine.PMachine;

/**
 * Classe qui implémente le saut conditionnel
 * pour la PMachine
 *
 * Implémente la classe {@link Instruction}
 *
 * @author jordan
 * @since 20-04-2020
 */
public class BZE implements Instruction {

    private int adr,val;


    /**
     * Constructeur sans paramètre
     */
    public BZE(){}


    /**
     * Constructeur à un paramètre
     *
     * @param adr l'adresse à laquelle on va "sauter"
     */
    public BZE(int adr) { this.adr = adr; }


    /**
     * Met à jour l'adresse de saut
     *
     * @param adr la nouvelle adresse de saut
     */
    public void setJump(int adr){ this.adr = adr; }


    /**
     * Fonction requise pour utiliser l'interface {@link Instruction}
     *
     * L'instruction teste si la tête de la pile est 0 :
     *  - si c'est le cas : on saute comme pour la l'instruction {@link BRN}
     *  - sinon : on passe à l'instruction suivante
     *
     * @param pMachine la machine qui interprète le langage pascal
     */
    @Override
    public void exec(PMachine pMachine){
        val = pMachine.pop();

        if (val == 0)
            pMachine.goTo(adr);
        else
            pMachine.next();
    }


    /**
     * Convertit l'instruction en chaîne de caractères
     *
     * @return la chaîne de caractères
     */
    public String toString(){ return "BZE " +val+" -> "+adr; }
}
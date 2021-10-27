package Instructions.Internes;

import Instructions.Instruction;
import Machine.PMachine;

/**
 * Classe qui implémente l'appel de procédure
 * pour la PMachine
 *
 * Implémente la classe {@link Instruction}
 *
 * @author jordan
 * @since 20-04-2020
 */
public class CAL implements Instruction {

    private int nbInst, adr, param;


    /**
     * Constructeur à trois paramètres
     *
     * @param nbInst l'adresse de retour de la procédure
     * @param adr l'adresse de la procédure
     * @param param le nombre de paramètre de la procédure
     */
    public CAL(int nbInst,int adr,int param){
        this.nbInst = nbInst;
        this.adr = adr;
        this.param = param;
    }


    /**
     * Fonction requise pour utiliser l'interface {@link Instruction}
     *
     * L'instruction met à jour le décalage principal pour les procédures,
     * Met en tête de pile l'adresse de retour et "saute" à l'adresse de la
     * procédure
     *
     * @param pMachine la machine qui interprète le langage pascal
     */
    @Override
    public void exec(PMachine pMachine) {
        pMachine.setMainShift(pMachine.memSize() - param);
        //System.out.println(pMachine.getMainShift());
        pMachine.push(nbInst+1);
        pMachine.goTo(adr);
    }


    /**
     * Convertit l'instruction en chaîne de caractères
     *
     * @return la chaîne de caractères
     */
    @Override
    public String toString() {
        return "CAL "+adr;
    }
}

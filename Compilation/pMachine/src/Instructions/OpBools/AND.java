package Instructions.OpBools;

import Instructions.Instruction;
import Machine.PMachine;


/**
 * Classe qui implémente l'opérateur inter
 * pour la PMachine
 *
 * Implémente la classe {@link Instruction}
 *
 * @author jordan
 * @since 20-04-2020
 */
public class AND implements Instruction {

    /**
     * Fonction requise pour utiliser l'interface {@link Instruction}
     *
     * L'instruction effectue une opération logique && entre les deux
     * élément en tête de pile
     *
     * @param pMachine la machine qui interprète le langage pascal
     */
    @Override
    public void exec(PMachine pMachine) {
        int x = pMachine.pop();
        int y = pMachine.pop();

        if(x == 1 && y == 1){
            pMachine.push(1);
        }else{
            pMachine.push(0);
        }
        pMachine.next();
    }


    /**
     * Convertit l'instruction en chaîne de caractères
     *
     * @return la chaîne de caractères
     */
    @Override
    public String toString() {
        return "AND";
    }
}

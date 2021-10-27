package Instructions;

import Machine.PMachine;

/**
 * Interface représentant une instruction pour la PMachine
 *
 * @author jordan
 * @since 27-03-2020
 */
public interface Instruction {

    /**
     * Exécute une instruction de la PMachine
     *
     * @param pMachine la machine qui interprète le langage Pascal
     */
    void exec(PMachine pMachine);

    /**
     * Convertit un instruction en chaîne de caractères
     *
     * @return la chaîne de caractères représentant l'instruction
     */
    String toString();

}

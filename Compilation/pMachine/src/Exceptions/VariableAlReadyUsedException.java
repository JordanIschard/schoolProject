package Exceptions;

/**
 * Exception notifiant que la variable
 * est déjà utilisée
 *
 * Hérite de la classe Exception
 *
 * @author jordan
 * @since 27-03-2020
 */
public class VariableAlReadyUsedException extends Exception {

    /**
     * Surcharge de la méthode héritée
     *
     * @param message le message d'erreur
     */
    public VariableAlReadyUsedException(String message) { super(message); }
}

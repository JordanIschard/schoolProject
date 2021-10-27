package Exceptions;

/**
 * Exception notifiant qu'une demande n'est pas supporté
 * par le type
 *
 * Hérite de la classe Exception
 *
 * @author jordan
 * @since 27-03-2020
 */
public class AskNotSupportedException extends Exception {

    /**
     * Surcharge de la méthode héritée
     *
     * @param message le message d'erreur
     */
    public AskNotSupportedException(String message) { super(message); }

}

package Exceptions;

/**
 * Exception notifiant qu'il y a
 * une erreur de syntaxe
 *
 * Hérite de la classe Exception
 *
 * @author jordan
 * @since 27-03-2020
 */
public class SyntaxErrorException extends Exception{

    /**
     * Surcharge de la méthode héritée
     *
     */
    public SyntaxErrorException() { super(); }

    /**
     * Surcharge de la méthode héritée
     *
     * @param message le message d'erreur
     */
    public SyntaxErrorException(String message) {
        super(message);
    }
}

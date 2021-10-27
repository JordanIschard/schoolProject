package Exceptions;

/**
 * Exception notifiant qu'un symbole n'a pas été
 * trouvé dans la table des symboles
 *
 * Hérite de la classe Exception
 *
 * @author jordan
 * @since 27-03-2020
 */
public class SymbolNotFoundException extends Exception{

    /**
     * Surcharge de la méthode héritée
     *
     * @param message le message d'erreur
     */
    public SymbolNotFoundException(String message) { super(message); }
}

package Exceptions;

/**
 * Exception notifiant qu'une adresse n'a pas été trouvé
 * dans la table
 *
 * Hérite de la classe Exception
 *
 * @author jordan
 * @since 27-03-2020
 */
public class AddrNotFoundException extends Exception {

    /**
     * Surcharge de la méthode héritée
     *
     * @param message le message d'erreur
     */
    public AddrNotFoundException(String message) { super(message); }
}

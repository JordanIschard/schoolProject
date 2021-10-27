package TablesSymboles.Types;

/**
 * Implémente le principe de type
 *
 * @author jordan
 * @since 27-03-2020
 */
public interface Type {

    /**
     * Retourne la taille du type
     *
     * @return la taille du type
     */
    int size();

    void print(String s);

    String toString();
}

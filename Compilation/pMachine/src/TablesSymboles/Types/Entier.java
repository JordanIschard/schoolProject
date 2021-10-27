package TablesSymboles.Types;

/**
 * Implémente les entiers dans la PMachine
 *
 * Implémente la classe {@link Type}
 *
 * @author jordan
 * @since 27-03-2020
 */
public class Entier implements Type {

    /**
     * Méthode surchargée de l'interface {@link Type}
     *
     * @return la taille d'un booléen
     */
    @Override
    public int size() {
        return 1;
    }

    @Override
    public void print(String s) {
        System.out.print("Entier");
    }

    /**
     * Convertit un booléen en chaîne de caractères
     *
     * @return une chaîne de caractères
     */
    public String toString() {
        return "Entier";
    }
}

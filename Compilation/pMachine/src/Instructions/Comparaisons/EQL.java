package Instructions.Comparaisons;

/**
 * Classe qui implémente l'égalité de deux entiers pour la
 * PMachine
 *
 * Hérite de la classe {@link Comparator}
 *
 * @author jordan
 * @since 27-03-2020
 */
public class EQL extends Comparator {

    /**
     * Un constructeur sans paramètre
     */
    public EQL(){}

    /**
     * Convertit l'instruction en chaîne de caractères
     *
     * @return la chaîne de caractères
     */
    public String toString(){ return "EQL "; }

    /**
     * Méthode héritée de la classe {@link Comparator}
     *
     * @param x le première entier
     * @param y le second entier
     * @return le résultat de la comparaison sous la forme d'un
     * booléen
     */
    @Override
    public boolean binaryComparator(int x, int y) { return x == y; }

}
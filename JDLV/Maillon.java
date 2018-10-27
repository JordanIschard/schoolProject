package JDLV;

/**Maillon générique
 *
 * @param <T>
 * @author Mathieu
 */
public class Maillon<T extends Comparable<T>> {
    private Maillon<T> next;
    private T value;

    /**Constructeur avec paramètre
     *
     */
    Maillon(T c) {
        this.next = null;
        this.value = c;
    }

    /**Constructeur avec paramètres
     *
     */
    Maillon(T c, Maillon<T> next) {
        this.next = next;
        this.value = c;
    }

    /**Donne le maillon suivant
     *
     * @return Maillon<T>
     */
    public Maillon<T> getNext() {
        return this.next;
    }

    /**Change le maillon suivant
     *
     * @param m maillon qui va devenir
     *          le suivant du maillon courant
     */
    void setNext(Maillon<T> m) {
        this.next = m;
    }

    /**Donne la valeur contenue dans le maillon
     *
     * @return T
     */
    public T getValue() { return this.value; }

    public void setValue(T value) { this.value=value; }

    /**renvoie le contenue du maillon soit la forme d'une chaine de caractere
     *
     * @return String
     */
    public String toString() {
        return "Maillon: Suivant= " + this.next.getValue() + "  Valeur: " + value.toString();
    }

}
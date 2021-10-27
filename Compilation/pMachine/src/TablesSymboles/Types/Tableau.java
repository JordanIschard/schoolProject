package TablesSymboles.Types;

/**
 * Implémente les tableaux dans la PMachine
 *
 * Implémente la classe {@link Type}
 *
 * @author jordan
 * @since 27-03-2020
 */
public class Tableau implements Type {

    private int begin;
    private int end;
    private int size;
    private Type type;

    /**
     * Constructeur
     *
     * @param begin la borne inférieur
     * @param end la borne supérieur
     * @param type le type des éléments
     */
    public Tableau(int begin,int end,Type type){
        this.size = end - begin;
        this.begin = begin;
        this.end = end;
        this.type = type;
    }

    /**
     * Méthode surchargée de l'interface {@link Type}
     *
     * @return la taille d'un booléen
     */
    @Override
    public int size() {
        return size;
    }

    @Override
    public void print(String s) {
        System.out.print("Tableau < "+type+" > ["+begin+".."+end+"]");
    }

    /**
     * Convertit un booléen en chaîne de caractères
     *
     * @return une chaîne de caractères
     */
    public String toString() {
        return "Tableau < "+type+" > ["+begin+".."+end+"]";
    }

    /**
     * Retourne la borne inférieur
     *
     * @return la borne inférieur
     */
    public int getBegin() { return begin; }

    /**
     * Retourne le type des éléments
     *
     * @return le type des éléments
     */
    public Type getType() { return type; }
}

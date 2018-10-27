package JDLV;

import java.util.Iterator;

/**Liste chaînée générique
 *
 * @param <T>
 * @author Mathieu
 */
public class Liste<T extends Comparable<T>> implements Iterable<T>{
    private Maillon<T> tete;
    private int nombre_de_Maillon;

    /**Constructeur sans paramètre
     */
    Liste(){
        this.tete=null;
        this.nombre_de_Maillon=0;
    }

    /**Donne le nombre de Maillons
     *
     * @return int
     */
    public int getNbrMaillon(){ return nombre_de_Maillon; }

    /**Change le nombre de maillon
     *
     * @param t nouveau nombre de maillon
     */
    public void setNbrMaillon(int t){ nombre_de_Maillon = t; }

    /**Ajoute un élément T à la liste chaînée triée
     *
     * @param t élément T à ajouter à la liste
     */
    public void add(T t){
        if(this.nombre_de_Maillon==0){ this.tete= new Maillon<>(t); }
        else { trie(t); }
        this.nombre_de_Maillon++;
    }

    /**Ajoute un Maillon contenant la Cellule c à la liste chainée triée
     *
     * @param t élément à ajouter dans la liste
     */
    public void trie(T t){
        Maillon<T> tmp=this.tete;
        while(tmp!=null) {
            if(t.compareTo((tmp.getValue()))<0) {
                tmp.setNext(new Maillon<>(tmp.getValue(), tmp.getNext()));
                tmp.setValue(t);
                return;
            }
            if(tmp.getNext()==null){
                tmp.setNext(new Maillon<>(t,null));
                return;
            }
            tmp=tmp.getNext();
        }
    }

    /**Donne le maillon de tete
     *
     * @return MaillonG tete
     */
    public Maillon<T> getTete(){ return this.tete; }

    /**Change le maillon de tete
     *
     * @param tete nouvelle tête de la liste
     */
    public void setTete(Maillon<T> tete){ this.tete=tete; }

    /**retourne l'affichage de liste
     *
     * @return String
     */
    public String toString(){
        StringBuilder res = new StringBuilder();
        for (T t : this) {
            res.append(t.toString()).append("\n");
        }
        return res.toString();
    }

    /**Créer un itérateur
     *
     * @return Iterator<E>
     */
    @Override
    public Iterator<T> iterator() { return new Iterateur<>(); }

    /**Classe Itérateur
     *
     * @param <E> le curseur
     */
    public class Iterateur<E extends Comparable<E>> implements Iterator<E> {
        private Maillon<E> cursor;

        /**Constructeur de la
         * classe itérateur
         */
        Iterateur(){ this.cursor= (Maillon<E>) tete; }

        /**Vérifie si il y a un suivant
         *
         * @return boolean
         */
        @Override
        public boolean hasNext() { return cursor!=null; }

        /**Retourne un élément t et
         * passe au suivant
         *
         * @return un élément t
         */
        @Override
        public E next() {
            E t =cursor.getValue();
            cursor=cursor.getNext();
            return t;
        }
    }
}
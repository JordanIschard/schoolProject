package JDLV;

/**Classe qui représente une cellule
 * via sa position x,y et son nombre
 * de voisin
 *
 * @author Jordan
 */
public class Cellule implements Comparable<Cellule>{
    private int voisins;
    private int x;
    private int y;

    /**Constructeur à 2 paramètres
     *
     * @param x position en abscisse
     * @param y position en ordonnée
     */
    Cellule(int x, int y){
        this.voisins=0;
        this.x=x;
        this.y=y;
    }

    /**Constructeur à 3 paramètres
     *
     * @param x position en abscisse
     * @param y position en ordonnée
     * @param voisins nombre de voisins
     */
    Cellule(int x, int y, int voisins){
        this.voisins=voisins;
        this.x=x;
        this.y=y;
    }

    /**Retourne la position x
     *
     * @return x
     */
    public int getX(){ return this.x; }

    /**Retourne la position y
     *
     * @return y
     */
    public int getY(){ return this.y; }

    /**Retourne le nombre de voisins d'une cellule
     *
     * @return voisins
     */
    public int getVoisins(){ return this.voisins; }

    /**Change le nombre de voisins d'une cellule
     *
     * @param nb nombre de voisin
     */
    public void setVoisins(int nb){ this.voisins=nb; }

    /**Vérifie par rapport à un tableau de int si la
     * cellule courante peut survivre ou naître
     * via son nombre de voisins
     *
     * @param nbrVoisins tableau contenant le nombre de voisins
     *                   nécessaire pour survivre ou naître
     * @return boolean
     */
    public boolean reglesSurvieOuNaiss(int [] nbrVoisins){
        for(int nbr : nbrVoisins)
            if(this.voisins==nbr){ return true; }
        return false;
    }

    /**Compare 2 cellules et retourne un entier
     *
     * @param c second cellule à comparer avec la cellule courante
     * @return 1 si this>c
     *         0 si this=c
     *         -1 si this<c
     */
    public int compareTo(Cellule c){
        if(this.y==c.getY()){ return Integer.compare(this.x,c.getX()); }

        else if(this.y>c.getY()){ return 1; }

        else{ return -1; }
    }

    /**Retourne l'affichage d'une cellule
     *
     * @return string
     */
    public String toString(){
        return "Cellule_04: x="+this.x+" y="+this.y+" Nbr voisins="+this.voisins;
    }

}

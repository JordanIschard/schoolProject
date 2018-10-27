package JDLV;

/**Selection pour des voisins
 * d'une cellule
 *
 * @author Jordan
 */
public interface TypeMonde {

    /**Vérifie si une position et bonne par rapport
     * au type de monde et la modifie si nécessaire
     *
     * @param taille taille de la grille
     * @param x position x de la cellule
     * @param y position y de la cellule
     * @return int [] nouvelle position
     */
    int [] estVoisin(int taille, int x, int y);

    /**Lambda expression pour les mondes infini
     */
    TypeMonde estVoisinPourMondeInf=((taille, x, y) -> new int []{x,y});

    /**Lambda expression pour les mondes fini
     *
     */
    TypeMonde estVoisinPourMondeFini =((taille, x, y) -> (x>=0 && x<=taille && y>=0 && y<=taille)?new int[]{x,y}
                                                                                        :null);

    /**Lambda expression pour le monde circulaire
     */
    TypeMonde estVoisinPourMondeCirc=((taille, x, y) ->
            x<taille && 0<=x && y<0 ?new int[]{x,taille-1}:
                    x<taille && 0<=x && y>=taille?new int []{x,0}:
                            x>=taille && y<taille && 0<=y ?new int []{0,y}:
                                    x<0 && y<taille && 0<=y ?new int []{taille-1,y}:
                                            x>=taille && y<0?new int []{0,taille-1}:
                                                    x<0 && y<0?new int []{taille-1,taille-1}:
                                                            x<0 && y>=taille?new int []{taille-1,0}:
                                                                    x>=taille && y>=taille?new int []{0,0}:
                                                                            new int[] {x,y});
}

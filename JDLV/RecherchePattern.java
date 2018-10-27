package JDLV;

import java.util.Iterator;

/**Recherche les différents pattern possibles
 * d'un arrangement du jeu de la vie
 *
 * @author Jordan et Kamélia
 */
public class RecherchePattern {

    /**Teste si une génération est morte:
     *elle regarde le nombre de maillon de la
     * liste de la génération
     *
     * @param Gen génération à tester
     * @return boolean mort ou vivant
     */
    private static boolean Mort(Generation Gen) {
        return Gen.getListe().getNbrMaillon()==0;
    }

    /**Teste si une génération est stable:
     * elle compare chaque maillon de la liste
     * de la génération 0 et 1
     *
     * @param Gen1 génération à tester
     * @return boolean stable ou non stable
     */
    private static boolean Stable(Generation Gen1) {
        Generation Gen2=new Generation(Gen1);
        Gen2.nextGen();
        if(Gen1.getListe().getNbrMaillon()!=Gen2.getListe().getNbrMaillon()){ return false; }
        Iterator<Cellule> it1 = Gen1.getListe().iterator();
        Iterator<Cellule> it2 = Gen2.getListe().iterator();

        while(it1.hasNext()){
            if(it1.next().compareTo(it2.next())!=0){ return false; }
        }
        return true;
    }

    /**Regarde si deux générations sont égales à un
     * déplacement près et retourne un tableau de
     * 3 paramètres:
     * le 1er donne 1 pour égaux 0 pour différents
     * le 2ème donne le déplacement x
     * le 3ème donne le déplacement y
     *
     * @param Gentmps1 l'une des générations à comparer
     * @param Gentmps2 la second génération à comparer
     * @return int [] tableau des res
     */
    private static int [] egals (Generation Gentmps1, Generation Gentmps2){
        if(Gentmps1.getListe().getNbrMaillon()!=Gentmps2.getListe().getNbrMaillon()) return new int[]{0,0,0};

        Cellule tmps1=Gentmps1.getListe().getTete().getValue();
        Cellule tmps2=Gentmps2.getListe().getTete().getValue();
        Iterator<Cellule> it1 = Gentmps1.getListe().iterator();
        Iterator<Cellule> it2 = Gentmps2.getListe().iterator();

        if (!it1.hasNext() || !it2.hasNext()) { return new int[]{0,0,0}; }
        else{
            int deplacementX = tmps1.getX() - tmps2.getX();
            int deplacementY = tmps1.getY() - tmps2.getY();

            while (it1.hasNext()) {
                tmps1 = it1.next();
                tmps2 = it2.next();
                if (deplacementX != tmps1.getX() - tmps2.getX()
                        || deplacementY != tmps1.getY() - tmps2.getY()) {
                    return new int[]{0, 0, 0};
                }
            }
            return new int[]{1, deplacementX, deplacementY};
        }
    }

    /**Recherche le pattern potentiel de
     * l'arrangement de cellule sur n générations
     * (n=max) et retourne un tableau avec les infos
     * utiles et null si "inconnu"
     *
     * @param zero Génération de base
     * @param max nombre d'itération max à faire pour trouver un pattern
     * @return int [] infos sur le pattern trouvé
     */
    public static int[] recherchePattern (Generation zero, int max){

        Generation Gentmps1 = new Generation(zero);
        Generation Gentmps2 = new Generation(zero);
        Gentmps2.nextGen();

        while(max>0){

            if(Mort(Gentmps1)){ System.out.println("Mort");return new int []{}; }
            if(Stable(Gentmps1)){ System.out.println("Stable");return new int []{1}; }
            if(Gentmps1.getNumGen() != Gentmps2.getNumGen()){

                int [] deplacements=egals(Gentmps1,Gentmps2);

                if(deplacements[0]==1){

                    int periode=periode(Gentmps1);
                    System.out.println("Peridode:"+periode
                            +" depX="+deplacements[1]+" depY="+deplacements[2]);

                    //Pour trouver la taille de la queue on prends toutes les "formes" du pattern
                    //et on les mets dans un tableau
                    Generation queue=new Generation(zero);
                    Generation[] lGen =new Generation[periode];

                    for (int i=0;i<periode; i++){
                        if(i==0){ lGen[i]=new Generation(Gentmps1); }
                        else{
                            Generation tmp =new Generation(lGen[i-1]);
                            tmp.nextGen();
                            lGen[i]=new Generation(tmp);
                        }
                    }

                    //Pour chaque génération en partant de zéro on test toutes les "formes" du pattern,
                    //si on le trouve on sort de la boucle
                    boolean trouve =false;
                    do{
                        for(int i=0;i<periode;i++) {
                            if ((egals(queue,lGen[i]))[0] == 1){
                                trouve=true;
                                break;
                            }
                        }
                        if(!trouve){ queue.nextGen(); }
                    }while (!trouve);

                    System.out.println("Taille de la queue: "+queue.getNumGen());
                    return new int[]{periode , deplacements[1], deplacements[2],queue.getNumGen()};
                }
            }
            Gentmps1.nextGen();
            Gentmps2.nextGen();
            Gentmps2.nextGen();
            max--;
        }
        System.out.println("inconnu");
        return null;
    }

    /**Calcul la période d'un pattern en partant
     * de la génération "se déplaçant d'un" dans
     * notre algorithme
     *
     * @param gen génération "se déplaçant d'un"
     * @return la période calculé
     */
    private static int periode(Generation gen){
        Generation tmp =new Generation(gen);
        int count=0;
       do{
            count++;
            tmp.nextGen();
        } while ((egals(gen,tmp))[0]!=1);
        return count;
    }
}


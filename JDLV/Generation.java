package JDLV;

import java.io.File;
import java.util.Iterator;

/**Génération du jeu de la vie
 *
 * @author Jordan
 */
public class Generation implements Comparable<Generation> {
    private Liste<Cellule> Gen;
    private int numGen;
    private int [] nbrVoisinsSurvieNaiss;
    private int taille;
    private TypeMonde monde;

    /**Constructeur avec paramètres
     *
     * @param taille taille du plateau (important seulement dans un monde
     *               fini et circulaire)
     * @param monde type de monde
     */
    Generation(int taille, TypeMonde monde){
        this.Gen=new Liste<>();
        this.numGen=0;
        this.taille=taille;
        this.monde=monde;
        this.nbrVoisinsSurvieNaiss=(new int[]{3,12,13});
    }

    /**Constructeur avec paramètres et fichier
     *
     * @param taille c'est la taille
     * @param monde c'est le type de monde
     */
    Generation(int taille, TypeMonde monde, String fichier){
        try {
            Generation g=new Generation((new Traitement()).recup(new File(fichier), taille, monde));
            this.Gen=g.Gen;
            this.nbrVoisinsSurvieNaiss=g.nbrVoisinsSurvieNaiss;
            this.taille=taille;
            this.monde=g.monde;
        } catch (IllegalArgumentException e) {
            System.out.println("Erreur le fichier est erroné");
        }

    }

    /**Constructeur avec une génération
     *
     * @param e Génération à copier
     */
    Generation(Generation e){
        if(e.getListe()!=null) { this.Gen = e.getListe(); }
        else{ this.Gen=new Liste<>(); }
        this.numGen=e.getNumGen();
        this.taille=e.taille;
        this.monde=e.monde;
        this.nbrVoisinsSurvieNaiss=e.nbrVoisinsSurvieNaiss;
    }

    /**Retourne la liste de cellules de la génération
     * courante
     *
     * @return Liste liste de cellules
     */
    public Liste<Cellule> getListe(){ return Gen; }

    /**Retourne le numéro de génération de la génération
     * courante
     *
     * @return int numéro de génération
     */
    public int getNumGen(){ return numGen; }

    /**Change le tableau contenant le nombre de voisin
     * pour survivre ou naître
     *
     * @param t nouveau tableau
     */
    public void setNbrVoisinsSurvieNaiss (int [] t){ nbrVoisinsSurvieNaiss=t; }


    /**Vérifie si la cellule c est dans la liste gen
     * si elle est déjà présente on ajoute le nombre de voisins
     * de c à la cellule déjà présente dans la liste sinon on
     * la place au bonne endroit pour garder un trie
     *
     * @param l liste passée en paramètre
     * @param c Cellule que l'on doit ajouter à la liste l
     */
    private void ajout(Liste<Cellule> l, Cellule c){
        Maillon<Cellule> it = l.getTete();
        while(it!=null){
            Cellule tmp=it.getValue();
            switch (tmp.compareTo(c)){
                case 0: tmp.setVoisins(tmp.getVoisins()+c.getVoisins()); return;
                case -1:
                    it.setNext(new Maillon<>(it.getValue(), it.getNext()));
                    it.setValue(c);
                    return;
                default: if(it.getNext()==null){ it.setNext(new Maillon<>(c)); return; }
                         else{ it = it.getNext(); }
            }
        }
        l.setTete(new Maillon<>(c));
    }

    /**Calcul la liste génération suivante, remplace
     * l'ancienne liste par la nouvelle et incrément le
     * numéro de génération
     */
    public void nextGen(){
        Liste<Cellule> nextStepInt=new Liste<>();
        Iterator<Cellule> it = Gen.iterator();
        while(it.hasNext()){
            Cellule temp=it.next();
            for (int i=-1;i<=1;i++){
                for (int j=-1; j<=1;j++) {
                        int [] TypeMonde=monde.estVoisin(taille,temp.getX()+i,temp.getY()+j);
                        if(TypeMonde!=null) {
                            int Nx=TypeMonde[0];
                            int Ny=TypeMonde[1];
                            Cellule c = new Cellule(Nx,Ny,1);
                            if (i == 0 && j == 0) {
                                c.setVoisins(10);
                                ajout(nextStepInt,c);
                            } else {
                                ajout(nextStepInt,c);
                            }
                        }
                }
            }
        }
        Liste<Cellule> nextStepFin=new Liste<>();
        it = nextStepInt.iterator();

        while(it.hasNext()){
            Cellule tmp = it.next();
            if(tmp.reglesSurvieOuNaiss(nbrVoisinsSurvieNaiss)){
                tmp.setVoisins(0);
                nextStepFin.add(tmp);
            }
        }
        Gen=nextStepFin;
        numGen++;
    }

    /**Retourne l'affichage de la liste et numéro de génération
     * de la génération courante
     *
     * @return String affichage
     */
    public String toString(){
        return Gen.toString()+"\n Numéro de génération:"+numGen;
    }

    @Override
    public int compareTo(Generation generation) {
        return -1;
    }
}

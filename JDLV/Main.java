package JDLV;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**Classe qui s'occupe du traitement des
 * arguments mis en paramètre
 * @author Jordan
 */
public class Main {

    /**Affiche l'aide
     */
    private static void aide(){
        try {
            BufferedReader br=new BufferedReader(new FileReader("./help.txt"));
            String str;
            while((str=br.readLine())!=null)
                System.out.println(str);
            br.close();
        } catch (IOException e) { System.out.println("Tant pis"); }
    }

    /**Choisi le type de commande via le nombre de paramètre
     *
     * @param args paramètres donnés via la commande
     */
    private static void selectionCommande(String [] args){
        int nbrParam=args.length;
        if(nbrParam==1) {
            selectionCommandeInformative(args[0]);
        }else{
            if(nbrParam==3 || nbrParam==5){
                selectionCommandeTypeMonde(args);
            }else {
                System.out.println("Le paramètre est erroné; Utilisez la commande suivante pour vous aidez: ");
                System.out.println("->  java -jar JeuDeLaVie.jar -h");
            }
        }
    }

    /**Affiche les commandes de type Informative
     * c'est à dire les noms et l'aide
     *
     * @param args paramètres donnés via la commande
     */
    private static void selectionCommandeInformative(String args){
        switch (args){
            case "-name":
                System.out.println("Noms des étudiants du groupe:\n" +
                        "    -Guilbert Mathieu\n" +
                        "    -Galant Maëlys\n" +
                        "    -Ischard Jordan\n" +
                        "    -Alileche Kamélia\n");
                break;
            case "-h":
                aide();
                break;
            default:
                System.out.println("Le paramètre est erroné; Utilisez la commande suivante pour vous aidez: ");
                System.out.println("->  java -jar JeuDeLaVie.jar -h");
        }
    }

    /**Choisi le type de monde via les paramètres
     *
     * @param args paramètres donnés via la commande
     */
    private static void selectionCommandeTypeMonde(String [] args){
        if(args.length==3) {
            selectionCommandeAction(args,0,0, TypeMonde.estVoisinPourMondeInf);
        }else{
            if(args[0].equals("-circ")){

               selectionCommandeAction(args,Integer.parseInt(args[1]),2, TypeMonde.estVoisinPourMondeCirc);
            }else{
                if(args[0].equals("-f")){
                    selectionCommandeAction(args,Integer.parseInt(args[1]),2, TypeMonde.estVoisinPourMondeFini);
                }else{
                    System.out.println("Les paramètres sont erronés; Utilisez la commande suivante pour vous aidez: ");
                    System.out.println("->  java -jar JeuDeLaVie.jar -h");
                }
            }
        }
    }

    /**Selectionne l'action à réaliser
     *
     * @param args paramètres donnés via la commande
     * @param taille taille du plateau
     * @param decalage décalage pour choisir le bonne argument
     * @param monde type de monde choisi
     */
    private static void selectionCommandeAction(String [] args, int taille, int decalage, TypeMonde monde){
        switch(args[decalage]){
            case "-s":
                Generation l=new Generation(taille,monde,args[decalage+2]);
                new Affichage(l,Integer.parseInt(args[decalage+1]));
                break;
            case "-c":
                Traitement t1 = new Traitement();
                File f =new File(args[decalage+2]);
                try {
                    t1.ouvertureHTML();
                    t1.affichageHTML(f,t1.recup(f,taille,monde),Integer.parseInt(args[decalage+1]));
                    t1.fermetureHTML();
                } catch (IllegalArgumentException e) {
                    System.out.println("Le fichier lif est erroné !");
                }
                break;
            case "-w":
                Traitement t= new Traitement();
                t.ouvertureHTML();
                t.initialisation(args[decalage+2],taille,monde,Integer.parseInt(args[decalage+1]));
                t.fermetureHTML();
                break;
            default:
                System.out.println("Les paramètres sont erronés; Utilisez la commande suivante pour vous aidez: ");
                System.out.println("->  java -jar JeuDeLaVie.jar -h");
        }
    }

    public static void main (String [] args){
        selectionCommande(args);
    }
}

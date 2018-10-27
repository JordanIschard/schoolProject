package JDLV;

import java.io.*;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**Traite les fichiers .lif, les dossiers et écrit dans
 * une page html
 *
 * @author Maëlys
 */
public class Traitement {

    private FileWriter fw;
    {
        try {
            fw = new FileWriter("JDLV.html");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private BufferedWriter bw = new BufferedWriter(fw);
    private PrintWriter pw = new PrintWriter(bw);

    /**Ouverture d'un fichier html
     *
     */
    public void ouvertureHTML(){
            pw.print("<!DOCTYPE html>\n" +
                    "<html>\n" +
                    "    <head>\n" +
                    "        <meta charset=\"utf-8\" />\n" +
                    "        <title>Jeu de la vie</title>\n" +
                    "        <link rel=\"stylesheet\" media=\"all\" href=\"style.css\"/>\n" +
                    "    </head>\n" +
                    "\n" +
                    "    <body>\n");
    }

    /**Fermeture d'un fichier html
     *
     */
    public void fermetureHTML()  {
        try {
            pw.print("</body>\n" +
                    "</html>");
            pw.close();
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**Fais l'affichage d'un fichier html
     *
     * @param fichier fichier à traiter
     * @param gen génération à traiter
     * @param max nombre de génération max
     */
    public void affichageHTML(File fichier,Generation gen,int max){
        try {
            int count=1;
            FileReader fr = new FileReader(fichier);
            BufferedReader br = new BufferedReader(fr);
            pw.print("<fieldset>");
            pw.print("<legend>" + fichier.getName() + "</legend>");
            String a;
            while((a=br.readLine())!=null){
                if(a.length()>0) {
                    if (count == 1) {
                        pw.print("<h4>" + a + "</h4>");
                    } else {
                        if (a.charAt(0) == '#') {
                            pw.print("<p>" + a.substring(0, 2) + " " + a.substring(2) + "</p>");
                        } else {
                            pw.print(a + "</br>");
                        }
                    }
                    count++;
                }
            }
            recherche(gen,max);
            pw.print("</fieldset>");

        }catch (IOException e){
            e.printStackTrace();
        }

    }

    /**Récupère les données d'un fichier .lif pour
     * créer une génération
     *
     * @param fichier fichier.lif
     * @param taille taile de la grille de jeu
     * @param s type de monde choisie
     * @return la génération construite
     * @throws IllegalArgumentException ce n'est pas un bon élément
     */
    public Generation recup(File fichier, int taille, TypeMonde s) throws IllegalArgumentException {
        Generation g= new Generation(taille,s);
        int count = 0;
        try {
            FileReader r = new FileReader(fichier);
            BufferedReader b = new BufferedReader(r);
            String a = b.readLine();
            while (a != null) {
                StringTokenizer st = new StringTokenizer(a);
                String premier = st.nextToken();
                switch (premier) {
                    case "#D":
                        if (count < 22) {
                            count++;
                        } else {
                            throw new IllegalArgumentException("Nombre de commentaire du fichier lif invalide (>22)");
                        }
                        a = b.readLine();
                        break;
                    case "#N":
                        g.setNbrVoisinsSurvieNaiss(new int[]{3, 12, 13});
                        a = b.readLine();
                        break;
                    case "#R":
                        String deuxieme = st.nextToken();
                        String[] d = deuxieme.split("/");
                        int l1 = d[0].length();
                        int l2 = d[1].length();
                        int[] tmp1 = new int[l1 + l2];
                        String[] e = d[0].split("");
                        for (int i = 0; l1 > i; i++) {
                            tmp1[i] = Integer.parseInt(e[i] + 10);
                        }
                        String[] ee = d[1].split("");
                        for (int i = l1; l2 + l1 > i; i++) {
                            tmp1[i] = Integer.parseInt(ee[i]);
                        }
                        g.setNbrVoisinsSurvieNaiss(tmp1);
                        a = b.readLine();
                        break;
                    case "#P":
                        int x = Integer.parseInt(st.nextToken());
                        int y = Integer.parseInt(st.nextToken());
                        a = b.readLine();
                        while (a != null) {
                            int i = x;
                            String[] s1 = a.split("");
                            for (String w : s1) {
                                if (w.equals(".")) {
                                    i += 1;
                                } else {
                                    if (w.equals("*")) {
                                        g.getListe().add(new Cellule(i, y));
                                        i += 1;
                                    }
                                }
                            }
                            y += 1;
                            a = b.readLine();
                        }
                        break;
                    default:
                        a = b.readLine();
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return g;
    }

    /**Ecrit dans le fichier html les infos
     * récoltés par la méthode RecherchePattern
     *
     * @param gen Génération de base
     * @param max nombre de génération max utilisé pour la
     *            recherche de pattern
     */
    private void recherche(Generation gen,int max){
            int [] tmp;
                tmp =RecherchePattern.recherchePattern(gen,max);
                if(tmp==null){
                    pw.print("<h3>Type de monde INCONNU</h3>");
                }else {
                    switch (tmp.length) {
                        case 0:
                            pw.print("<h3>Type de monde MORT</h3>");
                            break;
                        case 1:
                            pw.print("<h3>Type de mode STABLE</h3>");
                            break;
                        case 4:
                            if (tmp[1]==0 && tmp[2]==0){
                                pw.print("<h3>Type de mode OSCILLATEUR</h3>");
                                pw.print("<h5>-Période "+tmp[0]+"</h5>");
                                pw.print("<h5>-Queue "+tmp[3]+"</h5>");
                            }else{
                                pw.print("<h3>Type de mode OSCILLATEUR</h3>");
                                pw.print("<h5>-Période "+tmp[0]+"</h5>");
                                pw.print("<h5>-DéplacementX "+tmp[1]+"</h5>");
                                pw.print("<h5>-DéplacementY "+tmp[2]+"</h5>");
                                pw.print("<h5>-Queue "+tmp[3]+"</h5>");
                            }
                    }
                }
    }

    /**Traite d'un dossier
     *
     * @param dossier dossier à traiter
     * @param taille taille de la grille
     * @param s type de monde
     * @param max nombre de génération max
     */
    public void initialisation(String dossier, int taille, TypeMonde s, int max){
        File doss = new File(dossier);
        try {
            if (!doss.isDirectory()) {
                throw new IOException("Pas un dossier");
            }
            File[] fichiers = doss.listFiles();
            if (fichiers != null) {
                for (File fichier : fichiers) {
                    if (fichier.isFile()) {
                        String fichName = fichier.getName();
                        Pattern P = Pattern.compile("[a-zA-Z0-9_ ().+-]+\\.lif$");
                        Matcher M = P.matcher(fichName);
                        if (M.matches()) {
                            affichageHTML(fichier, (recup(fichier, taille, s)), max);
                        }
                    }
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}

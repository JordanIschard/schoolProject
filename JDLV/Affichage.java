package JDLV;

import javax.swing.*;
import java.awt.*;

/**Ouvre une fenêtre pour faire un affichage
 * en temps réel de l'evolution du jeu de la vie
 *
 * @author Jordan
 */
class Affichage extends JFrame {
    private Panneau pan = new Panneau();
    private Generation Gen;
    private int decX;
    private int decY;

    /**Constructeur avec paramètres d'une
     * fenêtre
     *
     * @param Gen Génération de base
     * @param duree nombre de génération à afficher
     */
    Affichage(Generation Gen, int duree) {
        this.setTitle("Animation");
        this.setSize(1360, 760);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setContentPane(pan);
        this.setVisible(true);
        this.Gen=Gen;
        this.decY = this.getHeight()/4;
        this.decX = this.getWidth()/4;
        simulation(Gen,duree);
    }

    /**Fais une simulation de l'evolution
     * de l'arrangement de cellule de Gen
     * pour une certaine durée et affiche le
     * résultat sur une fenêtre avec une pause
     * de 500ms par refresh
     *
     * @param Gen Génération de base
     * @param duree nombre de génération à afficher
     */
    private void simulation(Generation Gen, int duree){
        if(duree>0) {
            long t =System.currentTimeMillis();
            System.out.print(Gen.getListe().getNbrMaillon()+" Cellules    ");
            Gen.nextGen();
            System.out.println(System.currentTimeMillis()-t+" ms   ");
            pan.repaint();
            try {
                Thread.sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            simulation(Gen,duree-1);
        }
    }

    public class Panneau extends JPanel {

        /**Crée l'affichage de la fenêtre
         *
         * @param g Graphics utilisé
         */
        public void paintComponent(Graphics g) {
            initAffichage(g);
            affichage(Gen.getListe(),g);
        }

        /**Initialise la fenêtre en effaçant l'image précédente
         * et en mettant le nouveau numéro de génération
         *
         * @param g Graphics
         */
        private void initAffichage(Graphics g){
            g.setColor(Color.white);
            g.fillRect(0,0,this.getWidth(),this.getHeight());
            Font font= new Font("Génération: "+Gen.getNumGen(),Font.BOLD,15);
            g.setFont(font);
            g.setColor(Color.red);
            g.drawString("Génération: "+Gen.getNumGen(), 15, 15);
        }

        /**Affiche la nouvelle génération
         *
         * @param t liste de cellule à afficher
         * @param g Graphics
         */
        private void affichage(Liste<Cellule> t, Graphics g){
            int taille=4;
            for (Cellule m : t) {
                g.setColor(Color.black);
                g.fillOval(m.getX() * taille - taille + 20 + decX, m.getY() * taille - taille + 20 + decY, taille, taille);
            }
        }
    }
}

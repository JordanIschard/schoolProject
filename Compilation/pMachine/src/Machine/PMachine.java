package Machine;

import Instructions.Instruction;
import java.util.Stack;

import static java.lang.Math.round;

/**
 * Simulation de la PMachine
 *
 * @author jordan
 * @since 27-03-2020
 */
public class PMachine {

    private Instruction[] pcode;
    private Stack<Integer> mem = new Stack<>();
    private int PC = 0;
    private boolean run = false;
    private int mainShift = 0;


    /**
     * Constructeur
     *
     * @param pcode la liste des instructions
     */
    public PMachine(Instruction[] pcode) { this.pcode = pcode; }


    /**
     * Exécute les instructions
     */
    public void exec() {
        run = true;
        while(run) {
            //if (debug) System.out.println("\nInstruction : "+pcode[PC].toString());

            pcode[PC].exec(this);

            //if (debug) printMem();
        }
    }

    /**
     * Convertit le pcode en chaîne de caractères
     *
     * @return la chaîne de caractères représentant le pcode
     */
    public String toString() {
        StringBuilder res = new StringBuilder();

        for (int i = 0; i < pcode.length; i++)
            res.append(i).append("\t").append(pcode[i]).append("\n");

        return res.toString();
    }


    /**
     * Arrête l'exécution du pcode
     */
    public void stop() { run = false; }

    /**
     * Retourne l'élément à la position adr
     *
     * @param adr l'adresse où l'on veut récupérer l'information
     * @return l'élément mis à la position adr
     */
    public int get(int adr) { return mem.get(adr); }

    /**
     * Place le pointeur à l'adresse indiquée
     *
     * @param adr une adresse
     */
    public void goTo(int adr) { PC = adr; }

    /**
     * Remplace l'élément à l'indice indiqué
     *
     * @param index l'indice de l'élément que l'on veut remplacer
     * @param element le nouvelle élément
     */
    public void replace(int index,int element){
        //System.out.println(index+" "+element);
        mem.set(index,element);
        //System.out.println(mem);
    }

    /**
     * Prend l'élément en tête de la pile
     *
     * @return l'élément en tête de pile
     */
    public int pop(){ return mem.pop(); }

    /**
     * Ajoute un élément en tête de pile
     *
     * @param n l'élément à ajouter
     */
    public void push(int n){ mem.push(n); }

    /**
     * Passe à l'instruction suivante
     */
    public void next(){ PC++; }


    public void setMainShift(int mainShift) {
        this.mainShift = mainShift;
    }

    public int getMainShift() {
        return mainShift;
    }

    private int secret(int i){
        if(i/10 < 1) return 1;
        else return 1+secret(i/10);
    }

    private String secret1(int i){
        String res ="";
        for (int j = 0; j < i; j++) {
            res += "=";
        }
        return res;
    }

    private String secret2(int a,int i){
        String res ="";
        if(i > 1) {
            for (int j = 0; j < i; j++) {
                if (j == round(i / 2) -1) {
                    res += a;
                } else {
                    res += " ";
                }
            }
        }else{ res = String.valueOf(a); }
        return res;
    }
    /**
     * Affiche l'état de la mémoire
     */
    void printMem() {

        System.out.println("\n===========\n  Mémoire  \n===========\n");


        System.out.print(" ");
        for (int z = 0; z < mem.size() ;z++) {

            if(z/10 < 1)
                System.out.print(" " + secret2(z,secret(mem.get(z))) + "  ");
            else
                System.out.print(" " + secret2(z,secret(mem.get(z))) + " ");
        }
        System.out.print("\n=");

        for (Integer i : mem) {

            System.out.print("=" + secret1(secret(i)) + "==");
        }
        System.out.print("\n|");
        for (Integer i : mem) {

            System.out.print(" " + i + " |");
        }
        System.out.print("\n=");
        for (Integer i : mem) {

            System.out.print("=" + secret1(secret(i)) + "==");
        }
    }

    public int memSize() { return mem.size(); }
}

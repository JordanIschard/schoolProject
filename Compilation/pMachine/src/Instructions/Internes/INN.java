package Instructions.Internes;

import Instructions.Instruction;
import Machine.PMachine;

import java.util.Scanner;


/**
 * Classe qui implémente l'entrée texte
 * pour la PMachine
 *
 * Implémente la classe {@link Instruction}
 *
 * @author jordan
 * @since 20-04-2020
 */
public class INN implements Instruction {

    private int adr,x;
    private Scanner scanner = new Scanner(System.in);


    /**
     * Constructeur sans paramètre
     */
    public INN(){}


    /**
     * Fonction requise pour utiliser l'interface {@link Instruction}
     *
     * L'instruction prend une entier en entrée et le place dans l'adresse placé en tête de la pile
     *
     * @param pMachine la machine qui interprète le langage pascal
     */
    @Override
    public void exec(PMachine pMachine){


        adr = pMachine.pop();
        boolean notTake = true;
        do {
            try {
                System.out.print("< ");
                String tmp = scanner.next();
                x = Integer.parseInt(tmp);
            } catch (NumberFormatException e) {
                System.out.println("Error Bad entry. Give an integer please.\n");
                continue;
            }
            notTake = false;
        }while(notTake);

        pMachine.replace(adr,x);
        pMachine.next();
    }


    /**
     * Convertit l'instruction en chaîne de caractères
     *
     * @return la chaîne de caractères
     */
    @Override
    public String toString(){
        return "INN "+adr+" "+x;
    }
}

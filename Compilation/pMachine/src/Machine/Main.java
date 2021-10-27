package Machine;

import Exceptions.SyntaxErrorException;
import TablesSymboles.SymbolTable;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.misc.Pair;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Classe principale
 *
 * @author jordan (basé sur celle de Jean-Michel Couvreur)
 * @since 27-03-2020
 */
public class Main {

    private final static String EXAMPLESFOLDER = "src/examples/";

    static void printSymbolTable(HashMap<String, Pair<String, SymbolTable>> symbolTable){
        System.out.println("\n======================\n  Table des Symboles  \n======================\n\n");
        for (Map.Entry<String,Pair<String,SymbolTable>> symbolTableEntry : symbolTable.entrySet()) {
            String shift = "   ";
            System.out.println("------------\n  Procédure  \n------------\n"+symbolTableEntry.getKey()+" : init dans "+symbolTableEntry.getValue().a);
            symbolTableEntry.getValue().b.print(shift);
            System.out.println("\n");
        }
    }

    /**
     * Lance la PMachine
     *
     * @param args les arguments données en lignes de commande
     */
    public static void main(String[] args) {

        File folder = new File(EXAMPLESFOLDER);
        String[] filesList = folder.list();
        String file = "";
        Scanner sc = new Scanner(System.in);
        String debug;
        boolean notchoose = true;

        System.out.println("Bonjour et bienvenue dans l'interface intéractive de la PMachine !");

        do {

            System.out.println("\nVeuillez choisir un fichier test parmi les fichiers proposés :");
            assert filesList != null;
            for (int i = 0; i < filesList.length; i++) {
                System.out.println("    "+(i+1)+"- "+filesList[i]);
            }
            System.out.println("\nAfin de choisir le fichier que vous voulez, tapez son index");

            String tmp = sc.next();
            int index;
            try {
                index = Integer.parseInt(tmp);
            } catch (NumberFormatException e){
                System.out.println("Erreur ce n'est pas un entier qui a été donné !\n\n\n");
                continue;
            }

            if(index-1 < 0 || index-1 >= filesList.length){
                System.out.println("Erreur mauvaise index !\n\n\n");
                continue;
            }

            notchoose = false;
            file = EXAMPLESFOLDER+filesList[index-1];
            System.out.println("Vous avez choisi : "+file+"\n");

            try {
                System.out.println("\n=============\n  Programme  \n=============\n\n");
                BufferedReader in = new BufferedReader(new FileReader(file));
                System.out.println("------------------------------------------------");
                in.lines().forEach(System.out::println);
                System.out.println("------------------------------------------------\n");
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }while(notchoose);

        try
        {
            CharStream input = CharStreams.fromFileName(file);
            Machine.PascalLexer lexer = new Machine.PascalLexer(input);
            CommonTokenStream tokens = new CommonTokenStream(lexer);
            Machine.PascalParser parser = new Machine.PascalParser(tokens);
            Program program = parser.lire();
            PMachine machine = new PMachine(program.getPCode());


            do {
                System.out.print("Debug ? (Y/N) ");
                debug = sc.next().toUpperCase();
                if(!debug.matches("[YN]")) System.out.println("Entrée invalide !\nChoisissez entre Y et N");

            }while (!debug.matches("[YN]"));

            machine.exec();

            if(debug.equals("Y")) {
                System.out.println("\n==============\n  Assembleur  \n==============\n\n" + machine + "\n");
                printSymbolTable(program.getSymbolTables());
                machine.printMem();
            }
        }
        catch (SyntaxErrorException ignored) {}
        catch (IOException e) { e.printStackTrace(); }

    }
}

package Machine;

import Exceptions.SymbolNotFoundException;
import Exceptions.VariableAlReadyUsedException;
import Instructions.Instruction;
import TablesSymboles.*;
import TablesSymboles.Types.*;
import TablesSymboles.Types.Boolean;
import org.antlr.v4.runtime.misc.Pair;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Classe gérant le programme pascal
 *
 * @author jordan
 * @since 27-03-2020
 */
public class Program {

    private final static String MAIN = "main";
    final static String TYPEPERSO = "type";
    final static String PROCEDURE = "procedure";
    final static String VARIABLE = "variable";
    final static String PARAMETER = "parameter";

    private HashMap<String, Pair<String, SymbolTable>> tableList;
    private String current;
    private ArrayList<Instruction> instructions;
    private ArrayList<String> listError;


    /**
     * Constructeur sans paramètre
     */
    public Program(){
        tableList = new HashMap<>();
        tableList.put(MAIN,new Pair<>(null,new SymbolTable()));
        current = MAIN;
        instructions = new ArrayList<>();
        listError = new ArrayList<>();
    }





    // Gestion des tables





    /**
     * Change la table courante par sa table père
     */
    void upLevel() {
        current = tableList.get(current).a;
    }


    /**
     * Change la table courante par la table de la procédure courante
     *
     * @param procedure le nom de la procédure courante
     */
    void downLevel(String procedure) {
        if (tableList.containsKey(procedure)) {
            current = procedure;
        } else {
            listError.add(procedure + " is not initialized");
        }
    }


    /**
     * Ajoute dans la table courante
     *
     * @param var le nom du symbole à ajouter
     * @param data le type du symbole à ajouter
     * @param procVarTyp la position du nouveau symbole dans la table courante
     */
    void putInTable(String var, Object data,String procVarTyp) {
        try {
            switch(procVarTyp) {
                case VARIABLE : Type type = (Type)data;
                                if(type instanceof Tableau){
                                    Tableau t = ((Tableau) type);
                                    if(t.size() <= 0){
                                        listError.add(var+" has a negative or null length");
                                        t = new Tableau(0,1,t.getType());
                                    }
                                    currentTable().putVariable(var,t);
                                }else{  currentTable().putVariable(var,(Type)data); }
                                break;

                case PROCEDURE : currentTable().putVariable(var,(Type)data);
                                 tableList.put(var,new Pair<>(current,new SymbolTable()));
                                 break;

                case TYPEPERSO : currentTable().putType(var,(Type)data);
                                 break;

                case PARAMETER : currentTable().putParameter(var,(Parameter)data);
            }
        } catch (VariableAlReadyUsedException e) { listError.add(e.getMessage()); }
    }



    /**
     * Retourne la taille de la table des symboles
     *
     * @return la taille
     */
    int getSizeSymbolsTable() { return currentTable().getSizeTableVariable(); }


    /**
     * Retourne les tables des symboles
     *
     * @return les tables
     */
    HashMap<String,Pair<String, SymbolTable>> getSymbolTables() { return tableList; }


    /**
     * Retourne le décalage pour les adresses liées à une procédure
     *
     * @param c le nom de la procédure courante
     *
     * @return le décalage
     */
    int shiftCurrentProc(Object... c) {
        Pair<String, SymbolTable> tmp;
        String name;
        if(c.length > 0){
            name = (String) c[0];
            tmp = tableList.get(name);
        }else{
            tmp = tableList.get(current);
            name = current;
        }

        if(name.equals(MAIN)){ return 0; }
        else{
            try {
                Data d = tableList.get(tmp.a).b.getElemVariable(name);
                return ((Procedure)d.getType()).getShift() + shiftCurrentProc(tmp.a);
            } catch (SymbolNotFoundException e) {
                System.out.println("not found");
                return 0;
            }
        }
    }


    /**
     * Retourne l'adresse et le type d'un sous élément
     *
     * @param var le nom de la variable
     * @param subVar le nom du champ
     *
     * @return une pair adresse, type
     */
    Data getSubElementData(String var, String subVar) {

        try {
            Object e = currentTable().getTypeVariable(var);

            if(e instanceof Record){
                Record enr = (Record)e;
                return enr.getSubData(subVar);
            }else{
                listError.add(var + " is not a record or a type");
                return new Data(-1, new Entier());
            }

        } catch (SymbolNotFoundException e) {
            listError.add(e.getMessage());
            return new Data(-1,new Entier());
        }
    }


    /**
     * Retourne le type des éléments d'un tableau
     *
     * @param var le nom du tableau
     * @param t le type
     *
     * @return null si var n'est pas un tableau, un type sinon
     */
    Type getTypeElemList(String var,Type t) {

        if(t instanceof Tableau){
            return ((Tableau)t).getType();
        }else{
            listError.add(var+" is not a list");
            return new Entier();
        }
    }


    /** Retourne la table des symboles courante
     *
     * @return la table courante
     */
    private SymbolTable currentTable(){ return tableList.get(current).b; }


    /**
     * Retourne l'adresse et le type d'un élément
     *
     * @param var le nom de la variable
     *
     * @return une pair adresse, type
     */
    Data getData(String var,String... types) {
        for (String type : types) {
            try{
                switch (type) {
                    case TYPEPERSO : return currentTable().getElemType(var);

                    case VARIABLE : return  currentTable().getElemVariable(var);

                    case PARAMETER : return  currentTable().getElemParam(var);
                }
            } catch (SymbolNotFoundException ignored) {}
        }
        listError.add(var+" not found");
        return new Data(-1,new Entier());
    }


    /**
     * Retourne le décalage pour un tableau
     *
     * @param var le symbole du tableau
     * @param t le type qui est sensé être un tableau
     *
     * @return le décalage
     */
    int getBeginList(String var, Type t) {
        if(t instanceof Tableau){
            return ((Tableau)t).getBegin();
        }else{
            listError.add(var+" is not a list");
            return 0;
        }
    }





    // Gestion des procédures




    /**
     * L'adresse de la procédure dans la pile
     *
     * @param var le symbole de la procédure
     * @param current la procédure courante
     *
     * @return l'adresse
     */
    int callProc(String var, Object... current) {
        Pair<String, SymbolTable> tmp;
        if(current.length > 0) {
            String name = (String)current[0];
            tmp = tableList.get(name);
        }else {
            tmp = tableList.get(this.current);
        }
        try {
            return ((Procedure)tmp.b.getTypeVariable(var)).getAdr();
        } catch (SymbolNotFoundException e) {
            String father = tmp.a;
            if(father == null) {
                listError.add(e.getMessage());
                return  0;
            }else {
                return callProc(var, father);
            }
        }
    }


    /**
     * Vérifie si il y a des paramètre pour la procédure ou non
     *
     * @param symbol le symbole qui référence la procédure dans la table
     *
     * @return vrai si il n'a pas de paramètre et faux sinon
     */
    boolean hasNoParam(String symbol) { return tableList.get(symbol).b.hasNoParam(); }


    /**
     * Retourne le nombre de paramètre d'une procédure
     *
     * @param symbol le symbole qui référence la procédure dans la table
     *
     * @return le nombre de paramètre de la procédure
     */
    int getNbrParam(String symbol){ return  tableList.get(symbol).b.getSizeTableParameter(); }


    /**
     * Vérifie si le paramètre à une position donnée est un passage par valeur ou non
     *
     * @param symbol le symbole qui référence la procédure dans la table
     * @param current la position du paramètre dans la liste
     *
     * @return vrai si le paramètre est passé par valeur, faux sinon
     */
    boolean byValue(String symbol,int current) {
        try {
            return !((Parameter)tableList.get(symbol).b.getParam(current).getType()).isReference();
        } catch (SymbolNotFoundException e) {
            listError.add(e.getMessage());
            return false;
        }
    }


    /**
     * Retourne la position dans la liste de paramètre via son symbole
     *
     * @param symbol le symbole qui référence le paramètre dans la procédure
     *
     * @return la position du paramètre dans la liste
     */
    int getPosParameter(String symbol) {
        try { return currentTable().getPosParam(symbol); }
        catch (SymbolNotFoundException e) {
            listError.add(e.getMessage());
            return -1;
        }
    }





    // Gestion des erreurs




    /**
     * Vérifie si il y a des erreurs
     *
     * @return true si il y a au moins une erreur, false sinon
     */
    boolean HasError(){ return listError.size() > 0; }


    /**
     * Ajoute un message d'erreur
     *
     * @param e le message
     */
    void addError(String e){ listError.add(e); }


    /**
     * Affiche la liste des erreurs
     *
     */
    void printErr(){ listError.forEach(System.err::println); }




    // Gestion des instructions


    /**
     * Ajoute une collection d'instructions
     *
     * @param insts la collection d'instructions
     */
    void addPCode(Collection<Instruction> insts){ insts.forEach(this::addPCode); }


    /**
     * Ajoute une instruction
     *
     * @param inst l'instruction à ajouter
     */
    void addPCode(Instruction inst){ instructions.add(inst); }


    /**
     * Retourne une liste de toutes les instructions
     *
     * @return la liste des instructions
     */
    Instruction[] getPCode(){ return instructions.toArray(new Instruction[]{}); }


    /**
     * Retire l'instruction en tête de pile
     */
    void removeHead(){ instructions.remove(instructions.size()-1); }


    /**
     * Donne le nombre d'instructions
     *
     * @return le nombre d'instructions
     */
    int getNbrInstruction() { return instructions.size(); }


    /**
     * Copie une partie délimité des instructions et la recopie à
     * la fin de la liste des instructions
     *
     * @param debut la limite "gauche"
     * @param fin la limite "droite"
     */
    void copySetInstruction(int debut, int fin) { instructions.addAll(instructions.subList(debut,fin)); }


    /**
     * Indique si le type est un booléen
     *
     * @param t le type a vérifier
     *
     * @return vrai si c'est un booléen, faux sinon
     */
    boolean isBoolean(Type t) {
        return (t instanceof Boolean);
    }
}


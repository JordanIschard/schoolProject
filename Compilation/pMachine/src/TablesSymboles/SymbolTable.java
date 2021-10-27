package TablesSymboles;

import Exceptions.SymbolNotFoundException;
import Exceptions.VariableAlReadyUsedException;
import TablesSymboles.Types.Parameter;
import TablesSymboles.Types.Type;
import org.antlr.v4.runtime.misc.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Représente une table des symboles
 *
 * @author jordan
 * @since 27-03-2020
 */
public class SymbolTable {

    private HashMap<String, Data> tableVariable;
    private HashMap<String, Data> tableType;
    private ArrayList<Pair<String, Data>> tableParameter;
    private int sizeTableVariable;
    private int sizeTableType;

    /**
     * Constructeur
     */
    public SymbolTable(){
        this.tableParameter = new ArrayList<>();
        this.tableVariable = new HashMap<>();
        this.tableType = new HashMap<>();
        this.sizeTableVariable = 0;
        this.sizeTableType= 0;
    }

    /**
     * Ajoute une donnée dans la table
     *
     * @param variable le nom de la variable
     * @param type le type de la variable
     * @throws VariableAlReadyUsedException la variable est déjà utilisée
     */
    public void putVariable(String variable, Type type) throws VariableAlReadyUsedException {
        if(this.existVariable(variable)){ throw new VariableAlReadyUsedException(variable+" already used"); }
        tableVariable.put(variable, new Data(sizeTableVariable,type));
        sizeTableVariable += type.size();
    }

    /**
     * Ajoute un type dans la table
     * @param variable le nom du type
     * @param type le type
     * @throws VariableAlReadyUsedException la variable est déjà utilisée
     */
    public void putType(String variable, Type type) throws VariableAlReadyUsedException {
        if(this.existType(variable)){ throw new VariableAlReadyUsedException(variable+" already used"); }
        tableType.put(variable, new Data(sizeTableType,type));
        sizeTableType += type.size();
    }

    /**
     * Ajoute une donnée dans la table
     *
     * @param variable le nom de la variable
     * @param param le type de la variable
     * @throws VariableAlReadyUsedException la variable est déjà utilisée
     */
    public void putParameter(String variable, Parameter param) throws VariableAlReadyUsedException {
        if(this.existVariable(variable)){ throw new VariableAlReadyUsedException(variable+" already used"); }
        int ad = -1;
        tableParameter.add(new Pair<>(variable, new Data(ad,param)));
    }


    /**
     * Vérifie si la variable existe dans la table
     *
     * @param variable la variable que l'on veut vérifier
     *
     * @return true si la variable existe dans la table, false sinon
     */
    private Boolean existVariable(String variable){ return tableVariable.containsKey(variable); }


    /**
     * Vérifie si un type existe dans la table
     *
     * @param variable le nom du type
     *
     * @return vrai si le type existe, faux sinon
     */
    private Boolean existType(String variable){ return tableType.containsKey(variable); }


    /**
     * Retourne le type pour un nom donné
     *
     * @param variable variable dont on veut son type
     *
     * @return le type de la variable
     *
     * @throws SymbolNotFoundException levée si la variable n'est pas initialisée
     */
    public Type getTypeVariable(String variable) throws SymbolNotFoundException {
        if (existVariable(variable)) { return tableVariable.get(variable).getType(); }
        throw  new SymbolNotFoundException(variable+" is not initialized");
    }


    /**
     * Retourne la taille de la table
     *
     * @return la taille
     */
    public int getSizeTableVariable() { return sizeTableVariable; }


    /**
     * Retourne l'adresse et le type d'une variable
     *
     * @param var le nom de la variable
     *
     * @return une pair adresse, type
     *
     * @throws SymbolNotFoundException On n'a pas trouvé le nom dans la table
     */
    public Data getElemVariable(String var) throws SymbolNotFoundException { return getElem(var,tableVariable); }

    /**
     * Retourne l'adresse et le type d'une variable
     *
     * @param var le nom de la variable
     *
     * @return une pair adresse, type
     *
     * @throws SymbolNotFoundException On n'a pas trouvé le nom dans la table
     */
    public Data getElemType(String var) throws SymbolNotFoundException { return getElem(var,tableType); }

    /**
     * Retourne l'adresse et le type d'une variable
     *
     * @param var le nom de la variable
     *
     * @return une pair adresse, type
     *
     * @throws SymbolNotFoundException On n'a pas trouvé le nom dans la table
     */
    public Data getElemParam(String var) throws SymbolNotFoundException {
        for (Pair<String,Data> elem :tableParameter) {
            if(elem.a.equals(var)){ return elem.b; }
        }
        throw new SymbolNotFoundException(var+" not found");
    }

    /**
     * Retourne l'adresse et le type d'une variable
     *
     * @param var le nom de la variable
     *
     * @return une pair adresse, type
     *
     * @throws SymbolNotFoundException On n'a pas trouvé le nom dans la table
     */
    private Data getElem(String var, HashMap<String, Data> table) throws SymbolNotFoundException {
        if (table.containsKey(var))
            return table.get(var);
        throw new SymbolNotFoundException(var+" not found");
    }


    /**
     * Convertit la table des symboles en chaîne de caractères
     *
     * @return la chaîne de caractères représentant la table
     */
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder("=================\nTable des Symboles\n=================\n\n");
        for (Map.Entry<String, Data> e : tableVariable.entrySet()){
            res.append("    ").append(e.getKey()).append(" : ").append(e.getValue()).append("\n");
        }
        return res.append("}\n").toString();
    }

    /**
     * Affiche la table des symboles en entiers
     *
     * @param shift le décalage pour un affichage plus agréable
     */
    public void print(String shift) {

        System.out.println(shift+"Variables : ");
        for (Map.Entry<String,Data> e : tableVariable.entrySet()) {
            System.out.print(shift+"    "+e.getKey()+" -> ");
            e.getValue().print(shift+"  ");
        }

        System.out.println(shift+"Types : ");
        for (Map.Entry<String,Data> e : tableType.entrySet()) {
            System.out.print(shift+"    "+e.getKey()+" -> ");
            e.getValue().print(shift+"  ");
        }

        System.out.println(shift+"Paramètres : ");
        for (Pair<String,Data> e : tableParameter) {
            System.out.print(shift+"    "+e.a+" -> ");
            e.b.print(shift+"  ");
        }
    }


    /**
     * Vérifie si la table qui gère les paramètres est vide
     * ou non
     *
     * @return vrai si elle est vide, faux sinon
     */
    public boolean hasNoParam() {
        return tableParameter.isEmpty();
    }


    /**
     * Retourne un paramètre par rapport à sa position dans la liste de paramètres
     *
     * @param current la position du paramètre dans la liste
     *
     * @throws SymbolNotFoundException il n'y a pas de paramètre à la position current
     *
     * @return les données du paramètre recherché
     */
    public Data getParam(int current) throws SymbolNotFoundException {
        int tmp = 0;
        for (Pair<String,Data> e : tableParameter) {
            if(current == tmp){
                return e.b;
            }
            tmp ++;
        }
        throw new SymbolNotFoundException(current+" not found");
    }

    /**
     * Retourne la taille de la table qui s'occupe des paramètres
     *
     * @return la taille
     */
    public int getSizeTableParameter() { return tableParameter.size(); }

    /**
     * Retourne la position d'un paramètre par rapport à son nom
     *
     * @param symbol le symbole représentant le paramètre
     *
     * @return la position du paramètre
     *
     * @throws SymbolNotFoundException le symbole n'a pas été trouvé dans la liste
     */
    public int getPosParam(String symbol) throws SymbolNotFoundException {
        for (int i = 0; i < tableParameter.size(); i++) {
            if(tableParameter.get(i).a.equals(symbol))
                return i;
        }
        throw new SymbolNotFoundException(symbol+" not found");
    }
}

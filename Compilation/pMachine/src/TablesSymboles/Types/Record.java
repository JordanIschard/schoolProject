package TablesSymboles.Types;

import Exceptions.SymbolNotFoundException;
import Exceptions.VariableAlReadyUsedException;
import TablesSymboles.Data;
import org.antlr.v4.runtime.misc.Pair;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implémente les enregistrements dans la PMachine
 *
 * Implémente la classe {@link Type}
 *
 * @author jordan
 * @since 27-03-2020
 */
public class Record implements Type {

    private HashMap<String, Data> variables = new HashMap<>();
    private int size;

    /**
     * Constructeur
     *
     * @param vars la liste des champs
     * @throws VariableAlReadyUsedException un nom de variable est dupliqué
     */
    public Record(List<Pair<String,Type>> vars) throws VariableAlReadyUsedException {
        for(Pair<String,Type> var : vars){
            if(variables.containsKey(var.a)){ throw new VariableAlReadyUsedException(var.a+" is already use");}
            variables.put(var.a,new Data(size,var.b));
            size += var.b.size();
        }
    }

    /**
     * Méthode surchargée de l'interface {@link Type}
     *
     * @return la taille d'un booléen
     */
    @Override
    public int size() { return size; }

    @Override
    public void print(String s) {
        System.out.println("Enregistrement :: ");
        for(Map.Entry<String,Data> var : variables.entrySet()){
            System.out.print(s+var.getKey()+" -> ");
            var.getValue().print(s+"    ");
        }
    }

    /**
     * Convertit un booléen en chaîne de caractères
     *
     * @return une chaîne de caractères
     */
    public String toString() {
        return "Enregistrement\n"+variables.toString();
    }

    /**
     * Retourne l'adresse d'un champ
     * @param subElem le champ
     * @return l'adresse du champ donné en paramètre
     * @throws SymbolNotFoundException le nom du champ n'existe pas
     */
    public Data getSubData(String subElem) throws SymbolNotFoundException {
        if(variables.containsKey(subElem)){
            return variables.get(subElem);
        }
        throw new SymbolNotFoundException(subElem+" is not defined in our record.");
    }

}

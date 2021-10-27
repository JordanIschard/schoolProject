package TablesSymboles;

import TablesSymboles.Types.Type;

/**
 * Classe qui représente les données stockées dans la table
 *
 * @author jordan
 * @since 27-03-2020
 */
public class Data {

    private int adr;
    private Type type;

    /**
     * Constructeur
     *
     * @param adr l'adresse de la donnée
     * @param type le type de la donnée
     */
    public Data(int adr, Type type){
        this.adr = adr;
        this.type = type;
    }

    /**
     * Retourne l'adresse de la donnée
     *
     * @return l'adresse
     */
    public int getAdr() { return adr; }

    /**
     * Met à jour l'adresse de la donnée
     *
     * @param adr la nouvelle adresse
     */
    public void setAdr(int adr) { this.adr = adr; }

    /**
     * Retourne le type de la donnée
     *
     * @return le type
     */
    public Type getType() { return type; }

    /**
     * Met à jour le type de la donnée
     *
     * @param type le nouveau type
     */
    public void setType(Type type) { this.type = type; }

    /**
     * Convertit la donnée en chaîne de caractères
     *
     * @return la chaîne de caractères
     */
    @Override
    public String toString() { return "< adr = " + adr + ", type = " + type + " >"; }


    public void print(String s) {
        System.out.print("| adr : "+adr+" -- ");
        type.print(s+"           ");
        System.out.println(" |");
    }
}

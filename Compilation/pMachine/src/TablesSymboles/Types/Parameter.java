package TablesSymboles.Types;

public class Parameter implements Type {

    private Type type;
    private boolean isReference ;

    public Parameter(){}

    public Parameter(int ad, Type type, boolean isRef){
        this.type = type;
        this.isReference = isRef;
    }

    @Override
    public int size() {
        if(isReference){ return 0; }
        return type.size();
    }

    @Override
    public void print(String s) {
        System.out.print("Paramètre : ref -> "+isReference+" Type :");
        type.print(s+"      ");

    }

    @Override
    public String toString() {
        return "Paramètre : "+" ref -> "+isReference+" type -> "+type;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isReference() {
        return isReference;
    }

    public void setReference(boolean reference) {
        isReference = reference;
    }
}

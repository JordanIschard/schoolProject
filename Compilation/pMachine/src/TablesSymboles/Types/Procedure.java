package TablesSymboles.Types;

public class Procedure implements Type {

    private int adr;
    private int shift;

    public Procedure(int adr,int shift){
        this.adr = adr;
        this.shift = shift;
    }

    @Override
    public int size() {
        return 1;
    }

    @Override
    public void print(String s) {
        System.out.print("Procédure : adr : "+adr+" - dec : "+shift);
    }

    public int getShift() { return shift; }

    public int getAdr(){
        return adr;
    }

    @Override
    public String toString() {
        return "Procedure "+adr+" "+shift;
    }
}

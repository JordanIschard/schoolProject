package pattern;

import java.util.ArrayList;
import java.util.List;

public class Observateur{
    
    private List<Sujet> sujets;

    public Observateur() { sujets = new ArrayList<>(); }

    protected void addSujet(Sujet sujet){ sujets.add(sujet); }

    protected void deleteSujet(Sujet sujet){ sujets.remove(sujet); }

    protected void deleteAllSujet(){ sujets.clear(); }
    
    public void notifier(Object o){
        for ( Sujet sujet: sujets ) {
            if(!o.equals(sujet)){ sujet.maj(this,null); }
        }
    }

}

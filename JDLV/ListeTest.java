package JDLV;

import org.junit.Assert;

import java.util.Collections;
import java.util.LinkedList;

/**Classe de test JUnit
 *
 * @author Mathieu et Maëlys
 */
public class ListeTest {

    private Liste<Integer> l = new Liste<>();
    private LinkedList<Integer> lAPI = new LinkedList<>();

    public  void main(String[] args){
        Testadd();
        TestsetTete();
        TestsetNbrMaillon();
        Testtrie();
    }

    /**Test l'ajout
     */
    @org.junit.Test
    public void Testadd() {
        Assert.assertEquals(lAPI.size(), l.getNbrMaillon());
        l.add(1);
        lAPI.add(1);
        Assert.assertEquals(lAPI.size(), l.getNbrMaillon());
        Assert.assertEquals(lAPI.getFirst(), l.getTete().getValue());
    }

    /**Test le changement de
     * tête
     */
    @org.junit.Test
    public void TestsetTete() {
        l.add(1);
        lAPI.add(2);
        Maillon<Integer> m = new Maillon<>(2);
        l.setTete(m);
        Assert.assertEquals(lAPI.getFirst(), l.getTete().getValue());
    }

    /**Test le changement du nombre
     * de maillon
     */
    @org.junit.Test
    public void TestsetNbrMaillon() {
        lAPI.add(1);
        l.setNbrMaillon(1);
        Assert.assertEquals(lAPI.size(),l.getNbrMaillon());
    }

    /**Test le trie
     */
    @org.junit.Test
    public void Testtrie() {
        lAPI.add(1); lAPI.add(3);
        l.add(1); l.add(3);
        lAPI.add(2);
        Collections.sort(lAPI);
        l.trie(2);
        Maillon<Integer> tmp = l.getTete();
        for (int i=0; i<3; i++){
            Assert.assertEquals(lAPI.get(i), tmp.getValue());
            tmp = tmp.getNext();
        }
    }
}

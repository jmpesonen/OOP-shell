/*
    Tiedosto-luokka
    Joni Pesonen
*/
package oope2017ht.tiedot;

/**
    Tiedosto-luokka, joka sisältää tiedostoille ominaisia piirteitä.
    
    Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2017.
    
    @author Joni Pesonen,
    Luonnontieteiden tiedekunta, Tampereen yliopisto.
*/

public class Tiedosto extends Tieto {
    // Attribuutit
    /** Olion koko. */
    private int koko;
    
    // Rakentajat
    public Tiedosto(StringBuilder n, int k) {
        super(n);
        koko(k);
    }
    
    // Kopiorakentaja
    public Tiedosto(Tiedosto kopioitava) throws IllegalArgumentException {
        super(kopioitava);
        if (kopioitava instanceof Tiedosto) {
            koko = kopioitava.koko();
        }
        else
            throw new IllegalArgumentException();
    }
    
    // Aksessorit
    public void koko(int k) throws IllegalArgumentException {
        if (k >= 0)
            koko = k;
        else
            throw new IllegalArgumentException();
    }
    
    public int koko() {
        return koko;
    }
    
    // Metodit
    
    /** Palauttaa olion merkkijonoesityksen. Hyödyntää Tieto-luokan samannimistä metodia.
    
        @return olion merkkijonoesitys.
    */
    public String toString() {
        return super.toString() + " " + koko;
    }
}
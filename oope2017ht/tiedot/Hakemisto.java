/*
    Hakemisto-luokka
    Joni Pesonen
*/
package oope2017ht.tiedot;
import oope2017ht.omalista.*;
import fi.uta.csjola.oope.lista.*;
import apulaiset.Komennettava;

/**
    Hakemisto-luokka, joka sisältää tiedostoille ominaisia piirteitä.
    
    Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2017.
    
    @author Joni Pesonen,
    Luonnontieteiden tiedekunta, Tampereen yliopisto.
*/
public class Hakemisto extends Tieto implements Komennettava<Tieto> {
    // Attribuutit
    /** Lista, johon olion sisältämä hakemistorakenne talletetaan. */
    private OmaLista lista;
    /** Viite ylihakemistoon. */
    private Hakemisto ylihakemisto;
    
    
    // Rakentajat
    
    public Hakemisto() {
        super();
        ylihakemisto(null);
        lista = new OmaLista();
    }
    
    public Hakemisto(StringBuilder n, Hakemisto y) throws IllegalArgumentException {
        super(n);
        ylihakemisto(y);
        lista = new OmaLista();
    }
    
    
    // Aksessorit
    public void lista(OmaLista o) {
        lista = o;
    }
    
    public LinkitettyLista sisalto() {
        LinkitettyLista palautettavaLista = (LinkitettyLista)lista;
        return palautettavaLista;
    }
    
    public void ylihakemisto(Hakemisto y) {
        ylihakemisto = y;
    }
    
    public Hakemisto ylihakemisto() {
        return ylihakemisto;
    }
    
    // Metodit
    /** Palauttaa olion merkkijonoesityksen. Hyödyntää Tieto-luokan samannimistä metodia.
    
        @return olion merkkijonoesitys.
    */
    public String toString() {
        return super.toString() + "/ " + lista.koko();
    }
    
    // Rajapinnan toteutettavat metodit    
    
    /** Hakee listalta oliota, joka on samanniminen parametrin kanssa.
    
        @param haettava etsittävän olion nimi merkkijonona.
        @return Tieto-tyyppinen viite samannimiseen olioon, tai null, mikäli samannimistä ei löytynyt
        tai tapahtui virhe.
    */
    public Tieto hae(String haettava) {
        if (haettava != null) {
            try {
                Tiedosto vertailu = new Tiedosto(new StringBuilder(haettava), 1);
                return (Tieto)lista.hae(vertailu);
            }
            catch (Exception e) {
                return null;
            }
        }
        else
            return null;
    }
    
    /** Lisää listalle parametrinaan saaman olion, mikäli listalla ei ole jo samannimistä oliota.
    
        @param lisattava lisättävä olio.
        @return true, jos listalle lisäys onnistui, false, jos listalle lisäys epäonnistui.
    */
    public boolean lisaa(Tieto lisattava) {
        if (lisattava != null) {
            // Tutkitaan samannimisyyksien varalta
            boolean samanimi = false;
            for (int i = 0; i < lista.koko(); i++) {
                if (lista.alkio(i) != null) {
                    if (lisattava.equals(lista.alkio(i)))
                        samanimi = true;
                }
            }
            // Lisätään, jos ei samannimisyyttä
            if (!samanimi)
                return lista.lisaa(lisattava);
            else
                return false;
        }
        else
            return false;
    }
    
    /** Poistaa listalta olion, jolla on sama nimi parametrin kanssa.
    
        @param poistettava poistettavan olion nimi.
        @return Tieto-tyyppinen viite poistettavaan olioon, tai null, jos samannimistä ei löytynyt
        tai tapahtui virhe.
    */
    public Tieto poista(String poistettava) {
        if (poistettava != null) {
            try {
                Tiedosto vertailu = new Tiedosto(new StringBuilder(poistettava), 1);
                // Käy hae-operaatiolla läpi?
                // Käydään lista läpi ja poistetaan haettu alkio.
                for (int i = 0; i < lista.koko(); i++) {
                    if (lista.alkio(i) != null) {
                        if (vertailu.equals(lista.alkio(i))) {
                            return (Tieto)lista.poista(i);
                        }
                    }
                }
                return null;
            }
            catch (Exception e) {
                return null;
            }
        }
        else
            return null;
    }
}
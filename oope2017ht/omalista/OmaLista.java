/*
    Oma lista
    Joni Pesonen
*/
package oope2017ht.omalista;
import fi.uta.csjola.oope.lista.*;
import apulaiset.Ooperoiva;

/**
    OmaLista-luokka, jonka avulla säilötään hakemistorakenteen tiedot.
    
    Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2017.
    
    @author Joni Pesonen,
    Luonnontieteiden tiedekunta, Tampereen yliopisto.
*/
public class OmaLista extends LinkitettyLista implements Ooperoiva {
    
    // Käydään lista läpi silmukassa ja palautetaan vastaava alkio.
    /** Hakee listalta equals-mielessä vastaavaa alkiota.
    
        @param etsittava listalta etsittava olio.
        @return viite vastaavaan olioon, tai null, mikäli vastaavuutta ei löytynyt.
    */
    public Object hae(Object etsittava) {
        if (etsittava != null) {
            for (int i = 0; i < koko(); i++) {
                if (alkio(i) != null) {
                    if (etsittava.equals(alkio(i))) {
                        return alkio(i);
                    }
                }
            }
            return null;
        }
        else
            return null;
    }
    
    /** Lisää parametrina saamansa olion listan alkioksi.
    
        @param lisattava lisättävä olio.
        @return true, jos listalle lisäys onnistui, false, jos listalle lisäys epäonnistui.
    */
    @SuppressWarnings({"unchecked"})
    public boolean lisaa(Object lisattava) {
        if (lisattava != null) {
            boolean lisatty = false;
            // Jos lista on tyhjä, lisätään suoraan alkuun.
            // vaihda onkotyhjä operaatioksi
            if (koko() == 0) {
                lisaaAlkuun(lisattava);
                lisatty = true;
            }
            else {
                // Käydään listaa läpi solmu kerrallaan, aloittamalla päästä.
                Solmu nykyinenSolmu = paa();
                int i = 0;
                // Käydään läpi niin kauan, kun solmu ei ole null (niin kauan kuin listaa jäljellä),
                // tai kun listaan ei ole vielä lisätty mitään.
                while (nykyinenSolmu != null && lisatty == false) {
                    Comparable vertailtava = (Comparable)nykyinenSolmu.alkio();
                    
                    if (vertailtava.compareTo(lisattava) <= 0)
                        i++;
                    else
                        lisatty = super.lisaa(i, lisattava);
                        
                    nykyinenSolmu = nykyinenSolmu.seuraava();
                }
                // Jos on saavutettu listan pää, eikä lisäys ole onnistunut, lisätään manuaalisesti.
                if (nykyinenSolmu == null && lisatty == false) {
                    lisaaLoppuun(lisattava);
                    lisatty = true;
                }
            }
            return lisatty;
        }
        else
            return false;
    }
    
    /** Poistaa listalta parametriaan vastaavan olion.
    
        @param poisto poistettava olio.
        @return viite poistettuun olioon, tai null, mikäli vastaavuutta ei löytynyt listalta.
    */
    public Object poista(Object poisto) {        
        // Laskurimuuttuja i, jonka avulla tiedämme poistettavan alkion indeksiarvon.
        // Lippumuuttuja loydetty, jonka avulla pystymme katkaisemaan silmukan.
        int i = 0;
        boolean loydetty = false;
        
        // Tarkistetaan null-arvon ja tyhjän listan varalta ja käydään listaa läpi silmukassa.
        if (poisto != null && onkoTyhja() == false) {
            while (i < koko() && loydetty == false) {
                if (alkio(i).equals(poisto))
                    loydetty = true;
                else
                    i++;
            }
            
            // Hyödynnetään LinkitettyLista-luokan omaa poisto-operaatiota laskurimuuttujan
            // säilyttämän indeksiarvon avulla.
            if (loydetty)
                return super.poista(i);
            
            // Jos poistettavaa alkiota ei löydetty, palautetaan null.
            else
                return null;
        }
        
        // Jos parametri oli null tai lista tyhjä, palautetaan null.
        else
            return null;
    }
}
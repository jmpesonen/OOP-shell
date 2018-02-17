/*
    Komentotulkki
    Joni Pesonen
*/
package oope2017ht;
import oope2017ht.tiedot.*;
import oope2017ht.omalista.OmaLista;
import fi.uta.csjola.oope.lista.*;

/**
    Komentotulkki-luokka, jonka avulla suoritetaan käyttöliittymässä luetut komennot.
    
    Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2017.
    
    @author Joni Pesonen,
    Luonnontieteiden tiedekunta, Tampereen yliopisto.
*/
public class Komentotulkki {
    // Attribuutit
    /** Viite juurihakemistoon. */
    private Hakemisto juurihakemisto = new Hakemisto();
    /** Viite senhetkiseen hakemistoon. */
    private Hakemisto nykyinenHakemisto = new Hakemisto();
    
    //final String VIRHE = "Error!";
    
    // Rakentajat
    
    // Aksessorit
    
    public Hakemisto juurihakemisto() {
        return juurihakemisto;
    }
    
    public void nykyinenHakemisto(Hakemisto h) {
        nykyinenHakemisto = h;
    }
    
    public Hakemisto nykyinenHakemisto() {
        return nykyinenHakemisto;
    }
    
    // Metodit
    
    /** Hakee tiedostopolun ja palauttaa sen.
    
        @param tama senhetkinen hakemisto.
        @return senhetkinen hakemistopolku merkkijonona.
    */
    private String annaPolku(Hakemisto tama) {
        String polku = "";
        // Silmukka hakemistopolun luomiseen. Lisää ylihakemiston nimen polun alkuun.
        while (tama != null) {
            polku = tama.nimi() + "/" + polku;
            tama = tama.ylihakemisto();
        }
        return polku;
    }
    
    /** Aksessorin tapainen apumetodi polunmuodostajalle.
    
        @return kutsuttavan metodin palauttama hakemistopolku merkkijonona.
    */
    public String annaPolku() {
        return annaPolku(nykyinenHakemisto);
    }
    
    /** Asettaa käsiteltävän hakemiston viittaamaan juurihakemistoon. */
    public void nykyinenJuureksi() {
        nykyinenHakemisto = juurihakemisto;
    }
    
    /** Luo parametrin nimisen hakemiston. Kutsuu Hakemisto-luokan lisäysmetodia.
    
        @param s luotavan hakemiston nimi.
        @return true, jos lisäys onnistui, false, jos lisäys epäonnistui.
    */
    public boolean luoHakemisto(String s) {
        Hakemisto uusiHakemisto = new Hakemisto(new StringBuilder(s), nykyinenHakemisto);
        return nykyinenHakemisto.lisaa(uusiHakemisto);
    }
    
    /** Vaihtaa senhetkistä hakemistoa, jos löytyy parametrin niminen alihakemisto.
    
        @param hakemistonNimi kohdekansion nimi.
        @throws IllegalArgumentException mikäli haettua hakemistoa ei löytynyt (null).
    */
    public void vaihdaHakemisto(String hakemistonNimi) throws IllegalArgumentException {
        if (hakemistonNimi != null) {
            try {
                // Jos parametri oli ylihakemistoon viittaava syöte, siirrytään pystyttäessä ylihakemistoon.
                // Muuten etsitään nykyhakemistosta parametrin mukaista alihakemistoa, ja asetetaan
                // nykyhakemiston viite siihen. Toiminnon epäonnistuessa (null) heitetään virhe.
                String siirryYlos = "..";
                if (hakemistonNimi.equals(siirryYlos)) {
                    if (nykyinenHakemisto.ylihakemisto() != null)
                        nykyinenHakemisto = nykyinenHakemisto.ylihakemisto();
                    else
                        throw new IllegalArgumentException();
                }
                else {
                    Hakemisto apu = (Hakemisto)nykyinenHakemisto.hae(hakemistonNimi);
                    if (apu != null)
                        nykyinenHakemisto = (Hakemisto)nykyinenHakemisto.hae(hakemistonNimi);
                    else
                        throw new IllegalArgumentException();
                }
            }
            catch (IllegalArgumentException e) {
                throw new IllegalArgumentException();
            }
            catch (Exception e) {
                throw new IllegalArgumentException();
            }
        }
    }
    
    /** Luo parametrin nimisen tiedoston. Kutsuu Hakemisto-luokan lisäysmetodia.
    
        @param s luotavan tiedoston nimi.
        @param k luotavan tiedoston koko.
        @return true, jos lisäys onnistui, false, jos lisäys epäonnistui.
    */
    public boolean luoTiedosto(String s, int k) {
        Tiedosto uusiTiedosto = new Tiedosto(new StringBuilder(s), k);
        return nykyinenHakemisto.lisaa(uusiTiedosto);
    }
    
    /** Nimeää uudelleen kohdetiedoston tai -hakemiston.
    
        @param vanhaNimi nimeä vaihtavan olion nimi.
        @param uusiNimi olion uusi nimi.
        @return true, jos nimenvaihto onnistui, false, jos nimenvaihto epäonnistui.
        @throws IllegalArgumentException, mikäli lisäys epäonnistui esim. samannimisyyden vuoksi.
    */
    public boolean nimeaUudelleen(String vanhaNimi, String uusiNimi) throws IllegalArgumentException {
        if (vanhaNimi != null && uusiNimi != null) {
            boolean kopiointiOK = false;
            boolean poistoOK = false;
            // Jos parametrit ovat päteviä ja hakemistosta löytyy halutulla nimellä tieto, hyödynnetään
            // kopiointioperaatiota, joka lisää hakemistoon uudella nimellä halutun tiedon. Sen jälkeen
            // poistetaan vanhalla nimellä oleva tieto.
            if (nykyinenHakemisto.hae(vanhaNimi) != null) {
                // Suoritetaan try-catch-rakenteessa, sillä lisäys voi heittää poikkeuksen.
                try {
                    // Tarkistetaan, onko löydetty tieto Tiedosto vai Hakemisto. Kutsutaan senmukaisia operaatioita.
                    if (nykyinenHakemisto.hae(vanhaNimi) instanceof Tiedosto) {
                        kopiointiOK = kopioiTiedosto(vanhaNimi, uusiNimi);
                        poistoOK = poista(vanhaNimi);
                    }
                    else if (nykyinenHakemisto.hae(vanhaNimi) instanceof Hakemisto) {
                        kopiointiOK = kopioiHakemisto(vanhaNimi, uusiNimi);
                        poistoOK = poista(vanhaNimi);
                    }
                    return kopiointiOK;
                }
                // Poikkeuksen sattuessa heitetään se vielä eteenpäin käyttöliittymässä ratkaistavaksi.
                catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException();
                }
                catch (Exception e) {
                    throw new IllegalArgumentException();
                }
            }
            else
                throw new IllegalArgumentException();
        }
        else
            throw new IllegalArgumentException();
    }
    
    /** Listaa senhetkisen hakemiston sisällön.
    
        @throws IllegalArgumentException jos lista oli tyhjä (null).
    */
    public void listaa() throws IllegalArgumentException {
        // Otetaan nykyisen hakemiston sisältö talteen ja tulostetaan se for-silmukassa yksi alkio kerrallaan.
        LinkitettyLista lista = nykyinenHakemisto.sisalto();
        if (lista != null) {
            for (int i = 0; i < lista.koko(); i++)
                System.out.println(lista.alkio(i));
        }
        else
            throw new IllegalArgumentException();
    }
    
    /** Listaa parametrin nimisen tiedoston tai hakemiston tiedot.
    
        @param tiedonNimi etsittävän olion nimi.
        @throws IllegalArgumentException jos haettua kohdetta ei löytynyt (null).
    */
    public void listaa(String tiedonNimi) throws IllegalArgumentException {
        // Etsitään parametrin nimistä tietoa. Jos sellainen löytyy, tulostetaan sen merkkijonoesitys,
        // muussa tapauksessa heitetään poikkeus käyttöliittymän ratkaistavaksi.
        Tieto t = nykyinenHakemisto.hae(tiedonNimi);
        if (t != null)
            System.out.println(t.toString());
        else
            throw new IllegalArgumentException();
    }
    
    /** Kopioi kohdetiedoston.
    
        @param kopioitavanNimi kopioitavan tiedoston nimi.
        @param uusiNimi kopiolle annettava nimi.
        @return true, jos vaihto ja listalle lisäys onnistui, false, jos epäonnistui.
        @throws IllegalArgumentException jos lisäys epäonnistui tai kopioitavaa kohdetta ei löytynyt (null).
    */
    public boolean kopioiTiedosto(String kopioitavanNimi, String uusiNimi) throws IllegalArgumentException {
        if (kopioitavanNimi != null && uusiNimi != null) {
            // Etsitään ensimmäisen parametrin nimistä tiedostoa. Jos löytyy, hyödynnetään Tiedosto-luokan
            // kopiorakentajaa.
            Tiedosto apu = (Tiedosto)nykyinenHakemisto.hae(kopioitavanNimi);
            if (apu != null) {
                Tiedosto kopio = new Tiedosto(apu);
                kopio.nimi(new StringBuilder(uusiNimi));
                boolean lisaysOK = nykyinenHakemisto.lisaa(kopio);
                
                // Jos lisäys epäonnistuu esim. samannimisyyden vuoksi, heitetään poikkeus.
                if (lisaysOK)
                    return true;
                else
                    throw new IllegalArgumentException();
            }
            else
                throw new IllegalArgumentException();
        }
        else
            throw new IllegalArgumentException();
    }
    
    /** Apumetodi hakemiston kopioimiseen, jota hyödynnetään uudelleennimeämisen yhteydessä.
    
        @param kopioitavanNimi kopioitavan hakemiston nimi.
        @param uusiNimi kopiolle annettava nimi.
        @return true, jos vaihto ja listalle lisäys onnistui, false, jos epäonnistui.
        @throws IllegalArgumentException jos lisäys epäonnistui tai kopioitavaa kohdetta ei löytynyt (null).
    */
    public boolean kopioiHakemisto(String kopioitavanNimi, String uusiNimi) throws IllegalArgumentException {
        try {
            if (kopioitavanNimi != null && uusiNimi != null) {
                // Etsitään ensimmäisen parametrin nimistä hakemistoa. Jos löytyy, hyödynnetään Hakemisto-luokan
                // kopiorakentajaa.
                Hakemisto apu = (Hakemisto)nykyinenHakemisto.hae(kopioitavanNimi);
                if (apu != null) {
                    StringBuilder hakemistoNimi = new StringBuilder(apu.nimi());
                    Hakemisto kopio = new Hakemisto(hakemistoNimi, apu.ylihakemisto());
                    kopio.nimi(new StringBuilder(uusiNimi));
                    
                    // Otetaan talteen löydetyn hakemiston sisältö, ja asetetaan se myös kopion sisällöksi.
                    OmaLista sisalto = (OmaLista)apu.sisalto();
                    kopio.lista(sisalto);
                    
                    // Jos lisäys epäonnistuu esim. samannimisyyden vuoksi, heitetään poikkeus.
                    boolean onnistui = nykyinenHakemisto.lisaa(kopio);
                    if (onnistui)
                        return true;
                    else
                        throw new IllegalArgumentException();
                }
                else
                    throw new IllegalArgumentException();
            }
            else
                throw new IllegalArgumentException();
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
        catch (Exception e) {
            throw new IllegalArgumentException();
        }
    }
    
    /** Poistaa listalta parametrin nimisen olion. Hyödyntää Hakemisto-luokan poistometodia.
    
        @param poistettava poistettavan olion nimi.
        @return true, jos poisto onnistui, false, jos epäonnistui.
        @throws IllegalArgumentException jos poistettavaa kohdetta ei löytynyt (null).
    */
    public boolean poista(String poistettava) throws IllegalArgumentException {
        Tieto t = nykyinenHakemisto.poista(poistettava);
        // Jos parametrin mukaista tietoa ei löytynyt, heitetään poikkeus.
        if (t != null)
            return true;
        else
            throw new IllegalArgumentException();
    }
    
    /** Listaa rekursion avulla hakemistorakenteen näytölle.
    
        @param tama senhetkinen hakemisto.
        @return hakemistopolun merkkijonoesitys.
    */
    public String listaaRekursiolla(Hakemisto tama) {
        // Asetetaan apuviite senhetkisen hakemiston sisältöön.
        OmaLista sisalto = (OmaLista)tama.sisalto();
        int i = 0;
        String p = "";
        while (i < sisalto.koko()) {
            // Asetetaan apuviite läpikäytävään alkioon. Tulostetaan senhetkinen polku.
            // Suoritetaan metodi uudelleen, jos läpikäytävä alkio on hakemisto.
            Tieto t = (Tieto)sisalto.alkio(i);
            System.out.print(annaPolku(tama));
            System.out.println(sisalto.alkio(i));
            if (t instanceof Hakemisto) {
                Hakemisto apu = (Hakemisto)t;
                p = p + listaaRekursiolla(apu);
            }
            i++;
        }
        return p;
    }
    
    /** Apumetodi rekursiivisen listauksen kutsumiseen luokan ulkopuolelta,
        kun nykyinen hakemisto ei ole tiedossa.
        
        @return hakemistopolun merkkijonoesitys.
    */
    public String listaaRekursiolla() {
        return listaaRekursiolla(nykyinenHakemisto);
    }
}
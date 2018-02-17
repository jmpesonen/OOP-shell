/*
    Abstrakti Tieto-luokka
    Joni Pesonen
*/
package oope2017ht.tiedot;

/**
    Abstrakti Tieto-luokka, joka sisältää yhteisiä piirteitä tallennettaville tiedoille.
    
    Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2017.
    
    @author Joni Pesonen,
    Luonnontieteiden tiedekunta, Tampereen yliopisto.
*/

public abstract class Tieto implements Comparable<Tieto> {
    // Attribuutit
    
    /** Olion nimi */
    private StringBuilder nimi = new StringBuilder();
    
    // Rakentajat
    public Tieto() {
        nimi = new StringBuilder("");
    }
    
    public Tieto(StringBuilder n) throws IllegalArgumentException {
        if (n != null) {
            try {
                nimi(n);
            }
            catch(IllegalArgumentException e) {
                throw new IllegalArgumentException();
            }
        }
    }
    
    // Kopiorakentaja
    public Tieto(Tieto kopioitava) throws IllegalArgumentException {
        if (kopioitava instanceof Tieto)
            //nimi = kopioitava.nimi();
            nimi = new StringBuilder(kopioitava.nimi);
        else
            throw new IllegalArgumentException();
    }
    
    // Aksessorit
    public void nimi(StringBuilder sb) throws IllegalArgumentException {
        try {
            if (nimiOK(sb))
                nimi = sb;
        }
        catch(IllegalArgumentException e) {
            throw new IllegalArgumentException();
        }
    }
    
    public StringBuilder nimi() {
        return nimi;
    }
    
    // Metodit
    
    // Tarkistetaan nimen oikeellisuus
    
    /** Metodi, jossa tarkistetaan, että merkkijono täyttää tarvittavat vaatimukset.
    
        @param tarkistettavaNimi tarkistettava merkkijono.
        @return true, jos selvittiin tarkastuksista.
        @throws IllegalArgumentException jos parametrissä havaittiin virheitä.
    */
    public boolean nimiOK(StringBuilder tarkistettavaNimi) throws IllegalArgumentException {
        int pistelaskuri = 0;
        for (int i = 0; i < tarkistettavaNimi.length(); i++) {
            char merkki = tarkistettavaNimi.charAt(i);
            boolean pieni = ('a' <= merkki && merkki <= 'z');
            boolean iso = ('A' <= merkki && merkki <= 'Z');
            boolean numerot = ('0' <= merkki && merkki <= '9');
            boolean alaviiva = (merkki == '_');
            boolean piste = (merkki == '.');
            
            // Suoritus keskeytetään, jos kaikista boolean-muuttujista tuli tuloksena false
            // (ei kuulunut hyväksyttyjen merkkien joukkoon)
            if (!pieni && !iso && !numerot && !alaviiva && !piste)
                throw new IllegalArgumentException();
            
            if (piste)
                pistelaskuri++;
            
            // Jos pisteitä oli enemmän kuin yksi, tai se oli merkkijonon ainut merkki,
            // keskeytetään suoritus
            if (pistelaskuri > 1 || (piste && tarkistettavaNimi.length() <= 1))
                throw new IllegalArgumentException();
        }
        return true;
    }
    
    /** Palauttaa olion merkkijonoesityksen.
    
        @return olion merkkijonoesitys.
    */
    public String toString() {
        return nimi.toString();
    }
    
    /** Vertailee olioita keskenään.
    
        @param t vertailtava olio.
        @return < 0, jos kutsuva on arvoltaan pienempi, 0, jos vertailtavat samanarvoisia,
        > 0, jos kutsuva on arvoltaan suurempi.
    */
    public int compareTo(Tieto t) {
        String vertailu1 = t.nimi.toString();
        String vertailu2 = nimi.toString();
        // Hyödynnetään String-luokan omaa compareTo-metodia
        return vertailu2.compareTo(vertailu1);
    }
    
    /** Tarkistelee olioiden yhtäsuuruutta.
    
        @param vertailtava vertailtava olio.
        @return true, jos vertailtavat samanarvoisia, false, jos erisuuruisia tai käsittelyssä tapahtui virhe.
    */
    public boolean equals(Object vertailtava) {
        try {
            Tieto t = (Tieto)vertailtava;
            String vertailu1 = t.nimi.toString();
            String vertailu2 = nimi.toString();
            // String-luokan oma equals-metodi
            return vertailu1.equals(vertailu2);
        }
        catch (Exception e) {
            return false;
        }
    }
}
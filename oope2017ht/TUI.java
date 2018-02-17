/*
    Käyttöliittymä
    Joni Pesonen
*/
package oope2017ht;
import apulaiset.In;

/**
    Käyttöliittymä-luokka, joka pääosin tulostaa näytöllä näkyvät asiat ja joka yhdistää käyttäjän
    komennot varsinaisesti suorittavaan tulkkiin.
    
    Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2017.
    
    @author Joni Pesonen,
    Luonnontieteiden tiedekunta, Tampereen yliopisto.
*/
public class TUI {
    // Attribuutit
    
    // Ohjelman tunnistamat komennot vakioituna.
    /** Vakioitu komento hakemiston luomiseen. */
    final String LUOHAKEMISTO = "md";
    /** Vakioitu komento hakemiston vaihtamiseen. */
    final String VAIHDAHAKEMISTO = "cd";
    /** Vakioitu komento tiedoston luomiseen. */
    final String LUOTIEDOSTO = "mf";
    /** Vakioitu komento tiedon uudelleen nimeämiseen. */
    final String NIMEAUUDELLEEN = "mv";
    /** Vakioitu komento hakemiston sisällön listaamiseen. */
    final String LISTAA = "ls";
    /** Vakioitu komento tiedoston kopioimiseen. */
    final String KOPIOITIEDOSTO = "cp";
    /** Vakioitu komento tiedon poistamiseen. */
    final String POISTA = "rm";
    /** Vakioitu komento rekursiivisen listauksen tekemiseen. */
    final String LISTAAREKURSIOLLA = "find";
    /** Vakioitu komento ohjelman lopettamiseen. */
    final String LOPETA = "exit";
    
    /** Vakioitu virhetuloste. */
    final String VIRHE = "Error!";
    
    /** Logiikkaluokan attribuutti, jolle asetetaan arvo true käynnistämisen yhteydessä. */
    private boolean kaynnistys = false;
    
    // Rakentajat
    
    public TUI() {
        kaynnistys();
    }
    
    // Aksessorit
    
    public void kaynnistys() {
        kaynnistys = true;
    }
    
    // Metodit
    
    /** Koko ohjelman pääsilmukka, jota kutsutaan ajoluokasta. */
    public void paasilmukka() {
        // Käyttäjän tervehtiminen, lippumuuttujan ja komentotulkin luominen, sekä nykyisen hakemiston
        // juurihakemistoksi asettaminen.
        System.out.println("Welcome to SOS.");
        
        boolean jatketaan = true;
        Komentotulkki tulkki = new Komentotulkki();
        tulkki.nykyinenJuureksi();
        
        boolean toimintoOK = false;
        do {
            // Suoritetaan pääsilmukkaa try-catch-rakenteen sisällä, jolloin voidaan kopata itse silmukassa
            // tai kutsuttavista operaatiosta tulevat virheet.
            try {
                // Hakemistopolun muodostaminen ja tulostaminen. Syötteen lukeminen ja osiin pilkkominen.
                String hakemistopolku = tulkki.annaPolku() + ">";
                System.out.print(hakemistopolku);
                String syote = In.readString();
                String[] pilkottu = syote.split("[ ]");
                
                // Switch-case-rakenne komentojen suorittamiseen.
                switch (pilkottu[0]) {
                    case LUOHAKEMISTO:
                        // Jos syötteessä oli oikea parametrimäärä, koetetaan vaihtaa hakemistoa.
                        if (pilkottu.length == 2) {
                            toimintoOK = tulkki.luoHakemisto(pilkottu[1]);
                        }
                        else
                            throw new IllegalArgumentException();
                        break;
                    case VAIHDAHAKEMISTO:
                        // Jos syötteessä oli pelkkä komento, asetetaan nykyinen hakemisto juurihakemistoksi.
                        if (pilkottu.length == 1) {
                            tulkki.nykyinenJuureksi();
                        }
                        // Jos syötteessä oli parametri komennolle, koetetaan vaihtaa annettuun kansioon.
                        else if (pilkottu.length == 2) {
                            tulkki.vaihdaHakemisto(pilkottu[1]);
                        }
                        else
                            throw new IllegalArgumentException();
                        break;
                    case LUOTIEDOSTO:
                        // Jos syötteessä oli oikea parametrimäärä, koetetaan luoda tiedosto.
                        if (pilkottu.length == 3) {
                            int tiedostoKoko = 0;
                            tiedostoKoko = Integer.parseInt(pilkottu[2]);
                            toimintoOK = tulkki.luoTiedosto(pilkottu[1], tiedostoKoko);
                        }
                        else
                            throw new IllegalArgumentException();
                        break;
                    case NIMEAUUDELLEEN:
                        // Jos syötteessä oli oikea parametrimäärä, koetetaan nimetä tiedosto uudelleen.
                        if (pilkottu.length == 3) {
                            toimintoOK = tulkki.nimeaUudelleen(pilkottu[1], pilkottu[2]);
                        }
                        else
                            throw new IllegalArgumentException();
                        break;
                    case LISTAA:
                        // Jos syötteessä oli pelkkä komento, listataan nykyisen hakemiston sisältö.
                        if (pilkottu.length == 1) {
                            tulkki.listaa();
                        }
                        // Jos syötteessä oli parametri komennolle, koetetaan listata annetun tiedon tiedot.
                        else if (pilkottu.length == 2) {
                            tulkki.listaa(pilkottu[1]);
                        }
                        else
                            throw new IllegalArgumentException();
                        break;
                    case KOPIOITIEDOSTO:
                        // Jos syötteessä oli oikea parametrimäärä, koetetaan kopioida tiedosto.
                        if (pilkottu.length == 3) {
                            toimintoOK = tulkki.kopioiTiedosto(pilkottu[1], pilkottu[2]);
                        }
                        else
                            throw new IllegalArgumentException();
                        break;
                    case POISTA:
                        // Jos syötteessä oli oikea parametrimäärä, koetetaan poistaa tiedosto.
                        if (pilkottu.length == 2)
                            tulkki.poista(pilkottu[1]);
                        else
                            throw new IllegalArgumentException();
                        break;
                    case LISTAAREKURSIOLLA:
                        // Jos syöte oli oikeellinen, listataan hakemistorakenteen sisältö rekursiivisesti.
                        if (pilkottu.length == 1)
                            System.out.print(tulkki.listaaRekursiolla());
                        else
                            throw new IllegalArgumentException();
                        break;
                    case LOPETA:
                        // Jos syöte oli oikeellinen, poistutaan ohjelmasta.
                        if (pilkottu.length == 1)
                            jatketaan = false;
                        else
                            throw new IllegalArgumentException();
                        break;
                    default:
                        // Jos syötettä ei tunnistettu, heitetään virhe.
                        throw new IllegalArgumentException();
                }
            }
            // Kopataan virheet ja tulostetaan virhesyöte.
            catch (IllegalArgumentException e) {
                System.out.println(VIRHE);
            }
            catch (Exception e) {
                System.out.println(VIRHE);
            }
        }
        while (jatketaan);
        
        // Ilmoitetaan ohjelman sulkemisesta.
        System.out.println("Shell terminated.");
    }
}
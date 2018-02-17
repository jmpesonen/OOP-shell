/*
    Ajoluokka
    Joni Pesonen
*/
package oope2017ht;

/**
    Ohjelman ajoluokka, jossa luodaan käyttöliittymästä apuolio ja kutsutaan sen kautta pääsilmukkaa.
    
    Harjoitustyö, Olio-ohjelmoinnin perusteet, kevät 2017.
    
    @author Joni Pesonen,
    Luonnontieteiden tiedekunta, Tampereen yliopisto.
*/
public class Oope2017HT {
    public static void main(String[] args) {
        TUI apuolio = new TUI();
        apuolio.paasilmukka();
    }
}
package azkviz.otazky;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Třída reprezentující otázku v kvízu.
 */
public class Otazka {

    private final String otazka;
    private String[] odpovedi;

    /**
     * Konstruktor pro vytvoření instance otázky s možnými odpověďmi.
     *
     * @param otazka Text otázky.
     * @param odpovedi Pole s možnými odpověďmi.
     */
    public Otazka(String otazka, String[] odpovedi) {
        this.otazka = otazka;
        this.odpovedi = odpovedi;
    }

    /**
     * Konstruktor pro vytvoření instance otázky bez možných odpovědí.
     *
     * @param otazka Text otázky.
     */
    public Otazka(String otazka) {
        this.otazka = otazka;
    }

    /**
     * Metoda pro získání textu otázky.
     *
     * @return Text otázky.
     */
    public String getOtazka() {
        return otazka;
    }

    /**
     * Metoda pro získání pole možných odpovědí na otázku.
     *
     * @return Pole možných odpovědí.
     */
    public String[] getOdpovedi() {
        return odpovedi;
    }

    /**
     * Metoda pro ověření, zda je zadaná odpověď správná.
     *
     * @param odpoved Zadaná odpověď.
     * @return True, pokud je odpověď správná, jinak false.
     */
    public boolean jeOdpovedSpravne(String odpoved){
        for(String spravnaOdpoved : odpovedi){
            if(odstraneniDiakritiky(spravnaOdpoved).equalsIgnoreCase(odstraneniDiakritiky(odpoved))) return true;
        }

        return false;
    }

    /**
     * Metoda pro odstranění diakritiky z textu.
     *
     * @param text Text, ze kterého má být odstraněna diakritika.
     * @return Text bez diakritiky.
     */
    private String odstraneniDiakritiky(String text){

        if(text == null) text = "";

        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }
}

package azkviz.otazky;

/**
 * Třída reprezentující náhradní otázku v kvízu.
 * Náhradní otázka má pouze jednu možnou odpověď, která je reprezentována celým číslem.
 */
public class NahradniOtazka extends Otazka {

    private final int odpoved;

    /**
     * Konstruktor pro vytvoření instance náhradní otázky.
     *
     * @param otazka Text otázky.
     * @param odpoved Číslo představující odpověď (0 pro "Ano", jinak "Ne").
     */
    public NahradniOtazka(String otazka, int odpoved) {
        super(otazka);
        this.odpoved = odpoved;
    }

    /**
     * Metoda pro ověření, zda je zadaná odpověď správná.
     *
     * @param odpoved Zadaná odpověď (0 pro "Ano", jinak "Ne").
     * @return True, pokud je odpověď správná, jinak false.
     */
    public boolean jeOdpovedSpravne(int odpoved){
        return this.odpoved == odpoved;
    }

    /**
     * Metoda pro získání textového vyjádření odpovědi.
     *
     * @return Textové vyjádření odpovědi ("Ano" pro 0, "Ne" pro jiné číslo).
     */
    public String getOdpoved() {
        return odpoved == 0 ? "Ano" : "Ne";
    }
}

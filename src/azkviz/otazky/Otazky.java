package azkviz.otazky;

import utils.Files;

import java.io.IOException;
import java.util.*;

/**
 * Třída pro načítání a generování otázek pro kvíz Az Kvíz.
 */
public class Otazky {

    private final Random random = new Random();

    private final List<Otazka> semifinaloveOtazky = new ArrayList<>();
    private final Map<String, List<Otazka>> finaloveOtazky = new HashMap<>();
    private final List<Otazka> nahradniOtazky = new ArrayList<>();

    /**
     * Konstruktor pro načtení otázek ze souborů při vytvoření instance.
     *
     * @throws IOException Pokud dojde k chybě při čtení souborů.
     */
    public Otazky() throws IOException {
        nactiSemifinaloveOtazky();
        nactiFinaloveOtazky();
        nactiNahradniOtazky();
    }

    /**
     * Metoda pro načtení semifinálových otázek ze souboru.
     *
     * @throws IOException Pokud dojde k chybě při čtení souboru.
     */
    public void nactiSemifinaloveOtazky() throws IOException {
        for(String line : Files.getLinesFromFile("azkviz/otazky/semifinale.csv")){
            String[] otazkaOdpoved = line.split(";");
            String[] odpovedi = Arrays.copyOfRange(otazkaOdpoved, 1, otazkaOdpoved.length);

            semifinaloveOtazky.add(new Otazka(otazkaOdpoved[0], odpovedi));
        }
    }

    /**
     * Metoda pro načtení finálových otázek ze souboru.
     *
     * @throws IOException Pokud dojde k chybě při čtení souboru.
     */
    public void nactiFinaloveOtazky() throws IOException {
        for(String line : Files.getLinesFromFile("azkviz/otazky/finale.csv")){
            String[] pismenoOtazkaOdpoved = line.split(";");

            String pismeno = pismenoOtazkaOdpoved[0];
            String otazka = pismenoOtazkaOdpoved[1];
            String[] odpovedi = Arrays.copyOfRange(pismenoOtazkaOdpoved, 2, pismenoOtazkaOdpoved.length);

            if(!finaloveOtazky.containsKey(pismeno)) finaloveOtazky.put(pismeno, new ArrayList<>());
            finaloveOtazky.get(pismeno).add(new Otazka(otazka, odpovedi));
        }
    }

    /**
     * Metoda pro načtení náhradních otázek ze souboru.
     *
     * @throws IOException Pokud dojde k chybě při čtení souboru.
     */
    public void nactiNahradniOtazky() throws IOException {
        for(String line : Files.getLinesFromFile("azkviz/otazky/nahradni.csv")){
            String[] otazkaOdpoved = line.split(";");
            int odpoved = otazkaOdpoved[1].equalsIgnoreCase("ano") ? 0 : 1;

            nahradniOtazky.add(new NahradniOtazka(otazkaOdpoved[0], odpoved));
        }
    }

    /**
     * Metoda pro vygenerování náhodné otázky podle parametrů.
     *
     * @param finale True, pokud má být vygenerována finálová otázka, jinak false pro semifinálovou.
     * @param nahradni True, pokud má být vygenerována náhradní otázka, jinak false pro standardní.
     * @param letter Písmeno, podle kterého se má vygenerovat finálová otázka.
     * @return Vygenerovaná otázka.
     */
    public Otazka vygenerujOtazku(boolean finale, boolean nahradni, String letter){
        if(nahradni) return vygenerujOtazkuZListu(nahradniOtazky);
        return vygenerujOtazkuZListu(finale ? finaloveOtazky.get(letter.toLowerCase()) : semifinaloveOtazky);
    }

    /**
     * Metoda pro vygenerování náhodné otázky z daného seznamu otázek.
     *
     * @param otazky Seznam otázek, ze kterého se má vygenerovat otázka.
     * @return Vygenerovaná otázka.
     */
    private Otazka vygenerujOtazkuZListu(List<Otazka> otazky) {
        Otazka otazka = otazky.get(random.nextInt(otazky.size()));
        otazky.remove(otazka);
        return otazka;
    }
}

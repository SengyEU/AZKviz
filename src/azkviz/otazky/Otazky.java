package azkviz.otazky;

import azkviz.Files;

import java.io.IOException;
import java.text.Normalizer;
import java.util.*;
import java.util.regex.Pattern;

public class Otazky {

    Random random = new Random();

    private final List<Otazka> semifinaloveOtazky = new ArrayList<>();
    private final Map<String, List<Otazka>> finaloveOtazky = new HashMap<>();
    private final List<Otazka> nahradniOtazky = new ArrayList<>();

    public Otazky() throws IOException {
        nactiSemifinaloveOtazky();
        nactiFinaloveOtazky();
        nactiNahradniOtazky();
    }

    public void nactiSemifinaloveOtazky() throws IOException {
        for(String line : Files.getLinesFromFile("azkviz/otazky/semifinale.csv")){
            String[] otazkaOdpoved = line.split(";");
            String[] odpovedi = Arrays.copyOfRange(otazkaOdpoved, 1, otazkaOdpoved.length);

            semifinaloveOtazky.add(new Otazka(otazkaOdpoved[0], odpovedi));
        }
    }

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

    public void nactiNahradniOtazky() throws IOException {
        for(String line : Files.getLinesFromFile("azkviz/otazky/nahradni.csv")){
            String[] otazkaOdpoved = line.split(";");
            int odpoved = otazkaOdpoved[1].equalsIgnoreCase("ano") ? 0 : 1;

            nahradniOtazky.add(new NahradniOtazka(otazkaOdpoved[0], odpoved));
        }
    }

    public Otazka vygenerujOtazku(boolean finale, boolean nahradni, String letter){
        if(nahradni) return vygenerujOtazkuZListu(nahradniOtazky);
        return vygenerujOtazkuZListu(finale ? finaloveOtazky.get(letter.toLowerCase()) : semifinaloveOtazky);
    }

    private Otazka vygenerujOtazkuZListu(List<Otazka> otazky) {
        Otazka otazka = otazky.get(random.nextInt(otazky.size()));
        otazky.remove(otazka);
        return otazka;
    }


}

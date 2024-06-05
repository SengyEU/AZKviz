package azkviz.otazky;

import azkviz.Files;

import java.io.IOException;
import java.util.*;

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

            semifinaloveOtazky.add(new Otazka(otazkaOdpoved[0], otazkaOdpoved[1]));
        }
    }

    public void nactiFinaloveOtazky() throws IOException {
        for(String line : Files.getLinesFromFile("azkviz/otazky/finale.csv")){
            String[] pismenoOtazkaOdpoved = line.split(";");

            String pismeno = pismenoOtazkaOdpoved[0];
            String otazka = pismenoOtazkaOdpoved[1];
            String odpoved = pismenoOtazkaOdpoved[2];

            if(!finaloveOtazky.containsKey(pismeno)) finaloveOtazky.put(pismeno, new ArrayList<>());
            finaloveOtazky.get(pismeno).add(new Otazka(otazka, odpoved));
        }
    }

    public void nactiNahradniOtazky() throws IOException {
        for(String line : Files.getLinesFromFile("azkviz/otazky/nahradni.csv")){
            String[] otazkaOdpoved = line.split(";");

            nahradniOtazky.add(new Otazka(otazkaOdpoved[0], otazkaOdpoved[1]));
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

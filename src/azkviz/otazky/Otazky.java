package azkviz.otazky;

import azkviz.Files;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Otazky {

    Random random = new Random();

    private List<Otazka> vedomostniOtazky = new ArrayList<>();
    private List<Otazka> anoNeOtazky = new ArrayList<>();

    public Otazky() throws IOException {
        nactiVedomostniOtazky();
        nactiAnoNeOtazky();
    }

    public void nactiVedomostniOtazky() throws IOException {
        for(String line : Files.getLinesFromFile("azkviz/otazky/vedomostni.csv")){
            String[] otazkaOdpoved = line.split(";");

            vedomostniOtazky.add(new Otazka(otazkaOdpoved[0], otazkaOdpoved[1]));
        }
    }

    public void nactiAnoNeOtazky() throws IOException {
        for(String line : Files.getLinesFromFile("azkviz/otazky/ano_ne.csv")){
            String[] otazkaOdpoved = line.split(";");

            anoNeOtazky.add(new Otazka(otazkaOdpoved[0], otazkaOdpoved[1]));
        }
    }

    public Otazka vygenerujOtazku(boolean vedomostniOtazka){
        if(vedomostniOtazka) return vedomostniOtazky.get(random.nextInt(vedomostniOtazky.size()));
        return anoNeOtazky.get(random.nextInt(anoNeOtazky.size()));
    }

}

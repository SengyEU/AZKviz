package azkviz.otazky;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class Otazka {

    private String otazka;
    private String[] odpovedi;

    public Otazka(String otazka, String[] odpovedi) {
        this.otazka = otazka;
        this.odpovedi = odpovedi;
    }

    public Otazka(String otazka) {
        this.otazka = otazka;
    }

    public String getOtazka() {
        return otazka;
    }

    public String[] getOdpovedi() {
        return odpovedi;
    }

    public boolean jeOdpovedSpravne(String odpoved){
        for(String spravnaOdpoved : odpovedi){
            if(odstraneniDiakritiky(spravnaOdpoved).equalsIgnoreCase(odstraneniDiakritiky(odpoved))) return true;
        }

        return false;
    }

    private String odstraneniDiakritiky(String text){

        if(text == null) text = "";

        String normalized = Normalizer.normalize(text, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        return pattern.matcher(normalized).replaceAll("");
    }
}

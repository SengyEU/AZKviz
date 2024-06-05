package azkviz.otazky;

public class NahradniOtazka extends Otazka {

    private int odpoved;

    public NahradniOtazka(String otazka, String[] odpovedi) {
        super(otazka, odpovedi);
    }

    public NahradniOtazka(String otazka,int odpoved) {
        super(otazka);
        this.odpoved = odpoved;
    }

    public boolean jeOdpovedSpravne(int odpoved){
        return this.odpoved == odpoved;
    }

    public String getOdpoved() {
        return odpoved == 0 ? "Ano" : "Ne";
    }
}

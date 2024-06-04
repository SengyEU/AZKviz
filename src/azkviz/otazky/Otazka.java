package azkviz.otazky;

public class Otazka {

    private String otazka;
    private String odpoved;

    public Otazka(String otazka, String odpoved) {
        this.otazka = otazka;
        this.odpoved = odpoved;
    }

    public String getOtazka() {
        return otazka;
    }

    public void setOtazka(String otazka) {
        this.otazka = otazka;
    }

    public String getOdpoved() {
        return odpoved;
    }

    public void setOdpoved(String odpoved) {
        this.odpoved = odpoved;
    }

    public boolean jeOdpovedSpravne(String odpoved){
        return this.odpoved.equalsIgnoreCase(odpoved);
    }
}

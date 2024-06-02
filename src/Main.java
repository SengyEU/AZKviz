import azkviz.AzKviz;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        AzKviz azKviz = new AzKviz();
        try {
            azKviz.start();
        } catch (IOException e) {
            System.out.println("Nepodařilo se načíst otázky. Máš správně uložené soubory?");
        }
    }
}
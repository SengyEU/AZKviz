package azkviz.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

/**
 * Custom JButton reprezentující hexagonální tlačítko.
 */
public class HexagonButton extends JButton {
    // Cesta pro vykreslení hexagonu
    protected HexagonPath hexagonPath;

    // Barva hexagonu
    private Color color;

    /**
     * Konstruktor třídy HexagonButton.
     *
     * @param text Text tlačítka
     * @param color Barva hexagonu
     */
    public HexagonButton(String text, Color color) {
        super(text);
        applyDefaults();
        this.color = color;
    }

    // Metoda pro invalidaci cesty hexagonu při změně velikosti tlačítka
    @Override
    public void invalidate() {
        hexagonPath = null;
        super.invalidate();
    }

    // Metoda pro získání maximální dimenze tlačítka
    protected int getMaxDimension() {
        Dimension size = super.getPreferredSize();
        return Math.max(size.width, size.height);
    }

    // Metoda pro získání preferované velikosti tlačítka
    @Override
    public Dimension getPreferredSize() {
        int maxDimension = getMaxDimension();
        return new Dimension(maxDimension, maxDimension);
    }

    // Metoda pro získání maximální velikosti tlačítka
    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    // Metoda pro získání minimální velikosti tlačítka
    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    // Nastavení výchozích vlastností tlačítka
    protected void applyDefaults() {
        setBorderPainted(false);
        setFocusPainted(false);
    }

    // Metoda pro získání instance cesty hexagonu
    protected HexagonPath getHexagonPath() {
        if (hexagonPath == null) {
            hexagonPath = new HexagonPath(getMaxDimension() - 1);
        }
        return hexagonPath;
    }

    // Metoda pro nastavení barvy hexagonu
    public void setColor(Color color) {
        this.color = color;
        repaint();
    }

    // Metoda pro vykreslení okraje tlačítka
    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        HexagonPath path = getHexagonPath();
        g2d.setColor(getForeground());
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        g2d.draw(path);
        g2d.setColor(color);

        paintText(g, g2d, path);
    }

    // Metoda pro vykreslení textu na tlačítko
    protected void paintText(Graphics g, Graphics2D g2d, HexagonPath path) {
        g2d.fill(path);

        g2d.dispose();

        String text = getText();
        if (text != null && !text.isEmpty()) {
            Graphics2D textG2d = (Graphics2D) g.create();
            textG2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            FontMetrics fm = textG2d.getFontMetrics();
            Rectangle textBounds = fm.getStringBounds(text, textG2d).getBounds();
            int x = (getWidth() - textBounds.width) / 2;
            int y = (getHeight() + fm.getAscent() - fm.getDescent()) / 2;
            textG2d.setColor(getForeground());
            textG2d.drawString(text, x, y);
            textG2d.dispose();
        }
    }

    // Metoda pro vykreslení hexagonu jako pozadí tlačítka
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D hexagonG2d = (Graphics2D) g.create();
        hexagonG2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        hexagonG2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        hexagonG2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        hexagonG2d.setColor(getBackground());
        hexagonG2d.fill(getHexagonPath());
        hexagonG2d.dispose();
    }

    // Získání barvy pozadí tlačítka
    @Override
    public Color getBackground() {
        if (getModel().isArmed()) {
            return Color.BLUE;
        }
        return super.getBackground();
    }

    // Získání barvy popředí tlačítka
    @Override
    public Color getForeground() {
        if (getModel().isArmed()) {
            return Color.WHITE;
        }
        return super.getForeground();
    }

    // Vnitřní třída pro vytvoření cesty hexagonu
    protected static class HexagonPath extends Path2D.Double {

        // Konstruktor třídy HexagonPath
        public HexagonPath(double size) {
            double centerX = size / 2d;
            double centerY = size / 2d;
            for (double i = 0; i < 6; i++) {
                double angleDegrees = (60d * i) - 30d;
                double angleRad = ((float) Math.PI / 180.0f) * angleDegrees;

                double x = centerX + ((size / 2f) * Math.cos(angleRad));
                double y = centerY + ((size / 2f) * Math.sin(angleRad));

                if (i == 0) {
                    moveTo(x, y);
                } else {
                    lineTo(x, y);
                }
            }
            closePath();
        }

    }
}
package azkviz.gui;

import azkviz.AzKviz;
import azkviz.otazky.Otazka;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Path2D;

public class HexagonButton extends JButton {

    private final AzKviz azKviz;
    private HexagonPath hexagonPath;
    private final int row;
    private final int col;

    public HexagonButton(AzKviz azKviz, String text, int row, int col) {
        super(text);
        applyDefaults();

        this.azKviz = azKviz;
        this.row = row;
        this.col = col;

        this.addActionListener(e -> {

            if(azKviz.getButtonState(row, col) != 1) return;

            if(azKviz.isPlayer1Playing()){
                if(otazka()){
                    setState(2);
                } else {
                    setState(4);
                }
                azKviz.setPlayer1Playing(false);
            } else {
                if(otazka()){
                    setState(3);
                } else {
                    setState(4);
                }
                azKviz.setPlayer1Playing(true);
            }
        });
    }

    private boolean otazka(){
        Otazka otazka = azKviz.otazky.vygenerujOtazku(true);

        String odpoved = JOptionPane.showInputDialog(null, otazka.getOtazka());

        return otazka.jeOdpovedSpravce(odpoved);
    }

    private Color getStateColor(){
        Color fillColor;
        switch(azKviz.getButtonState(row, col)){
            case 2 -> fillColor = Color.decode("#70e3e5");
            case 3 -> fillColor = Color.decode("#f3a004");
            case 4 -> fillColor = Color.BLACK;
            default -> fillColor = Color.decode("#f8f4f4");
        }

        return fillColor;
    }

    public void setState(int state) {

        azKviz.setButtonState(state, row, col);


        repaint();
        azKviz.play();
    }

    @Override
    public void invalidate() {
        hexagonPath = null;
        super.invalidate();
    }

    protected int getMaxDimension() {
        Dimension size = super.getPreferredSize();
        return Math.max(size.width, size.height);
    }

    @Override
    public Dimension getPreferredSize() {
        int maxDimension = getMaxDimension();
        return new Dimension(maxDimension, maxDimension);
    }

    @Override
    public Dimension getMaximumSize() {
        return getPreferredSize();
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }

    protected void applyDefaults() {
        setBorderPainted(false);
        setFocusPainted(false);
    }

    protected HexagonPath getHexagonPath() {
        if (hexagonPath == null) {
            hexagonPath = new HexagonPath(getMaxDimension() - 1);
        }
        return hexagonPath;
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        HexagonPath path = getHexagonPath();
        g2d.setColor(getForeground());
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

        g2d.draw(path);
        g2d.setColor(getStateColor());
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






    @Override
    public Color getBackground() {
        if (getModel().isArmed()) {
            return Color.BLUE;
        }
        return super.getBackground();
    }

    @Override
    public Color getForeground() {
        if (getModel().isArmed()) {
            return Color.WHITE;
        }
        return super.getForeground();
    }

    protected static class HexagonPath extends Path2D.Double {

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

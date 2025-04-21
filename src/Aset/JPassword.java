package Aset;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class JPassword extends JPasswordField  {

    private int roundedkiriatas;
    private int roundedkananatas;
    private int roundedkiribawah;
    private int roundedkananbawah;

    public JPassword() {
        setOpaque(false);
    }

    public int getRoundedkiriatas() {
        return roundedkiriatas;
    }

    public void setRoundedkiriatas(int roundedkiriatas) {
        this.roundedkiriatas = roundedkiriatas;
        repaint();
    }

    public int getRoundedkananatas() {
        return roundedkananatas;
    }

    public void setRoundedkananatas(int roundedkananatas) {
        this.roundedkananatas = roundedkananatas;
        repaint();
    }

    public int getRoundedkiribawah() {
        return roundedkiribawah;
    }

    public void setRoundedkiribawah(int roundedkiribawah) {
        this.roundedkiribawah = roundedkiribawah;
        repaint();
    }

    public int getRoundedkananbawah() {
        return roundedkananbawah;
    }

    public void setRoundedkananbawah(int roundedkananbawah) {
        this.roundedkananbawah = roundedkananbawah;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());

        Area area = new Area(buatroundedkiriatas());

        if (roundedkananatas > 0) {
            area.intersect(new Area(buatroundedkananatas()));
        }
        if (roundedkananbawah > 0) {
            area.intersect(new Area(buatroundedkananbawah()));
        }
        if (roundedkiribawah > 0) {
            area.intersect(new Area(buatroundedkiribawah()));
        }

        g2d.fill(area);
        g2d.dispose();

        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground()); // Atau gunakan warna border yang berbeda

        Area area = new Area(buatroundedkiriatas());

        if (roundedkananatas > 0) {
            area.intersect(new Area(buatroundedkananatas()));
        }
        if (roundedkananbawah > 0) {
            area.intersect(new Area(buatroundedkananbawah()));
        }
        if (roundedkiribawah > 0) {
            area.intersect(new Area(buatroundedkiribawah()));
        }

        g2d.draw(area);
        g2d.dispose();
    }

    private Shape buatroundedkiriatas() {
        int width = getWidth();
        int height = getHeight();
        int roundX = Math.min(width, roundedkiriatas);
        int roundY = Math.min(height, roundedkiriatas);

        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(roundY / 2, 0, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, roundY / 2, width, height - roundY / 2)));
        return area;
    }

    private Shape buatroundedkananatas() {
        int width = getWidth();
        int height = getHeight();
        int roundX = Math.min(width, roundedkananatas);
        int roundY = Math.min(height, roundedkananatas);

        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(0, 0, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, roundY / 2, width, height - roundY / 2)));
        return area;
    }

    private Shape buatroundedkananbawah() {
        int width = getWidth();
        int height = getHeight();
        int roundX = Math.min(width, roundedkananbawah);
        int roundY = Math.min(height, roundedkananbawah);

        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(0, 0, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, 0, width, height - roundY / 2)));
        return area;
    }

    private Shape buatroundedkiribawah() {
        int width = getWidth();
        int height = getHeight();
        int roundX = Math.min(width, roundedkiribawah);
        int roundY = Math.min(height, roundedkiribawah);

        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(roundX / 2, 0, width - roundX / 2, height)));
        area.add(new Area(new Rectangle2D.Double(0, 0, width, height - roundY / 2)));
        return area;
    }

    public void setEchoChar(String string) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
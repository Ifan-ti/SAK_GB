package Aset;

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public class Rounded extends JPanel {
 
    /**
     * @return the awal
     */
    public Color getAwal() {
        return awal;
    }

    /**
     * @param awal the awal to set
     */
    public void setAwal(Color awal) {
        this.awal = awal;
        repaint();
    }

    /**
     * @return the akhir
     */
    public Color getAkhir() {
        return akhir;
    }

    /**
     * @param akhir the akhir to set
     */
    public void setAkhir(Color akhir) {
        this.akhir = akhir;
        repaint();
    }

    /**
     * @return the elipsScale
     */
    public double getElipsScale() {
        return elipsScale;
    }

    /**
     * @param elipsScale the elipsScale to set
     */
    public void setElipsScale(double elipsScale) {
        this.elipsScale = elipsScale;
        repaint();
    }

    /**
     * @return the elipsPosition
     */
    public int getElipsPosition() {
        return elipsPosition;
    }

    /**
     * @param elipsPosition the elipsPosition to set
     */
    public void setElipsPosition(int elipsPosition) {
        this.elipsPosition = elipsPosition;
        repaint();
    }

    private Color awal;
    private Color akhir;
    private double elipsScale; // Faktor skala untuk elips
    private int elipsPosition; // Posisi elips (0: atas, 1: kanan, 2: bawah, 3: kiri)

    public Rounded(Color awal, Color akhir, double elipsScale, int elipsPosition) {
        this.awal = awal;
        this.akhir = akhir;
        this.elipsScale = elipsScale;
        this.elipsPosition = elipsPosition;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // Membuat bentuk elips dengan skala yang dapat disesuaikan
        Ellipse2D ellipse = null;
        Rectangle2D rectangle = new Rectangle2D.Double(0, 0, width, height);
        switch (getElipsPosition()) {
            case 0: // Atas
                ellipse = new Ellipse2D.Double(0, -height * (getElipsScale() - 1), width * getElipsScale(), height * getElipsScale());
                break;
            case 1: // Kanan
                ellipse = new Ellipse2D.Double(-width * (getElipsScale() - 1), 0, width * getElipsScale(), height * getElipsScale());
                break;
            case 2: // Bawah
                ellipse = new Ellipse2D.Double(0, 0, width * getElipsScale(), height * getElipsScale());
                break;
            case 3: // Kiri
                ellipse = new Ellipse2D.Double(0, 0, width * getElipsScale(), height * getElipsScale());
                break;
        }

        // Melakukan operasi pemotongan
        Area area = new Area(ellipse);
        area.intersect(new Area(rectangle));

        // Membuat gradien warna
        GradientPaint gradient = new GradientPaint(0, 0, getAwal(), width, height, getAkhir());
        g2d.setPaint(gradient);

        // Mengisi bentuk dengan gradien
        g2d.fill(area);

        g2d.dispose();
    }
}
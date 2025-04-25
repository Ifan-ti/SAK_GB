package Aset;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class RoundedTextArea extends JTextArea {

    private int roundedkiriatas;
    private int roundedkananatas;
    private int roundedkiribawah;
    private int roundedkananbawah;

    public RoundedTextArea() {
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
        
        Shape shape = createRoundedShape();
        g2d.fill(shape);
        g2d.dispose();
        
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getForeground());
        
        Shape shape = createRoundedShape();
        g2d.draw(shape);
        g2d.dispose();
    }
    
    private Shape createRoundedShape() {
        int width = getWidth();
        int height = getHeight();
        
        // Batasi ukuran radius ke setengah lebar/tinggi
        int kiriAtas = Math.min(roundedkiriatas, Math.min(width/2, height/2));
        int kananAtas = Math.min(roundedkananatas, Math.min(width/2, height/2));
        int kiriBawah = Math.min(roundedkiribawah, Math.min(width/2, height/2));
        int kananBawah = Math.min(roundedkananbawah, Math.min(width/2, height/2));
        
        // Buat path untuk bentuk dengan 4 sudut yang berbeda
        Path2D path = new Path2D.Double();
        
        // Mulai dari sudut kiri atas
        if (kiriAtas > 0) {
            path.moveTo(0, kiriAtas);
            path.quadTo(0, 0, kiriAtas, 0);
        } else {
            path.moveTo(0, 0);
        }
        
        // Garis atas ke sudut kanan atas
        if (kananAtas > 0) {
            path.lineTo(width - kananAtas, 0);
            path.quadTo(width, 0, width, kananAtas);
        } else {
            path.lineTo(width, 0);
        }
        
        // Garis kanan ke sudut kanan bawah
        if (kananBawah > 0) {
            path.lineTo(width, height - kananBawah);
            path.quadTo(width, height, width - kananBawah, height);
        } else {
            path.lineTo(width, height);
        }
        
        // Garis bawah ke sudut kiri bawah
        if (kiriBawah > 0) {
            path.lineTo(kiriBawah, height);
            path.quadTo(0, height, 0, height - kiriBawah);
        } else {
            path.lineTo(0, height);
        }
        
        // Tutup path
        path.closePath();
        
        return path;
    }
}
package Aset;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.*;

public class JTextAreaRounded extends JTextArea {

    private int roundedTopLeft;
    private int roundedTopRight;
    private int roundedBottomLeft;
    private int roundedBottomRight;

    public JTextAreaRounded() {
        setOpaque(false);
        setLineWrap(true);
        setWrapStyleWord(true);
        setRoundedAll(10); // Nilai default rounded
    }

    public int getRoundedTopLeft() {
        return roundedTopLeft;
    }

    public void setRoundedTopLeft(int roundedTopLeft) {
        this.roundedTopLeft = Math.max(0, roundedTopLeft);
        repaint();
    }

    public int getRoundedTopRight() {
        return roundedTopRight;
    }

    public void setRoundedTopRight(int roundedTopRight) {
        this.roundedTopRight = Math.max(0, roundedTopRight);
        repaint();
    }

    public int getRoundedBottomLeft() {
        return roundedBottomLeft;
    }

    public void setRoundedBottomLeft(int roundedBottomLeft) {
        this.roundedBottomLeft = Math.max(0, roundedBottomLeft);
        repaint();
    }

    public int getRoundedBottomRight() {
        return roundedBottomRight;
    }

    public void setRoundedBottomRight(int roundedBottomRight) {
        this.roundedBottomRight = Math.max(0, roundedBottomRight);
        repaint();
    }

    public void setRoundedAll(int rounded) {
        setRoundedTopLeft(rounded);
        setRoundedTopRight(rounded);
        setRoundedBottomLeft(rounded);
        setRoundedBottomRight(rounded);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        g2d.fill(buatRoundedArea());
        g2d.dispose();
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getForeground());
        g2d.setStroke(new BasicStroke(1));
        g2d.draw(buatRoundedArea());
        g2d.dispose();
    }

    private Shape buatRoundedArea() {
        int width = getWidth();
        int height = getHeight();
        Area area = new Area(new Rectangle2D.Double(0, 0, width, height));

        // Kurangi area dengan sudut yang tidak ingin dibulatkan
        if (roundedTopLeft > 0) {
            area.subtract(new Area(new Rectangle2D.Double(0, 0, roundedTopLeft, roundedTopLeft)));
        }
        if (roundedTopRight > 0) {
            area.subtract(new Area(new Rectangle2D.Double(width - roundedTopRight, 0, roundedTopRight, roundedTopRight)));
        }
        if (roundedBottomLeft > 0) {
            area.subtract(new Area(new Rectangle2D.Double(0, height - roundedBottomLeft, roundedBottomLeft, roundedBottomLeft)));
        }
        if (roundedBottomRight > 0) {
            area.subtract(new Area(new Rectangle2D.Double(width - roundedBottomRight, height - roundedBottomRight, roundedBottomRight, roundedBottomRight)));
        }

        // Tambahkan kembali area rounded
        Area round = new Area(new RoundRectangle2D.Double(0, 0, width, height, roundedTopLeft, roundedTopLeft));
        round.add(new Area(new RoundRectangle2D.Double(0, 0, width, height, roundedTopRight, roundedTopRight)));
        round.add(new Area(new RoundRectangle2D.Double(0, 0, width, height, roundedBottomLeft, roundedBottomLeft)));
        round.add(new Area(new RoundRectangle2D.Double(0, 0, width, height, roundedBottomRight, roundedBottomRight)));

        // Gabungkan area rounded dengan area persegi panjang utama
        area.add(round);

        return area;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Rounded JTextArea Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new FlowLayout());
            frame.getContentPane().setBackground(Color.LIGHT_GRAY); // Set background frame

            JTextAreaRounded roundedTextArea = new JTextAreaRounded();
            roundedTextArea.setPreferredSize(new Dimension(200, 100));
            roundedTextArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            roundedTextArea.setBackground(Color.WHITE); // Set background JTextArea
            roundedTextArea.setForeground(Color.BLACK);

            roundedTextArea.setRoundedTopLeft(20);
            roundedTextArea.setRoundedTopRight(20);
            roundedTextArea.setRoundedBottomLeft(20);
            roundedTextArea.setRoundedBottomRight(20);

            frame.add(roundedTextArea);
            frame.setSize(300, 200);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
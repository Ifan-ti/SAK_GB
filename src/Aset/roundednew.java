/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Aset;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JPanel;

/**
 *
 * @author achmad ifan
 */
public class roundednew extends JPanel{

    /**
     * @return the roundedkiriatas
     */
    public int getRoundedkiriatas() {
        return roundedkiriatas;
    }

    /**
     * @param roundedkiriatas the roundedkiriatas to set
     */
    public void setRoundedkiriatas(int roundedkiriatas) {
        this.roundedkiriatas = roundedkiriatas;
        repaint();
    }

    /**
     * @return the roundedkananatas
     */
    public int getRoundedkananatas() {
        return roundedkananatas;
    }

    /**
     * @param roundedkananatas the roundedkananatas to set
     */
    public void setRoundedkananatas(int roundedkananatas) {
        this.roundedkananatas = roundedkananatas;
                repaint();

    }

    /**
     * @return the roundedkiribawah
     */
    public int getRoundedkiribawah() {
        return roundedkiribawah;
    }

    /**
     * @param roundedkiribawah the roundedkiribawah to set
     */
    public void setRoundedkiribawah(int roundedkiribawah) {
        this.roundedkiribawah = roundedkiribawah;
                repaint();

    }

    /**
     * @return the roundedkananbawah
     */
    public int getRoundedkananbawah() {
        return roundedkananbawah;
    }

    /**
     * @param roundedkananbawah the roundedkananbawah to set
     */
    public void setRoundedkananbawah(int roundedkananbawah) {
        this.roundedkananbawah = roundedkananbawah;
                repaint();

    }

    public Color getColorstar() {
        return colorstar;
    }

    public void setColorstar(Color colorstar) {
        this.colorstar = colorstar;
        repaint();
    }

    public Color getColorend() {
        return colorend;
        
    }

    public void setColorend(Color colorend) {
        this.colorend = colorend;
        repaint();
    }
    public Color colorstar = Color.BLACK;
    private Color colorend = Color.WHITE;
    private int roundedkiriatas;
    private int roundedkananatas;
    private int roundedkiribawah;
    private int roundedkananbawah;
    
    public roundednew(){
        
        setOpaque(false);
    }

   

    
    
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        
        
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground());
        
        GradientPaint gradient = new GradientPaint(0, 20, colorstar, getWidth(), getHeight(), colorend);
        g2d.setPaint(gradient);
        
        Area area = new Area (buatroundedkiriatas());
        
        if (roundedkananatas>0){
            area.intersect(new Area(buatroundedkananatas()));
        }
        if (roundedkananbawah>0){
            area.intersect(new Area(buatroundedkananbawah()));
        }
        if (roundedkiribawah>0){
            area.intersect(new Area(buatroundedkiribawah()));
        }
        
          

        g2d.fill(area);
        g2d.dispose();
        
        super.paintComponent(g); 
    }
//   

    private Shape buatroundedkiriatas() {
        int width=getWidth();
        int higt=getHeight();
        int roundX=Math.min(width, roundedkiriatas);
        int roundY=Math.min(higt, roundedkiriatas);
        
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, higt, roundX,roundY));
        area.add(new Area(new Rectangle2D.Double(roundY/2, 0, width-roundX/2, higt)));
        area.add(new Area(new Rectangle2D.Double(0, roundY/2,width, higt-roundY/2)));
        return area;
    }

    private Shape buatroundedkananatas() {
        int width=getWidth();
        int higt=getHeight();
        int roundX=Math.min(width, roundedkananatas);
        int roundY=Math.min(higt, roundedkananatas);
        
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, higt, roundX,roundY));
        area.add(new Area(new Rectangle2D.Double(0, 0, width-roundX/2, higt)));
        area.add(new Area(new Rectangle2D.Double(0, roundY/2,width, higt-roundY/2)));
        return area;
    }

    private Shape buatroundedkananbawah() {
      int width=getWidth();
        int higt=getHeight();
        int roundX=Math.min(width, roundedkananbawah);
        int roundY=Math.min(higt, roundedkananbawah);
        
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, higt, roundX,roundY));
        area.add(new Area(new Rectangle2D.Double(0, 0, width-roundX/2, higt)));
        area.add(new Area(new Rectangle2D.Double(0,0,width, higt-roundY/2)));
         return area;
    }

    private Shape buatroundedkiribawah() {
        int width=getWidth();
        int higt=getHeight();
        int roundX=Math.min(width, roundedkiribawah);
        int roundY=Math.min(higt, roundedkiribawah);
        
        Area area = new Area(new RoundRectangle2D.Double(0, 0, width, higt, roundX, roundY));
        area.add(new Area(new Rectangle2D.Double(roundX / 2, 0, width - roundX / 2, higt)));
        area.add(new Area(new Rectangle2D.Double(0, 0, width, higt - roundY / 2)));
        return area;
    }
    
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Aset;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;

/**
 *
 * @author achmad ifan
 */
public class button extends JButton{

    /**
     * @return the over
     */
    public boolean isOver() {
        return over;
    }

    /**
     * @param over the over to set
     */
    public void setOver(boolean over) {
        this.over = over;
    }

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param color the color to set
     */
    public void setColor(Color color) {
        this.color = color;
        setBackground(color);

    }

    /**
     * @return the colorover
     */
    public Color getColorover() {
        return colorover;
    }

    /**
     * @param colorover the colorover to set
     */
    public void setColorover(Color colorover) {
        this.colorover = colorover;
    }

    /**
     * @return the colorclic
     */
    public Color getColorclic() {
        return colorclic;
    }

    /**
     * @param colorclic the colorclic to set
     */
    public void setColorclic(Color colorclic) {
        this.colorclic = colorclic;
    }

    /**
     * @return the colorborder
     */
    public Color getColorborder() {
        return colorborder;
    }

    /**
     * @param colorborder the colorborder to set
     */
    public void setColorborder(Color colorborder) {
        this.colorborder = colorborder;
    }

    /**
     * @return the radius
     */
    public int getRadius() {
        return radius;
    }

    /**
     * @param radius the radius to set
     */
    public void setRadius(int radius) {
        this.radius = radius;
    }
    
    public button(){
        
        setColor(color.WHITE);
        colorover = new Color (255,255,255);
        colorclic = new Color (255,255,255);
        colorborder = new Color (255,255,255);
        setContentAreaFilled(false);
    }
        private boolean over;
        private Color color;
        private Color colorover;
        private Color colorclic;
        private Color colorborder;
        private int radius = 0;

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2=(Graphics2D)grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(colorborder);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
//        g2.setColor(getBackground());
        g2.fillRoundRect(2, 2, getWidth() - 4, getHeight()-4, radius, radius);
        super.paintComponent(grphcs); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    public void setIcon(String cUsersachmad_ifanDocumentsNetBeansProject) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
        
        
}

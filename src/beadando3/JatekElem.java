/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beadando3;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

/**
 *
 * @author roszk
 */
public abstract class JatekElem {
    protected int y;
    protected int x;
    protected int meret;
    protected Image kep;
    
    public JatekElem(int x, int y, int meret, Image kep){
        this.y=y;
        this.x=x;
        this.meret=meret;
        this.kep=kep;
    }
    
    public void kirajzol(Graphics g) {
        if (kep != null && kep.getWidth(null) > 0) {
            g.drawImage(kep, x, y, meret, meret, null);
        } else {
            g.setColor(Color.GRAY);
            g.fillRect(x, y, meret, meret);
        }
    }
    
    public int getY(){
        return this.y;
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getMeret(){
        return this.meret;
    }
    
    public boolean utkozik(int masikX, int masikY){
        return (this.x == masikX && this.y == masikY);
    }
}

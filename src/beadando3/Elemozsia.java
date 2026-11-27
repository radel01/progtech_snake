/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beadando3;

import java.awt.Image;

/**
 *
 * @author roszk
 */
public class Elemozsia extends JatekElem{
    
    public  Elemozsia(int x, int y, int meret, Image kep){
        super(x, y,meret, kep);
    }
    public void ujPozicio(int ujX, int ujY){
        this.x=ujX;
        this.y=ujY;
    }
}

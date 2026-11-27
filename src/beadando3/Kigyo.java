/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beadando3;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.List;
import java.awt.Point;
import java.util.ArrayList;
import javax.swing.ImageIcon;


/**
 *
 * @author roszk
 */
public class Kigyo {
    private List<Point> test;
    private Irany irany;
    private int meret;
    private Image fejKep;
    private Image testKep;
    private Image farokKep;
    private boolean no;
    
    public Kigyo(int kezdoX, int kezdoY, int meret, Irany kezdoIrany){
        this.meret=meret;
        this.irany=kezdoIrany;
        this.no=false;
        this.test= new ArrayList<> () ;
        test.add(new Point(kezdoX, kezdoY));
        int farokX=kezdoX;
        int farokY=kezdoY;
        switch (kezdoIrany) {
            case FEL:
                farokY=farokY+meret;
                break;
            case LE:
                farokY=farokY-meret;
                break;
            case JOBBRA:
                farokX=farokX-meret;
                break;
            case BALRA:
                farokX=farokX+meret;
                break;
        }
        test.add(new Point(farokX,farokY));
        this.fejKep= new ImageIcon("kepek/fej.png").getImage();
        this.testKep= new ImageIcon("kepek/test.png").getImage();
        this.farokKep= new ImageIcon("kepek/farok.png").getImage();
    }
    public void mozog(){
        Point fej=test.get(0);
        int fejX = fej.x;
        int fejY = fej.y;
        switch (irany) {
            case FEL:
                fejY=fejY-meret;
                break;
            case LE:
                fejY=fejY+meret;
                break;
            case JOBBRA:
                fejX=fejX+meret;
                break;
            case BALRA:
                fejX=fejX-meret;
                break;
        }
        test.add(0, new Point(fejX, fejY));
        if(no){
            no=false;
        }else{
            test.remove(test.size() - 1);
        }
    }
    
    public void novekszik(){
        no=true;
    }
    
    public void setIrany(Irany ujIrany){
        if((this.irany == Irany.FEL && ujIrany != Irany.LE) || (this.irany == Irany.LE && ujIrany != Irany.FEL) 
                || (this.irany == Irany.BALRA && ujIrany != Irany.JOBBRA)|| (this.irany == Irany.JOBBRA && ujIrany != Irany.BALRA)){
            this.irany=ujIrany;
        }
    }
    
    public boolean maganakUtkozott(){
        Point fej=test.get(0);
        for (int i = 1; i < test.size(); i++) {
            if(test.get(i).equals(fej)){
                return true;
            }
        }
        return false;
    }

    public Point getFej() {
        return test.get(0);
    }

    public List<Point> getTest() {
        return test;
    }
    
    public void kirajzol(Graphics g) {
    for (int i = 0; i < test.size(); i++) {
        Point p = test.get(i);

        if (i == 0 || i == test.size() - 1) {
            double szog = 0;
            Image kep;

            if (i == 0) {
                kep = fejKep;
                switch (irany) {
                    case JOBBRA: szog = 0; break;
                    case LE:     szog = Math.PI / 2; break;
                    case BALRA:  szog = Math.PI; break;
                    case FEL:    szog = -Math.PI / 2; break;
                }
            } else {
                kep = farokKep;
                Point elozo = test.get(i - 1);
                if (elozo.x > p.x) {
                    szog = 0;
                } else if (elozo.x < p.x) {
                    szog = Math.PI;
                } else if (elozo.y > p.y) {
                    szog = Math.PI / 2;
                } else if (elozo.y < p.y) {
                    szog = -Math.PI / 2;
                }
            }

            Graphics2D g2d = (Graphics2D) g.create();
            g2d.rotate(szog, p.x + meret / 2.0, p.y + meret / 2.0);
            g2d.drawImage(kep, p.x, p.y, meret, meret, null);
            g2d.dispose();
        } else {
            g.drawImage(testKep, p.x, p.y, meret, meret, null);
        }
    }
}
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beadando3;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.Timer;
import javax.swing.JPanel;

/**
 *
 * @author roszk
 */
public class Jatekter extends JPanel{
    private final int MEZO_MERET = 50;
    private final int SZELESSEG = 600;
    private final int MAGASSAG = 600;
    private final Random rnd = new Random();
    
    private Kigyo kigyo;
    private Elemozsia elemozsia;
    private List<Ko> kovek;
    private Timer jatekIdozito;
    private Timer oraIdozito;
    private int elteltMasodpercek;
    private int pontszam;
    private boolean jatekFut;
    private Image hatterKep;
    private Ablak szuloAblak;
    
    public Jatekter(Ablak szuloAblak) {
        this.szuloAblak = szuloAblak;
        this.kovek = new ArrayList<>();
        this.hatterKep = new ImageIcon("kepek/hatter.png").getImage();

        this.setPreferredSize(new Dimension(SZELESSEG, MAGASSAG));
        this.setFocusable(true);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (!jatekFut || kigyo == null) {
                    return;
                }
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_W:
                        kigyo.setIrany(Irany.FEL);
                        break;
                    case KeyEvent.VK_S:
                        kigyo.setIrany(Irany.LE);
                        break;
                    case KeyEvent.VK_A:
                        kigyo.setIrany(Irany.BALRA);
                        break;
                    case KeyEvent.VK_D:
                        kigyo.setIrany(Irany.JOBBRA);
                        break;
                }
            }
        });

        this.jatekIdozito = new Timer(350, e -> lepes());

        this.oraIdozito = new Timer(1000, e -> {
            if (jatekFut) {
                elteltMasodpercek++;
                if (szuloAblak != null) {
                    szuloAblak.frissitAllapotsav(pontszam, elteltMasodpercek);
                }
            }
        });

        ujJatek();
    }

    public void ujJatek() {
        this.pontszam = 0;
        this.elteltMasodpercek = 0;
        this.jatekFut = true;

        if (szuloAblak != null) {
            szuloAblak.frissitAllapotsav(pontszam, elteltMasodpercek);
        }

        int rndSzam = rnd.nextInt(4);
        this.kigyo = new Kigyo(SZELESSEG/2, MAGASSAG/2, MEZO_MERET, Irany.values()[rndSzam]);

        kovekGeneralasa();
        ujElemozsiaGeneralas();

        jatekIdozito.start();
        oraIdozito.start();
        repaint();
    }
    /**
     * 
     * @param g 
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(new Color(117, 173, 24));
        g.fillRect(0, 0, SZELESSEG, MAGASSAG);

        g.setColor(Color.GRAY);
        for (int x = 0; x <= SZELESSEG; x += MEZO_MERET) {
            g.drawLine(x, 0, x, MAGASSAG);
        }
        for (int y = 0; y <= MAGASSAG; y += MEZO_MERET) {
            g.drawLine(0, y, SZELESSEG, y);
        }if (kovek != null) {
            for (Ko ko : kovek) {
                ko.kirajzol(g);
            }
        }if (elemozsia != null) {
            elemozsia.kirajzol(g);
        }if (kigyo != null) {
            kigyo.kirajzol(g);
        }
    }

    private void lepes() {
        if (!jatekFut) {
            return;
        }
        kigyo.mozog();
        if (utkozesEllenorzes()) {
            jatekVege();
            return;
        }
        Point fej = kigyo.getFej();
        if (elemozsia != null && elemozsia.utkozik(fej.x, fej.y)) {
            kigyo.novekszik();
            pontszam++;
            if (szuloAblak != null) {
                szuloAblak.frissitAllapotsav(pontszam, elteltMasodpercek);
            }
            ujElemozsiaGeneralas();
        }
        repaint();
    }

    private boolean utkozesEllenorzes() {
        Point fej = kigyo.getFej();
        if (fej.x < 0 || fej.x >= SZELESSEG || fej.y < 0 || fej.y >= MAGASSAG) {
            return true;
        }
        if (kigyo.maganakUtkozott()) {
            return true;
        }
        for (Ko ko : kovek) {
            if (ko.utkozik(fej.x, fej.y)) {
                return true;
            }
        }
        return false;
    }
    
    private void kovekGeneralasa() {
        kovek.clear();
        Image koKep = new ImageIcon("kepek/ko.png").getImage();
        int oszlopok = SZELESSEG/MEZO_MERET;
        int sorok = MAGASSAG/MEZO_MERET;
        int kovekSzama = 5;

        while (kovek.size() < kovekSzama) {
            int x = rnd.nextInt(oszlopok) * MEZO_MERET;
            int y = rnd.nextInt(sorok) * MEZO_MERET;

            if (!foglaltE(x, y)) {
                kovek.add(new Ko(x, y, MEZO_MERET, koKep));
            }
        }
    }

    private void ujElemozsiaGeneralas() {
        int oszlopok = SZELESSEG / MEZO_MERET;
        int sorok = MAGASSAG / MEZO_MERET;

        while (true) {
            int x = rnd.nextInt(oszlopok) * MEZO_MERET;
            int y = rnd.nextInt(sorok) * MEZO_MERET;

            if (!foglaltE(x, y)) {
                if (elemozsia == null) {
                    Image elemozsiaKep = new ImageIcon("kepek/elemozsia.png").getImage();
                    elemozsia = new Elemozsia(x, y, MEZO_MERET, elemozsiaKep);
                } else {
                    elemozsia.ujPozicio(x, y);
                }
                break;
            }
        }
    }

    private boolean foglaltE(int x, int y) {
        if (kigyo != null && kigyo.getTest().contains(new Point(x, y))) {
            return true;
        }
        for (Ko ko : kovek) {
            if (ko.utkozik(x, y)) {
                return true;
            }
        }
        return false;
    }
    
    private void jatekVege() {
        this.jatekFut = false;
        jatekIdozito.stop();
        oraIdozito.stop();

        if (szuloAblak != null) {
            szuloAblak.jatekVege(pontszam);
        }
    }
}

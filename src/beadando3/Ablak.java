/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beadando3;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author roszk
 */
public class Ablak extends JFrame {
    private Jatekter jatekter;
    private Adatbazis adatbazis;
    private JLabel idoCimke;
    private JLabel pontszamCimke;

    public Ablak(){
        this.setTitle("Kígyó játék");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.setResizable(false);

        this.adatbazis = new Adatbazis();

        JPanel allapotsav = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 5));
        this.pontszamCimke = new JLabel("Pontszám: 0");
        this.idoCimke = new JLabel("Idő: 0 mp");
        allapotsav.add(pontszamCimke);
        allapotsav.add(idoCimke);
        this.add(allapotsav, BorderLayout.NORTH);

        menuLetrehozasa();

        this.jatekter = new Jatekter(this);
        this.add(jatekter, BorderLayout.CENTER);

        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void menuLetrehozasa() {
        JMenuBar menuSor = new JMenuBar();
        JMenu jatekMenu = new JMenu("Játék");

        JMenuItem ujJatekItem = new JMenuItem("Új játék");
        ujJatekItem.addActionListener(e -> {
            jatekter.ujJatek();
            jatekter.requestFocusInWindow();
        });

        JMenuItem top10Item = new JMenuItem("Top 10");
        top10Item.addActionListener(e -> top10Megjelenitese());

        JMenuItem kilepesItem = new JMenuItem("Kilépés");
        kilepesItem.addActionListener(e -> System.exit(0));

        jatekMenu.add(ujJatekItem);
        jatekMenu.add(top10Item);
        jatekMenu.addSeparator();
        jatekMenu.add(kilepesItem);

        menuSor.add(jatekMenu);
        this.setJMenuBar(menuSor);
    }

    public void frissitAllapotsav(int pontszam, int elteltIdo) {
        pontszamCimke.setText("Pontszám: " + pontszam);
        idoCimke.setText("Idő: " + elteltIdo + " mp");
    }

    public void jatekVege(int pontszam) {
        String nev = JOptionPane.showInputDialog(
            this,
            "Vége a játéknak! Elért pontszám: " + pontszam + "\nAdd meg a neved a mentéshez:",
            "Játék vége",
            JOptionPane.PLAIN_MESSAGE
        );
        if (nev != null && !nev.trim().isEmpty()) {
            adatbazis.eredmenyMentese(nev.trim(), pontszam);
        }
        int valasz = JOptionPane.showConfirmDialog(
            this,
            "Szeretnél új játékot indítani?",
            "Új kör",
            JOptionPane.YES_NO_OPTION
        );
        if (valasz == JOptionPane.YES_OPTION) {
            jatekter.ujJatek();
            jatekter.requestFocusInWindow();
        }
    }

    private void top10Megjelenitese() {
        List<String> eredmenyek = adatbazis.top10Lekerese();
        StringBuilder szoveg = new StringBuilder("Top 10 eredmény:\n\n");
        
        if (eredmenyek == null || eredmenyek.isEmpty()) {
            szoveg.append("Még nincs mentett eredmény.");
        } else {
            for (int i = 0; i < eredmenyek.size(); i++) {
                szoveg.append(i + 1).append(". ").append(eredmenyek.get(i)).append("\n");
            }
        }

        JOptionPane.showMessageDialog(
            this,
            szoveg.toString(),
            "Ranglista",
            JOptionPane.INFORMATION_MESSAGE
        );
    }
}

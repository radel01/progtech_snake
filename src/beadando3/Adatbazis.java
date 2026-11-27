/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beadando3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author roszk
 */
public class Adatbazis {

    private String url;

    public Adatbazis() {
        this.url = "jdbc:sqlite:eredmenyek.db";
        tablaLetrehozasa();
    }

    private void tablaLetrehozasa() {
        String sql = "CREATE TABLE IF NOT EXISTS eredmenyek ("
                   + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
                   + "nev TEXT NOT NULL, "
                   + "pontszam INTEGER NOT NULL"
                   + ");";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.err.println("Hiba a tábla létrehozásakor: " + e.getMessage());
        }
    }

    public void eredmenyMentese(String nev, int pontszam) {
        String sql = "INSERT INTO eredmenyek (nev, pontszam) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, nev);
            pstmt.setInt(2, pontszam);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Hiba a mentéskor: " + e.getMessage());
        }
    }

    public List<String> top10Lekerese() {
        List<String> lista = new ArrayList<>();
        String sql = "SELECT nev, pontszam FROM eredmenyek ORDER BY pontszam DESC LIMIT 10";

        try (Connection conn = DriverManager.getConnection(url);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String nev = rs.getString("nev");
                int pont = rs.getInt("pontszam");
                lista.add(nev + " - " + pont + " pont");
            }
        } catch (SQLException e) {
            System.err.println("Hiba a lekérdezéskor: " + e.getMessage());
        }

        return lista;
    }
}

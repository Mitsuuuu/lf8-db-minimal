package de.schule;

import java.sql.*;

public class RunSQLConnection {
    // ====== 1) DB-Zugangsdaten (ggf. anpassen) ======
    private static final String URL = "jdbc:mariadb://localhost:3306/lf8_converter";
    private static final String USER = "root";
    private static final String PASS = ""; // XAMPP häufig leer: ""

    // Führt ein SELECT aus und gibt die Tabelle in der Konsole aus
    public static void runQuery(String sql) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("DB OK ✅");
            Main.printResultSet(rs);

        } catch (SQLException e) {
            System.out.println("Fehler ❌: " + e.getMessage());
            System.out.println("Checkliste:");
            System.out.println("- XAMPP/MariaDB läuft?");
            System.out.println("- DB lf8_converter existiert?");
            System.out.println("- Tabelle Persons existiert?");
            System.out.println("- URL/User/Pass korrekt?");
        }
    }
}

import java.sql.*;

public class Main {

    // ====== 1) DB-Zugangsdaten (ggf. anpassen) ======
    private static final String URL  = "jdbc:mariadb://localhost:3306/lf8_converter";
    private static final String USER = "root";
    private static final String PASS = ""; // XAMPP häufig leer: ""

    public static void main(String[] args) {

        // ====== 2) HIER SQL-Befehl eintragen ======
        // Tipp für den Anfang: Erstmal nur COUNT(*)
        //String sql = "SELECT COUNT(*) AS anzahl FROM persons";
        String sql = "SELECT * FROM persons";

        runQuery(sql);
    }

    // Führt ein SELECT aus und gibt die Tabelle in der Konsole aus
    private static void runQuery(String sql) {
        try (Connection con = DriverManager.getConnection(URL, USER, PASS);
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("DB OK ✅");
            printResultSet(rs);

        } catch (SQLException e) {
            System.out.println("Fehler ❌: " + e.getMessage());
            System.out.println("Checkliste:");
            System.out.println("- XAMPP/MariaDB läuft?");
            System.out.println("- DB lf8_converter existiert?");
            System.out.println("- Tabelle Persons existiert?");
            System.out.println("- URL/User/Pass korrekt?");
        }
    }

    // Gibt ein ResultSet dynamisch aus (Spaltennamen + Werte)
    private static void printResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData meta = rs.getMetaData();
        int cols = meta.getColumnCount();

        // Spaltenüberschriften
        for (int i = 1; i <= cols; i++) {
            System.out.print(meta.getColumnLabel(i));
            if (i < cols) System.out.print(" | ");
        }
        System.out.println();

        // Trennlinie
        for (int i = 1; i <= cols; i++) {
            System.out.print("--------");
            if (i < cols) System.out.print("+");
        }
        System.out.println();

        // Zeilen
        boolean any = false;
        while (rs.next()) {
            any = true;
            for (int i = 1; i <= cols; i++) {
                System.out.print(rs.getString(i));
                if (i < cols) System.out.print(" | ");
            }
            System.out.println();
        }

        if (!any) {
            System.out.println("(Keine Zeilen)");
        }
    }
}
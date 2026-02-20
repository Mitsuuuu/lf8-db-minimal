package de.schule;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.*;
import java.util.Scanner;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.*;
import java.util.List;

public class Main {

    // ====== 1) DB-Zugangsdaten (ggf. anpassen) ======
    private static final String URL = "jdbc:mariadb://localhost:3306/lf8_converter";
    private static final String USER = "root";
    private static final String PASS = ""; // XAMPP häufig leer: ""

    public static void main(String[] args) throws IOException, CsvException {

        // ====== 2) HIER SQL-Befehl eintragen ======
        // Tipp für den Anfang: Erstmal nur COUNT(*)
        //String sql = "SELECT COUNT(*) AS anzahl FROM persons";
        String sql = "SELECT * FROM persons";

        runQuery(sql);

        readjson("src/main/resources/json_import_testing/beispiel.json");

        readCSV("src/main/resources/csv_file/csv_testing.csv");
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

    // Parsen der JSON Datei
    private static void readjson(String filename) {
        try {
            String text = Files.readString(Path.of(filename));
            JSONArray person = new JSONArray(text);

            for (int i = 0; i < person.length(); i++) {
                JSONObject p = person.getJSONObject(i);

                String firstName = p.getString("firstName");
                String lastName = p.getString("lastName");
                String email = p.getString("email");

                String sqlJSON = "INSERT INTO persons (first_name, last_name, email) VALUES ('" + firstName + "', '" + lastName + "', '" + email + "')";
                runQuery(sqlJSON);

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Parsen der CSV-Datei
    private static void readCSV(String csv_filename) throws IOException{
            try (CSVReader reader = new CSVReader(new FileReader((csv_filename)))){

                List<String[]> csv_person = reader.readAll();

                for (String[] row : csv_person) {
                    String firstName = row[0]; // first column
                    String lastName  = row[1]; // second column
                    String email     = row[2]; // third column

                    System.out.println(firstName + " | " + lastName + " | " + email);

                    String sqlJSON = "INSERT INTO persons (first_name, last_name, email) " + "VALUES ('" + firstName + "', '" + lastName + "', '" + email + "')";
                    runQuery(sqlJSON);
                }

            } catch (CsvException e){
                throw new RuntimeException(e);
                }
    }
    public static  void User_input(){
        System.out.println("Für CSV Datei einlesen: 1\n Für JSON Datei einlesen: 2\n Für DB ausgabe: 3");
        Scanner sc = new Scanner(System.in);
        int input  = sc.nextInt();
        switch (input){
            case 1:
                readCSV();
                break;
            case 2:
                readjson();
                break;
            case 3:
                printResultSet();
                break;
            case default:
                break;
        }
    }
}

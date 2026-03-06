package de.schule;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.*;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException{
        User_input();
    }

    // Gibt ein ResultSet dynamisch aus (Spaltennamen + Werte)
    public static void printResultSet(ResultSet rs) throws SQLException {
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


    public static  void User_input() throws IOException {
        System.out.println("Für CSV Datei einlesen: 1\n Für JSON Datei einlesen: 2\n Für DB ausgabe: 3");
        Scanner sc = new Scanner(System.in);
        int input  = sc.nextInt();
        String filename = null;
        if(input == 1 || input == 2) {
            filename = sc.next();
        }
        switch (input){
            case 1:
                ReadCSV.readCSV(filename);
                break;
            case 2:
                ReadJSON.readjson(filename);
                break;
            case 3:
                RunSQLConnection.runQuery("SELECT * FROM persons");
                break;
            default:
                break;
        }
    }
}

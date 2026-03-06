package de.schule;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class ReadCSV {
    // Parsen der CSV-Datei
    public static void readCSV(String csv_filename) throws IOException {
        try (CSVReader reader = new CSVReader(new FileReader((csv_filename)))){

            List<String[]> csv_person = reader.readAll();

            for (String[] row : csv_person) {
                String firstName = row[0]; // first column
                String lastName  = row[1]; // second column
                String email     = row[2]; // third column

                System.out.println(firstName + " | " + lastName + " | " + email);

                String sqlCSV = "INSERT INTO persons (first_name, last_name, email) " + "VALUES ('" + firstName + "', '" + lastName + "', '" + email + "')";
                RunSQLConnection.runQuery(sqlCSV);
            }

        } catch (CsvException e){
            throw new RuntimeException(e);
        }
    }
}

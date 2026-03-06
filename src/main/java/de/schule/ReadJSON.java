package de.schule;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.file.Files;
import java.nio.file.Path;

public class ReadJSON
{
    // Parsen der JSON Datei
    public static void readjson(String filename) {
        try {
            String text = Files.readString(Path.of(filename));
            JSONArray person = new JSONArray(text);

            for (int i = 0; i < person.length(); i++) {
                JSONObject p = person.getJSONObject(i);

                String firstName = p.getString("firstName");
                String lastName = p.getString("lastName");
                String email = p.getString("email");

                String sqlJSON = "INSERT INTO persons (first_name, last_name, email) VALUES ('" + firstName + "', '" + lastName + "', '" + email + "')";
                RunSQLConnection.runQuery(sqlJSON);

            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

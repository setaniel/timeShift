import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 Makes an HTTP request to the API <a href="https://ipinfo.io">ipinfo.io</a> <br>
 Gets JSON with location data <br>
 Parse JSON using Json <br>
 Extracts and outputs the name of the city, region, and country
 @author Setaniel
 @version 1.1.6
 */

public class GeoLocationFinder {
    private static final String API_KEY = "17f1f080d4eb42";
    private static final String API_URL = "https://ipinfo.io/json?token=%s";

    /**
     * Returns the current city based on your ip address
    */
    static String getCity() {
        String jsonResponse = null;
        try {
            jsonResponse = getJsonFromApi();
        } catch (IOException e) {
            //
             //
             //
            // Сделать окно ошибок !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            //
             //
             //
             //
            System.err.println("Ошибка при получении данных о местоположении: " + e.getMessage());
            throw new RuntimeException(e);
        }
        JsonObject response = new Gson().fromJson(jsonResponse, JsonObject.class);
        return response.get("city").getAsString();
    }

    /**
    The returned String contains the values: <br>
     City <br>
     Country <br>
     Region <br>
     Coordinates <br>
     IP Address <br>
     Internet Service Provider <br>
     Postal <br>
     Timezone
    */
    private static String getJsonFromApi() throws IOException {
        URL url = new URL(String.format(API_URL, API_KEY));
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        }
    }
}

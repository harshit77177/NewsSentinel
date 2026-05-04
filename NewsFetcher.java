import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

public class NewsFetcher {
    private static final String API_KEY = "1c9ebf20d63b44c1810531e5285d5814";
    private static final String ENDPOINT = "https://newsapi.org/v2/everything";

    public static List<String> fetch(String topic) {
        List<String> newsList = new ArrayList<>();

        try {
            // Convert topic to lowercase (team rule)
            String processedTopic = topic.toLowerCase();

            // Encode topic for URL
            String encodedTopic = URLEncoder.encode(processedTopic, StandardCharsets.UTF_8.toString());

            // 🔥 UPDATED: Limit results to 10
            String urlString = String.format(
                    "%s?q=%s&pageSize=10&language=en&apiKey=%s",
                    ENDPOINT,
                    encodedTopic,
                    API_KEY
            );

            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setReadTimeout(5000);

            int responseCode = conn.getResponseCode();

            // Handle API errors
            if (responseCode != 200) {
                System.err.println("API Error: " + responseCode);
                return newsList;
            }

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            StringBuilder response = new StringBuilder();
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            in.close();

            // Parse JSON
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray articles = jsonResponse.getJSONArray("articles");

            for (int i = 0; i < articles.length(); i++) {
                JSONObject article = articles.getJSONObject(i);

                String title = article.optString("title", "");

                // Clean invalid titles
                if (!title.equals("[Removed]") && !title.isEmpty()) {
                    newsList.add(title); // clean headline only
                }
            }

            conn.disconnect(); // good practice

        } catch (Exception e) {
            System.err.println("Error in NewsFetcher: " + e.getMessage());
        }

        return newsList;
    }
}
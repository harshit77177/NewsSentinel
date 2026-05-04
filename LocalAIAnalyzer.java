import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class LocalAIAnalyzer {

    public static String analyze(String text) {
        try {
            URL url = new URL("http://localhost:11434/api/generate");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setDoOutput(true);

            JSONObject body = new JSONObject();

            body.put("model", "mistral");

            body.put("prompt",
                "You are a news analyst.\n\n" +
                "Analyze the following news headlines and respond STRICTLY in this format:\n\n" +

                "Positive Insights:\n" +
                "- short point\n" +
                "- short point\n\n" +

                "Negative Concerns:\n" +
                "- short point\n" +
                "- short point\n\n" +

                "Overall Sentiment:\n" +
                "One line only (Positive/Negative/Neutral)\n\n" +

                "IMPORTANT RULES:\n" +
                "- Each point must be ONE LINE only\n" +
                "- Keep it SHORT (max 10 words)\n" +
                "- Do NOT explain\n" +
                "- Do NOT write paragraphs\n\n" +

                "News:\n" + text
            );

            body.put("stream", false);

            OutputStream os = conn.getOutputStream();
            os.write(body.toString().getBytes());
            os.flush();
            os.close();

            BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream())
            );

            StringBuilder response = new StringBuilder();
            String line;

            while ((line = br.readLine()) != null) {
                response.append(line);
            }

            br.close();

            JSONObject json = new JSONObject(response.toString());

            return json.getString("response");

        } catch (Exception e) {
            return "AI Error: " + e.getMessage();
        }
    }
}
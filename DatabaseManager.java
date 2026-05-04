import java.sql.*;
import java.util.*;

public class DatabaseManager {

    private static Connection conn;

    public static void connect() {
        try {
            String url = "jdbc:postgresql://ep-sweet-band-ancnqqlu-pooler.c-6.us-east-1.aws.neon.tech/neondb?sslmode=require&channel_binding=require";
            String user = "neondb_owner";
            String password = "npg_bS4Fe8MZuKfr";

            conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connected to Neon DB");

        } catch (Exception e) {
            System.out.println("DB Connection Error: " + e.getMessage());
        }
    }

    public static void save(String topic, List<String> news, List<String> sentiments) {
        try {
            String query = "INSERT INTO news_history(topic, news, sentiment) VALUES (?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(query);

            for (int i = 0; i < news.size(); i++) {
                ps.setString(1, topic);
                ps.setString(2, news.get(i));
                ps.setString(3, sentiments.get(i));
                ps.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println("Error saving to DB: " + e.getMessage());
        }
    }

    // 🔥 THIS WAS MISSING → FIXED
    public static List<String> getHistory() {
        List<String> history = new ArrayList<>();

        try {
            String query = "SELECT topic, news, sentiment FROM news_history ORDER BY id DESC LIMIT 20";

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                String topic = rs.getString("topic");
                String news = rs.getString("news");
                String sentiment = rs.getString("sentiment");

                history.add("Topic: " + topic + "\n" + news + "\n→ Sentiment: " + sentiment);
            }

        } catch (Exception e) {
            System.out.println("Error fetching history: " + e.getMessage());
        }

        return history;
    }
}
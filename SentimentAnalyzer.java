import java.util.*;

public class SentimentAnalyzer {

    public static List<String> analyze(List<String> news) {

        List<String> sentiments = new ArrayList<>();

        String[] positiveWords = {
            "growth", "profit", "success", "boost", "win",
            "increase", "strong", "record", "benefit", "improve"
        };

        String[] negativeWords = {
            "war", "loss", "crisis", "decline", "fall",
            "risk", "damage", "attack", "conflict", "fail"
        };

        for (String text : news) {

            text = text.toLowerCase();

            int pos = 0, neg = 0;

            for (String w : positiveWords) {
                if (text.contains(w)) pos++;
            }

            for (String w : negativeWords) {
                if (text.contains(w)) neg++;
            }

            if (pos > neg) sentiments.add("Positive");
            else if (neg > pos) sentiments.add("Negative");
            else sentiments.add("Neutral");
        }

        return sentiments;
    }
}
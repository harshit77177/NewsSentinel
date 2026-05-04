import java.util.*;

public class SummaryGenerator {

    public static String generate(List<String> news, List<String> sentiments) {

        StringBuilder positive = new StringBuilder();
        StringBuilder negative = new StringBuilder();

        int pos = 0, neg = 0, neu = 0;

        for (int i = 0; i < news.size(); i++) {
            String sentiment = sentiments.get(i);
            String headline = news.get(i);

            if (sentiment.equals("Positive")) {
                positive.append("• ").append(headline).append("\n");
                pos++;
            } 
            else if (sentiment.equals("Negative")) {
                negative.append("• ").append(headline).append("\n");
                neg++;
            } 
            else {
                neu++;
            }
        }

        String bias;
        if (pos > neg) bias = "Mostly Positive";
        else if (neg > pos) bias = "Mostly Negative";
        else bias = "Neutral / Mixed";

        // 🔥 Smart conclusion
        String conclusion;
        if (bias.contains("Positive")) {
            conclusion = "The news coverage reflects optimism and positive developments.";
        } else if (bias.contains("Negative")) {
            conclusion = "The news coverage is dominated by concerns and negative developments.";
        } else {
            conclusion = "The news shows a balanced mix of positive and negative perspectives.";
        }

        return "📰 NEWS ANALYSIS REPORT\n"
                + "━━━━━━━━━━━━━━━━━━━━━━\n\n"

                + "📈 Positive Insights:\n"
                + (pos == 0 ? "• No strong positive trends found\n" : positive.toString())

                + "\n📉 Negative Concerns:\n"
                + (neg == 0 ? "• No major risks identified\n" : negative.toString())

                + "\n⚖ Overall Bias:\n"
                + "→ " + bias + "\n\n"

                + "📊 Statistics:\n"
                + "• Positive: " + pos + "\n"
                + "• Negative: " + neg + "\n"
                + "• Neutral: " + neu + "\n\n"

                + "💡 Conclusion:\n"
                + conclusion;
    }
}
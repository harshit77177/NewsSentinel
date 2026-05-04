import javax.swing.*;
import java.awt.*;
import java.util.List;

public class UI {

    private JFrame frame;
    private JTextField topicField;
    private JTextArea output;

    public UI() {
        frame = new JFrame("News Analysis System");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new FlowLayout());

        JLabel label = new JLabel("Topic:");
        topicField = new JTextField(25);

        JButton analyzeButton = new JButton("Analyze");
        JButton historyButton = new JButton("History");

        topPanel.add(label);
        topPanel.add(topicField);
        topPanel.add(analyzeButton);
        topPanel.add(historyButton);

        output = new JTextArea();
        output.setEditable(false);
        output.setFont(new Font("Monospaced", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(output);

        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(scrollPane, BorderLayout.CENTER);

        analyzeButton.addActionListener(e -> analyze());
        historyButton.addActionListener(e -> showHistory());

        frame.setVisible(true);
    }

    private void analyze() {
        String topic = topicField.getText().trim();

        if (topic.isEmpty()) {
            output.setText("⚠ Please enter a topic.");
            return;
        }

        output.setText("🔄 Fetching news...\n");

        List<String> news = NewsFetcher.fetch(topic);

        if (news.isEmpty()) {
            output.setText("❌ No news found or API failed.");
            return;
        }

        List<String> sentiments = SentimentAnalyzer.analyze(news);

        DatabaseManager.save(topic, news, sentiments);

        String combinedNews = String.join("\n", news);

        output.setText("🧠 Analyzing with AI...\n");

        String aiReport = LocalAIAnalyzer.analyze(combinedNews);

        // fallback if AI fails
        if (aiReport.startsWith("AI Error")) {
            aiReport = SummaryGenerator.generate(news, sentiments);
        }

        output.setText("📊 NEWS ANALYSIS REPORT\n\n" + aiReport);
    }

    private void showHistory() {
        List<String> history = DatabaseManager.getHistory();

        if (history.isEmpty()) {
            output.setText("No history found.");
            return;
        }

        StringBuilder sb = new StringBuilder("📜 STORED RESULTS\n\n");

        for (String record : history) {
            sb.append(record).append("\n\n");
        }

        output.setText(sb.toString());
    }
}
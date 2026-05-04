# News Bias Analyzer

A Java Swing desktop application that fetches news headlines for a topic, analyzes their sentiment, generates a short report, and stores previous results in a PostgreSQL database.

## Features

- Search news by topic using NewsAPI
- Fetch up to 10 English headlines for each search
- Classify each headline as Positive, Negative, or Neutral
- Generate an AI-based report using local Ollama/Mistral when available
- Fall back to a basic summary generator when AI is unavailable
- Store analyzed headlines and sentiment results in PostgreSQL
- View recent analysis history from the desktop UI

## Tech Stack

- Java
- Java Swing
- NewsAPI
- org.json
- PostgreSQL JDBC
- Neon PostgreSQL
- Ollama with Mistral, optional

## Project Structure

```text
oops project final/
+-- README.md
`-- oops project/
    +-- MainApp.java
    +-- UI.java
    +-- NewsFetcher.java
    +-- SentimentAnalyzer.java
    +-- SummaryGenerator.java
    +-- LocalAIAnalyzer.java
    +-- DatabaseManager.java
    `-- lib/
        +-- json-20251224.jar
        `-- postgresql-42.7.10.jar
```

## Requirements

- JDK 8 or newer
- NewsAPI key
- PostgreSQL database
- Optional: Ollama installed locally with the `mistral` model

## Database Setup

Create the table used for storing news history:

```sql
CREATE TABLE IF NOT EXISTS news_history (
    id SERIAL PRIMARY KEY,
    topic VARCHAR(255) NOT NULL,
    news TEXT NOT NULL,
    sentiment VARCHAR(50) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

Update the database connection details in `DatabaseManager.java` before running the project.

## How to Run

Open PowerShell inside the source folder:

```powershell
cd "E:\oops project final\oops project"
```

Compile the project:

```powershell
javac -cp ".;lib/*" *.java
```

Run the application:

```powershell
java -cp ".;lib/*" MainApp
```

For Linux or macOS, use `:` instead of `;` in the classpath:

```bash
javac -cp ".:lib/*" *.java
java -cp ".:lib/*" MainApp
```

## Optional AI Setup

The project can use a local Ollama server for AI-based analysis.

Install Ollama, then pull the Mistral model:

```bash
ollama pull mistral
```

Make sure Ollama is running at:

```text
http://localhost:11434
```

If Ollama is not available, the app automatically uses the basic summary generator.

## How to Use

1. Run the application.
2. Enter a news topic in the text field.
3. Click **Analyze**.
4. View the generated report and sentiment statistics.
5. Click **History** to view recently stored analysis results.

## Important Security Note

Before uploading this project to a public GitHub repository, do not commit real API keys, database usernames, or database passwords. Move sensitive values from the Java source files into environment variables or a local configuration file that is ignored by Git.

## Future Improvements

- Move API keys and database credentials to environment variables
- Add a `.gitignore` file for generated `.class` files
- Add charts for sentiment statistics
- Add input validation and user-friendly error dialogs
- Package the project as an executable JAR

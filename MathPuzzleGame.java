import java.io.*;
import java.util.*;

public class MathPuzzleGame {
    public static void main(String[] args) {
        @SuppressWarnings("resource")
        Scanner scanner = new Scanner(System.in);

        // Get player's name
        System.out.print("Enter your name: ");
        String playerName = scanner.nextLine();

        // Get difficulty level
        System.out.print("Enter difficulty level (easy/medium/hard): ");
        String difficulty = scanner.nextLine().toLowerCase();

        // File paths
        String inputFilePath = "questions.txt";
        String outputFilePath = "scores.txt";

        try {
            // Read questions from the file
            List<String> questions = readQuestionsFromFile(inputFilePath, difficulty);

            if (questions.isEmpty()) {
                System.out.println("No questions available for the selected difficulty level.");
                return;
            }

            int score = 0;

            // Ask questions
            for (String question : questions) {
                String[] parts = question.split(",");
                String mathQuestion = parts[0];
                int correctAnswer = Integer.parseInt(parts[1]);

                System.out.print(mathQuestion + " ");
                int playerAnswer = scanner.nextInt();

                if (playerAnswer == correctAnswer) {
                    score++;
                }
            }

            // Save score to the file
            saveScoreToFile(outputFilePath, playerName, score);

            System.out.println("Thank you for playing, " + playerName + "! Your score: " + score);

        } catch (IOException e) {
            System.out.println("An error occurred while processing the files: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter numeric answers.");
        }
    }

    private static List<String> readQuestionsFromFile(String filePath, String difficulty) throws IOException {
        List<String> questions = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith(difficulty)) {
                    questions.add(line.substring(difficulty.length() + 1));
                }
            }
        }

        return questions;
    }

    private static void saveScoreToFile(String filePath, String playerName, int score) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(playerName + ": " + score);
            writer.newLine();
        }
    }
}

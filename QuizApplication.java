import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

class Question {
    String question;
    String[] options;
    String answer;

    Question(String question, String[] options, String answer) {
        this.question = question;
        this.options = options;
        this.answer = answer;
    }
}

public class QuizApplication extends JFrame {
    private ArrayList<Question> questions;
    private int currentQuestionIndex = -1;
    private int score = 0;

    private JLabel questionLabel;
    private JRadioButton[] optionButtons;
    private ButtonGroup optionsGroup;
    private JButton submitButton;
    private JButton nextButton;
    private JLabel feedbackLabel;
    private JTextArea feedbackArea;
    private JButton feedbackSubmitButton;

    private JPanel welcomePanel;
    private JButton startButton;
    private JPanel quizPanel;
    private JPanel questionPanel;
    private JPanel feedbackPanel;

    public QuizApplication() {
        // Set up the frame
        setTitle("Quiz Application");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize questions
        questions = new ArrayList<>();
        questions.add(new Question("what is the capital of India?", new String[]{"PUNE", "LUCKNOW", "DELHI", "NOIDA"}, "DELHI"));
        questions.add(new Question("What is the NATONAL BIRD OF INDIA?", new String[]{"PEACOCK", "COCK", "HECOCK", "DECOCK"}, "PEACOCK"));
        questions.add(new Question("What is the other name of India?", new String[]{"Bharat", "Pakistan", "France", "Korea"}, "Bharat"));
        questions.add(new Question("What is the CURRENCY OF INDIA?", new String[]{"YEN", "DIRHAM", "RUPEE", "DOLLAR"}, "RUPEE"));
        questions.add(new Question("Who is the prime minister of India?", new String[]{"Amit Shah", "Narendra Modi", "Arun Jetly", "Mahendra Singh Dhoni"}, "Narendra Modi"));

        // Set up welcome panel
        welcomePanel = new JPanel();
        welcomePanel.setLayout(new BorderLayout());
        
        JLabel welcomeLabel = new JLabel("Welcome to the Quiz Application", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 30));
        
        startButton = new JButton("Start Quiz");
        startButton.setFont(new Font("Serif", Font.BOLD, 18));
        
        // Add an image to the welcome panel
        ImageIcon welcomeImage = new ImageIcon("C:/Users/Administrator/Documents/WhatsApp Image 2024-06-22 at 10.32.41 AM.jpeg"); // Change this path to your image path
        JLabel imageLabel = new JLabel(welcomeImage, SwingConstants.CENTER);
        
        welcomePanel.add(welcomeLabel, BorderLayout.NORTH);
        welcomePanel.add(imageLabel, BorderLayout.CENTER);
        welcomePanel.add(startButton, BorderLayout.SOUTH);

        // Set up quiz panel
        quizPanel = new JPanel(new BorderLayout());

        questionPanel = new JPanel(new GridLayout(6, 1));
        questionLabel = new JLabel();
        questionPanel.add(questionLabel);
        optionsGroup = new ButtonGroup();
        optionButtons = new JRadioButton[4];
        for (int i = 0; i < 4; i++) {
            optionButtons[i] = new JRadioButton();
            optionsGroup.add(optionButtons[i]);
            questionPanel.add(optionButtons[i]);
        }
        submitButton = new JButton("Submit Answer");
        nextButton = new JButton("Next Question");
        nextButton.setEnabled(false);

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);
        buttonPanel.add(nextButton);

        quizPanel.add(questionPanel, BorderLayout.CENTER);
        quizPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Set up feedback panel
        feedbackPanel = new JPanel(new BorderLayout());
        feedbackLabel = new JLabel("Please provide your feedback below:");
        feedbackArea = new JTextArea(5, 20);
        feedbackArea.setLineWrap(true);
        feedbackArea.setWrapStyleWord(true);
        JScrollPane feedbackScrollPane = new JScrollPane(feedbackArea);
        feedbackSubmitButton = new JButton("Submit Feedback");
        feedbackPanel.add(feedbackLabel, BorderLayout.NORTH);
        feedbackPanel.add(feedbackScrollPane, BorderLayout.CENTER);
        feedbackPanel.add(feedbackSubmitButton, BorderLayout.SOUTH);
        feedbackPanel.setVisible(false); // Initially hidden

        // Add welcome panel to frame
        add(welcomePanel);

        // Event handling for start button
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startQuiz();
            }
        });

        // Event handling for quiz
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkAnswer();
            }
        });

        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showNextQuestion();
            }
        });

        feedbackSubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitFeedback();
            }
        });

        // Show the frame
        setVisible(true);
    }

    private void startQuiz() {
        remove(welcomePanel);
        add(quizPanel);
        revalidate();
        repaint();
        showNextQuestion();
    }

    private void checkAnswer() {
        for (JRadioButton optionButton : optionButtons) {
            if (optionButton.isSelected()) {
                if (optionButton.getText().equalsIgnoreCase(questions.get(currentQuestionIndex).answer)) {
                    score++;
                }
                submitButton.setEnabled(false);
                nextButton.setEnabled(true);
                break;
            }
        }
    }

    private void showNextQuestion() {
        currentQuestionIndex++;
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            questionLabel.setText("Question: " + currentQuestion.question);
            for (int i = 0; i < optionButtons.length; i++) {
                optionButtons[i].setText(currentQuestion.options[i]);
                optionButtons[i].setSelected(false);
            }
            submitButton.setEnabled(true);
            nextButton.setEnabled(false);
        } else {
            displayFinalScore();
        }
    }

    private void displayFinalScore() {
        questionLabel.setText("Quiz Completed! Your score: " + score + " out of " + questions.size());
        for (JRadioButton optionButton : optionButtons) {
            optionButton.setVisible(false);
        }
        submitButton.setVisible(false);
        nextButton.setVisible(false);

        // Add the feedback panel to the quiz panel
        quizPanel.add(feedbackPanel, BorderLayout.SOUTH);
        feedbackPanel.setVisible(true);
        revalidate();
        repaint();
    }

    private void submitFeedback() {
        String feedback = feedbackArea.getText().trim();
        if (!feedback.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Thank you for your feedback!", "Feedback", JOptionPane.INFORMATION_MESSAGE);
            feedbackSubmitButton.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(this, "Please enter your feedback before submitting.", "Feedback Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new QuizApplication();
    }
}

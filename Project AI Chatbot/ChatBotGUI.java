import javax.swing.*;
import java.awt.*;
import java.util.*;

public class ChatBotGUI extends JFrame {
    private JTextArea chatArea;
    private JTextField inputField;
    private JButton sendButton;

    private Map<String, String> faqMap;

    public ChatBotGUI() {
        setTitle("AI Chatbot");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Chat area (output)
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        // Input area (user message)
        inputField = new JTextField();
        sendButton = new JButton("Send");

        // Panel for input and button
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendButton, BorderLayout.EAST);
        add(inputPanel, BorderLayout.SOUTH);

        // Action when button is clicked or enter is pressed
        sendButton.addActionListener(e -> processInput());
        inputField.addActionListener(e -> processInput());

        // Pre-load FAQ responses
        loadFAQs();

        setVisible(true);
    }

    private void processInput() {
        String userInput = inputField.getText().trim();
        if (userInput.isEmpty()) return;

        chatArea.append("You: " + userInput + "\n");
        String response = generateResponse(userInput);
        chatArea.append("Bot: " + response + "\n\n");

        inputField.setText("");
    }

    // NLP processing + response
    private String generateResponse(String input) {
        String cleanedInput = input.toLowerCase().replaceAll("[^a-zA-Z0-9\\s]", "").trim();

        // Match keywords in map
        for (String key : faqMap.keySet()) {
            if (cleanedInput.contains(key)) {
                return faqMap.get(key);
            }
        }

        return "I'm sorry, I don't understand. Can you please rephrase?";
    }

    // FAQ database
    private void loadFAQs() {
    faqMap = new HashMap<>();

    // Greetings
    faqMap.put("hello", "Hello! How can I assist you?");
    faqMap.put("hi", "Hi there! Ask me anything.");
    faqMap.put("hey", "Hey! I'm here to help.");

    // About the bot
    faqMap.put("your name", "I'm a simple AI Chatbot created in Java.");
    faqMap.put("who created you", "I was created by a Java developer for an internship project.");
    
    // Help topics
    faqMap.put("help", "Sure! You can ask me about programming, Java, or internships.");
    faqMap.put("java", "Java is a powerful object-oriented programming language.");
    faqMap.put("internship", "An internship is a work experience opportunity for students.");
    faqMap.put("project", "Do you need help with your Java project?");
    faqMap.put("career", "A career in Java development is strong and in-demand!");

    // Programming/Technology
    faqMap.put("python", "Python is a versatile language popular in AI, data science, and scripting.");
    faqMap.put("ai", "Artificial Intelligence is the simulation of human intelligence by machines.");
    faqMap.put("machine learning", "Machine Learning lets systems learn from data without being explicitly programmed.");
    faqMap.put("programming language", "Popular languages include Java, Python, JavaScript, and C++.");

    // IDE/tools
    faqMap.put("best ide", "Popular Java IDEs include IntelliJ IDEA, Eclipse, and VS Code.");
    faqMap.put("vs code", "VS Code is a lightweight and powerful source code editor.");

    // Date/time
    faqMap.put("time", "Sorry, I can't access real-time data in this version.");
    faqMap.put("date", "I'm not connected to a clock, but you can check your system's date!");

    // Goodbye
    faqMap.put("bye", "Goodbye! Have a great day!");
    faqMap.put("thanks", "You're welcome! Happy to help.");
    faqMap.put("thank you", "You're welcome!");
}

       public static void main(String[] args) {
        SwingUtilities.invokeLater(ChatBotGUI::new);
    }
}

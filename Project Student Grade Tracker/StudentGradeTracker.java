
import javax.swing.*; // For GUI components
import javax.swing.table.DefaultTableModel; // For table model
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.*; // For event handling
import java.util.*; // For ArrayList and List

// Main class for the Student Grade Tracker
public class StudentGradeTracker extends JFrame {

    // GUI input fields and buttons
    private JTextField nameField, gradeField;
    private JButton addButton, reportButton;
    private JTable table;
    private DefaultTableModel tableModel;

    // List to store student data
    private java.util.List<Student> students = new ArrayList<>();

    // Constructor to set up the GUI
    public StudentGradeTracker() {
        setTitle("Student Grade Tracker");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create the top panel for input fields
        JPanel inputPanel = new JPanel(new GridLayout(2, 2));

        inputPanel.add(new JLabel("Student Name:"));
        nameField = new JTextField(); // Input for name
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("Grades (comma-separated):"));
        gradeField = new JTextField(); // Input for grades
        inputPanel.add(gradeField);

        add(inputPanel, BorderLayout.NORTH); // Add input panel to top

        // Panel for buttons
        JPanel buttonPanel = new JPanel();
        addButton = new JButton("Add Student");
        reportButton = new JButton("Show Report");
        buttonPanel.add(addButton);
        buttonPanel.add(reportButton);
        add(buttonPanel, BorderLayout.CENTER); // Add buttons to center

        // Create table with columns
        String[] columnNames = {"Name", "Grades", "Average", "Highest", "Lowest"};
        tableModel = new DefaultTableModel(columnNames, 0); // Table model
        table = new JTable(tableModel); // Table to display student info
        add(new JScrollPane(table), BorderLayout.SOUTH); // Add table to bottom

        // Button to add a student
        addButton.addActionListener(e -> addStudent());

        // Button to display summary report
        reportButton.addActionListener(e -> updateTable());

        setVisible(true); // Make the window visible
    }

    // Function to add a student from input fields
    private void addStudent() {
        String name = nameField.getText().trim(); // Get name
        String[] gradeStrs = gradeField.getText().split(","); // Get grades

        if (name.isEmpty() || gradeStrs.length == 0) {
            JOptionPane.showMessageDialog(this, "Enter a name and at least one grade.");
            return;
        }

        try {
            List<Integer> grades = new ArrayList<>();
            for (String s : gradeStrs) {
                grades.add(Integer.parseInt(s.trim())); // Convert to integer
            }

            Student s = new Student(name, grades); // Create new student
            students.add(s); // Add to list

            // Clear input fields
            nameField.setText("");
            gradeField.setText("");

            JOptionPane.showMessageDialog(this, "Student added!");

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Grades must be numbers.");
        }
    }

    // Function to update table with all students and calculated stats
    private void updateTable() {
        tableModel.setRowCount(0); // Clear previous rows

        // Add each student to table
        for (Student s : students) {
            tableModel.addRow(new Object[]{
                    s.getName(),
                    s.getGrades().toString(),
                    s.getAverage(),
                    s.getHighest(),
                    s.getLowest()
            });
        }
    }

    // Main method to run the application
    public static void main(String[] args) {
        SwingUtilities.invokeLater(StudentGradeTracker::new);
    }
}

// Helper class to store student data
class Student {
    private String name;
    private List<Integer> grades;

    // Constructor
    public Student(String name, List<Integer> grades) {
        this.name = name;
        this.grades = grades;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Getter for grades list
    public List<Integer> getGrades() {
        return grades;
    }

    // Calculate average grade
    public double getAverage() {
        return grades.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }

    // Find highest grade
    public int getHighest() {
        return grades.stream().mapToInt(Integer::intValue).max().orElse(0);
    }

    // Find lowest grade
    public int getLowest() {
        return grades.stream().mapToInt(Integer::intValue).min().orElse(0);
    }
}

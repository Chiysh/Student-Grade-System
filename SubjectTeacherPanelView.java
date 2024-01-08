//SubjectTeacherPanelView.java
package project;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class SubjectTeacherPanelView extends JPanel {
    private JTable studentTable;
    private DefaultTableModel tableModel;
    private String subjectTeacherUsername;

    public SubjectTeacherPanelView(String subjectTeacherUsername) {
        this.subjectTeacherUsername = subjectTeacherUsername;
        setLayout(null);

        // Initialize the table model and add columns
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Student Username");
        tableModel.addColumn("Student Name");

        // Fetch registered students from the Users class
        Map<String, String> studentUsers = Users.getStudentUsers();
        for (Map.Entry<String, String> entry : studentUsers.entrySet()) {
            String studentUsername = entry.getKey();
            String studentFullName = Users.getStudentFullName(studentUsername);
            tableModel.addRow(new Object[]{studentUsername, studentFullName});
        }

        // Create the table with the model
        studentTable = new JTable(tableModel);
        studentTable.setBounds(50, 100, 300, 150);

        // Add a scroll pane for the table
        JScrollPane scrollPane = new JScrollPane(studentTable);
        scrollPane.setBounds(50, 100, 300, 150);
        add(scrollPane);

        // Add a selection listener to the table
        studentTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int selectedRow = studentTable.getSelectedRow();
                    if (selectedRow != -1) {
                        String studentId = (String) tableModel.getValueAt(selectedRow, 0);
                        String studentName = (String) tableModel.getValueAt(selectedRow, 1);

                        // Check if the student has been graded for this subject
                        if (hasStudentBeenGraded(studentId, subjectTeacherUsername)) {
                            double[] existingGrades = getExistingGrades(studentId, subjectTeacherUsername);
                            openGradingFrame(studentId, studentName, existingGrades);
                        } else {
                            openGradingFrame(studentId, studentName, null);
                        }
                    }
                }
            }
        });

        JButton viewAllGradesButton = new JButton("View Grades");
        viewAllGradesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow != -1) {
                    String studentId = (String) tableModel.getValueAt(selectedRow, 0);
                    String studentName = (String) tableModel.getValueAt(selectedRow, 1);

                    // View grades for all subjects
                    viewAllStudentGrades(studentId, studentName);
                } else {
                    JOptionPane.showMessageDialog(SubjectTeacherPanelView.this,
                            "Please select a student to view all grades.",
                            "No Student Selected",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        viewAllGradesButton.setBounds(600, 90, 115, 40);
        add(viewAllGradesButton);

        JButton backButton = new JButton("Back to Home");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Go back to the home panel
                MainFrame.showHomePanel();
            }
        });
        backButton.setBounds(600, 300, 115, 40);
        add(backButton);

        JButton updateGradeButton = new JButton("Update Grade");
        updateGradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = studentTable.getSelectedRow();
                if (selectedRow != -1) {
                    String studentId = (String) tableModel.getValueAt(selectedRow, 0);
                    String studentName = (String) tableModel.getValueAt(selectedRow, 1);

                    // Check if the student has been graded for this subject
                    if (hasStudentBeenGraded(studentId, subjectTeacherUsername)) {
                        double[] existingGrades = getExistingGrades(studentId, subjectTeacherUsername);
                        openGradingFrame(studentId, studentName, existingGrades);
                    } else {
                        openGradingFrame(studentId, studentName, null);
                    }
                } else {
                    JOptionPane.showMessageDialog(SubjectTeacherPanelView.this,
                            "Please select a student to update grades.",
                            "No Student Selected",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        updateGradeButton.setBounds(600, 145, 115, 40);
        add(updateGradeButton);
    }

    private void openGradingFrame(String studentUsername, String studentName, double[] existingGrades) {
        String subject = Users.getSubjectTeacherSubject(subjectTeacherUsername);

        JFrame gradingFrame = new JFrame("Grading System for " + studentName + " in " + subject);
        gradingFrame.setSize(400, 300);
        gradingFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel gradingPanel = new JPanel();
        gradingPanel.setLayout(null);

        JLabel titleLabel = new JLabel("Grading System for " + studentName + " in " + subject);
        titleLabel.setFont(new Font("Inter", Font.BOLD, 18));
        titleLabel.setBounds(50, 20, 300, 30);
        gradingPanel.add(titleLabel);

        JLabel quizLabel = new JLabel("Enter Quiz Score (out of 100):");
        quizLabel.setBounds(50, 70, 200, 25);
        gradingPanel.add(quizLabel);

        JTextField quizField = new JTextField(existingGrades != null ? String.valueOf(existingGrades[0]) : "");
        quizField.setBounds(250, 70, 100, 25);
        gradingPanel.add(quizField);

        JLabel examLabel = new JLabel("Enter Exam Score (out of 100):");
        examLabel.setBounds(50, 110, 200, 25);
        gradingPanel.add(examLabel);

        JTextField examField = new JTextField(existingGrades != null ? String.valueOf(existingGrades[1]) : "");
        examField.setBounds(250, 110, 100, 25);
        gradingPanel.add(examField);

        JButton submitButton = new JButton("Submit");
        submitButton.setBounds(150, 160, 100, 30);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double quizScore = Double.parseDouble(quizField.getText());
                double examScore = Double.parseDouble(examField.getText());
                double finalGrade = (quizScore * 0.6) + (examScore * 0.4);

                // Save the grade to file
                saveGradeToFile(subjectTeacherUsername, subject, studentUsername, finalGrade);

                JOptionPane.showMessageDialog(gradingFrame, "Grade for " + studentName + " in " + subject + ": " + finalGrade);
                gradingFrame.dispose(); // Close the grading frame
            }
        });
        gradingPanel.add(submitButton);

        gradingFrame.add(gradingPanel);
        gradingFrame.setVisible(true);
    }

    private void viewAllStudentGrades(String studentUsername, String studentName) {
        JFrame allGradesFrame = new JFrame("All Grades for " + studentName);
        allGradesFrame.setSize(400, 350); // Increased height for displaying average
        allGradesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel allGradesPanel = new JPanel();
        allGradesPanel.setLayout(new GridLayout(0, 2));

        // Get all subjects for subject teachers
        Set<String> allSubjects = getAllSubjectsForSubjectTeachers();

        double totalGradeSum = 0;
        int totalSubjects = 0;

        for (String subject : allSubjects) {
            String grade = getStudentGrade(studentUsername, subject);
            JLabel subjectLabel = new JLabel(subject + " Grade:");
            allGradesPanel.add(subjectLabel);
            JLabel gradeLabel = new JLabel(grade);
            allGradesPanel.add(gradeLabel);

            // Calculate total grade sum for average
            if (!grade.equals("N/A")) {
                totalGradeSum += Double.parseDouble(grade);
                totalSubjects++;
            }
        }

        // Calculate and display the total grade average
        double totalAverage = (totalSubjects > 0) ? totalGradeSum / totalSubjects : 0;
        JLabel averageLabel = new JLabel("Total Grade Average:");
        allGradesPanel.add(averageLabel);
        JLabel totalAverageLabel = new JLabel(String.format("%.2f", totalAverage));
        allGradesPanel.add(totalAverageLabel);

        allGradesFrame.add(allGradesPanel);
        allGradesFrame.setVisible(true);
    }

    private Set<String> getAllSubjectsForSubjectTeachers() {
        Set<String> subjects = new HashSet<>();
        Map<String, String> subjectTeacherUsers = Users.getSubjectTeacherUsers();
        for (String subject : subjectTeacherUsers.values()) {
            subjects.add(subject);
        }
        return subjects;
    }

    private String getStudentGrade(String studentUsername, String subject) {
        Map<String, String> subjectTeacherUsers = Users.getSubjectTeacherUsers();
        String fileName = subject + "_grades.txt";
        File gradeFile = new File(fileName);

        try {
            Scanner scanner = new Scanner(gradeFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].equals(studentUsername) && subjectTeacherUsers.containsKey(parts[2])) {
                    return parts[1]; // Return the grade for the specified subject
                }
            }
            scanner.close();
        } catch (IOException e) {
            // Ignore if the file doesn't exist yet
        }

        return "N/A"; // Return "N/A" if the grade is not found
    }

    private boolean hasStudentBeenGraded(String studentUsername, String subjectTeacherUsername) {
        String fileName = subjectTeacherUsername + "_grades.txt";
        File gradeFile = new File(fileName);

        try {
            Scanner scanner = new Scanner(gradeFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].equals(studentUsername) && parts[2].equals(subjectTeacherUsername)) {
                    return true; // Student has been graded for this subject
                }
            }
            scanner.close();
        } catch (IOException e) {
            // Ignore if the file doesn't exist yet
        }

        return false; // Student not graded for this subject
    }

    private void saveGradeToFile(String subjectTeacherUsername, String subject, String studentUsername, double finalGrade) {
        String fileName = subject + "_grades.txt";
        try {
            // Read existing grades
            Map<String, String> existingGrades = getExistingGradesMap(subjectTeacherUsername, fileName);

            // Update the grade for the student
            existingGrades.put(studentUsername, String.valueOf(finalGrade));

            // Write all grades back to the file
            try (PrintWriter writer = new PrintWriter(new FileWriter(fileName, false))) {
                for (Map.Entry<String, String> entry : existingGrades.entrySet()) {
                    writer.println(entry.getKey() + "," + entry.getValue() + "," + subjectTeacherUsername);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating grade in file.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private Map<String, String> getExistingGradesMap(String subjectTeacherUsername, String fileName) throws IOException {
        Map<String, String> existingGrades = new HashMap<>();
        File gradeFile = new File(fileName);

        if (gradeFile.exists()) {
            Scanner scanner = new Scanner(gradeFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[2].equals(subjectTeacherUsername)) {
                    existingGrades.put(parts[0], parts[1]);
                }
            }
            scanner.close();
        }

        return existingGrades;
    }

    private double[] getExistingGrades(String studentUsername, String subjectTeacherUsername) {
        String fileName = Users.getSubjectTeacherSubject(subjectTeacherUsername) + "_grades.txt";
        File gradeFile = new File(fileName);

        try {
            Scanner scanner = new Scanner(gradeFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].equals(studentUsername) && parts[2].equals(subjectTeacherUsername)) {
                    // Correct the index for exam score
                    double quizScore = Double.parseDouble(parts[1]);
                    double examScore = Double.parseDouble(parts[1]); // Corrected index
                    return new double[]{quizScore, examScore};
                }
            }
            scanner.close();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return null;
    }
}

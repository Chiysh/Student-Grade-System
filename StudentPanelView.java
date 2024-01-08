// StudentPanelView.java
package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Set;

public class StudentPanelView extends JPanel {
    private String studentUsername;

    public StudentPanelView(String studentUsername) {
        this.studentUsername = studentUsername;
        setLayout(null); // Use null layout for absolute positioning

        JLabel titleLabel = new JLabel("Welcome, Student!");
        titleLabel.setFont(new Font("Inter", Font.BOLD, 24));
        titleLabel.setBounds(50, 50, 300, 50);
        add(titleLabel);

        JButton viewGradesButton = new JButton("View Grades");
        viewGradesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Fetch subjects for the student and display grades
                Set<String> studentSubjects = Users.getStudentSubjects(studentUsername);
                openViewGradesFrame(studentSubjects);
            }
        });
        viewGradesButton.setBounds(100, 150, 115, 40);
        add(viewGradesButton);

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
    }

    private void openViewGradesFrame(Set<String> studentSubjects) {
        JFrame viewGradesFrame = new JFrame("View Grades");
        viewGradesFrame.setSize(400, 350); // Increased height for displaying average
        viewGradesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel viewGradesPanel = new JPanel();
        viewGradesPanel.setLayout(new GridLayout(0, 2));

        // Display grades for each subject
        for (String subject : studentSubjects) {
            String grade = Users.getStudentGrade(studentUsername, subject);

            if (!grade.equals("N/A")) {
                JLabel subjectLabel = new JLabel(subject + " Grade:");
                viewGradesPanel.add(subjectLabel);
                JLabel gradeLabel = new JLabel(grade);
                viewGradesPanel.add(gradeLabel);
            } else {
                JLabel subjectLabel = new JLabel(subject + ": Not Graded Yet");
                viewGradesPanel.add(subjectLabel);
                JLabel gradeLabel = new JLabel("N/A");
                viewGradesPanel.add(gradeLabel);
            }
        }

        // Calculate and display the total grade average
        double totalAverage = calculateTotalGradeAverage(studentUsername, studentSubjects);
        JLabel averageLabel = new JLabel("Total Grade Average:");
        viewGradesPanel.add(averageLabel);
        JLabel totalAverageLabel = new JLabel(String.format("%.2f", totalAverage));
        viewGradesPanel.add(totalAverageLabel);

        viewGradesFrame.add(viewGradesPanel);
        viewGradesFrame.setVisible(true);
    }

    private double calculateTotalGradeAverage(String studentUsername, Set<String> studentSubjects) {
        double totalGradeSum = 0;
        int totalSubjects = 0;

        for (String subject : studentSubjects) {
            String grade = Users.getStudentGrade(studentUsername, subject);

            // Calculate total grade sum for average
            if (!grade.equals("N/A")) {
                totalGradeSum += Double.parseDouble(grade);
                totalSubjects++;
            }
        }

        // Calculate and return the total grade average
        return (totalSubjects > 0) ? totalGradeSum / totalSubjects : 0;
    }
}

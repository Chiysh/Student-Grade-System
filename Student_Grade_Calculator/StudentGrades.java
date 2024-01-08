//StudentGrades.java
package project;

import java.util.HashMap;
import java.util.Map;

public class StudentGrades {
    private String studentUsername;
    private Map<String, Double[]> subjectGrades;

    public StudentGrades(String studentUsername) {
        this.studentUsername = studentUsername;
        this.subjectGrades = new HashMap<>();
    }

    public void addGrade(String subject, double quizScore, double examScore) {
        Double[] grades = {quizScore, examScore};
        subjectGrades.put(subject, grades);
    }

    public double calculateGWA() {
        if (subjectGrades.isEmpty()) {
            return 0.0;
        }

        double totalWeightedPoints = 0.0;
        double totalUnits = 0.0;

        for (Map.Entry<String, Double[]> entry : subjectGrades.entrySet()) {
            double quizScore = entry.getValue()[0];
            double examScore = entry.getValue()[1];
            double finalGrade = (quizScore * 0.6) + (examScore * 0.4);

            // Assigning units based on subject (for example purposes)
            double units = getSubjectUnits(entry.getKey());

            totalWeightedPoints += finalGrade * units;
            totalUnits += units;
        }

        return totalWeightedPoints / totalUnits;
    }

    private double getSubjectUnits(String subject) {
        // You can implement your logic to assign units based on subjects
        // For example purposes, just return 3 units for all subjects
        return 3.0;
    }

    public Map<String, Double[]> getSubjectGrades() {
        return subjectGrades;
    }
}

package project;

import java.io.Serializable;

public class GradeData implements Serializable {
    private static final long serialVersionUID = 1L;

    private String studentUsername; // Added student username field
    private String studentName;
    private double quizScore;
    private double examScore;

    public GradeData(String studentUsername, String studentName, double quizScore, double examScore) {
        this.studentUsername = studentUsername;
        this.studentName = studentName;
        this.quizScore = quizScore;
        this.examScore = examScore;
    }

    public String getStudentUsername() {
        return studentUsername;
    }

    public String getStudentName() {
        return studentName;
    }

    public double getQuizScore() {
        return quizScore;
    }

    public double getExamScore() {
        return examScore;
    }
}
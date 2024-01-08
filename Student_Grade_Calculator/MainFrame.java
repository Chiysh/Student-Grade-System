// MainFrame.java
package project;

import javax.swing.*;
import java.awt.*;

public class MainFrame {
    private static JFrame mainFrame;
    private static JPanel cardPanel;
    private static CardLayout cardLayout;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    public static void createAndShowGUI() {
        mainFrame = new JFrame("Application");
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        // Add HomeView to the cardPanel
        addHomePanel();

        mainFrame.setSize(750, 400);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setVisible(true);
        mainFrame.add(cardPanel, BorderLayout.CENTER);
    }

    private static void addHomePanel() {
        JPanel homePanel = new HomeView();
        cardPanel.add(homePanel, "HomeView");
        cardLayout.show(cardPanel, "HomeView");
    }

    public static void showAdminPanel() {
        JPanel adminPanel = new AdminPanelView();
        cardPanel.add(adminPanel, "AdminPanel");
        cardLayout.show(cardPanel, "AdminPanel");
    }

    public static void showStudentPanel(String studentUsername) {
        JPanel studentPanel = new StudentPanelView(studentUsername);
        cardPanel.add(studentPanel, "StudentPanel");
        cardLayout.show(cardPanel, "StudentPanel");
    }

    public static void showSubjectTeacherPanel(String subjectTeacherUsername) {
        JPanel subjectTeacherPanel = new SubjectTeacherPanelView(subjectTeacherUsername);
        cardPanel.add(subjectTeacherPanel, "SubjectTeacherPanel");
        cardLayout.show(cardPanel, "SubjectTeacherPanel");
    }

    public static void showHomePanel() {
        cardLayout.show(cardPanel, "HomeView");
    }

    public static JFrame getMainFrame() {
        return mainFrame;
    }
}

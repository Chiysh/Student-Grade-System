// AdminPanelView.java
package project;

import javax.swing.*;
import java.awt.*;

public class AdminPanelView {
    public static void showAdminPanel() {
        JFrame adminFrame = new JFrame("Admin Panel");

        JPanel adminPanel = new JPanel();
        adminPanel.setBounds(0, 0, 400, 200);

        JLabel welcomeLabel = new JLabel("Welcome, Admin!");
        welcomeLabel.setFont(new Font("Inter", Font.BOLD, 24));
        adminPanel.add(welcomeLabel);

        adminFrame.setSize(400, 200);
        adminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        adminFrame.setLayout(null);
        adminFrame.setVisible(true);
        adminFrame.add(adminPanel);
    }
}
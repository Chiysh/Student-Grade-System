// AdminPanelView.java
package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminPanelView extends JPanel {
    public AdminPanelView() {
        setLayout(null); 

        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(null); 
        adminPanel.setBounds(0, 0, 400, 200);

        JLabel welcomeLabel = new JLabel("Welcome, Admin!");
        welcomeLabel.setFont(new Font("Inter", Font.BOLD, 24));
        welcomeLabel.setBounds(50, 50, 300, 50);
        adminPanel.add(welcomeLabel);

        JButton backButton = new JButton("Back to Home");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.showHomePanel();
            }
        });

        backButton.setBounds(600, 280, 115, 40); // Adjust these values as needed

        add(adminPanel);
        add(backButton);
    }
}

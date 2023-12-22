// HomeView.java
package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HomeView {
    public static void createAndShowGUI() {
        JFrame homeView = new JFrame("Home View");

        JPanel homePanel = new JPanel();
        homePanel.setBounds(0, 0, 800, 400);
        Color c1 = new Color(217, 217, 217);

        JLabel titleLabel0 = new JLabel(" STUDENT");
        titleLabel0.setFont(new Font("Inter", Font.BOLD, 42));
        titleLabel0.setBounds(78, 125, 242, 91);
        homePanel.add(titleLabel0);

        JLabel titleLabel1 = new JLabel(" GRADE");
        titleLabel1.setFont(new Font("Inter", Font.BOLD, 32));
        titleLabel1.setBounds(78, 153, 242, 91);
        homePanel.add(titleLabel1);

        JLabel titleLabel2 = new JLabel(" SYSTEM");
        titleLabel2.setFont(new Font("Inter", Font.BOLD, 32));
        titleLabel2.setBounds(78, 178, 242, 91);
        homePanel.add(titleLabel2);

        JLabel loginLabel = new JLabel(" LOGIN");
        loginLabel.setFont(new Font("Inter", Font.PLAIN, 36));
        loginLabel.setBounds(530, 68, 141, 43);
        homePanel.add(loginLabel);

        JLabel userLabel = new JLabel(" ENTER USERNAME: ");
        userLabel.setFont(new Font("Inter", Font.PLAIN, 16));
        userLabel.setBounds(335, 145, 159, 37);
        homePanel.add(userLabel);

        JLabel passLabel = new JLabel(" ENTER PASSWORD: ");
        passLabel.setFont(new Font("Inter", Font.PLAIN, 16));
        passLabel.setBounds(335, 210, 180, 37);
        homePanel.add(passLabel);

        JTextField userField = new JTextField();
        userField.setBounds(500, 145, 202, 33);
        homePanel.add(userField);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(500, 210, 202, 33);
        homePanel.add(passField);

        JButton loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Inter", Font.BOLD, 16));
        loginButton.setBounds(535, 280, 108, 40);

        // Adding ActionListener for the Login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());

                if (username.equals("admin") && password.equals("admin")) {
                    // If the credentials match, show the admin panel
                    AdminPanelView.showAdminPanel();
                } else {
                    // Otherwise, show an error message or perform other actions
                    JOptionPane.showMessageDialog(homeView, "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        homePanel.add(loginButton);

        homePanel.setLayout(null);
        homePanel.setBackground(c1);

        homeView.setSize(800, 400);
        homeView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homeView.setLayout(null);
        homeView.setVisible(true);
        homeView.add(homePanel);
    }
}
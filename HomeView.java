// HomeView.java
package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeView extends JPanel {
    public HomeView() {
        setLayout(null); // Use null layout for manual positioning

        Color c1 = new Color(217, 217, 217);

        JLabel titleLabel0 = new JLabel(" STUDENT");
        titleLabel0.setFont(new Font("Inter", Font.BOLD, 42));
        titleLabel0.setBounds(78 , 125 , 242 , 91); // Adjust these values as needed
        add(titleLabel0);

        JLabel titleLabel1 = new JLabel(" GRADE");
        titleLabel1.setFont(new Font("Inter", Font.BOLD, 32));
        titleLabel1.setBounds(78 , 153 , 242 , 91); // Adjust these values as needed
        add(titleLabel1);

        JLabel titleLabel2 = new JLabel(" SYSTEM");
        titleLabel2.setFont(new Font("Inter", Font.BOLD, 32));
        titleLabel2.setBounds(78 , 178 , 242 , 91); // Adjust these values as needed
        add(titleLabel2);

        JLabel loginLabel = new JLabel(" LOGIN");
        loginLabel.setFont(new Font("Inter", Font.PLAIN, 36));
        loginLabel.setBounds(530 , 68 , 141 , 43); // Adjust these values as needed
        add(loginLabel);

        JLabel userLabel = new JLabel(" ENTER USERNAME: ");
        userLabel.setFont(new Font("Inter", Font.PLAIN, 16));
        userLabel.setBounds(335 , 145 , 159 , 37 ); // Adjust these values as needed
        add(userLabel);

        JTextField userField = new JTextField();
        userField.setBounds(500 , 145 , 202 , 33); // Adjust these values as needed
        add(userField);

        JLabel passLabel = new JLabel(" ENTER PASSWORD: ");
        passLabel.setFont(new Font("Inter", Font.PLAIN, 16));
        passLabel.setBounds( 335 , 210 , 180 , 37); // Adjust these values as needed
        add(passLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(500 , 210 , 202 , 33); // Adjust these values as needed
        add(passField);

        JButton loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Inter", Font.BOLD, 16));
        loginButton.setBounds(535 , 280 , 108 , 40); // Adjust these values as needed

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());

                if (LoginService.authenticate(username, password)) {
                    // If the credentials match, show the admin panel
                    MainFrame.showAdminPanel();
                } else {
                    JOptionPane.showMessageDialog(MainFrame.getMainFrame(), "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(loginButton);
        setBackground(c1);
    }
}

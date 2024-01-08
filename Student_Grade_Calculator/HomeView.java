// HomeView.java
package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomeView extends JPanel {
    private static final Color PANEL_BACKGROUND_COLOR = new Color(217, 217, 217);
    private static final int LABEL_FONT_SIZE_1 = 42;
    private static final int LABEL_FONT_SIZE_2 = 32;
    private static final int LABEL_FONT_SIZE_3 = 16;
    private static final int BUTTON_FONT_SIZE = 16;

    private JTextField userField;
    private JPasswordField passField;

    public HomeView() {
        setLayout(null);

        setBackground(PANEL_BACKGROUND_COLOR);

        JLabel titleLabel0 = createLabel(" STUDENT", LABEL_FONT_SIZE_1, 78, 125, 242, 91);
        add(titleLabel0);

        JLabel titleLabel1 = createLabel(" GRADE", LABEL_FONT_SIZE_2, 78, 153, 242, 91);
        add(titleLabel1);

        JLabel titleLabel2 = createLabel(" SYSTEM", LABEL_FONT_SIZE_2, 78, 178, 242, 91);
        add(titleLabel2);

        JLabel loginLabel = createLabel(" LOGIN", LABEL_FONT_SIZE_3, 530, 68, 141, 43);
        add(loginLabel);

        JLabel userLabel = createLabel(" ENTER USERNAME: ", LABEL_FONT_SIZE_3, 335, 145, 159, 37);
        add(userLabel);

        userField = createTextField(500, 145, 202, 33);
        add(userField);

        JLabel passLabel = createLabel(" ENTER PASSWORD: ", LABEL_FONT_SIZE_3, 335, 210, 180, 37);
        add(passLabel);

        passField = createPasswordField(500, 210, 202, 33);
        add(passField);

        JButton loginButton = createButton("LOGIN", BUTTON_FONT_SIZE, 535, 280, 108, 40);
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());

                String userType = LoginService.authenticate(username, password);

                if (userType != null) {
                    switch (userType) {
                        case "Admin":
                            MainFrame.showAdminPanel();
                            break;
                        case "Student":
                            MainFrame.showStudentPanel(username);
                            break;
                        case "Subject Teacher":
                            MainFrame.showSubjectTeacherPanel(username);
                            break;
                        default:
                            break;
                    }
                } else {
                    JOptionPane.showMessageDialog(MainFrame.getMainFrame(), "Invalid username or password", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add(loginButton);
    }

    private JLabel createLabel(String text, int fontSize, int x, int y, int width, int height) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Inter", Font.PLAIN, fontSize));
        label.setBounds(x, y, width, height);
        return label;
    }

    private JTextField createTextField(int x, int y, int width, int height) {
        JTextField textField = new JTextField();
        textField.setBounds(x, y, width, height);
        return textField;
    }

    private JPasswordField createPasswordField(int x, int y, int width, int height) {
        JPasswordField passwordField = new JPasswordField();
        passwordField.setBounds(x, y, width, height);
        return passwordField;
    }

    private JButton createButton(String text, int fontSize, int x, int y, int width, int height) {
        JButton button = new JButton(text);
        button.setFont(new Font("Inter", Font.BOLD, fontSize));
        button.setBounds(x, y, width, height);
        return button;
    }
}

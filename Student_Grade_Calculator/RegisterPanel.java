// RegisterPanel.java
package project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class RegisterPanel extends JPanel {
    private String selectedUserType;
    private JPanel additionalPanel;
    private JTextField fullNameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JComboBox<String> subjectComboBox; // New combo box for subject selection
    private JButton registerButton;

    private RegistrationListener registrationListener;

    public RegisterPanel() {
        setLayout(null);
        setBounds(0, 0, 800, 300);

        JButton userTypeButton = new JButton("Select User Type");
        userTypeButton.setBounds(50, 100, 150, 25);
        userTypeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showUserTypeDialog();
            }
        });
        add(userTypeButton);

        JLabel registerLabel = new JLabel("Register!");
        registerLabel.setFont(new Font("Inter", Font.BOLD, 20));
        registerLabel.setBounds(50, 50, 300, 30);
        add(registerLabel);

        additionalPanel = new JPanel();
        additionalPanel.setLayout(null);
        additionalPanel.setBounds(400, 0, 700, 400);
        additionalPanel.setVisible(false);
        add(additionalPanel);

        // Initialize the input fields
        fullNameField = new JTextField();
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();
        subjectComboBox = new JComboBox<>(new String[]{"Math", "English", "Science"}); // Default subjects

        registerButton = new JButton("Register");
        registerButton.setBounds(10, 190, 90, 25);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegistration();
            }
        });
        additionalPanel.add(registerButton);
    }

    public void setRegistrationListener(RegistrationListener listener) {
        this.registrationListener = listener;
    }

    public String getUsername() {
        return usernameField.getText();
    }

    private void showUserTypeDialog() {
        String[] options = {"Student", "Subject Teacher"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Choose User Type",
                "User Type Selection",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice >= 0) {
            selectedUserType = options[choice];
            handleUserTypeSelection(choice);
        } else {
            selectedUserType = null;
            hideAdditionalPanel();
        }
    }

    private void handleUserTypeSelection(int choice) {
        additionalPanel.removeAll();
        additionalPanel.setVisible(true);

        JLabel userTypeLabel = new JLabel(selectedUserType + ":");
        userTypeLabel.setFont(new Font("Inter", Font.BOLD, 16));
        userTypeLabel.setBounds(10, 10, 150, 20);
        additionalPanel.add(userTypeLabel);

        JLabel fullNameLabel = new JLabel("Full Name:");
        fullNameLabel.setBounds(10, 40, 80, 25);
        additionalPanel.add(fullNameLabel);

        fullNameField.setBounds(120, 40, 150, 25);
        additionalPanel.add(fullNameField);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(10, 70, 80, 25);
        additionalPanel.add(usernameLabel);

        usernameField.setBounds(120, 70, 150, 25);
        additionalPanel.add(usernameField);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(10, 100, 80, 25);
        additionalPanel.add(passwordLabel);

        passwordField.setBounds(120, 100, 150, 25);
        additionalPanel.add(passwordField);

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setBounds(10, 130, 120, 25);
        additionalPanel.add(confirmPasswordLabel);

        confirmPasswordField.setBounds(140, 130, 130, 25);
        additionalPanel.add(confirmPasswordField);

        if ("Subject Teacher".equals(selectedUserType)) {
            JLabel subjectLabel = new JLabel("Subject:");
            subjectLabel.setBounds(10, 160, 80, 25);
            additionalPanel.add(subjectLabel);

            subjectComboBox.setBounds(120, 160, 150, 25);
            additionalPanel.add(subjectComboBox);
        }

        JButton registerButton = new JButton("Register");
        registerButton.setBounds(10, 190, 90, 25);
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleRegistration();
            }
        });
        additionalPanel.add(registerButton);

        additionalPanel.setBounds(400, 0, 700, 400);
        repaint();
        revalidate();
    }

    private void handleRegistration() {
        String fullName = fullNameField.getText();
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String confirmPassword = new String(confirmPasswordField.getPassword());

        if (checkFields(fullNameField, usernameField, passwordField, confirmPasswordField)
                && checkPasswordMatch(passwordField, confirmPasswordField)) {
            boolean registrationSuccessful;
            if ("Student".equals(selectedUserType)) {
                registrationSuccessful = Users.registerStudent(fullName, username, password);
            } else if ("Subject Teacher".equals(selectedUserType)) {
                String selectedSubject = (String) subjectComboBox.getSelectedItem();
                registrationSuccessful = Users.registerTeacher(fullName, username, password, selectedSubject);
            } else {
                registrationSuccessful = false;
            }

            if (registrationSuccessful) {
                // Notify the listener about registration success and user type
                if (registrationListener != null) {
                    registrationListener.onRegistrationSuccess(selectedUserType);
                }
                clearFields();
            } else {
                JOptionPane.showMessageDialog(RegisterPanel.this, "Username already exists. Please choose a different username.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean checkFields(JTextField... fields) {
        for (JTextField field : fields) {
            if (field.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(RegisterPanel.this, "All fields are required.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    private boolean checkPasswordMatch(JPasswordField passwordField, JPasswordField confirmPasswordField) {
        char[] password = passwordField.getPassword();
        char[] confirmPassword = confirmPasswordField.getPassword();

        if (!Arrays.equals(password, confirmPassword)) {
            JOptionPane.showMessageDialog(RegisterPanel.this, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void hideAdditionalPanel() {
        additionalPanel.setVisible(false);
        repaint();
        revalidate();
    }

    private void clearFields() {
        fullNameField.setText("");
        usernameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        subjectComboBox.setSelectedIndex(0);
    }

    public interface RegistrationListener {
        void onRegistrationSuccess(String userType);
    }
}

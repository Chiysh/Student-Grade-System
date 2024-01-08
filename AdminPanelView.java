//AdminPanelView.java
package project;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.DefaultTableModel;

public class AdminPanelView extends JPanel {
    private JPanel adminPanel;
    private RegisterPanel registerPanel;
    private JButton registerButton;
    private JTable userTable;
    private DefaultTableModel tableModel;

    public AdminPanelView() {
        setLayout(null);

        adminPanel = new JPanel();
        adminPanel.setLayout(null);
        adminPanel.setBounds(0, 0, 800, 300);

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
        backButton.setBounds(600, 300, 115, 40);

        registerButton = new JButton("Register User");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showRegisterPanel();
            }
        });
        registerButton.setBounds(450, 300, 115, 40);

        add(adminPanel);
        add(backButton);
        add(registerButton);

        registerPanel = new RegisterPanel();
        registerPanel.setVisible(false);
        registerPanel.setRegistrationListener(new RegisterPanel.RegistrationListener() {
            @Override
            public void onRegistrationSuccess(String userType) {
                handleRegistrationSuccess(userType);
            }
        });
        add(registerPanel);

        userTable = new JTable();
        initUserTable();
    }

    private void initUserTable() {
        tableModel = new DefaultTableModel();
        tableModel.addColumn("Username");
        tableModel.addColumn("User Type");

        Map<String, String> studentUsers = Users.getStudentUsers();
        for (Map.Entry<String, String> entry : studentUsers.entrySet()) {
            tableModel.addRow(new Object[]{entry.getKey(), "Student"});
        }

        Map<String, String> teacherUsers = Users.getTeacherUsers();
        for (Map.Entry<String, String> entry : teacherUsers.entrySet()) {
            tableModel.addRow(new Object[]{entry.getKey(), "Subject Teacher"});
        }

        userTable.setModel(tableModel);

        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBounds(50, 100, 300, 200);
        adminPanel.add(scrollPane);

        userTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleUserTableClick(e);
            }
        });
    }

    private void showRegisterPanel() {
        adminPanel.setVisible(false);
        registerButton.setVisible(false);
        registerPanel.setVisible(true);

        registerPanel.setRegistrationListener(new RegisterPanel.RegistrationListener() {
            @Override
            public void onRegistrationSuccess(String userType) {
                handleRegistrationSuccess(userType);
            }
        });
    }

    private void handleUserTableClick(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e) && e.getClickCount() == 1) {
            int selectedRow = userTable.getSelectedRow();

            if (selectedRow != -1) {
                String usernameToRemove = (String) tableModel.getValueAt(selectedRow, 0);
                int confirmResult = JOptionPane.showConfirmDialog(
                        this,
                        "Are you sure you want to remove the user with username '" + usernameToRemove + "'?",
                        "Remove User Confirmation",
                        JOptionPane.YES_NO_OPTION);

                if (confirmResult == JOptionPane.YES_OPTION) {
                    boolean removalSuccessful = Users.removeUser(usernameToRemove);

                    if (removalSuccessful) {
                        JOptionPane.showMessageDialog(this, "User removed successfully.");
                        updateTable();
                    } else {
                        JOptionPane.showMessageDialog(this, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        }
    }

    private void updateTable() {
        Map<String, String> studentUsers = Users.getStudentUsers();
        Map<String, String> teacherUsers = Users.getTeacherUsers();

        tableModel.setRowCount(0);

        for (Map.Entry<String, String> entry : studentUsers.entrySet()) {
            tableModel.addRow(new Object[]{entry.getKey(), "Student"});
        }

        for (Map.Entry<String, String> entry : teacherUsers.entrySet()) {
            tableModel.addRow(new Object[]{entry.getKey(), "Subject Teacher"});
        }
    }

    private void handleRegistrationSuccess(String userType) {
        String username = registerPanel.getUsername();
        tableModel.addRow(new Object[]{username, userType});

        String successMessage;
        if ("Student".equals(userType)) {
            successMessage = "Student registered successfully!";
        } else if ("Subject Teacher".equals(userType)) {
            successMessage = "Subject Teacher registered successfully!";
        } else {
            successMessage = "User registered successfully!";
        }

        JOptionPane.showMessageDialog(this, successMessage);
        adminPanel.setVisible(true);
        registerButton.setVisible(true);
        registerPanel.setVisible(false);
    }
}

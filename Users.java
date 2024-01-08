// Users.java
package project;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Scanner;

public class Users {
    private static Map<String, String> studentUsers = new HashMap<>();
    private static Map<String, String> teacherUsers = new HashMap<>();
    private static Map<String, String> subjectTeacherUsers = new HashMap<>();
    private static Map<String, String> teacherSubjects = new HashMap<>();

    private static final String USERS_FILE_PATH = "users.dat";

    static {
        loadUsersFromFile();
    }

    public static boolean registerStudent(String fullName, String username, String password) {
        if (!userExists(username)) {
            studentUsers.put(username, password);
            saveUsersToFile();
            return true;
        }
        return false;
    }

    public static boolean registerTeacher(String fullName, String username, String password, String subject) {
        if (!userExists(username)) {
            teacherUsers.put(username, password);
            teacherSubjects.put(username, subject);
            subjectTeacherUsers.put(username, fullName);
            saveUsersToFile();
            return true;
        }
        return false;
    }

    public static boolean removeUser(String username) {
        if (studentUsers.containsKey(username)) {
            studentUsers.remove(username);
            saveUsersToFile();
            return true;
        } else if (teacherUsers.containsKey(username)) {
            teacherUsers.remove(username);
            teacherSubjects.remove(username);
            subjectTeacherUsers.remove(username);
            saveUsersToFile();
            return true;
        }
        return false;
    }

    public static boolean authenticateStudent(String username, String password) {
        return studentUsers.containsKey(username) && studentUsers.get(username).equals(password);
    }

    public static boolean authenticateTeacher(String username, String password) {
        return teacherUsers.containsKey(username) && teacherUsers.get(username).equals(password);
    }

    public static String getSubjectTeacherSubject(String username) {
        return teacherSubjects.getOrDefault(username, "");
    }

    public static String getSubjectTeacherUsername(String subject) {
        for (Map.Entry<String, String> entry : teacherSubjects.entrySet()) {
            if (entry.getValue().equals(subject)) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static Map<String, String> getStudentUsers() {
        return studentUsers;
    }

    public static Map<String, String> getTeacherUsers() {
        return teacherUsers;
    }

    public static Map<String, String> getStudentFullNames() {
        Map<String, String> studentFullNameMap = new HashMap<>();
        for (Map.Entry<String, String> entry : studentUsers.entrySet()) {
            String username = entry.getKey();
            String fullName = entry.getKey(); // Note: Corrected from entry.getValue() to entry.getKey()
            studentFullNameMap.put(username, fullName);
        }
        return studentFullNameMap;
    }

    public static String getStudentFullName(String username) {
        Map<String, String> studentFullNameMap = getStudentFullNames();
        return studentFullNameMap.getOrDefault(username, "Unknown Student");
    }

    public static String getUserType(String username) {
        if (studentUsers.containsKey(username)) {
            return "Student";
        } else if (teacherUsers.containsKey(username)) {
            return "Subject Teacher";
        } else {
            return null;
        }
    }

    public static Map<String, String> getSubjectTeacherUsers() {
        return subjectTeacherUsers;
    }

    public static Map<String, String> getTeacherSubjects() {
        return teacherSubjects;
    }

    public static void setTeacherSubjects(Map<String, String> teacherSubjects) {
        Users.teacherSubjects = teacherSubjects;
    }

    public static void addTeacherSubject(String teacherUsername, String subject) {
        teacherSubjects.put(teacherUsername, subject);
    }

    public static Set<String> getStudentSubjects(String studentUsername) {
        // Placeholder logic to fetch subjects for the student
        // In a real application, you might retrieve this information from a database
        Set<String> subjects = new HashSet<>();
        subjects.add("Math");
        subjects.add("English");
        subjects.add("Science");
        return subjects;
    }

    public static String getStudentGrade(String studentUsername, String subject) {
        String fileName = subject + "_grades.txt";
        File gradeFile = new File(fileName);

        try {
            Scanner scanner = new Scanner(gradeFile);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 3 && parts[0].equals(studentUsername) && subjectTeacherUsers.containsKey(parts[2])) {
                    return parts[1]; // Return the grade for the specified subject
                }
            }
            scanner.close();
        } catch (IOException e) {
            // Ignore if the file doesn't exist yet
        }

        return "N/A"; // Return "N/A" if the grade is not found
    }

    private static void saveUsersToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE_PATH))) {
            Map<String, Map<String, String>> usersMap = new HashMap<>();
            usersMap.put("students", studentUsers);
            usersMap.put("teachers", teacherUsers);
            usersMap.put("subjectTeachers", subjectTeacherUsers);
            usersMap.put("subjects", teacherSubjects);
            oos.writeObject(usersMap);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadUsersFromFile() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERS_FILE_PATH))) {
            Map<String, Map<String, String>> usersMap = (Map<String, Map<String, String>>) ois.readObject();
            studentUsers = usersMap.getOrDefault("students", new HashMap<>());
            teacherUsers = usersMap.getOrDefault("teachers", new HashMap<>());
            subjectTeacherUsers = usersMap.getOrDefault("subjectTeachers", new HashMap<>());
            teacherSubjects = usersMap.getOrDefault("subjects", new HashMap<>());
        } catch (FileNotFoundException e) {
            // Ignore if the file doesn't exist yet
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static boolean userExists(String username) {
        return studentUsers.containsKey(username) || teacherUsers.containsKey(username);
    }
}

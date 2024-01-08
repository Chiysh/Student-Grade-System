//LoginService.java
package project;

public class LoginService {
    public static String authenticate(String username, String password) {
        if (Constants.ADMIN_USERNAME.equals(username) && Constants.ADMIN_PASSWORD.equals(password)) {
            return "Admin"; // Admin authentication
        } else {
            // Check if the provided username and password match any user's credentials
            String userType = Users.getUserType(username);
            if (userType != null) {
                if (userType.equals("Student") && Users.authenticateStudent(username, password)) {
                    return userType;
                } else if (userType.equals("Subject Teacher") && Users.authenticateTeacher(username, password)) {
                    return userType;
                }
            }

            return null; // User not found or invalid credentials
        }
    }

    public static String getUserType(String username) {
        if (Constants.ADMIN_USERNAME.equals(username)) {
            return "Admin";
        } else {
            return Users.getUserType(username);
        }
    }
}

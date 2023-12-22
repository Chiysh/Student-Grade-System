package project;

 class LoginService {
    public static boolean authenticate(String username, String password) {
        return Constants.ADMIN_USERNAME.equals(username) && Constants.ADMIN_PASSWORD.equals(password);
    }
}
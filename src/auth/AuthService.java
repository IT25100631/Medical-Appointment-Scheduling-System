package auth;

import model.Admin;
import model.Patient;
import model.User;
import filehandler.UserFileHandler;

import java.util.List;

/**
 * AuthService - Handles authentication for the Medical Appointment System.
 * Responsibilities: login, registration, session management, role checks.
 * Author: IT25103192 (M2)
 */
public class AuthService {

    private static AuthService instance;
    private User currentUser; // logged-in session

    private UserFileHandler userFileHandler;

    private AuthService() {
        this.userFileHandler = new UserFileHandler();
    }

    // Singleton pattern
    public static AuthService getInstance() {
        if (instance == null) {
            instance = new AuthService();
        }
        return instance;
    }

    /**
     * Authenticate a user by email and password.
     * @return User object if successful, null otherwise.
     */
    public User login(String email, String password) {
        if (email == null || password == null || email.isBlank() || password.isBlank()) {
            System.out.println("[AuthService] Login failed: empty credentials.");
            return null;
        }

        List<User> users = userFileHandler.loadAllUsers();
        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email.trim()) &&
                    user.getPassword().equals(password)) {
                currentUser = user;
                System.out.println("[AuthService] Login successful: " + user.getName() +
                        " (" + user.getRole() + ")");
                return user;
            }
        }

        System.out.println("[AuthService] Login failed: invalid email or password.");
        return null;
    }

    /**
     * Register a new Patient account.
     * @return true if registration was successful.
     */
    public boolean registerPatient(String name, String email, String password,
                                   String phone, String dob, String gender, String bloodType) {
        if (!isValidEmail(email)) {
            System.out.println("[AuthService] Registration failed: invalid email.");
            return false;
        }
        if (password == null || password.length() < 6) {
            System.out.println("[AuthService] Registration failed: password too short (min 6 chars).");
            return false;
        }
        if (emailExists(email)) {
            System.out.println("[AuthService] Registration failed: email already in use.");
            return false;
        }

        String userId    = generateId("USR");
        String patientId = generateId("PAT");

        Patient newPatient = new Patient(userId, name, email, password, phone,
                patientId, dob, gender, bloodType);
        userFileHandler.addUser(newPatient);
        System.out.println("[AuthService] Patient registered: " + name);
        return true;
    }

    /**
     * Register a new Admin account (restricted — only callable by a super admin).
     * @return true if successful.
     */
    public boolean registerAdmin(String name, String email, String password,
                                 String phone, String department, String accessLevel) {
        if (!isLoggedIn() || !currentUser.getRole().equals("ADMIN")) {
            System.out.println("[AuthService] Unauthorized: only admins can create admin accounts.");
            return false;
        }
        if (!isValidEmail(email) || emailExists(email)) {
            System.out.println("[AuthService] Registration failed: invalid or duplicate email.");
            return false;
        }

        String userId  = generateId("USR");
        String adminId = generateId("ADM");

        Admin newAdmin = new Admin(userId, name, email, password, phone,
                adminId, department, accessLevel);
        userFileHandler.addUser(newAdmin);
        System.out.println("[AuthService] Admin registered: " + name);
        return true;
    }

    /**
     * Log out the current user.
     */
    public void logout() {
        if (currentUser != null) {
            System.out.println("[AuthService] User logged out: " + currentUser.getName());
            currentUser = null;
        }
    }

    /**
     * Change password for the current logged-in user.
     */
    public boolean changePassword(String oldPassword, String newPassword) {
        if (!isLoggedIn()) {
            System.out.println("[AuthService] No user is logged in.");
            return false;
        }
        if (!currentUser.getPassword().equals(oldPassword)) {
            System.out.println("[AuthService] Password change failed: incorrect old password.");
            return false;
        }
        if (newPassword == null || newPassword.length() < 6) {
            System.out.println("[AuthService] Password change failed: new password too short.");
            return false;
        }
        currentUser.setPassword(newPassword);
        userFileHandler.updateUser(currentUser);
        System.out.println("[AuthService] Password updated successfully.");
        return true;
    }

    // ── Helpers ────────────────────────────────────────────────

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public boolean isAdmin() {
        return isLoggedIn() && "ADMIN".equals(currentUser.getRole());
    }

    public boolean isPatient() {
        return isLoggedIn() && "PATIENT".equals(currentUser.getRole());
    }

    public boolean isDoctor() {
        return isLoggedIn() && "DOCTOR".equals(currentUser.getRole());
    }

    private boolean emailExists(String email) {
        List<User> users = userFileHandler.loadAllUsers();
        return users.stream()
                .anyMatch(u -> u.getEmail().equalsIgnoreCase(email.trim()));
    }

    private boolean isValidEmail(String email) {
        return email != null && email.matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$");
    }

    private String generateId(String prefix) {
        return prefix + "-" + System.currentTimeMillis();
    }
}


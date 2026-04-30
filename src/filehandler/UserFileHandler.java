package filehandler;

import model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserFileHandler {

    private static final String FILE_PATH = "data/users.txt";

    // Reads all users from users.txt and returns them as a list
    public static List<User> loadAllUsers() {
        List<User> users = new ArrayList<>();

        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return users; // Return empty list if file doesn't exist yet
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    users.add(User.fromFileString(line));
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading users file: " + e.getMessage());
        }

        return users;
    }

    // Saves the full list of users to users.txt (overwrites existing content)
    public static void saveAllUsers(List<User> users) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : users) {
                writer.write(user.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users file: " + e.getMessage());
        }
    }

    // Adds a single new user to users.txt (appends to end of file)
    public static void addUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(user.toFileString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error adding user: " + e.getMessage());
        }
    }

    // Finds a user by their ID — returns null if not found
    public static User getUserById(String userId) {
        for (User user : loadAllUsers()) {
            if (user.getUserId().equals(userId)) {
                return user;
            }
        }
        return null;
    }

    // Finds a user by their email — useful for login
    public static User getUserByEmail(String email) {
        for (User user : loadAllUsers()) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                return user;
            }
        }
        return null;
    }

    // Updates an existing user's details (matches by userId, replaces the entry)
    public static void updateUser(User updatedUser) {
        List<User> users = loadAllUsers();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserId().equals(updatedUser.getUserId())) {
                users.set(i, updatedUser);
                break;
            }
        }
        saveAllUsers(users);
    }

    // Deletes a user by their ID
    public static void deleteUser(String userId) {
        List<User> users = loadAllUsers();
        users.removeIf(user -> user.getUserId().equals(userId));
        saveAllUsers(users);
    }
}

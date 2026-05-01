package admin;

import model.Admin;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * AdminFileHandler - Handles reading and writing Admin records to a flat file.
 * File format (pipe-separated):
 *   userId|name|email|password|phone|adminId|department|accessLevel
 * Author: IT25103192 (M2)
 */
public class AdminFileHandler {

    private static final String FILE_PATH = "data/admins.txt";

    public AdminFileHandler() {
        ensureFileExists();
    }

    // ── Public API ──────────────────────────────────────────────

    /**
     * Load all admin records from file.
     */
    public List<Admin> loadAllAdmins() {
        List<Admin> admins = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                Admin admin = Admin.fromFileString(line);
                if (admin != null) {
                    admins.add(admin);
                }
            }
        } catch (IOException e) {
            System.out.println("[AdminFileHandler] Error reading file: " + e.getMessage());
        }
        return admins;
    }

    /**
     * Save a new admin record to file (append).
     */
    public void saveAdmin(Admin admin) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            writer.write(admin.toFileString());
            writer.newLine();
            System.out.println("[AdminFileHandler] Saved admin: " + admin.getName());
        } catch (IOException e) {
            System.out.println("[AdminFileHandler] Error saving admin: " + e.getMessage());
        }
    }

    /**
     * Update an existing admin record (matched by userId).
     */
    public boolean updateAdmin(Admin updatedAdmin) {
        List<Admin> admins = loadAllAdmins();
        boolean found = false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (Admin admin : admins) {
                if (admin.getUserId().equals(updatedAdmin.getUserId())) {
                    writer.write(updatedAdmin.toFileString());
                    found = true;
                } else {
                    writer.write(admin.toFileString());
                }
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("[AdminFileHandler] Error updating admin: " + e.getMessage());
            return false;
        }

        if (found) {
            System.out.println("[AdminFileHandler] Updated admin: " + updatedAdmin.getName());
        } else {
            System.out.println("[AdminFileHandler] Admin not found for update: " + updatedAdmin.getUserId());
        }
        return found;
    }

    /**
     * Delete an admin record by userId.
     */
    public boolean deleteAdmin(String userId) {
        List<Admin> admins = loadAllAdmins();
        boolean found = false;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, false))) {
            for (Admin admin : admins) {
                if (admin.getUserId().equals(userId)) {
                    found = true; // skip — effectively deletes
                } else {
                    writer.write(admin.toFileString());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("[AdminFileHandler] Error deleting admin: " + e.getMessage());
            return false;
        }

        if (found) {
            System.out.println("[AdminFileHandler] Deleted admin: " + userId);
        }
        return found;
    }

    /**
     * Find an admin by their email address.
     */
    public Admin findByEmail(String email) {
        return loadAllAdmins().stream()
                .filter(a -> a.getEmail().equalsIgnoreCase(email))
                .findFirst().orElse(null);
    }

    /**
     * Find an admin by their adminId.
     */
    public Admin findByAdminId(String adminId) {
        return loadAllAdmins().stream()
                .filter(a -> a.getAdminId().equals(adminId))
                .findFirst().orElse(null);
    }

    // ── Private Helpers ─────────────────────────────────────────

    private void ensureFileExists() {
        File dir  = new File("data");
        File file = new File(FILE_PATH);
        try {
            if (!dir.exists())  dir.mkdirs();
            if (!file.exists()) file.createNewFile();
        } catch (IOException e) {
            System.out.println("[AdminFileHandler] Could not create data file: " + e.getMessage());
        }
    }
}

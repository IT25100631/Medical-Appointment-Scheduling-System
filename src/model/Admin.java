package model;

public class Admin extends User {
    private String adminId;
    private String department;
    private String accessLevel; // e.g. SUPER, STANDARD

    public Admin() {}

    public Admin(String userId, String name, String email, String password,
                 String phone, String adminId, String department, String accessLevel) {
        super(userId, name, email, password, phone, "ADMIN");
        this.adminId = adminId;
        this.department = department;
        this.accessLevel = accessLevel;
    }

    // Serialize to file line
    public String toFileString() {
        return String.join("|",
                getUserId(), getName(), getEmail(), getPassword(), getPhone(),
                adminId,
                department != null ? department : "",
                accessLevel != null ? accessLevel : "STANDARD"
        );
    }

    // Deserialize from file line
    public static Admin fromFileString(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 6) return null;
        return new Admin(
                parts[0], parts[1], parts[2], parts[3], parts[4],
                parts[5],
                parts.length > 6 ? parts[6] : "General",
                parts.length > 7 ? parts[7] : "STANDARD"
        );
    }

    public boolean isSuperAdmin() {
        return "SUPER".equalsIgnoreCase(accessLevel);
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId='" + adminId + '\'' +
                ", name='" + getName() + '\'' +
                ", department='" + department + '\'' +
                ", accessLevel='" + accessLevel + '\'' +
                '}';
    }

    // Getters and Setters
    public String getAdminId() { return adminId; }
    public void setAdminId(String adminId) { this.adminId = adminId; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getAccessLevel() { return accessLevel; }
    public void setAccessLevel(String accessLevel) { this.accessLevel = accessLevel; }
}


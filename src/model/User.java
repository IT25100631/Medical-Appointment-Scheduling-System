package model;

public class User {

        // Fields (attributes of a user)
        private String userId;
        private String name;
        private String email;
        private String password;
        private String role; // "patient" or "admin"
        private String phoneNumber;

        // Constructor — used to create a new User object
        public User(){

        }
    public User(String userId, String name, String email, String password, String phoneNumber, String role) {
        this.userId = userId;
        this.name = name;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

        // Getters — used to read the values
        public String getUserId()   { return userId; }
        public String getName()     { return name; }
        public String getEmail()    { return email; }
        public String getPassword() { return password; }
        public String getRole()     { return role; }
        public String getPhone()    { return phoneNumber; }


        // Setters — used to update the values
        public void setName(String name)         { this.name = name; }
        public void setEmail(String email)       { this.email = email; }
        public void setPassword(String password) { this.password = password; }

        // Converts a User object into a line of text for saving to users.txt
        public String toFileString() {
            return userId + "," + name + "," + email + "," + password + "," + role;
        }

        // Converts a line of text from users.txt back into a User object
        public static User fromFileString(String line) {
            String[] parts = line.split(",");
            return new User(parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]);
        }
    }


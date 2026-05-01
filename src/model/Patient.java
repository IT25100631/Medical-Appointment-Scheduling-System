package model;

public class Patient extends User {
    private String patientId;
    private String dateOfBirth;
    private String gender;
    private String bloodType;
    private String allergies;
    private String medicalHistory;
    private String emergencyContactName;
    private String emergencyContactPhone;

    public Patient() {}

    public Patient(String userId, String name, String email, String password,
                   String phone, String patientId, String dateOfBirth,
                   String gender, String bloodType) {
        super(userId, name, email, password, phone, "PATIENT");
        this.patientId = patientId;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.bloodType = bloodType;
        this.allergies = "";
        this.medicalHistory = "";
    }

    // Serialize to file line
    public String toFileString() {
        return String.join("|",
                getUserId(), getName(), getEmail(), getPassword(), getPhone(),
                patientId, dateOfBirth, gender,
                bloodType != null ? bloodType : "",
                allergies != null ? allergies : "",
                medicalHistory != null ? medicalHistory : "",
                emergencyContactName != null ? emergencyContactName : "",
                emergencyContactPhone != null ? emergencyContactPhone : ""
        );
    }

    // Deserialize from file line
    public static Patient fromFileString(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 8) return null;
        Patient p = new Patient(
                parts[0], parts[1], parts[2], parts[3], parts[4],
                parts[5], parts[6], parts[7],
                parts.length > 8 ? parts[8] : ""
        );
        if (parts.length > 9)  p.setAllergies(parts[9]);
        if (parts.length > 10) p.setMedicalHistory(parts[10]);
        if (parts.length > 11) p.setEmergencyContactName(parts[11]);
        if (parts.length > 12) p.setEmergencyContactPhone(parts[12]);
        return p;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "patientId='" + patientId + '\'' +
                ", name='" + getName() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", dob='" + dateOfBirth + '\'' +
                ", gender='" + gender + '\'' +
                ", bloodType='" + bloodType + '\'' +
                '}';
    }

    // Getters and Setters
    public String getPatientId() { return patientId; }
    public void setPatientId(String patientId) { this.patientId = patientId; }

    public String getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(String dateOfBirth) { this.dateOfBirth = dateOfBirth; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getBloodType() { return bloodType; }
    public void setBloodType(String bloodType) { this.bloodType = bloodType; }

    public String getAllergies() { return allergies; }
    public void setAllergies(String allergies) { this.allergies = allergies; }

    public String getMedicalHistory() { return medicalHistory; }
    public void setMedicalHistory(String medicalHistory) { this.medicalHistory = medicalHistory; }

    public String getEmergencyContactName() { return emergencyContactName; }
    public void setEmergencyContactName(String n) { this.emergencyContactName = n; }

    public String getEmergencyContactPhone() { return emergencyContactPhone; }
    public void setEmergencyContactPhone(String p) { this.emergencyContactPhone = p; }
}


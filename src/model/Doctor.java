package com.example.doctormanagement.model;

public class Doctor {
    private String id;
    private String name;
    private String specialization;
    private String contact;

    public Doctor() {}

    public Doctor(String id, String name, String specialization, String contact) {
        this.id = id;
        this.name = name;
        this.specialization = specialization;
        this.contact = contact;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getSpecialization() { return specialization; }
    public void setSpecialization(String specialization) { this.specialization = specialization; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    @Override
    public String toString() {
        return id + "," + name + "," + specialization + "," + contact;
    }

    public static Doctor fromString(String line) {
        String[] parts = line.split(",");
        if (parts.length == 4) {
            return new Doctor(parts[0], parts[1], parts[2], parts[3]);
        }
        return null;
    }
}



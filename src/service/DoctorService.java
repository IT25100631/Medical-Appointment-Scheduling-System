package com.example.doctormanagement.service;

import com.example.doctormanagement.model.Doctor;
import com.example.doctormanagement.util.FileHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    public void addDoctor(Doctor doctor) {
        List<String> lines = FileHandler.readFromFile();
        lines.add(doctor.toString());
        FileHandler.writeToFile(lines);
    }

    public List<Doctor> getAllDoctors() {
        return FileHandler.readFromFile().stream()
                .map(Doctor::fromString)
                .filter(d -> d != null)
                .collect(Collectors.toList());
    }

    public void updateDoctor(String id, Doctor updatedDoctor) {
        List<Doctor> doctors = getAllDoctors();
        for (int i = 0; i < doctors.size(); i++) {
            if (doctors.get(i).getId().equals(id)) {
                doctors.set(i, updatedDoctor);
                break;
            }
        }
        saveAll(doctors);
    }

    public void deleteDoctor(String id) {
        List<Doctor> doctors = getAllDoctors();
        doctors.removeIf(d -> d.getId().equals(id));
        saveAll(doctors);
    }

    public List<Doctor> searchDoctors(String query) {
        String lowerQuery = query.toLowerCase();
        return getAllDoctors().stream()
                .filter(d -> d.getName().toLowerCase().contains(lowerQuery) || 
                             d.getSpecialization().toLowerCase().contains(lowerQuery) ||
                             d.getId().toLowerCase().contains(lowerQuery))
                .collect(Collectors.toList());
    }

    public long getDoctorCount() {
        return getAllDoctors().size();
    }

    private void saveAll(List<Doctor> doctors) {
        List<String> lines = doctors.stream()
                .map(Doctor::toString)
                .collect(Collectors.toList());
        FileHandler.writeToFile(lines);
    }
}


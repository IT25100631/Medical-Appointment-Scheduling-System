package com.medical.appointmentsystem.util;

import com.medical.appointmentsystem.model.Appointment;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private static final String FILE_NAME = "appointments.txt";

    public static List<Appointment> loadAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return appointments;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] data = line.split(",", -1);

                if (data.length < 9) {
                    continue;
                }

                Appointment appointment = new Appointment(
                        data[0],
                        data[1],
                        data[2],
                        data[3],
                        data[4],
                        LocalDateTime.parse(data[5]),
                        LocalDateTime.parse(data[6]),
                        data[7],
                        data[8]
                );

                appointments.add(appointment);
            }
        } catch (IOException e) {
            System.out.println("Error loading appointments: " + e.getMessage());
        }

        return appointments;
    }

    public static void saveAppointments(List<Appointment> appointments) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Appointment a : appointments) {
                String line = String.join(",",
                        safe(a.getId()),
                        safe(a.getPatientId()),
                        safe(a.getPatientName()),
                        safe(a.getDoctorId()),
                        safe(a.getDoctorName()),
                        a.getStartTime() != null ? a.getStartTime().toString() : "",
                        a.getEndTime() != null ? a.getEndTime().toString() : "",
                        safe(a.getStatus()),
                        safe(a.getReason())
                );

                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving appointments: " + e.getMessage());
        }
    }

    private static String safe(String value) {
        return value == null ? "" : value.replace(",", " ");
    }
}
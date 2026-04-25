package com.medical.appointmentsystem.service;

import com.medical.appointmentsystem.model.Appointment;
import com.medical.appointmentsystem.util.FileHandler;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AppointmentService {

    private List<Appointment> appointments = new ArrayList<>();

    public AppointmentService() {
        appointments = FileHandler.loadAppointments();
    }

    public List<Appointment> getAllAppointments() {
        return appointments;
    }

    public Appointment getAppointmentById(String id) {
        for (Appointment appointment : appointments) {
            if (appointment.getId().equalsIgnoreCase(id)) {
                return appointment;
            }
        }
        return null;
    }

    public boolean bookAppointment(Appointment appointment) {
        if (appointment == null) {
            return false;
        }

        if (!isValidAppointment(appointment)) {
            return false;
        }

        if (isIdAlreadyExists(appointment.getId())) {
            return false;
        }

        if (!isDoctorSlotAvailable(appointment)) {
            return false;
        }

        appointment.setStatus(Appointment.STATUS_BOOKED);

        if (appointment.getEndTime() == null && appointment.getStartTime() != null) {
            appointment.setEndTime(
                    appointment.getStartTime().plusMinutes(Appointment.DEFAULT_DURATION_MINUTES)
            );
        }

        appointments.add(appointment);
        FileHandler.saveAppointments(appointments);
        return true;
    }

    public boolean cancelAppointment(String id) {
        Appointment appointment = getAppointmentById(id);

        if (appointment == null) {
            return false;
        }

        if (Appointment.STATUS_CANCELLED.equalsIgnoreCase(appointment.getStatus())) {
            return false;
        }

        appointment.cancel();
        FileHandler.saveAppointments(appointments);
        return true;
    }

    public boolean completeAppointment(String id) {
        Appointment appointment = getAppointmentById(id);

        if (appointment == null) {
            return false;
        }

        appointment.complete();
        FileHandler.saveAppointments(appointments);
        return true;
    }

    public List<Appointment> getAppointmentsByDoctor(String doctorId) {
        List<Appointment> result = new ArrayList<>();

        for (Appointment appointment : appointments) {
            if (appointment.getDoctorId().equalsIgnoreCase(doctorId)) {
                result.add(appointment);
            }
        }

        return result;
    }

    public List<Appointment> getAppointmentsByPatient(String patientId) {
        List<Appointment> result = new ArrayList<>();

        for (Appointment appointment : appointments) {
            if (appointment.getPatientId().equalsIgnoreCase(patientId)) {
                result.add(appointment);
            }
        }

        return result;
    }

    private boolean isIdAlreadyExists(String id) {
        for (Appointment appointment : appointments) {
            if (appointment.getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    private boolean isDoctorSlotAvailable(Appointment newAppointment) {
        for (Appointment existing : appointments) {
            boolean sameDoctor = existing.getDoctorId().equalsIgnoreCase(newAppointment.getDoctorId());
            boolean active = !Appointment.STATUS_CANCELLED.equalsIgnoreCase(existing.getStatus());

            if (sameDoctor && active && existing.overlapsWith(newAppointment)) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidAppointment(Appointment appointment) {
        return appointment.getId() != null && !appointment.getId().isBlank()
                && appointment.getPatientId() != null && !appointment.getPatientId().isBlank()
                && appointment.getPatientName() != null && !appointment.getPatientName().isBlank()
                && appointment.getDoctorId() != null && !appointment.getDoctorId().isBlank()
                && appointment.getDoctorName() != null && !appointment.getDoctorName().isBlank()
                && appointment.getStartTime() != null;
    }
}
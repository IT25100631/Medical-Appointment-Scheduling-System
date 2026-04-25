package com.medical.appointmentsystem.controller;

import com.medical.appointmentsystem.model.Appointment;
import com.medical.appointmentsystem.service.AppointmentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    @PostMapping
    public String bookAppointment(@RequestBody Appointment appointment) {
        boolean success = appointmentService.bookAppointment(appointment);
        return success ? "Appointment booked successfully" : "Booking failed";
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/{id}")
    public Appointment getAppointmentById(@PathVariable String id) {
        return appointmentService.getAppointmentById(id);
    }

    @PutMapping("/{id}/cancel")
    public String cancelAppointment(@PathVariable String id) {
        boolean success = appointmentService.cancelAppointment(id);
        return success ? "Appointment cancelled successfully" : "Cancellation failed";
    }

    @PutMapping("/{id}/complete")
    public String completeAppointment(@PathVariable String id) {
        boolean success = appointmentService.completeAppointment(id);
        return success ? "Appointment completed successfully" : "Completion failed";
    }

    @GetMapping("/doctor/{doctorId}")
    public List<Appointment> getAppointmentsByDoctor(@PathVariable String doctorId) {
        return appointmentService.getAppointmentsByDoctor(doctorId);
    }

    @GetMapping("/patient/{patientId}")
    public List<Appointment> getAppointmentsByPatient(@PathVariable String patientId) {
        return appointmentService.getAppointmentsByPatient(patientId);
    }
}
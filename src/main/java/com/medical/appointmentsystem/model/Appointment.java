package com.medical.appointmentsystem.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Appointment {

    public static final String STATUS_BOOKED = "BOOKED";
    public static final String STATUS_CANCELLED = "CANCELLED";
    public static final String STATUS_COMPLETED = "COMPLETED";

    public static final int DEFAULT_DURATION_MINUTES = 30;

    private static final DateTimeFormatter DISPLAY_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private String id;
    private String patientId;
    private String patientName;
    private String doctorId;
    private String doctorName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String status;
    private String reason;

    public Appointment() {
    }

    public Appointment(String id, String patientId, String patientName,
                       String doctorId, String doctorName,
                       LocalDateTime startTime, String reason) {
        this.id = id;
        this.patientId = patientId;
        this.patientName = patientName;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.startTime = startTime;
        this.endTime = startTime.plusMinutes(DEFAULT_DURATION_MINUTES);
        this.status = STATUS_BOOKED;
        this.reason = reason;
    }

    public Appointment(String id, String patientId, String patientName,
                       String doctorId, String doctorName,
                       LocalDateTime startTime, LocalDateTime endTime,
                       String status, String reason) {
        this.id = id;
        this.patientId = patientId;
        this.patientName = patientName;
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.status = status;
        this.reason = reason;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
        if (this.endTime == null && startTime != null) {
            this.endTime = startTime.plusMinutes(DEFAULT_DURATION_MINUTES);
        }
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public void cancel() {
        this.status = STATUS_CANCELLED;
    }

    public void complete() {
        this.status = STATUS_COMPLETED;
    }

    public boolean isUpcoming() {
        return STATUS_BOOKED.equalsIgnoreCase(this.status)
                && this.startTime != null
                && this.startTime.isAfter(LocalDateTime.now());
    }

    public boolean isPast() {
        return this.endTime != null && this.endTime.isBefore(LocalDateTime.now());
    }

    public boolean isCancellable() {
        return STATUS_BOOKED.equalsIgnoreCase(this.status)
                && this.startTime != null
                && this.startTime.isAfter(LocalDateTime.now());
    }

    public boolean overlapsWith(Appointment other) {
        if (other == null || this.startTime == null || this.endTime == null
                || other.startTime == null || other.endTime == null) {
            return false;
        }

        return this.startTime.isBefore(other.endTime)
                && this.endTime.isAfter(other.startTime);
    }

    public long getDurationMinutes() {
        if (startTime == null || endTime == null) {
            return 0;
        }
        return Duration.between(startTime, endTime).toMinutes();
    }

    public String getFormattedStartTime() {
        return startTime != null ? startTime.format(DISPLAY_FORMATTER) : "N/A";
    }

    public String getFormattedEndTime() {
        return endTime != null ? endTime.format(DISPLAY_FORMATTER) : "N/A";
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id='" + id + '\'' +
                ", patientId='" + patientId + '\'' +
                ", patientName='" + patientName + '\'' +
                ", doctorId='" + doctorId + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", startTime=" + getFormattedStartTime() +
                ", endTime=" + getFormattedEndTime() +
                ", status='" + status + '\'' +
                ", reason='" + reason + '\'' +
                '}';
    }
}
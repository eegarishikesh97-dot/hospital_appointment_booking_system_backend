package com.hospital.booking.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.hospital.booking.model.Appointment;
import com.hospital.booking.model.Doctor;
import com.hospital.booking.model.Patient;
import com.hospital.booking.repository.AppointmentRepository;
import com.hospital.booking.repository.DoctorRepository;
import com.hospital.booking.repository.PatientRepository;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
@RestController @RequestMapping("/api/appointments")
public class AppointmentController {
    private final AppointmentRepository appointments;
    private final PatientRepository patients;
    private final DoctorRepository doctors;
    public AppointmentController(AppointmentRepository a, PatientRepository p, DoctorRepository d){appointments=a;patients=p;doctors=d;}
    @GetMapping public List<Appointment> all(){return appointments.findAll();}
    @PostMapping public Appointment create(@Valid @RequestBody BookingRequest request) {
        Patient patient = patients.findById(request.patientId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Patient not found"));
        Doctor doctor = doctors.findById(request.doctorId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Doctor not found"));
        Appointment a = new Appointment();
        a.setPatient(patient); a.setDoctor(doctor); a.setAppointmentDate(request.appointmentDate());
        a.setAppointmentTime(request.appointmentTime()); a.setReason(request.reason()); a.setStatus("BOOKED");
        return appointments.save(a);
    }
    @PatchMapping("/{id}/status") public Appointment updateStatus(@PathVariable Long id, @RequestParam String status) {
        if (!List.of("BOOKED","CONFIRMED","COMPLETED","CANCELLED").contains(status.toUpperCase()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Invalid status");
        Appointment a=appointments.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Appointment not found"));
        a.setStatus(status.toUpperCase()); return appointments.save(a);
    }
    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long id) {
        if(!appointments.existsById(id)) throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Appointment not found");
        appointments.deleteById(id);
    }
    public record BookingRequest(@NotNull Long patientId, @NotNull Long doctorId,
        @NotNull java.time.LocalDate appointmentDate, @NotNull java.time.LocalTime appointmentTime, String reason) {}
}

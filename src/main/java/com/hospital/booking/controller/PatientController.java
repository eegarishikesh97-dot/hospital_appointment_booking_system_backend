package com.hospital.booking.controller;
import com.hospital.booking.model.Patient;
import com.hospital.booking.repository.PatientRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/patients")
public class PatientController {
    private final PatientRepository repo;
    public PatientController(PatientRepository repo){this.repo=repo;}
    @GetMapping public List<Patient> all(){return repo.findAll();}
    @PostMapping public Patient create(@Valid @RequestBody Patient patient){patient.setId(null); return repo.save(patient);}
}

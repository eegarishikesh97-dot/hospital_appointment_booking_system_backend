package com.hospital.booking.controller;
import com.hospital.booking.model.Doctor;
import com.hospital.booking.repository.DoctorRepository;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController @RequestMapping("/api/doctors")
public class DoctorController {
    private final DoctorRepository repo;
    public DoctorController(DoctorRepository repo){this.repo=repo;}
    @GetMapping public List<Doctor> all(){return repo.findAll();}
    @PostMapping public Doctor create(@Valid @RequestBody Doctor doctor){doctor.setId(null); return repo.save(doctor);}
}

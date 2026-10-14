package com.hospital.booking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
@Entity
public class Doctor {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
    @NotBlank private String name;
    @NotBlank private String specialization;
    private String availability;
    @PositiveOrZero private double consultationFee;

    public Doctor() {}
    public Doctor(String name, String specialization, String availability, double consultationFee) {
        this.name=name; this.specialization=specialization; this.availability=availability; this.consultationFee=consultationFee;
    }
    public Long getId(){return id;} public void setId(Long id){this.id=id;}
    public String getName(){return name;} public void setName(String name){this.name=name;}
    public String getSpecialization(){return specialization;} public void setSpecialization(String specialization){this.specialization=specialization;}
    public String getAvailability(){return availability;} public void setAvailability(String availability){this.availability=availability;}
    public double getConsultationFee(){return consultationFee;} public void setConsultationFee(double consultationFee){this.consultationFee=consultationFee;}
}

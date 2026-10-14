package com.hospital.booking;

import com.hospital.booking.model.Doctor;
import com.hospital.booking.repository.DoctorRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class HospitalBookingApplication {
    public static void main(String[] args) {
        SpringApplication.run(HospitalBookingApplication.class, args);
    }

    @Bean
    WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**").allowedOrigins("http://localhost:5173")
                    .allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")
                    .allowedHeaders("*");
            }
        };
    }

    @Bean
    CommandLineRunner seedDoctors(DoctorRepository doctors) {
        return args -> {
            if (doctors.count() == 0) {
                doctors.save(new Doctor("Dr. Ananya Sharma", "Cardiology", "Mon-Fri, 10 AM - 2 PM", 500.0));
                doctors.save(new Doctor("Dr. Rahul Verma", "General Medicine", "Mon-Sat, 9 AM - 1 PM", 300.0));
                doctors.save(new Doctor("Dr. Meera Reddy", "Dermatology", "Tue-Sat, 11 AM - 4 PM", 400.0));
            }
        };
    }
}

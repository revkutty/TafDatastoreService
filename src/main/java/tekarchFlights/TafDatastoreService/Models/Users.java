package tekarchFlights.TafDatastoreService.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

    @Data
    @Entity
    @Table(name = "users")
    public class Users {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NotBlank(message = "Username cannot be blank")
        @Size(max = 50, message = "Username cannot exceed 50 characters")
        @Column(unique = true, nullable = false)
        private String username;

        @Email(message = "Invalid email format")
        @NotBlank(message = "Email cannot be blank")
        @Column(nullable = false)
        private String email;

        @NotBlank(message = "Phone cannot be blank")
        @Size(min = 10, max = 15, message = "Phone must be between 10 and 15 characters")
        @Column(nullable = false)
        private String phone;

        @Column(nullable = false, updatable = false)
        private LocalDateTime createdAt;

        @Column(nullable = false)
        private LocalDateTime updatedAt;

        @PrePersist
        private void onCreate() {
            createdAt = updatedAt = LocalDateTime.now();
        }

        @PreUpdate
        private void onUpdate() {
            updatedAt = LocalDateTime.now();
        }
    }



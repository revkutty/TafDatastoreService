package tekarchFlights.TafDatastoreService.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "flights")
public class Flights {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Flight number cannot be blank")
    @Size(max = 20, message = "Flight number cannot exceed 20 characters")
    @Column(unique = true, nullable = false)
    private String flightNumber;

    @NotBlank(message = "Departure location cannot be blank")
    private String departure;

    @NotBlank(message = "Arrival location cannot be blank")
    private String arrival;

    @NotNull(message = "Departure time cannot be null")
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time cannot be null")
    private LocalDateTime arrivalTime;

    @Min(value = 0, message = "Price must be at least 0")
    private Double price;

    @Min(value = 0, message = "Available seats must be at least 0")
    private Integer availableSeats;

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

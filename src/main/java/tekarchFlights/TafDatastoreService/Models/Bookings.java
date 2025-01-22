package tekarchFlights.TafDatastoreService.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "bookings")
public class Bookings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User cannot be null")
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id", nullable = false)
    private Users user;

    @NotNull(message = "Flight cannot be null")
    @ManyToOne
    @JoinColumn(name = "flight_id", referencedColumnName = "id", nullable = false)
    private Flights flight;

    @NotBlank(message = "Status cannot be blank")
    @Column(nullable = false)
    private String status;


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

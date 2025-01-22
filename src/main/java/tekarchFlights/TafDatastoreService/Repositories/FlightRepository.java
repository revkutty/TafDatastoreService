package tekarchFlights.TafDatastoreService.Repositories;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import tekarchFlights.TafDatastoreService.Models.Flights;

public interface FlightRepository extends JpaRepository<Flights, Long> {
    boolean existsByFlightNumber(@NotBlank(message = "Flight number cannot be blank") @Size(max = 20, message = "Flight number cannot exceed 20 characters") String flightNumber);
}

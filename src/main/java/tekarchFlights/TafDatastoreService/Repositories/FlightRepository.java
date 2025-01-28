package tekarchFlights.TafDatastoreService.Repositories;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tekarchFlights.TafDatastoreService.Models.Flights;

import java.time.LocalDate;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flights, Long> {
    boolean existsByFlightNumber(@NotBlank(message = "Flight number cannot be blank") @Size(max = 20, message = "Flight number cannot exceed 20 characters") String flightNumber);
    // Using FUNCTION('DATE', departureTime) to extract only the date part of departureTime
 /*   @Query("SELECT f FROM flights f WHERE f.departure = :origin AND f.arrival = :destination AND FUNCTION('DATE', f.departureTime) = :date")
    List<Flights> findByDepartureAndArrivalAndDepartureTimeDate(@Param("origin") String origin,
                                                               @Param("destination") String destination,
                                                               @Param("date") LocalDate date);

  */
}

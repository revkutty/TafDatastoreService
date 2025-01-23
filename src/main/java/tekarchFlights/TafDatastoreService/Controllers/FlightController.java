package tekarchFlights.TafDatastoreService.Controllers;

import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tekarchFlights.TafDatastoreService.Models.Flights;
import tekarchFlights.TafDatastoreService.Models.Users;
import tekarchFlights.TafDatastoreService.Repositories.FlightRepository;
import tekarchFlights.TafDatastoreService.Repositories.UserRepository;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/flights")
public class FlightController {


    @Autowired
    private FlightRepository flightRepository;

    @GetMapping
    public List<Flights> getAllFlights() {
        return flightRepository.findAll();
    }

    private static final Logger logger = LogManager.getLogger(FlightController.class);

    @PostMapping
    public ResponseEntity<Flights> addFlight(@Valid @RequestBody Flights flights) {
        // Check if FlightNumber or email is already in use
        if (flightRepository.existsByFlightNumber(flights.getFlightNumber())) {
            throw new RuntimeException("FlightNumber is already taken");
        }


        return ResponseEntity.ok(flightRepository.save(flights));
    }
    @GetMapping("/{id}")
    public ResponseEntity<Flights> getFlightById(@PathVariable Long id) {
        Flights flight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + id));
        return ResponseEntity.ok(flight);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Flights> updateFlight(@PathVariable Long id, @Valid @RequestBody Flights flights) {
        Flights existingFlight = flightRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + id));
        existingFlight.setFlightNumber(flights.getFlightNumber());
        existingFlight.setDeparture(flights.getDeparture());
        existingFlight.setArrival(flights.getArrival());
        existingFlight.setDepartureTime(flights.getDepartureTime());
        existingFlight.setArrivalTime(flights.getArrivalTime());
        existingFlight.setPrice(flights.getPrice());
        existingFlight.setAvailableSeats(flights.getAvailableSeats());
        return ResponseEntity.ok(flightRepository.save(existingFlight));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        flightRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{flightId}/update")
    public ResponseEntity<Flights> updateFlights(@PathVariable Long flightId, @RequestBody Flights flight) {
        // Fetch flight from DB, update fields, and save back
        Optional<Flights> existingFlight = flightRepository.findById(flightId);
        if (existingFlight.isPresent()) {
            Flights updatedFlights = existingFlight.get();
            updatedFlights.setAvailableSeats(flight.getAvailableSeats());
            // Save updated flight
            flightRepository.save(updatedFlights);
            return ResponseEntity.ok(updatedFlights);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

}


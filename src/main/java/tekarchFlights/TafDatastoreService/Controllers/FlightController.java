package tekarchFlights.TafDatastoreService.Controllers;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tekarchFlights.TafDatastoreService.Models.Flights;
import tekarchFlights.TafDatastoreService.Models.Users;
import tekarchFlights.TafDatastoreService.Repositories.FlightRepository;
import tekarchFlights.TafDatastoreService.Repositories.UserRepository;
import java.util.List;


@RestController
@RequestMapping("/api/flights")
public class FlightController {


    @Autowired
    private FlightRepository flightRepository;

    @GetMapping
    public List<Flights> getAllFlights() {
        return flightRepository.findAll();
    }

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
}


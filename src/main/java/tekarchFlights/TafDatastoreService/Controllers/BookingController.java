package tekarchFlights.TafDatastoreService.Controllers;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tekarchFlights.TafDatastoreService.Models.*;
import tekarchFlights.TafDatastoreService.Repositories.BookingRepository;
import tekarchFlights.TafDatastoreService.Repositories.FlightRepository;
import tekarchFlights.TafDatastoreService.Repositories.UserRepository;
import tekarchFlights.TafDatastoreService.Service.BookingService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private BookingService bookingService;

    @PostMapping
    public ResponseEntity<Bookings> createBooking(@Valid @RequestBody BookingRequestDTO bookingRequestDTO) {
        Users users = userRepository.findById(bookingRequestDTO.getUser())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + bookingRequestDTO.getUser()));

        Flights flights = flightRepository.findById(bookingRequestDTO.getFlight())
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + bookingRequestDTO.getFlight()));

        Bookings bookings = new Bookings();
        bookings.setUser(users);
        bookings.setFlight(flights);
        bookings.setStatus(bookingRequestDTO.getStatus());

        return ResponseEntity.ok(bookingRepository.save(bookings));
    }

    @GetMapping
    public List<Bookings> getAllBookings() {
        return bookingRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bookings> getBookingById(@PathVariable Long id) {
        Bookings bookings = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
        return ResponseEntity.ok(bookings);
    }

    //This method gets the data from User DB
  /*  @GetMapping("/user/{userId}")
    public ResponseEntity<List<Bookings>> getBookingsForUser(@PathVariable Long userId) {
        List<Bookings> bookings = bookingService.getBookingsForUser(userId);
        if (bookings.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookings);
    }

   */


    //This method gets the User data from Booking DB
      @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponseDTO>> getBookingsForUser(@PathVariable Long userId) {
        List<BookingResponseDTO> bookings = bookingService.getBookingsForUser(userId);
        if (bookings.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bookings);
    }


    @PutMapping("/{id}")
    public ResponseEntity<Bookings> updateBooking(@PathVariable Long id, @Valid @RequestBody BookingRequestDTO bookingRequestDTO) {
        Bookings bookings = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));

        Users users = userRepository.findById(bookingRequestDTO.getUser())
                .orElseThrow(() -> new RuntimeException("User not found with id: " + bookingRequestDTO.getUser()));

        Flights flights = flightRepository.findById(bookingRequestDTO.getFlight())
                .orElseThrow(() -> new RuntimeException("Flight not found with id: " + bookingRequestDTO.getFlight()));

        bookings.setUser(users);
        bookings.setFlight(flights);
        bookings.setStatus(bookingRequestDTO.getStatus());

        return ResponseEntity.ok(bookingRepository.save(bookings));
    }

  /*  @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBooking(@PathVariable Long id) {
        Bookings bookings = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
        bookingRepository.delete(bookings);
        return ResponseEntity.noContent().build();
    }
       */

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id) {
        Bookings bookings = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));
        // Check if the booking is already canceled
        if ("Cancelled".equalsIgnoreCase(bookings.getStatus())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Booking is already cancelled.");
        }

        // Update the status to "Cancelled"
        bookings.setStatus("Cancelled");
        bookingRepository.save(bookings);

        return ResponseEntity.ok("Booking has been successfully cancelled.");
    }



    @PutMapping("/{id}/update")
    public ResponseEntity<?> updateBooking(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        Bookings booking = bookingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking not found with id: " + id));

        // Update the booking status if provided
        if (updates.containsKey("status")) {
            booking.setStatus((String) updates.get("status"));
        }

        // Save the updated booking back to the repository
        bookingRepository.save(booking);

        return ResponseEntity.ok("Booking status updated successfully.");
    }




}

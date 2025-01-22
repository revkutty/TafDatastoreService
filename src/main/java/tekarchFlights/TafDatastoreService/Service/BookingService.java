package tekarchFlights.TafDatastoreService.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tekarchFlights.TafDatastoreService.Models.BookingResponseDTO;
import tekarchFlights.TafDatastoreService.Models.Bookings;
import tekarchFlights.TafDatastoreService.Models.Flights;
import tekarchFlights.TafDatastoreService.Models.Users;
import tekarchFlights.TafDatastoreService.Repositories.BookingRepository;
import tekarchFlights.TafDatastoreService.Repositories.FlightRepository;
import tekarchFlights.TafDatastoreService.Repositories.UserRepository;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FlightRepository flightRepository;

    @Autowired
    private UserRepository userRepository;


  /*  public List<Bookings> getBookingsForUser(Long userId) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
        return bookingRepository.findByUser(user);
    }


   */

    //keep userId as a Long field and not use @ManyToOne
    public List<BookingResponseDTO> getBookingsForUser(Long userId) {
        List<Bookings> bookings = bookingRepository.findByUserId(userId);
        return bookings.stream()
                .map(booking -> {
                    Flights flight = flightRepository.findById(booking.getFlight().getId()).orElse(null);
                    return new BookingResponseDTO(booking, flight);
                })
                .collect(Collectors.toList());
    }





}
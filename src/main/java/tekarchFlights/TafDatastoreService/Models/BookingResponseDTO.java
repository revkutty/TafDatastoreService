package tekarchFlights.TafDatastoreService.Models;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class BookingResponseDTO {
    private Long bookingId;
    private Users user;
    private Flights flight;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    public BookingResponseDTO(Bookings booking, Flights flights) {
        this.bookingId = booking.getId();
        this.user = booking.getUser();
        this.flight = booking.getFlight();
        this.status = booking.getStatus();
        this.createdAt = booking.getCreatedAt();
        this.updatedAt = booking.getUpdatedAt();

    }

}

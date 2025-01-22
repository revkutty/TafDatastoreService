package tekarchFlights.TafDatastoreService.Models;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class BookingRequestDTO {

    @NotNull(message = "User ID is required")
    private Long user;

    @NotNull(message = "Flight ID is required")
    private Long flight;

    @NotNull(message = "Status is required")
    private String status;
}
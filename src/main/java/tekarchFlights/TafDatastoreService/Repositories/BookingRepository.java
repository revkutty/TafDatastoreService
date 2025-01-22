package tekarchFlights.TafDatastoreService.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tekarchFlights.TafDatastoreService.Models.Bookings;
import tekarchFlights.TafDatastoreService.Models.Users;

import java.util.List;

public interface BookingRepository extends JpaRepository<Bookings, Long> {

    List<Bookings> findByUserId(Long userId);

  //  List<Bookings> findByUser(Users user);   //If userId is mapped to a User entity
}
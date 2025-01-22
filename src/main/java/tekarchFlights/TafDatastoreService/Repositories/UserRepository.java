package tekarchFlights.TafDatastoreService.Repositories;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import tekarchFlights.TafDatastoreService.Models.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
    boolean existsByEmail(@Email(message = "Invalid email format") @NotBlank(message = "Email cannot be blank") String email);

    boolean existsByUsername(@NotBlank(message = "Username cannot be blank") @Size(max = 50, message = "Username cannot exceed 50 characters") String username);
}


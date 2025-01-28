package tekarchFlights.TafDatastoreService.Controllers;

import jakarta.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tekarchFlights.TafDatastoreService.Models.Users;
import tekarchFlights.TafDatastoreService.Repositories.UserRepository;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    private static final Logger logger = LogManager.getLogger(UserController.class);

    @GetMapping
    public List<Users> getAllUsers() {
        return userRepository.findAll();
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody Users users) {
        // Check if username or email is already in use
        if (userRepository.existsByUsername(users.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                  .body("Username is already taken");
        }
        if (userRepository.existsByEmail(users.getEmail())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Username is already taken");
           // throw new RuntimeException("Email is already registered");
        }

        return ResponseEntity.ok(userRepository.save(users));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Users> getUserById(@PathVariable Long id) {
        Users users = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Users> updateUser(@PathVariable Long id, @Valid @RequestBody Users users) {
        Users existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        existingUser.setUsername(users.getUsername());
        existingUser.setEmail(users.getEmail());
        existingUser.setPhone(users.getPhone());
        return ResponseEntity.ok(userRepository.save(existingUser));
    }

       @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Users users = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
        userRepository.delete(users);
        return ResponseEntity.noContent().build();
    }
}


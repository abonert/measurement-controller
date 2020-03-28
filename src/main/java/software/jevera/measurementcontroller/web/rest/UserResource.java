package software.jevera.measurementcontroller.web.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import software.jevera.measurementcontroller.service.UserService;
import software.jevera.measurementcontroller.service.dto.UserDTO;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserResource {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserDTO> addUser(@Valid @RequestBody UserDTO dto) throws URISyntaxException {
        if (dto.getId() != null) {
            throw new IllegalArgumentException("A new user cannot already have an ID");
        }
        UserDTO newUser = userService.add(dto);
        return ResponseEntity.created(new URI("/api/users/" + newUser.getId())).body(newUser);
    }

    @PutMapping
    public ResponseEntity<UserDTO> editUser(@Valid @RequestBody UserDTO dto) {
        UserDTO newUser = userService.edit(dto);
        return ResponseEntity.ok(newUser);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<UserDTO> removeUser(@PathVariable Long id) {
        userService.remove(id);
        return ResponseEntity.noContent().build();
    }
}

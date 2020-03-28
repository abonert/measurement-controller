package software.jevera.measurementcontroller.service;

import software.jevera.measurementcontroller.service.dto.UserDTO;

public interface UserService {

    UserDTO add(UserDTO dto);

    UserDTO edit(UserDTO dto);

    void remove(Long id);
}

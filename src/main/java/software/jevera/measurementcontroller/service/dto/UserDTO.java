package software.jevera.measurementcontroller.service.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
public class UserDTO {

    private Long id;

    @NotEmpty
    @Valid
    private List<VendorDTO> vendors;
}

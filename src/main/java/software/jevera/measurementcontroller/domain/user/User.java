package software.jevera.measurementcontroller.domain.user;

import lombok.Data;
import software.jevera.measurementcontroller.domain.vendor.Vendor;

import java.util.List;

@Data
public class User {

    private Long id;

    private UserState state;

    private List<Vendor> vendors;
}

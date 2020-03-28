package software.jevera.measurementcontroller.domain.vendor;

import lombok.Data;

import java.util.Map;

@Data
public class VendorData {

   private Long id;

   private Map<String, Object> data;
}

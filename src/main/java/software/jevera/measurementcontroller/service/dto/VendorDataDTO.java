package software.jevera.measurementcontroller.service.dto;

import lombok.Data;

import java.util.Map;

@Data
public class VendorDataDTO {

    private Long id;

    private Map<String, Object> data;
}

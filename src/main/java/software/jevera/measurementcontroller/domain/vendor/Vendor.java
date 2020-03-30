package software.jevera.measurementcontroller.domain.vendor;

import lombok.Data;

import java.util.List;

@Data
public class Vendor {

    private Long id;

    private String name;

    private List<VendorData> vendorDatas;
}

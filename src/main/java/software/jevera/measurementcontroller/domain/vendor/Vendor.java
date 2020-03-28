package software.jevera.measurementcontroller.domain.vendor;

import lombok.Data;

import java.util.List;

@Data
public class Vendor {

    private String name;

    private List<VendorData> vendorDatas;
}

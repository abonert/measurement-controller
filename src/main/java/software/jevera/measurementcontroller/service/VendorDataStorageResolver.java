package software.jevera.measurementcontroller.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.jevera.measurementcontroller.domain.vendor.enumeration.VendorStorageType;
import software.jevera.measurementcontroller.repository.VendorDataStorage;

import java.util.List;

/**
 * Class for resolving data storage according to vendor configuration
 * @see VendorStorageType
 */
@Component
@RequiredArgsConstructor
public class VendorDataStorageResolver {

    private final List<VendorDataStorage> vendorDataStorages;

    public VendorDataStorage getStorage(VendorStorageType storageType) {
            return vendorDataStorages.stream().filter(s -> s.getDataStorageType().equals(storageType)).findFirst().orElseThrow();
    }

}

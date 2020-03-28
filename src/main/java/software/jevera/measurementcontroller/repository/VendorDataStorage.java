package software.jevera.measurementcontroller.repository;

import software.jevera.measurementcontroller.domain.vendor.VendorData;
import software.jevera.measurementcontroller.domain.vendor.enumeration.VendorStorageType;

/**
 * Interface describes vendor data storage
 */
public interface VendorDataStorage {

    void store(VendorData data);

    VendorStorageType getDataStorageType();
}

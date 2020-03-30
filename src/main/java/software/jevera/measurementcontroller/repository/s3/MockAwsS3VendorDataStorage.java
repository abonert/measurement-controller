package software.jevera.measurementcontroller.repository.s3;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import software.jevera.measurementcontroller.domain.vendor.VendorData;
import software.jevera.measurementcontroller.domain.vendor.enumeration.VendorStorageType;
import software.jevera.measurementcontroller.repository.VendorDataStorage;

@Repository
@Slf4j
public class MockAwsS3VendorDataStorage implements VendorDataStorage {

    @Override
    public void store(VendorData data) {
        log.info("Save data to AWS S3: {}", data);
    }

    @Override
    public VendorStorageType getDataStorageType() {
        return VendorStorageType.AWS_S3;
    }
}

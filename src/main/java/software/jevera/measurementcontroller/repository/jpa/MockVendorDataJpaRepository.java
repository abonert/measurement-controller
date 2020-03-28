package software.jevera.measurementcontroller.repository.jpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import software.jevera.measurementcontroller.domain.vendor.VendorData;
import software.jevera.measurementcontroller.domain.vendor.enumeration.VendorStorageType;
import software.jevera.measurementcontroller.repository.VendorDataStorage;

import java.util.Objects;
import java.util.Optional;

@Repository
@Slf4j
public class MockVendorDataJpaRepository implements CrudRepository<VendorData, Long>, VendorDataStorage {


    @Override
    public <S extends VendorData> S save(S entity) {
        if (Objects.isNull(entity.getId())) {
            log.info("VendorData saved {}", entity);
        } else {
            log.info("VendorData updated {}", entity);
        }
        return entity;
    }

    @Override
    public <S extends VendorData> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<VendorData> findById(Long id) {
        log.info("VendorData found by ID {}", id);
        return Optional.of(new VendorData());
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<VendorData> findAll() {
        return null;
    }

    @Override
    public Iterable<VendorData> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long id) {
        log.info("VendorData with ID {} deleted", id);
    }

    @Override
    public void delete(VendorData entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends VendorData> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public void store(VendorData data) {
        save(data);
    }

    @Override
    public VendorStorageType getDataStorageType() {
        return VendorStorageType.RDBMS;
    }
}

package software.jevera.measurementcontroller.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import software.jevera.measurementcontroller.config.ApplicationProperties;
import software.jevera.measurementcontroller.config.ApplicationProperties.VendorConfiguration;
import software.jevera.measurementcontroller.domain.user.User;
import software.jevera.measurementcontroller.domain.user.UserState;
import software.jevera.measurementcontroller.domain.vendor.Vendor;
import software.jevera.measurementcontroller.domain.vendor.VendorData;
import software.jevera.measurementcontroller.repository.VendorDataStorage;
import software.jevera.measurementcontroller.service.UserService;
import software.jevera.measurementcontroller.service.VendorDataStorageResolver;
import software.jevera.measurementcontroller.service.VendorSubscriptionProvider;
import software.jevera.measurementcontroller.service.dto.UserDTO;
import software.jevera.measurementcontroller.service.mapper.UserMapper;

import java.util.Map;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final VendorSubscriptionProvider vendorSubscriptionProvider;
    private final VendorDataStorageResolver vendorDataStorageResolver;
    private final ApplicationProperties applicationProperties;
    private final CrudRepository<User, Long> userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDTO add(UserDTO dto) {
        User entity = userMapper.toEntity(dto);
        entity.setState(UserState.CREATED);
        User savedEntity = of(userRepository.save(entity)).orElseThrow();
        dto.getVendors().forEach(vendor -> vendorSubscriptionProvider.subscribe(savedEntity.getId(), vendor.getName()));
        savedEntity.setState(UserState.IN_PROGRESS);
        return userMapper.toDto(savedEntity);
    }

    @Override
    public UserDTO edit(UserDTO dto) {
        Long id = dto.getId();
        User updatedEntity = userRepository.findById(id)
                .map(entity -> {
                    vendorSubscriptionProvider.destroyAll(entity);
                    userMapper.updateEntity(dto, entity);
                    return userRepository.save(entity);
                })
                .orElseThrow();
        updatedEntity.getVendors().forEach(vendor -> vendorSubscriptionProvider.subscribe(id, vendor.getName()));
        return userMapper.toDto(updatedEntity);
    }

    @Override
    public void remove(Long id) {
        User user = userRepository.findById(id).orElseThrow();
        vendorSubscriptionProvider.destroyAll(user);
        userRepository.deleteById(id);
    }

    @Override
    public void triggerChunk(Long id, Long vendorId) {
        User user = userRepository.findById(id).orElseThrow();
        Vendor vendor = user.getVendors().stream()
                .filter(v -> vendorId.equals(v.getId()))
                .findFirst()
                .orElseThrow();
        VendorConfiguration vendorConfiguration = getVendorConfiguration(vendor.getName());
        VendorDataStorage storage = vendorDataStorageResolver.getStorage(vendorConfiguration.getStorageType());
        VendorData vendorData = new VendorData();
        vendorData.setData(getData(vendorConfiguration));
        storage.store(vendorData);
    }

    private Map<String, Object> getData(VendorConfiguration vendorConfiguration) {
        VendorConfiguration.HttpConfiguration chunk = vendorConfiguration.getChunk();
        log.info("Get chunk data: METHOD {} ENDPOINT {}", chunk.getMethod(), chunk.getEndpoint());
        return Map.of("chunk_value", "HARD_CODE");
    }

    private VendorConfiguration getVendorConfiguration(String vendor) {
        Map<String, VendorConfiguration> configurations = applicationProperties.getVendorConfigurations();
        return ofNullable(configurations.get(vendor)).orElseThrow();
    }
}

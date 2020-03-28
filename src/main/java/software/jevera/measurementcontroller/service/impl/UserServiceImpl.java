package software.jevera.measurementcontroller.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;
import software.jevera.measurementcontroller.domain.user.User;
import software.jevera.measurementcontroller.domain.user.UserState;
import software.jevera.measurementcontroller.service.SchedulingManager;
import software.jevera.measurementcontroller.service.UserService;
import software.jevera.measurementcontroller.service.VendorSubscriptionProvider;
import software.jevera.measurementcontroller.service.dto.UserDTO;
import software.jevera.measurementcontroller.service.mapper.UserMapper;

import static java.util.Optional.of;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final VendorSubscriptionProvider vendorSubscriptionProvider;
    private final CrudRepository<User, Long> userRepository;
    private final UserMapper userMapper;
    private final SchedulingManager schedulingManager;

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
                    userMapper.updateEntity(dto, entity);
                    schedulingManager.destroy(id);
                    return userRepository.save(entity);
                })
                .orElseThrow();
        updatedEntity.getVendors().forEach(vendor -> vendorSubscriptionProvider.subscribe(id, vendor.getName()));
        return userMapper.toDto(updatedEntity);
    }

    @Override
    public void remove(Long id) {
        userRepository.deleteById(id);
        schedulingManager.destroy(id);
    }
}

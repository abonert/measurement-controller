package software.jevera.measurementcontroller.repository.jpa;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import software.jevera.measurementcontroller.domain.user.User;

import java.util.Objects;
import java.util.Optional;
import java.util.Random;

@Repository
@Slf4j
public class MockUserJpaRepository implements CrudRepository<User, Long> {

    @Override
    public <S extends User> S save(S entity) {
        if (Objects.isNull(entity.getId())) {
            entity.setId(new Random().nextLong());
            log.info("User saved {}", entity);
        } else {
            log.info("User updated {}", entity);
        }
        return entity;
    }

    @Override
    public <S extends User> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<User> findById(Long id) {
        log.info("User found by ID {}", id);
        User entity = new User();
        entity.setId(id);
        return Optional.of(entity);
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<User> findAll() {
        return null;
    }

    @Override
    public Iterable<User> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long id) {
        log.info("User with ID {} deleted", id);
    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {

    }

    @Override
    public void deleteAll() {

    }
}

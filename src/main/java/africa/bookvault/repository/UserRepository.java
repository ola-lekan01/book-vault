package africa.bookvault.repository;

import africa.bookvault.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    @Query(value = "SELECT u FROM users u WHERE u.username = :username", nativeQuery = true)
    Optional<User> findByUserName(@Param("username") String username);
}

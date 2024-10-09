package kha.productsdemo.repository;

import kha.productsdemo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
    User findUserByEmail(String email);
    User findUserById(String id);
    User findUserByUsername(String username);
    boolean existsByEmail(String email);
}

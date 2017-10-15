package org.zerhusen.security.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.zerhusen.model.security.User;

import java.util.List;

/**
 * Created by stephan on 20.03.16.
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    @Query("select u from User u where u.username like ?1")
    List<User> findbyUserNameContains(String name);

    Page<User> findByUsernameContaining(String username, Pageable pageable);
}

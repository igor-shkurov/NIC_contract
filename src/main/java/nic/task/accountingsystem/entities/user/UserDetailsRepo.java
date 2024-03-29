package nic.task.accountingsystem.entities.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepo extends JpaRepository<User, Long> {
    User findUserByUsername(String username);
}

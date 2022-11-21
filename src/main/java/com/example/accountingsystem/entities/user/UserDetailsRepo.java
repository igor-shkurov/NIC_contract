package com.example.accountingsystem.entities.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepo extends JpaRepository<User, Long> {
    User findUserByUsername(String username);


    boolean existsByUsername(String username);


    //проблема с удалением связанных контрактов
    //@Modifying
    //@Query(value = "delete from user c where c.id=2;",
    //  nativeQuery = true)
    //void deleteAll();

    //ПРОБЛЕМА С УДАЛЕНИЕМ СВЯЗАННЫХ КОНТРАКТОВ - временно решена!
    void deleteById (Long id);

    User findUserById (Long id);

    @Override
    boolean existsById(Long aLong);
}

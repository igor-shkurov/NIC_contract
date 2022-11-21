package com.example.accountingsystem.entities.user;

import com.example.accountingsystem.entities.contract.ContractService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final UserDetailsRepo userDetailsRepo;
    private final ContractService contractService;

    @Autowired
    public CustomUserDetailsService(UserDetailsRepo userRepo, ContractService contractService) {
        this.userDetailsRepo = userRepo;
        this.contractService = contractService;
    }

    public boolean saveUser(User user) {
        if (userDetailsRepo.existsByUsername(user.getUsername())) {
            System.out.println("ПОЛЬЗОВАТЕЛЬ УЖЕ СУЩЕСТВУЕТ");
            return false;
        } else {
            userDetailsRepo.save(user);
            System.out.println("ПОЛЬЗОВАТЕЛЬ ЕЩЁ НЕ СУЩЕСТВУЕТ");
            return true;
        }
    }


    public boolean existUser(User user){
        return userDetailsRepo.findUserByUsername(user.getUsername()).equals(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDetailsRepo.findUserByUsername(username);
        if (user == null) {
            log.error("User not found");
            throw new UsernameNotFoundException("User not found in db");
        } else {
            log.info("User found");
        }
        return user;
    }

    public User getUser(String username) {
        User user = userDetailsRepo.findUserByUsername(username);
        if (user == null) {
            log.error("User not found");
            throw new UsernameNotFoundException("User not found in db");
        } else {
            log.info("User found");
        }
        return user;
    }

    public List<User> getUsers() {
        return userDetailsRepo.findAll();
    }


    public void deleteUser(Long id) {
        //ПРОБЛЕМА С УДАЛЕНИЕМ СВЯЗАННЫХ КОНТРАКТОВ!

        contractService.deleteContractsByUserId(id);
        if (userDetailsRepo.existsById(id))
            userDetailsRepo.deleteById(id);
    }
}

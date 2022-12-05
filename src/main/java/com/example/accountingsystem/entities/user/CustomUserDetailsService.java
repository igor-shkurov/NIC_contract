package com.example.accountingsystem.entities.user;

import com.example.accountingsystem.entities.counterparty.Counterparty;
import com.example.accountingsystem.entities.counterparty.CounterpartyDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final UserDetailsRepo userDetailsRepo;

    private final UserMapper mapper;

    @Autowired
    public CustomUserDetailsService(UserDetailsRepo userRepo) {
        this.userDetailsRepo = userRepo;
        this.mapper = Mappers.getMapper(UserMapper.class);
    }

    public boolean saveUser(UserDTO dto) {
        User user = mapper.DTOtoUser(dto);
         if (userDetailsRepo.findUserByUsername(user.getUsername()) != null)   {
             return false;
         }
         else {
             userDetailsRepo.save(user);
             return true;
         }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDetailsRepo.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found in db");
        }
        return user;
    }

    public User getUserById(long id) {
        Optional<User> opt = userDetailsRepo.findById(id);
        return opt.orElse(null);
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return (User) loadUserByUsername(currentUserName);
        }
        throw new UsernameNotFoundException("No one is logged in");
    }

    public List<UserDTO> getUsers() {
        List<User> entities = userDetailsRepo.findAll();
        return mapper.toListOfDTO(entities);
    }

    public void updateUser(UserDTO dto) {
        long id = dto.id;
        User updatingUser = mapper.DTOtoUser(dto);
        User userToBeUpdated = getUserById(id);
        if (userToBeUpdated != null) {
            try {
                BeanUtils.copyProperties(userToBeUpdated, updatingUser);
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            userToBeUpdated.setId(id);
            userDetailsRepo.save(userToBeUpdated);
        }
        else {
            userDetailsRepo.save(updatingUser);
        }
    }

    public void deleteUser(long id) {
        userDetailsRepo.deleteById(id);
    }
}

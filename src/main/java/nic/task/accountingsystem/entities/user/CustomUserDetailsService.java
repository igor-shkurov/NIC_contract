package nic.task.accountingsystem.entities.user;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserDetailsRepo userDetailsRepo;

    private final UserMapper mapper;

    @Autowired
    public CustomUserDetailsService(UserDetailsRepo userRepo) {
        this.userDetailsRepo = userRepo;
        this.mapper = Mappers.getMapper(UserMapper.class);
    }

    public HttpStatus saveUser(UserDTO dto, boolean isModify) {
        User user = mapper.DTOtoUser(dto);
        if (userDetailsRepo.findUserByUsername(user.getUsername()) != null && !isModify)   {
             return HttpStatus.CONFLICT;
        }
        else {
            userDetailsRepo.save(user);
            return isModify ? HttpStatus.OK : HttpStatus.CREATED;
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

    public HttpStatus updateUser(UserDTO dto) {
        long id = dto.getId();
        User userToBeUpdated = getUserById(id);

        if (userToBeUpdated != null) {
            userToBeUpdated.setId(dto.getId());
            userToBeUpdated.setUsername(dto.getUsername());
            userToBeUpdated.setFIO(dto.getFIO());
            userToBeUpdated.setRole(dto.getRole());

            userDetailsRepo.save(userToBeUpdated);

            return HttpStatus.OK;
        }
        else {
            return HttpStatus.NOT_FOUND;
        }
    }

    public HttpStatus updatePassword(UserDTO dto) {
        long id = dto.getId();
        UserDTO dtoToBeUpdated = mapper.userToDTO(getUserById(id));

        if (dtoToBeUpdated == null) {
            return HttpStatus.NOT_FOUND;
        }
        dtoToBeUpdated.setId(id);
        dtoToBeUpdated.setPassword(dto.getPassword());
        return saveUser(dtoToBeUpdated, true);
    }

    public HttpStatus deleteUser(long id) {
        User currentUser = getCurrentUser();
        if (userDetailsRepo.findById(id).isPresent()) {
            if (currentUser.getId() != id) {
                userDetailsRepo.deleteById(id);
                return HttpStatus.OK;
            }
            else {
                return HttpStatus.CONFLICT;
            }
        }
        return HttpStatus.NOT_FOUND;
    }
}

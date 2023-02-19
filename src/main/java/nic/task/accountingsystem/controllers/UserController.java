package nic.task.accountingsystem.controllers;

import com.fasterxml.jackson.annotation.JsonView;
import nic.task.accountingsystem.entities.user.CustomUserDetailsService;
import nic.task.accountingsystem.entities.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final CustomUserDetailsService userService;

    @Autowired
    public UserController(CustomUserDetailsService userService) {
        this.userService = userService;
    }

    @JsonView(UserDTO.PublicView.class)
    @GetMapping(path = "", produces = {"application/json"})
    public ResponseEntity<List<UserDTO>> showUsers() {
        List<UserDTO> list = userService.getUsers();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping(path = "/is_admin")
    public ResponseEntity<HttpStatus> isAdmin() {
        return new ResponseEntity<>(userService.isAdmin());
    }


    @PostMapping(path = "/add", consumes = {"application/json"})
    public ResponseEntity<Object> addUser(@RequestBody @Validated(UserDTO.New.class) UserDTO dto) {
        return new ResponseEntity<>(userService.saveUser(dto, false));
    }

    @PostMapping(path = "/change_password", consumes = {"application/json"})
    public ResponseEntity<Object> updatePassword(@RequestBody @Validated(UserDTO.PasswordOnly.class) UserDTO dto) {
        return new ResponseEntity<>(userService.updatePassword(dto));
    }

    @PutMapping(path = "/update", consumes = {"application/json"})
    public ResponseEntity<Object> updateUser(@RequestBody @Validated(UserDTO.Modify.class) UserDTO dto) {
        return new ResponseEntity<>(userService.updateUser(dto));
    }

    @DeleteMapping(path = "/delete/user_id={id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.deleteUser(id));
    }
}

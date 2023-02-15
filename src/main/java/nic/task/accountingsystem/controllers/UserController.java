package nic.task.accountingsystem.controllers;

import nic.task.accountingsystem.entities.user.CustomUserDetailsService;
import nic.task.accountingsystem.entities.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final CustomUserDetailsService userService;

    @Autowired
    public UserController(CustomUserDetailsService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "", produces = {"application/json"})
    public ResponseEntity<List<UserDTO>> showUsers() {
        List<UserDTO> list = userService.getUsers();
        return new ResponseEntity<>(list, (list != null) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping(path = "/add", consumes = {"application/json"})
    public ResponseEntity<Object> addUser(@RequestBody @Valid UserDTO dto) {
        return new ResponseEntity<>(userService.saveUser(dto));
    }

    @PutMapping(path = "/update", consumes = {"application/json"})
    public ResponseEntity<Object> updateUser(@RequestBody @Valid UserDTO dto) {
        return new ResponseEntity<>(userService.updateUser(dto));
    }

    @DeleteMapping(path = "/delete/user_id={id}")
    public ResponseEntity<Object> deleteUser(@PathVariable("id") Long id) {
        return new ResponseEntity<>(userService.deleteUser(id));
    }
}

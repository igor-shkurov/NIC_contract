package nic.task.accountingsystem.entities.user;

import com.fasterxml.jackson.annotation.JsonView;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

public class UserDTO {
    public interface New {}
    public interface Modify {}
    public interface PasswordOnly {}

    public interface PublicView {}

    @Null(groups = {New.class})
    @NotNull(groups = {Modify.class, PasswordOnly.class})
    @Min(value = 1, groups = {Modify.class, PasswordOnly.class})
    @JsonView(PublicView.class)
    private Long id;

    @Null(groups = PasswordOnly.class)
    @NotBlank(groups = {New.class, Modify.class})
    @Size(min = 5, max = 50, groups = {New.class, Modify.class})
    @JsonView(PublicView.class)
    private String FIO;

    @Null(groups = PasswordOnly.class)
    @NotBlank(groups = {New.class, Modify.class})
    @Size(min = 3, max = 50, groups = {New.class, Modify.class})
    @JsonView(PublicView.class)
    private String username;

    @Null(groups = {Modify.class})
    @NotBlank(groups = {New.class, PasswordOnly.class})
    @Size(min = 3, max = 50, groups = {New.class, Modify.class, PasswordOnly.class})
    private String password;

    @Null(groups = {New.class, Modify.class, PasswordOnly.class})
    private LocalDateTime expirationDate;

    @Null(groups = {PasswordOnly.class})
    @JsonView(PublicView.class)
    private User.Role role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFIO() {
        return FIO;
    }

    public void setFIO(String FIO) {
        this.FIO = FIO;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }
}

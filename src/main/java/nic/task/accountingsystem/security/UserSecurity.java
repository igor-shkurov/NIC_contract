package nic.task.accountingsystem.security;

import nic.task.accountingsystem.entities.user.CustomUserDetailsService;
import nic.task.accountingsystem.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("userSecurity")
public class UserSecurity {
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public UserSecurity(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    public boolean isAdmin(Authentication authentication) {
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = userDetailsService.getCurrentUser();
            return user.getRole() == User.Role.ADMIN;
        }
        return false;
    }
}
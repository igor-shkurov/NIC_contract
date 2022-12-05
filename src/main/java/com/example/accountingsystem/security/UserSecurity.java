package com.example.accountingsystem.security;

import com.example.accountingsystem.entities.contract.Contract;
import com.example.accountingsystem.entities.contract.ContractDTO;
import com.example.accountingsystem.entities.contract.ContractService;
import com.example.accountingsystem.entities.user.CustomUserDetailsService;
import com.example.accountingsystem.entities.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component("userSecurity")
public class UserSecurity {
    private final CustomUserDetailsService userDetailsService;
    private final ContractService contractService;

    @Autowired
    public UserSecurity(CustomUserDetailsService userDetailsService, ContractService contractService) {
        this.userDetailsService = userDetailsService;
        this.contractService = contractService;
    }

    public boolean hasUserId(Authentication authentication, Long userId) {
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = userDetailsService.getCurrentUser();
            return user.getId().equals(userId) || user.getRole().equals(User.Role.ADMIN);
        }
        return false;
    }

    public boolean contractBelongsToUser(Authentication authentication, Long contractId) {
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            User user = userDetailsService.getCurrentUser();
            Contract contract = contractService.getContractById(contractId);
            return user.getId().equals(contract.getAssociatedUser().getId());
        }
        return false;
    }
}
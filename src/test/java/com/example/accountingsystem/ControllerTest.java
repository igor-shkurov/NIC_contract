package com.example.accountingsystem;

import com.example.accountingsystem.controllers.ContractController;
import com.example.accountingsystem.controllers.UsersController;
import com.example.accountingsystem.entities.contract.ContractRepo;
import com.example.accountingsystem.entities.contract.ContractService;
import com.example.accountingsystem.entities.counterparty.CounterpartyService;
import com.example.accountingsystem.entities.counterparty_contract.CounterpartyContractService;
import com.example.accountingsystem.entities.stage.StageService;
import com.example.accountingsystem.entities.user.CustomUserDetailsService;
import com.example.accountingsystem.utility.ExcelExportService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(ContractController.class)
@ContextConfiguration(classes = {ContractService.class, StageService.class,  CustomUserDetailsService.class,
        CounterpartyContractService.class,   ExcelExportService.class,
        CounterpartyService.class, ContractRepo.class})
public class ControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testHomePage() throws Exception {
        mockMvc.perform(get("/api/contracts")).andExpect(status().isOk());
    }
}

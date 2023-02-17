package nic.task.accountingsystem;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.assertj.core.api.Assertions.assertThat;
import nic.task.accountingsystem.controllers.UserController;
import org.junit.experimental.results.ResultMatchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

//@RunWith(SpringRunner.class)
//@AutoConfigureMockMvc
//@SpringBootTest
//class AccountingSystemApplicationTests {
//
//    @Autowired
//    private UserController controller;
//
//    @Autowired
////    private MockMvc mvc;
//
//    @Test
//    void contextLoads() {
//    }
//
//    @Test
//    void getRequest() throws Exception {
////        // this doesn't work
////        this.mvc.perform(get("/currencies")).andExpect(status());
////
////        //tried this too
////        RequestBuilder request = get("/currencies");
////        MvcResult result = mvc.perform(request).andReturn();
////        assertThat(result.getResponse().getStatus() == 200);
//
//    }
//}

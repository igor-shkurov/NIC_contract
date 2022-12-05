//package com.example.accountingsystem;
//
//import com.example.accountingsystem.controllers.CounterpartiesController;
//import com.example.accountingsystem.entities.counterparty.CounterpartyService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.Before;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.TestPropertySource;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.setup.MockMvcBuilders;
//import org.springframework.web.context.WebApplicationContext;
//
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest(
//        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
//        classes = Application.class)
//@AutoConfigureMockMvc
//@TestPropertySource(
//        locations = "classpath:application-integrationtest.properties")
//public class WebMockTest {
//
//    @Autowired
//    private MockMvc mvc;
//
//
//    @MockBean
//    CounterpartyService counterpartyService;//the business service
//
//
////    @Before
////    public void setUp() {
////        mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
////    }
//
//    @Test
//    void whenValidInput_thenReturns200() throws Exception {
//        mvc.perform(get("http://localhost:8080/api/counterparties")
//                        .contentType("application/json"))
//                .andExpect(status().isOk());
//    }
//}
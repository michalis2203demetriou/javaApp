package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;

import java.util.Optional;

@WebMvcTest(CustomerController.class)
public class IntegrationTest {
    // In your test class
    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private MockMvc mockMvc;

    @MockBean // Add this annotation to mock CustomerRepository bean
    private CustomerRepository customerRepository;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testGetCustomerById() throws Exception {
        Customer customer = new Customer("Customer Ref", "Customer Name", "42 Main St", "42 Main St", "Oxford", "3", "GB", "OX1 1PT");

        when(customerRepository.findById(any(String.class))).thenReturn(Optional.of(customer));

        // Perform request and assertions
        mockMvc.perform(get("/customer/{customerRef}", "Customer Ref"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customerName").value("Customer Name"))
                .andExpect(jsonPath("$.addressLine1").value("42 Main St"))
                .andExpect(jsonPath("$.addressLine2").value("42 Main St"))
                .andExpect(jsonPath("$.town").value("Oxford"))
                .andExpect(jsonPath("$.county").value("3"))
                .andExpect(jsonPath("$.country").value("GB"))
                .andExpect(jsonPath("$.postcode").value("OX1 1PT"));

    }
}

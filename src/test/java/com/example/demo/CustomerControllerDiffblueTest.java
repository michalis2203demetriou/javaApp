package com.example.demo;

import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ContextConfiguration(classes = {CustomerController.class})
@ExtendWith(SpringExtension.class)
class CustomerControllerDiffblueTest {
    @Autowired
    private CustomerController customerController;

    @MockBean
    private CustomerRepository customerRepository;

    /**
     * Method under test: {@link CustomerController#getCustomerByRef(String)}
     */
    @Test
    void testGetCustomerByRef() throws Exception {
        Optional<Customer> ofResult = Optional
                .of(new Customer("Customer Ref", "Customer Name", "42 Main St", "42 Main St", "Oxford", "3", "GB", "OX1 1PT"));
        when(customerRepository.findById(Mockito.<String>any())).thenReturn(ofResult);
        MockHttpServletRequestBuilder requestBuilder = MockMvcRequestBuilders.get("/customer/{customerRef}",
                "Customer Ref");
        MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content()
                        .string(
                                "{\"customerRef\":\"Customer Ref\",\"customerName\":\"Customer Name\",\"addressLine1\":\"42 Main St\",\"addressLine2\":\"42"
                                        + " Main St\",\"town\":\"Oxford\",\"county\":\"3\",\"country\":\"GB\",\"postcode\":\"OX1 1PT\"}"));
    }

    /**
     * Method under test: {@link CustomerController#saveCustomer(Customer)}
     */
    @Test
    void testSaveCustomer() throws Exception {
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.get("/customer")
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult.content(objectMapper.writeValueAsString(
                new Customer("Customer Ref", "Customer Name", "42 Main St", "42 Main St", "Oxford", "3", "GB", "OX1 1PT")));
        ResultActions actualPerformResult = MockMvcBuilders.standaloneSetup(customerController)
                .build()
                .perform(requestBuilder);
        actualPerformResult.andExpect(MockMvcResultMatchers.status().is(405));
    }
}


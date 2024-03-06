package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ContextConfiguration(classes = {CustomerService.class})
@ExtendWith(SpringExtension.class)
class CustomerServiceDiffblueTest {
    @MockBean
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    /**
     * Method under test: {@link CustomerService#saveCustomer(Customer)}
     */
    @Test
    void testSaveCustomer() {
        Customer customer = new Customer();
        customer.setAddressLine1("42 Main St");
        customer.setAddressLine2("42 Main St");
        customer.setCountry("GB");
        customer.setCounty("3");
        customer.setCustomerName("Customer Name");
        customer.setCustomerRef("Customer Ref");
        customer.setPostcode("OX1 1PT");
        customer.setTown("Oxford");
        when(customerRepository.save(Mockito.<Customer>any())).thenReturn(customer);

        Customer customer2 = new Customer();
        customer2.setAddressLine1("42 Main St");
        customer2.setAddressLine2("42 Main St");
        customer2.setCountry("GB");
        customer2.setCounty("3");
        customer2.setCustomerName("Customer Name");
        customer2.setCustomerRef("Customer Ref");
        customer2.setPostcode("OX1 1PT");
        customer2.setTown("Oxford");
        customerService.saveCustomer(customer2);
        verify(customerRepository).save(Mockito.<Customer>any());
        assertEquals("42 Main St", customer2.getAddressLine1());
        assertEquals("Oxford", customer2.getTown());
        assertEquals("OX1 1PT", customer2.getPostcode());
        assertEquals("Customer Ref", customer2.getCustomerRef());
        assertEquals("Customer Name", customer2.getCustomerName());
        assertEquals("3", customer2.getCounty());
        assertEquals("GB", customer2.getCountry());
        assertEquals("42 Main St", customer2.getAddressLine2());
    }

    /**
     * Method under test: {@link CustomerService#getCustomersByRef(String)}
     */
    @Test
    void testGetCustomersByRef() {
        Customer customer = new Customer();
        customer.setAddressLine1("42 Main St");
        customer.setAddressLine2("42 Main St");
        customer.setCountry("GB");
        customer.setCounty("3");
        customer.setCustomerName("Customer Name");
        customer.setCustomerRef("Customer Ref");
        customer.setPostcode("OX1 1PT");
        customer.setTown("Oxford");
        when(customerRepository.findByCustomerRef(Mockito.<String>any())).thenReturn(customer);
        assertSame(customer, customerService.getCustomersByRef("Customer Ref"));
        verify(customerRepository).findByCustomerRef(Mockito.<String>any());
    }
}


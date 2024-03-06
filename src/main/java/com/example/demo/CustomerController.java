package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/*There is documentation for the whole project in the README file*/
@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping
    public ResponseEntity<String> saveCustomer(@RequestBody Customer customer) {
        customerRepository.save(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body("Customer data saved successfully.");
    }

    @GetMapping("/{customerRef}")
    public ResponseEntity<Customer> getCustomerByRef(@PathVariable String customerRef) {
        Customer customer = customerRepository.findById(customerRef)
                .orElseThrow(() -> new RuntimeException("Customer not found"));
        return ResponseEntity.ok(customer);
    }
}

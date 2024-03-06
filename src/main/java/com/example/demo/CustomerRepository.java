package com.example.demo;

import org.springframework.data.jpa.repository.JpaRepository;

/*There is documentation for the whole project in the README file*/
public interface CustomerRepository extends JpaRepository<Customer, String> {
    Customer findByCustomerRef(String customerRef);
}

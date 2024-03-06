package com.example.demo;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*There is documentation for the whole project in the README file*/

@Service
public class CSVService {

    @Autowired
    private CustomerService customerService;

    @Transactional
    public void readCSVAndSSentToExternalRestApi(String filePath) throws IOException {
        customerService.processCsvFile(filePath);
    }

    @Transactional
    public void readCSVAndSaveToDatabase(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Customer customer = new Customer(
                        data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7]
                );
                customerService.saveCustomer(customer); // Delegate to service
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

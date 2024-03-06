package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*There is documentation for the whole project in the README file*/
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository; // Inject repository


    @Autowired
    private OkHttpClient httpClient; // Inject OkHttpClient for HTTP requests

    public void processCsvFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                Customer customer = new Customer(
                        data[0], data[1], data[2], data[3], data[4], data[5], data[6], data[7]
                );

                // Convert customer to JSON
                String json = new ObjectMapper().writeValueAsString(customer);

                // Send POST request to external API using OkHttp
                String url = "https://example-api-endpoint.com/customers"; // example API URL
                RequestBody body = RequestBody.create(json, MediaType.parse("application/json"));
                Request request = new Request.Builder()
                        .url(url)
                        .post(body)
                        .build();

                try (Response response = httpClient.newCall(request).execute()) {
                    if (!response.isSuccessful()) {
                        throw new IOException("Failed to send customer data to API: " + response.code());
                    }
                }
            }
        } catch (IOException e) {
            // Handle exceptions appropriately, e.g., log errors
            throw e;
        }
    }

    public void saveCustomer(Customer customer) {
        customerRepository.save(customer); // Persist to database
    }

    public Customer getCustomersByRef(String customerRef) {
        return customerRepository.findByCustomerRef(customerRef); // Find by reference
    }
}

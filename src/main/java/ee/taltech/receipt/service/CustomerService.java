package ee.taltech.receipt.service;

import ee.taltech.receipt.model.Customer;
import ee.taltech.receipt.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    public Customer findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("No Customer with ID " + id));
    }

}

package ee.taltech.receipt.service;

import ee.taltech.receipt.model.Customer;
import ee.taltech.receipt.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;
    private PasswordEncoder passwordEncoder;

    public Customer create(Customer customer) {
        if (customer.getId() != null) {
            throw new IllegalArgumentException("Attempting to re-create an existing Customer with ID " + customer.getId());
        }
        if (repository.findAllByEmail(customer.getEmail()).size() > 0) {
            throw new IllegalArgumentException("Attempting to create a Customer with an existing email: " + customer.getEmail());
       }
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        return repository.save(customer);
    }

    public Long getAmount() {
        return repository.count();
    }

    public Customer findById(Long id) {
        return repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("No Customer with ID " + id));
    }

}

package com.liviabraz.eclipseHotel.service;

import com.liviabraz.eclipseHotel.entity.Customer;
import com.liviabraz.eclipseHotel.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository repository;

    public Customer createCustomer(Customer customer) {
        logger.info("Cadastrando cliente: {}", customer);
        return repository.save(customer);
    }

    public List<Customer> getAllCustomers() {
        logger.info("🔍 Buscando todos os clientes...");
        return repository.findAll();
    }

    public Optional<Customer> getCustomerById(Long id) {
        logger.info("🔍 Buscando cliente com ID: {}", id);
        return repository.findById(id);
    }

    public Customer updateCustomer(Long id, Customer customerInf) {
        logger.info("🔍 Buscando cliente com ID: {}", id);

        Customer toUpdate = repository.findById(id).orElseThrow(
                () -> new RuntimeException("Cliente não encontrado."));

        logger.info("Atualizando dados do cliente {}", id);

        toUpdate.setName(customerInf.getName());
        toUpdate.setEmail(customerInf.getEmail());
        toUpdate.setPhone(customerInf.getPhone());
        Customer updatedCustomer = repository.save(toUpdate);

        logger.info("Cliente atualizado: {}", updatedCustomer);

        return updatedCustomer;
    }

    public void deleteCustomer(Long id) {
        logger.info("Deletando cliente com ID: {}", id);
        repository.deleteById(id);
    }

}

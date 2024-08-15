package com.liviabraz.eclipseHotel.controller;

import com.liviabraz.eclipseHotel.dto.CustomerDTO;
import com.liviabraz.eclipseHotel.entity.Customer;
import com.liviabraz.eclipseHotel.entity.Reservation;
import com.liviabraz.eclipseHotel.exception.ResourceNotFoundException;
import com.liviabraz.eclipseHotel.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        Customer createdCustomer = customerService.createCustomer(customer);
        return ResponseEntity.ok(toDTO(createdCustomer));
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        List<CustomerDTO> customerDTOs = customers.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(customerDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
        Customer customer = customerService.getCustomerById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com o id: " + id));
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customer.getId());
        customerDTO.setName(customer.getName());
        customerDTO.setEmail(customer.getEmail());
        customerDTO.setPhone(customer.getPhone());
        customerDTO.setCreateAt(customer.getCreateAt());
        customerDTO.setReservations(customer.getReservations().stream()
                .map(Reservation::getId)
                .collect(Collectors.toList()));
        return ResponseEntity.ok(customerDTO);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setId(id);
        customer.setName(customerDTO.getName());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhone(customerDTO.getPhone());
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        return ResponseEntity.ok(toDTO(updatedCustomer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    private CustomerDTO toDTO(Customer customer) {
        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        dto.setCreateAt(customer.getCreateAt());
        dto.setReservations(customer.getReservations().stream()
                .map(Reservation::getId)
                .collect(Collectors.toList()));
        return dto;
    }
}


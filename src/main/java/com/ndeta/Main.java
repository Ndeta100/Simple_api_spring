package com.ndeta;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@SpringBootApplication
@RestController
@RequestMapping("/api/v1/customers")
public class Main {
    private final CustomerRepository customerRepository;

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args){
        SpringApplication.run(Main.class, args);
    }
//    @GetMapping("/")
//    public  GreetResponse greet(){
//        return new GreetResponse("<h1>Hello</h1>");
//    }
//    record GreetResponse(String greet){}
    @GetMapping
    public List<Customer> getCustomer(){
        return customerRepository.findAll();
    }
    record NewCustomerRequest(String name, String email, Integer age){}
    @PostMapping
    public void addCustomer(@RequestBody NewCustomerRequest request){
        Customer customer=new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setAge(request.age());
        customerRepository.save(customer);
    }
    @DeleteMapping("/{customerId}")
    public void deleteCustomer(@PathVariable("customerId") Integer id){
        customerRepository.deleteById(id);
    }
    @PutMapping("/{customerId}")
    public void updateCustomer(@RequestBody NewCustomerRequest request,
                               @PathVariable("customerId") Integer id) {
        if (customerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "customer not found");
        }
        Customer customer=new Customer();
        customer.setName(request.name());
        customer.setEmail(request.email());
        customer.setAge(request.age());
        customerRepository.save(customer);
    }
}

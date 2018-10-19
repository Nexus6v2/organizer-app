package ru.organizer.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.commons.io.FileUtils;
import ru.organizer.OrganizerApp;
import ru.organizer.entity.Customer;
import ru.organizer.exception.CustomerNotFoundException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static ru.organizer.util.Converter.*;

@AllArgsConstructor
@NoArgsConstructor
public class Organizer {
    @Getter
    private List<Customer> customers;
    @JsonIgnore
    private File file;
    
    @SneakyThrows
    public Organizer(String fileName) {
        String organizerXml = "<Organizer></Organizer>";
        
        try {
            file = new File(fileName);
            organizerXml = FileUtils.readFileToString(file, UTF_8);
        } catch (IOException e) {
            file = Files.createFile(Paths.get(fileName)).toFile();
        }
        
        customers = convertXmlToObject(organizerXml, Organizer.class).getCustomers();
    
        if (customers == null) {
            customers = new ArrayList<>();
        }
    }
    
    public Customer addCustomer(Customer customer) {
        customers.add(customer);
        persist();
        return customer;
    }
    
    public Customer deleteCustomer(String customerId) {
        Customer customer = findCustomerById(customerId);
        customers.removeIf(cus -> cus.getId().equals(customerId));
        persist();
        return customer;
    }
    
    public Customer updateCustomer(Customer newCustomer) {
        String customerId = newCustomer.getId();
        Customer customer = findCustomerById(customerId).update(newCustomer);
        deleteCustomer(customerId);
        addCustomer(customer);
        persist();
        return customer;
    }
    
    public Customer findCustomerByPhone(String phoneNumber) {
        return customers.stream()
                .filter(customer -> customer.getPhones() != null && customer.getPhones().contains(phoneNumber))
                .findAny()
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("Клиент с телефоном \"%s\" не найден", phoneNumber))
                );
    }
    
    private Customer findCustomerById(String customerId) {
        return customers.stream()
                .filter(cus -> cus.getId().equals(customerId))
                .findAny()
                .orElseThrow(() -> new CustomerNotFoundException(
                        String.format("Клиент с id \"%s\" не найден", customerId))
                );
    }
    
    @SneakyThrows
    private void persist() {
        XML_MAPPER.writeValue(file, this);
    }
}

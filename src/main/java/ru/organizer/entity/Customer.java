package ru.organizer.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static ru.organizer.util.Converter.convertMapToObject;

@Data
@Accessors(chain = true)
public class Customer {
    private String id;
    private String name;
    private String organization;
    private String position;
    private String email;
    private List<String> phones;
    
    public Customer() {
        this.id = UUID.randomUUID().toString().split("-")[0];
    }
    
    public static Customer fromMap(Map<String, String> map) {
        List<String> phones = null;
        String phonesString = map.get("phones");
        if (phonesString != null) {
            phones = Arrays.asList(phonesString.split(","));
            map.remove("phones");
        }
        Customer customer = convertMapToObject(map, Customer.class);
        customer.setPhones(phones);
        
        return customer;
    }
    
    public Customer update(Customer customer) {
        Arrays.stream(customer.getClass().getDeclaredFields())
                .filter(field -> {
                    try {
                        return field.get(customer) != null;
                    } catch (IllegalAccessException e) {
                        return false;
                    }
                }).forEach(field -> {
                    try {
                        customer.getClass()
                                .getDeclaredField(field.getName())
                                .set(this, field.get(customer));
                    } catch (ReflectiveOperationException e) {
                    }
                });
        
        return this;
    }
    
    public String toString() {
        return Arrays.stream(getClass().getDeclaredFields())
                .filter(field -> {
                    try {
                        return field.get(this) != null;
                    } catch (IllegalAccessException e) {
                        return false;
                    }
                })
                .map(field -> {
                    String fieldValue;
                    try {
                        fieldValue = field.get(this).toString();
                    } catch (IllegalAccessException e) {
                        fieldValue = "";
                    }
                    return String.format("%s: %s", field.getName(), fieldValue);
                })
                .reduce((customer, field) -> {
                    if (customer == null) {
                        return field;
                    }
                    return customer + ", " + field;
                })
                .orElse("");
    }
    
}

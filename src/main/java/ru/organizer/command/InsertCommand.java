package ru.organizer.command;

import ru.organizer.dao.Organizer;
import ru.organizer.entity.Customer;

public class InsertCommand extends Command {
    
    public InsertCommand(String commandBody) {
        super(commandBody);
    }
    
    @Override
    public String execute(Organizer organizer) {
        Customer customer = Customer.fromMap(arguments);
        Customer createdCustomer = organizer.addCustomer(customer);
        return String.format("Создан клиент: \"%s\"\n", createdCustomer.toString());
    }
}

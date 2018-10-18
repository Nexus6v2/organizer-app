package ru.organizer.command;

import ru.organizer.dao.Organizer;
import ru.organizer.entity.Customer;

public class UpdateCommand extends Command {
    
    public UpdateCommand(String commandBody) {
        super(commandBody);
    }
    
    @Override
    public String execute(Organizer organizer) {
        Customer customer = Customer.fromMap(arguments);
        customer.setId(mainArgument);
        Customer updatedCustomer = organizer.updateCustomer(customer);
        return String.format("Изменён клиент:\n%s", updatedCustomer);
    }
}

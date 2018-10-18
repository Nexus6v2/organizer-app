package ru.organizer.command;

import ru.organizer.dao.Organizer;
import ru.organizer.entity.Customer;

public class DeleteCommand extends Command {
    
    public DeleteCommand(String commandBody) {
        super(commandBody);
    }
    
    @Override
    public String execute(Organizer organizer) {
        Customer removedCustomer = organizer.deleteCustomer(mainArgument);
        return String.format("Удален клиент:\n%s", removedCustomer.toString());
    }
}

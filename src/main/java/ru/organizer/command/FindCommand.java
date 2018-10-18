package ru.organizer.command;

import ru.organizer.dao.Organizer;

public class FindCommand extends Command {
    
    public FindCommand(String commandBody) {
        super(commandBody);
    }
    
    @Override
    public String execute(Organizer organizer) {
        return organizer.findCustomerByPhone(mainArgument).toString();
    }
}

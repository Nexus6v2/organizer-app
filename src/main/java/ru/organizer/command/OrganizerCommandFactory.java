package ru.organizer.command;

import lombok.SneakyThrows;

public class OrganizerCommandFactory {
    
    @SneakyThrows
    public Command createCommand(String commandBody) {
        String commandName = commandBody.split(" ")[0];
    
        return CommandTypes.getCommandByName(commandName)
                .getConstructor(String.class)
                .newInstance(commandBody);
    }
}

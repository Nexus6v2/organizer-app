package ru.organizer.command;

import ru.organizer.dao.Organizer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public abstract class Command {
    protected final Map<String, String> arguments = new HashMap<>();
    protected final String mainArgument;
    
    public Command(String commandBody) {
        String[] args = commandBody.split(" -");
    
        String command = args[0];
        String[] commandParts = command.split(" ");
        mainArgument = commandParts.length == 1 ? null : commandParts[1];
    
        Arrays.asList(args)
                .subList(1, args.length)
                .forEach(argument -> {
                    String[] argumentParts = argument.split(" ");
                    String key = argumentParts[0];
                    String value = argumentParts.length == 1 ? null : argumentParts[1];
                    arguments.put(key, value);
                });
    }
    
    public abstract String execute(Organizer organizer);
    
}
